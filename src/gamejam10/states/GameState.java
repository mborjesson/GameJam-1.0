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

import java.util.*;

import gamejam10.Main;
import gamejam10.ai.BasicAI;
import gamejam10.audio.AudioPlayer;
import gamejam10.character.AIEnemy;
import gamejam10.character.Enemy;
import gamejam10.character.Player;
import gamejam10.level.Level;
import gamejam10.physics.AABoundingRect;
import gamejam10.physics.Physics;
import gamejam10.physics.Tile;

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


	private AudioPlayer musicPlayer;

   
    


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

		musicPlayer = AudioPlayer.getInstance();
		
		camera.setWidth(600);

		level = new Level("map06");
		level.setCamera(camera);

		player = level.getPlayer();

		physics = new Physics(this);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
		
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
		
		level.render(g);
		
		gc.getInput().clearControlPressedRecord();
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
        
        level.update(delta);
        
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
			musicPlayer.playMusic(MusicType.MENU, 1);
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
