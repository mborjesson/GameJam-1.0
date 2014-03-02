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
import gamejam10.shader.*;

import java.nio.*;
import java.util.*;

import org.lwjgl.*;
import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;
import org.newdawn.slick.state.transition.*;

/**
 * 
 * @author gregof
 */
public class GameState extends BasicGameState {

	private Level level = null;
	private Physics physics;
	private Player player;
	private boolean exitGame = false;
	private boolean highlightAllTiles = false;
	private boolean running = false;

	private Camera camera = new Camera();

	private AudioPlayer musicPlayer;

	private int textureId;
	private int offscreenFBO;
	private Shader quadShader;
	private Shader godShader;

	private int godFBO;
	private int godTex;

	@Override
	public int getID() {
		return States.GAME.getID();
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {

		// AIEnemy en = new AIEnemy(200, 200);
		// en.setAI(new BasicAI(en, player, 100, 200));
		// enemies.add(en);

		musicPlayer = AudioPlayer.getInstance();

		// calculate scale
		// we want to show what camera wants to show (in pixels)

		camera.setWidth(Constants.GAME_WIDTH);
		camera.setHeight((float) (camera.getWidth() / Main.getOptions()
				.getAspectRatio()));
		
		System.out.println("Camera: " + camera.getWidth() + " x " + camera.getHeight());
		System.out.println("Aspect: " + Main.getOptions().getAspectRatio());
 
		quadShader = Shader.makeShader("data/shaders/quad.vs.glsl",
				"data/shaders/quad.fs.glsl");
		godShader = Shader.makeShader("data/shaders/god.vs.glsl",
				"data/shaders/god.fs.glsl");

		fboInit();
	}

	private void fboInit() {

		int width = Main.getOptions().getWidth();
		int height = Main.getOptions().getHeight();

		boolean FBOEnabled = GLContext.getCapabilities().GL_EXT_framebuffer_object;

		IntBuffer buffer = ByteBuffer.allocateDirect(2 * 4)
				.order(ByteOrder.nativeOrder()).asIntBuffer(); // allocate a 1
																// int byte
																// buffer
		EXTFramebufferObject.glGenFramebuffersEXT(buffer); // generate
		offscreenFBO = buffer.get();
		godFBO = buffer.get();

		IntBuffer buf = BufferUtils.createIntBuffer(2);
		GL11.glGenTextures(buf);

		textureId = buf.get();
		godTex = buf.get();

		GL11.glPushAttrib(GL11.GL_TEXTURE_BIT);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, godTex);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER,
				GL11.GL_NEAREST);
		GL11.glTexParameterf(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER,
				GL11.GL_NEAREST);

		IntBuffer buftest = BufferUtils.createIntBuffer(1);
		buftest.rewind();
		buftest.put(0xFF00FFFF);
		buftest.rewind();

		GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA, width, height,
				0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);

		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		/*
		 * int depthRenderBufferID =
		 * EXTFramebufferObject.glGenRenderbuffersEXT();
		 * 
		 * EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject.
		 * GL_RENDERBUFFER_EXT, depthRenderBufferID);// bind the depth
		 * renderbuffer
		 * EXTFramebufferObject.glRenderbufferStorageEXT(EXTFramebufferObject
		 * .GL_RENDERBUFFER_EXT, GL14.GL_DEPTH_COMPONENT24, width, height);//
		 * get the data space for it
		 * EXTFramebufferObject.glBindRenderbufferEXT(EXTFramebufferObject
		 * .GL_RENDERBUFFER_EXT, 0);
		 */

		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, offscreenFBO);
		EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
				GL11.GL_TEXTURE_2D, textureId, 0);
		// EXTFramebufferObject.glFramebufferRenderbufferEXT(EXTFramebufferObject.GL_FRAMEBUFFER_EXT,EXTFramebufferObject.GL_DEPTH_ATTACHMENT_EXT,EXTFramebufferObject.GL_RENDERBUFFER_EXT,
		// depthRenderBufferID); // bind it to the renderbuffer
		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, godFBO);
		EXTFramebufferObject.glFramebufferTexture2DEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT,
				EXTFramebufferObject.GL_COLOR_ATTACHMENT0_EXT,
				GL11.GL_TEXTURE_2D, godTex, 0);
		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

		System.out.println("FBO: " + FBOEnabled + " " + offscreenFBO + " "
				+ textureId);

		GL11.glPopAttrib();

		/*
		 * RenderTexture tex = new RenderTexture(false, true, false, false,
		 * RenderTexture.RENDER_TEXTURE_2D, 1);
		 * 
		 * try { microBuffer = new Pbuffer(128, 128, new PixelFormat(), tex,
		 * null); } catch (LWJGLException e) { e.printStackTrace(); }
		 */

		GL11.glPopAttrib();

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {

		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, offscreenFBO);

		// GL11.glEnable(GL11.GL_DEPTH_TEST);

		g.pushTransform();

		// GL11.glEnable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_BLEND);
		// GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		//g.setBackground(new Color(level.getSun().getSunColor(), level.getSun().getSunColor(), level.getSun().getSunColor()));
		g.setBackground( new Color(0.5f, 0.5f, 0.5f, 1.0f) );
		g.clear();

		doRender(gc, sbg, g);

		g.popTransform();

		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, godFBO);

		g.pushTransform();

		GL11.glDisable(GL11.GL_BLEND);

		renderQuad2(gc, sbg, g);

		g.popTransform();

		EXTFramebufferObject.glBindFramebufferEXT(
				EXTFramebufferObject.GL_FRAMEBUFFER_EXT, 0);

		g.setBackground(new Color(1.0f, 1.0f, 1.0f, 1.0f));
		g.clear();

		g.pushTransform();

		// GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDisable(GL11.GL_BLEND);

		renderQuad1(gc, sbg, g);

		g.popTransform();
		
		
		GL11.glEnable(GL11.GL_BLEND);

		gc.getInput().clearControlPressedRecord();

		Shader.forceFixedShader();
	}

	private void doRender(GameContainer gc, StateBasedGame sbg, Graphics g) {

		float scaleX = ( Main.getOptions().getWidth() ) / camera.getWidth();
		float scaleY = ( Main.getOptions().getHeight() ) / camera.getHeight();
		g.scale(scaleX, scaleY);

		camera.setX(player.getX());
		camera.setY(player.getY());

		level.render(g);

	}

	private void renderQuad1(GameContainer gc, StateBasedGame sbg, Graphics g) {

		int width = Main.getOptions().getWidth();
		int height = Main.getOptions().getHeight();

		quadShader.startShader();
		quadShader.setUniformIntVariable("texture_0", 0);
		quadShader.setUniformIntVariable("texture_1", 1);
		quadShader.setUniformFloatVariable("sunFactor", level.getSun().getSunColor() );

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, godTex);

		g.scale(width - 1, height - 1);

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

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL13.glActiveTexture(GL13.GL_TEXTURE1);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, 0);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);

	}

	private void renderQuad2(GameContainer gc, StateBasedGame sbg, Graphics g) {

		int width = Main.getOptions().getWidth();
		int height = Main.getOptions().getHeight();

		godShader.startShader();
		godShader.setUniformFloatVariable("exposure", 0.0034f);
		godShader.setUniformFloatVariable("decay", 1.0f);
		godShader.setUniformFloatVariable("density", 0.84f);
		godShader.setUniformFloatVariable("weight", 2.65f);
		godShader.setUniformFloatVariable("lightPositionOnScreen", level
				.getSun().getSunPositionX(), level.getSun().getSunPositionY());
		// godShader.setUniformFloatVariable("test", 1.0f, 0.0f, 0.0f, 1.0f);

		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, textureId);

		g.scale(width - 1, height - 1);

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
			if (c instanceof AIEnemy) {
				AIEnemy e = (AIEnemy) c;
				e.updateAI(delta);
			}
		}

		level.update(delta);

		physics.handlePhysics(sbg, level, delta);

	}

	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {

		// we can both use the WASD or arrow keys to move around, obviously we
		// can't move both left and right simultaneously
		if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)
				|| isControllerPressed("left", i)) {
			player.moveLeft(delta);
			player.setMoving(true);
		} else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)
				|| isControllerPressed("right", i)) {
			player.moveRight(delta);
			player.setMoving(true);
		} else {
			player.setMoving(false);
		}

		if (i.isKeyDown(Input.KEY_UP) || i.isKeyDown(Input.KEY_W)
				|| isControllerPressed("a", i)) {
			player.jump();
		} else if (i.isKeyPressed(Input.KEY_J)) {
			player.setHighlight(!player.isHighlight());
		} else if (i.isKeyPressed(Input.KEY_K)) {
			this.highlightAllTiles = !this.highlightAllTiles;
		}

		if (i.isKeyPressed(Input.KEY_ESCAPE) || exitGame) {
			i.clearControlPressedRecord();
			i.clearKeyPressedRecord();
			exitGame = false;
			musicPlayer.playMusic(MusicType.MENU, 1);
			game.enterState(States.MENU.getID(), new FadeOutTransition(
					Color.black, 50), new FadeInTransition(Color.black, 50));
		}
	}

	private boolean isControllerPressed(String btn, Input i) {
		for (int c = 0; c < i.getControllerCount(); c++) {
			if (btn == "left" && i.isControllerLeft(c))
				return true;
			else if (btn == "right" && i.isControllerRight(c))
				return true;
			else if (btn == "a" && i.isButton1Pressed(c))
				return true;
		}

		return false;
	}

	public void controllerButtonPressed(int c, int b) {
		if (b == 8)
			exitGame = true;
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

	public void initializeLevel(String level) throws SlickException {
		this.level = new Level(level);
		this.level.setCamera(camera);

		player = this.level.getPlayer();

		physics = new Physics(this);

		running = true;
	}

	public void setRunning(boolean running) {
		this.running = running;
	}
	
	public boolean isRunning() {
		return running;
	}
}
