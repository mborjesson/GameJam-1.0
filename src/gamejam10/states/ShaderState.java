package gamejam10.states;

import gamejam10.shader.*;

import org.lwjgl.opengl.*;
import org.newdawn.slick.*;
import org.newdawn.slick.state.*;

public class ShaderState extends BasicGameState {

	private Shader testShader;
	
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		
		System.out.println("init");
		
		testShader = Shader.makeShader("data/shaders/test.vs.glsl", "data/shaders/test.fs.glsl");
		
	}

	@Override
	public void render(GameContainer arg0, StateBasedGame arg1, Graphics g) throws SlickException {
		
		//g.scale(Main.WINDOW_WIDTH, Main.WINDOW_HEIGTH);
		
		testShader.startShader();
		
		GL11.glBegin(GL11.GL_QUADS);
		
        	GL11.glVertex3f(0, 0, 0);
        	GL11.glVertex3f(0, 1, 0);
        	GL11.glVertex3f(1, 1, 0);
        	GL11.glVertex3f(1, 0, 0);
		
		GL11.glEnd();
		
	}

	@Override
	public void update(GameContainer arg0, StateBasedGame arg1, int arg2) throws SlickException {

		
	}

	@Override
	public int getID() {
		 return 0;//Main.STATE_GAME_OVER;
	}

}
