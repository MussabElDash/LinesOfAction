package users;

import utilities.Pair;
import ai.AI;

public class AIUser extends User {
	private AI theAI;

	public AIUser(int intelligence) {
		theAI = new AI(intelligence);
	}

	@Override
	public void play() {
		Pair p = theAI.nextMove(getBoardLayout().getBoard());
		getBoardLayout().move(p.getFirstPair(), p.getSecondPair());
	}

}
