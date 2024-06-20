package FSM;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JPanel;

import FlatLand.ViewableFlatLand;
import FlatLand.Physics.Physics;
import Playable.LevelPlayer;
import Playable.LevelZero;
import TheGame.Board;
import View.GameScreen;
import flatLand.trainingGround.theStudio.Camera;

public class GameInstance {

	private ArrayList<LevelI> level = new ArrayList<LevelI>();
	private LevelI currentLevel = null;
	private int levelCounter = 0;

	private ViewableFlatLand flatLandLE;
	private Board board;
	private GameScreen graphics;

	public GameInstance(ViewableFlatLand flatLandLE,GameScreen canvasLE, Board board) {
		this.flatLandLE = flatLandLE;
		this.graphics = canvasLE;
		
		this.board = board;
		
		LevelZero lzero = new LevelZero();
		level.add(lzero);
		
		
		
		
	}

	public void start() {

		
		while (levelCounter < level.size()) {

			loadLevel();
			currentLevel.play(flatLandLE, board,graphics);
			levelCounter++;
		}
	}

	private void loadLevel() {
		currentLevel = level.get(levelCounter);
	}

}
