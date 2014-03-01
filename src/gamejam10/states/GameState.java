/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import gamejam10.Main;
import gamejam10.audio.MusicPlayer;
import gamejam10.character.Enemy;
import gamejam10.character.Player;
import gamejam10.enums.States;
import gamejam10.level.Level;
import gamejam10.physics.AABoundingRect;
import gamejam10.physics.Physics;
import gamejam10.physics.Tile;

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

    private int mapX = 10;
    private int mapY = 0;
    private Level level = null;
    private Physics physics;
    private Player player;
    private boolean higLightPlayer = false;
    private boolean highlightAllTiles = false;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    
    private MusicPlayer musicPlayer;
            
    @Override
    public int getID() {
        return States.GAME.getID();
    }

   
    
    @Override
      public void init(GameContainer container, StateBasedGame sbg) throws SlickException {
 
       Enemy en = new Enemy(300, 250);
       enemies.add(en);
        
        musicPlayer = MusicPlayer.getInstance();
        
       //at the start of the game we don't have a player yet
        player = new Player(128,250);
 
        //once we initialize our level, we want to load the right level
        level = new Level("", player, enemies);
 
        //and we create a controller, for now we use the MouseAndKeyBoardPlayerController
 //       playerController = new MouseAndKeyBoardPlayerController(player);
 
        physics = new Physics(this);
    }

    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics grphcs) throws SlickException {
        //level.render(mapX, mapY);
        level.render();

        if (highlightAllTiles) {
            highlightAllTiles(gc, sbg, grphcs);
        }

        if (higLightPlayer) {
            highlightPlayer(gc, sbg, grphcs);
        }
        
        
//        Iterator enemyIterator = enemies.iterator();
//        while (enemyIterator.hasNext()) {
//            Enemy enemy = (Enemy)enemyIterator.next();
//
//            enemy.render(mapX, mapX);
//            
//        }

    }

 
   

    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {

      
        handleKeyboardInput(gc.getInput(), delta, sbg);

        physics.handlePhysics(level, delta);
      
    }


    private void handleKeyboardInput(Input i, int delta, StateBasedGame game) {

        //we can both use the WASD or arrow keys to move around, obviously we can't move both left and right simultaneously
        if (i.isKeyDown(Input.KEY_A) || i.isKeyDown(Input.KEY_LEFT)) {
            player.moveLeft(delta);
            player.setMoving(true);
        } else if (i.isKeyDown(Input.KEY_D) || i.isKeyDown(Input.KEY_RIGHT)) {
            player.moveRight(delta);
            player.setMoving(true);
        }  else {
             player.setMoving(false);
        }
        
        if (i.isKeyDown(Input.KEY_UP) || i.isKeyDown(Input.KEY_W)) {
            player.jump();
        }else  if (i.isKeyPressed(Input.KEY_J)) {
             this.higLightPlayer = !this.higLightPlayer;
        } else if (i.isKeyPressed(Input.KEY_K)) {
            this.highlightAllTiles = !this.highlightAllTiles;
        } 
        
        if (i.isKeyPressed(Input.KEY_ESCAPE)) {
            musicPlayer.playMenuMusic();
            game.enterState(States.MENU.getID(), new FadeOutTransition(Color.black, 50), new FadeInTransition(Color.black, 50));
        } 
    }
    
     /**
     *
     * @param gc
     * @param sbg
     * @param g
     * @param tile
     */
    private void highlightTile(GameContainer gc, StateBasedGame sbg, Graphics g, Tile tile) {

        g.setColor(Color.white);
        g.drawRect(tile.getX() * 32, tile.getY() * 32, 32, 32);

    }

    /**
     *
     * @param gc
     * @param sbg
     * @param g
     * @param onlySolid
     */
    private void highlightAllTiles(GameContainer gc, StateBasedGame sbg, Graphics g) {

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
    
         
        
    private void highlightPlayer(GameContainer gc, StateBasedGame sbg, Graphics g) {

        g.setColor(Color.white);
//        System.out.println("player.getX(): " + player.getX());
//        System.out.println("player.getY(): " + player.getY());
        
        AABoundingRect aab = (AABoundingRect)player.getBoundingShape();
        float width = aab.getWidth();
        float height = aab.getHeight();
        
        g.drawRect(player.getX(), player.getY(), width, height);
        
    }

   

    /**
     * @return the level
     */
    public Level getLevel() {
        return level;
    }

    /**
     * @param level the level to set
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
     * @param player the player to set
     */
    public void setPlayer(Player player) {
        this.player = player;
    }
    
    
}
