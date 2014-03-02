package gamejam10;

import org.newdawn.slick.*;
import org.newdawn.slick.state.transition.*;

public class Constants {
    public static final String GAME_NAME = "Luleå GameJam 1.0";

    public static Transition getDefaultLeaveTransition() {
    	return new FadeOutTransition(Color.black, 150);
    }
    
    public static Transition getDefaultEnterTransition() {
    	return new FadeInTransition(Color.black, 150);
    }

}
