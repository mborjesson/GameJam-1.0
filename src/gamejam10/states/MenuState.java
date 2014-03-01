/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import gamejam10.Main;
import gamejam10.audio.MusicPlayer;
import gamejam10.enums.States;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.state.transition.FadeInTransition;
import org.newdawn.slick.state.transition.FadeOutTransition;

    
/**
 *
 * @author gregof
 */
public class MenuState extends BasicGameState {
    
    private MusicPlayer musicPlayer;
    private Image menuImage;
    private boolean startGame = false;
    
    @Override
    public int getID() {
        return States.MENU.getID();
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        musicPlayer = MusicPlayer.getInstance();
        musicPlayer.playMenuMusic();
        menuImage = new org.newdawn.slick.Image("data/images/mantis.jpg");
        
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
		g.setBackground(Color.black);
		g.clear();

		g.setColor(Color.gray);
        g.drawString("PLAY", 40, 20);
        g.setColor(Color.white);
        g.drawString("EXIT", 40, 40);
        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_SPACE) || startGame) {
            musicPlayer.playGameMusic();
            startGame = false;
            game.enterState(States.GAME.getID(), new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500) );
        } 
        
    }
    
    public void controllerButtonPressed(int controller, int button)
    {	
    	if(button == 8)
    	{
    		startGame = true;
    	}
    }
    
    //this method is overriden from basicgamestate and will trigger once you press any key on your keyboard
    public void keyPressed(int key, char code){
        //if the key is escape, close our application
        if(key == Input.KEY_ESCAPE){
            System.exit(0);
        }
    }
}
