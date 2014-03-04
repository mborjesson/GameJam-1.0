package gamejam10.ai;

import gamejam10.ai.actions.*;
import gamejam10.character.Character;

import java.util.*;

public class BasicAI {

	private Collection<AIAction> aiActions = new ArrayList<AIAction>();
	
	public BasicAI() {

	}
	
	public void addAIAction(AIAction aiAction) {
		aiActions.add(aiAction);
	}
	
	public void update(Character character, double dt) {
		
		for (AIAction aiAction : aiActions) {
			aiAction.doAction(dt);
		}
		
	}
	
}
