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
import gamejam10.audio.MusicPlayer;
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


	private MusicPlayer musicPlayer;

   
    


	@Override
	public int getID() {
		return States.GAME.getID();
	}

	@Override
	public void init(GameContainer container, StateBasedGame sbg)
			throws SlickException {

        Enemy en = new Enemy(300, 250);
        en.setAI(new BasicAI(en, player));
        enemies.add(en);

		musicPlayer = MusicPlayer.getInstance();
		
		camera.setWidth(1500);

		level = new Level("map06");
		level.setCamera(camera);

		player = level.getPlayer();

		physics = new Physics(this);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException {
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
	}


	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException {

		handleKeyboardInput(gc.getInput(), delta, sbg);

        for (gamejam10.character.Character c : level.getCharacters()) {
        	if ( c instanceof Enemy ) {
        		Enemy e = (Enemy)c;
        		e.updateAI(delta);
        	}
		}
        
        
        physics.handlePhysics(level, delta);

	}

	private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {

		// we can both use the WASD or arrow keys to move around, obviously we
		// can't move both left and right simultaneously
		if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT) || i.isControllerLeft(2)) {
			player.moveLeft(delta);
			player.setMoving(true);
		} else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT) || i.isControllerRight(2)) {
			player.moveRight(delta);
			player.setMoving(true);
		} else {
			player.setMoving(false);
		}

		if (i.isKeyDown(Input.KEY_UP) || i.isKeyDown(Input.KEY_W) || i.isButton1Pressed(2)) {
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
