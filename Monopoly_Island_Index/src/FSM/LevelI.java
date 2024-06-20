package FSM;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JPanel;

import FlatLand.ViewableFlatLand;
import TheGame.Board;
import View.GameScreen;

public interface LevelI {

	boolean play(ViewableFlatLand flatLandLE, Board board, GameScreen canvasLE);

}
