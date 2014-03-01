/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package gamejam10.states;


import gamejam10.Main;
import gamejam10.audio.MusicPlayer;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
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
    Music m, m2;

    @Override
    public int getID() {
        return Main.STATE_MENU;
    }

    @Override
    public void init(GameContainer container, StateBasedGame game) throws SlickException {
        
        musicPlayer = MusicPlayer.getInstance();
        //musicPlayer.playMenuMusic();
        m = new Music("/data/music/menu.ogg");
        m2 = new Music("/data/music/game.ogg");
        m.loop();
        menuImage = new org.newdawn.slick.Image("data/images/mantis.jpg");
        
    }

    @Override
    public void render(GameContainer container, StateBasedGame game, Graphics g) throws SlickException {
        
        g.drawImage(menuImage, 170,0);
        
        g.drawString("Click space to enter Game or esc to quit.!!! ", 40, 230);
        g.drawString("Or press escape to quit. ", 40, 260);
        
    }

    @Override
    public void update(GameContainer container, StateBasedGame game, int delta) throws SlickException {
        Input input = container.getInput();
        if (input.isKeyPressed(Input.KEY_SPACE) || startGame) {
            //musicPlayer.playGameMusic();
            startGame = false;
            m.stop();
            m2.loop();
            game.enterState(Main.STATE_GAME, new FadeOutTransition(Color.black, 500), new FadeInTransition(Color.black, 500) );
        } 
        
    }
    
    public void controllerButtonPressed(int controller, int button)
    {
    	System.out.println(controller + " pressed " + button);
    	
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
