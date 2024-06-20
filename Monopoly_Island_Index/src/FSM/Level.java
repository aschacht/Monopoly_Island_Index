package FSM;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import Actions.RoundAction;
import FlatLand.ViewableFlatLand;
import FlatLand.Physics.Physics;
import TheGame.Board;
import View.GameScreen;
import flatLand.trainingGround.theStudio.Camera;

public abstract class Level {

	private static Camera theEyeInTheSky;
	private static int cameraWidth = 1300;
	private static int cameraHeight = 1300;
	private static int flatLandWidth = 1300;
	private static int flatLandHeight = 1300;
	private static int canvasWidth = 1300;
	private static int canvasHeight = 1300;
	private static int oncanvasX = 0;
	private static int oncanvasY = 0;

	public LevelStory levelStory;

	public boolean play(ViewableFlatLand flatLandLE, Board board, GameScreen panel) {

		flatLandLE.setFlatLandColor(Color.green);
		try {
			while (true) {
				long start = System.currentTimeMillis();
				panel.getGraphics();
				
				flatLandLE.update();


			
				
				
				panel.repaint();
				long end = System.currentTimeMillis();

				long length = end - start;
				if (16 - (length / 1000) > 0)
					Thread.sleep(16 - (length / 1000));

				if (board.actionResolved())
					board.collectActions();
				else {
					board.executeActions();

				}

			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		return false;
	}
}
