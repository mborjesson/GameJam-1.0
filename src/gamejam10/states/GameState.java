/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;

import gamejam10.*;
import gamejam10.audio.*;
import gamejam10.camera.*;
import gamejam10.character.*;
import gamejam10.enums.*;
import gamejam10.level.*;
import gamejam10.physics.*;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.*;

import gamejam10.Main;
import gamejam10.ai.BasicAI;
import gamejam10.audio.MusicPlayer;
import gamejam10.character.AIEnemy;
import gamejam10.character.Enemy;
import gamejam10.character.Player;
import gamejam10.level.Level;
import gamejam10.physics.AABoundingRect;
import gamejam10.physics.Physics;
import gamejam10.physics.Tile;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GLContext;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Logger;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;
import org.newdawn.slick.state.transition.RotateTransition;

import shader.Shader;


/**
 * 
 * @author gregof
 */
public class GameState extends BasicGameState {

	private Level level = null;
	private Physics physics;
	private Player player;
	private boolean higLightPlayer = false;
	private boolean highlightAllTiles = false;
	private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
	
	private Camera camera = new Camera();


	private MusicPlayer musicPlayer;

	private int textureId;
   private int offscreenFBO;
   private Shader quadShader;
    


	@Override
	public int getID() {
		return States.GAME.getID();
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {

//		AIEnemy en = new AIEnemy(200, 200);
//		en.setAI(new BasicAI(en, player, 100, 200));
//		enemies.add(en);

		musicPlayer = MusicPlayer.getInstance();
		
		camera.setWidth(1500);

		level = new Level("map06");
		level.setCamera(camera);

		player = level.getPlayer();

		physics = new Physics(this);
		
		quadShader = Shader.makeShader("data/shaders/quad.vs.glsl", "data/shaders/quad.fs.glsl");
		
		fboInit();
		
	}
	
	private void fboInit() {
		
		int width = Main.getOptions().getWidth();
		int height = Main.getOptions().getHeight();
		
		boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object;
		
		IntBuffer buffer = ByteBuffer.allocateDirect(1*4).order(ByteOrder.nativeOrder()).asIntBuffer(); // allocate a 1 int byte buffer
		EXTFramebufferObject.glGenFramebuffersEXT( buffer ); // generate
		offscreenFBO = buffer.get();
		

		IntBuffer buf = BufferUtils.createIntBuffer(1);
		GL11.glGenTextures(buf);
		
		textureId = buf.get(0);
		
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);

		GL11.glPixelStorei(GL11.GL_UNPACK_ALIGNMENT, 1);
		
		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height ,0,GL11.GL_RGBA,GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null );

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);
		
		
		int depthRenderBufferID = EXTFramebufferObject.glGenRenderbuffersEXT();
		
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthRenderBufferID);// bind the depth renderbuffer
		EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height);// get the data space for it
		
		EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.GL_RENDERBUFFER_EXT, 0);
		
		
		
		EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, offscreenFBO );
		EXTFramebufferObject.glFramebufferTexture2DEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT, GL11.GL_TEXTURE_2D, textureId, 0);
		//EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,EXTFramebufferObject.GL_RENDERBUFFER_EXT, depthRenderBufferID); // bind it to the renderbuffer
		EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0 );
		
		
		System.out.println("FBO: " + FBOEnabled + " " + offscreenFBO + " " + textureId + " " + depthRenderBufferID);
		
		
		/*
		RenderTexture tex = new RenderTexture(false, true, false, false, RenderTexture.RENDER_TEXTURE_2D, 1);
		
		try {
			microBuffer = new Pbuffer(128, 128, new PixelFormat(), tex, null);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		*/
		
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
		EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, offscreenFBO );
		
		g.pushTransform();
		
		doRender(gc, sbg, g);
		
		g.popTransform();

		EXTFramebufferObject.glBindFramebufferEXT( EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0 );
		
		g.pushTransform();
		
		//doRender(gc, sbg, g);
		
		renderQuad(gc, sbg, g);
		
		g.popTransform();
		
	}

	
	private void doRender(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		g.setBackground(Color.white);
		g.clear();
		
		// calculate scale
		// we want to show what camera wants to show (in pixels)
		float width = (float)camera.getWidth();
		float height = (float)(camera.getWidth()/Main.getOptions().getAspectRatio());
		camera.setHeight(height);
		float scaleX = Main.getOptions().getWidth()/width;
		float scaleY = Main.getOptions().getHeight()/height;
		g.scale(scaleX, scaleY);
		
		camera.setX(-player.getX());
		camera.setY(-player.getY());
		
		g.translate(camera.getX()+width*0.5f, camera.getY()+height*0.5f);
		
		level.render(g);
		
		gc.getInput().clearControlPressedRecord();
		
	}
	
	private void renderQuad(GameContainer gc, StateBasedGame sbg, Graphics g) {
		
		int width = Main.getOptions().getWidth();
		int height = Main.getOptions().getHeight();
		
		quadShader.startShader();

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		
		g.setBackground(Color.white);
		g.clear();
		
		g.scale(width, height);
		
		GL11.glBegin(GL11.GL_QUADS);

    		GL11.glTexCoord2f(0, 1);
	    	GL11.glVertex3f(0, 0, 0);
	    	GL11.glTexCoord2f(0, 0);
	    	GL11.glVertex3f(0, 1, 0);
	    	GL11.glTexCoord2f(1, 0);
	    	GL11.glVertex3f(1, 1, 0);
	    	GL11.glTexCoord2f(1, 1);
	    	GL11.glVertex3f(1, 0, 0);
		
		GL11.glEnd();
		
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		handleKeyboardInput(gc.getInput(), delta, sbg);

        for (gamejam10.character.Character c : level.getCharacters()) {
        	if ( c instanceof AIEnemy ) {
        		AIEnemy e = (AIEnemy)c;
        		e.updateAI(delta);
        	}
		}
        
        
        physics.handlePhysics(level, delta);

	}

	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {

		// we can both use the WASD or arrow keys to move around, obviously we
		// can't move both left and right simultaneously
		if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT) || isControllerPressed("left", i)) {
			player.moveLeft(delta);
			player.setMoving(true);
		} else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT) || isControllerPressed("right", i)) {
			player.moveRight(delta);
			player.setMoving(true);
		} else {
			player.setMoving(false);
		}

		if (i.isKeyDown(Input.KEY_UP) || i.isKeyDown(Input.KEY_W) || isControllerPressed("a", i)) {
			player.jump();
		} else if (i.isKeyPressed(Input.KEY_J)) {
			player.setHighlight(!player.isHighlight());
		} else if (i.isKeyPressed(Input.KEY_K)) {
			this.highlightAllTiles = !this.highlightAllTiles;
		}

		if (i.isKeyPressed(Input.KEY_ESCAPE)) {
			musicPlayer.playMenuMusic();
			game.enterState(States.MENU.getID(), new FadeOutTransition(
					Color.black, 50), new FadeInTransition(Color.black, 50));
		}
	}
	
	private boolean isControllerPressed(String btn, Input i)
	{
		for(int c = 0; c < i.getControllerCount(); c++)
		{
			if (btn == "left" && i.isControllerLeft(c))
				return true;
			else if (btn == "right" && i.isControllerRight(c))
				return true;
			else if (btn == "a" && i.isButton1Pressed(c))
				return true;
		}
		
		return false;
	}

	/**
	 * 
	 * @param gc
	 * @param sbg
	 * @param g
	 * @param onlySolid
	 */
	private void highlightAllTiles(GameContainer gc, StateBasedGame sbg,
			Graphics g) {

		Tile tiles[][] = level.getTiles();
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[0].length; y++) {
				if (tiles[x][y].isSolid()) {
					g.setColor(Color.red);
					g.drawRect(x * 32, y * 32, 32, 32);
				} else {
					g.setColor(Color.yellow);
					g.drawRect(x * 32, y * 32, 32, 32);
				}

			}
		}
	}

	/**
	 * @return the level
	 */
	public Level getLevel() {
		return level;
	}

	/**
	 * @param level
	 *            the level to set
	 */
	public void setLevel(Level level) {
		this.level = level;
	}

	/**
	 * @return the player
	 */
	public Player getPlayer() {
		return player;
	}

	/**
	 * @param player
	 *            the player to set
	 */
	public void setPlayer(Player player) {
		this.player = player;
	}

}
