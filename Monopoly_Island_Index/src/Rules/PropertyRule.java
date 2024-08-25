package Rules;

import java.awt.Color;

import FlatLander.FlatLandFacebook;
import FlatLander.TypeOfEntity;
import TheGame.Board;
import TheGame.BoardSpace;
import XMLLoader.FlatLanderWrper;
import XMLLoader.PlayerWrper;
import flatLand.trainingGround.Sprites.TerminalSprite;
import theStart.thePeople.FlatLanderFaceBook;

public class PropertyRule implements TheRules {

	
	


	private FlatLanderWrper terminal;

	public PropertyRule() {

	}
	
	public void check(PlayerWrper player,BoardSpace boardSpace,Board board) {
		if(!board.isSpaceOwned(boardSpace.getStatus())) {
			System.out.println("Display buy dialog");
			
			
			TerminalSprite terminalSprite = new TerminalSprite("/home/wes/git/repository4/Monopoly_Island_Index/res/charmap-oldschool_white.png", 32, 10, 100);
			terminal = new FlatLanderWrper(200, 200, "terminal", 1.2, true, true, TypeOfEntity.TERRAIN, Color.BLACK);
			terminal.setSprite(terminalSprite);
			
			Object theRequestie = new Object();
			while (!FlatLandFacebook.getInstance().requestToken(theRequestie)) {
			}
			FlatLandFacebook.getInstance().add(terminal, theRequestie);
			FlatLandFacebook.getInstance().releaseToken(theRequestie);

		}else if(board.isSpaceOwned(boardSpace.getStatus())&&board.isSpaceOwnedByPlayer(boardSpace.getStatus(),player)) {
			System.out.println("display Morgatage dialog");
		}else if(board.isSpaceOwned(boardSpace.getStatus())&&!board.isSpaceOwnedByPlayer(boardSpace.getStatus(),player)) {
			System.out.println("display Pay dialog");
		}
	}
	
}
