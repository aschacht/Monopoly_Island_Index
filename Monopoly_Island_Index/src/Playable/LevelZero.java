package Playable;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

import FSM.Level;
import FSM.LevelI;
import FlatLand.ViewableFlatLand;
import FlatLander.FlatLandFacebook;
import Player.Player;
import TheGame.Board;
import View.GameScreen;
import XMLLoader.PlayerWrper;

public class LevelZero extends Level implements LevelI {
	PlayerWrper player= null;
	private PlayerWrper player1;

	
	
	public LevelZero() {
		player=new PlayerWrper(Color.RED,0,0,"Player0",1.0,true);
		FlatLandFacebook.getInstance().requestToken(this);
		FlatLandFacebook.getInstance().add(player,this);
		FlatLandFacebook.getInstance().releaseToken(this);
		player1=new PlayerWrper(Color.RED,0,0,"Player1",1.0,true);
		FlatLandFacebook.getInstance().requestToken(this);
		FlatLandFacebook.getInstance().add(player1,this);
		FlatLandFacebook.getInstance().releaseToken(this);
		
	}
	
	
	
	
	
	@Override
	public boolean play(ViewableFlatLand flatLandLE, Board board, GameScreen panel) {
		board.addPlayer(player);
		board.addPlayer(player1);
		super.play(flatLandLE,board,panel);
	
		
		
		
		return false;
	}

}
