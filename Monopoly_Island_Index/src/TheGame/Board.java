package TheGame;


import java.awt.Graphics;

import java.util.ArrayList;
import java.util.HashMap;

import Actions.Move;
import Actions.RoundAction;
import Math.*;
import XMLLoader.PlayerWrper;

public class Board {
	int width = 1100;
	int height = 1100;
	static ArrayList<BoardSpace> gameSpaces = new ArrayList<>();
	ArrayList<SpaceConnections> connections = new ArrayList<>();
	BoardRules rules = new BoardRules();
	RuleInterpreter rI = new RuleInterpreter(rules);

	ArrayList<PlayerWrper> players = new ArrayList<>();

	HashMap<PlayerWrper, PlayerSpaces> portfolios = new HashMap<>();
	HashMap<PlayerWrper, PlayerChance> chance = new HashMap<>();
	HashMap<PlayerWrper, PlayerCommieChest> comunism = new HashMap<>();

	HashMap<PlayerWrper, ArrayList<BoardSpace>> currentPositions = new HashMap<>();
	HashMap<PlayerWrper, Status> statusus = new HashMap<>();
	ArrayList<RoundAction> roundActionHistory = new ArrayList<>();
	ArrayList<RoundAction> actionsToTakeThisRound = new ArrayList<>();
	ArrayList<HashMap<PlayerWrper, PlayerPositions>> posHist = new ArrayList<>();
	private GameDice instance;

	public Board() {

		instance = GameDice.getInstance();

	}

	public void updatePlayerPositionsBasedOnBoardRules() {

	}

	public void updatePlayerStatus(PlayerWrper p, Status s) {
		if (players.contains(p))
			statusus.replace(p, s);
	}

	public void addToPlayerSpacePortfolio(PlayerWrper p, Space s) {
		if (players.contains(p)) {
			PlayerSpaces playerSpaces = portfolios.get(p);
			playerSpaces.addSpace(s);
			portfolios.replace(p, playerSpaces);
		}
	}

	public void addToPlayerChance(PlayerWrper p, Chance c) {
		if (players.contains(p)) {
			PlayerChance playerChance = chance.get(p);
			playerChance.addChance(c);
			chance.replace(p, playerChance);
		}
	}

	public void addToTheDelinquencyOfTheYouth(PlayerWrper p, CommieChest c) {
		if (players.contains(p)) {
			PlayerCommieChest playerCommieChest = comunism.get(p);
			playerCommieChest.addToNothing(c);
			System.out.println("hey");
			comunism.replace(p, playerCommieChest);
		}

	}

	public void addToBoardSpace(BoardSpace boardSpace) {
		gameSpaces.add(boardSpace);
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void draw(Graphics frame) {
		
		for (BoardSpace boardSpace : gameSpaces) {
			boardSpace.draw(frame);
		}
		

	}

	public void addPlayers(ArrayList<PlayerWrper> players) {
		this.players = players;
	}

	public void addPlayer(PlayerWrper player) {

		this.players.add(player);

		for (BoardSpace boardSpace : gameSpaces) {
			if (boardSpace.getStatus() == Status.GO) {
				ArrayList<BoardSpace> arrayList = new ArrayList<BoardSpace>();
				arrayList.add(boardSpace);
				currentPositions.put(player, arrayList);
				statusus.remove(player);
				statusus.put(player, boardSpace.getStatus());

			}
		}

	}

	public ArrayList<RoundAction> getActions() {
		return actionsToTakeThisRound;

	}

	public void collectActions() {

		for (PlayerWrper player : players) {
			if (GameDice.getInstance().nextTurn()) {
				int x = playersnextxpos(player);
				int y = playersnextypos(player);
				BoardSpace findSpaceGivenXY = findSpaceGivenStatus(statusus.get(player));
				if(findSpaceGivenXY!=null) {
				statusus.put(player, findSpaceGivenXY.getStatus());
				actionsToTakeThisRound.add(new Move(x, y, player, this));
				GameDice.getInstance().setNextTurn(false);
				}
			}
		}

	}

	private BoardSpace findSpaceGivenStatus(Status status) {
		int lastRoll = instance.getLastRoll();
		
		Status nextStatus = Status.getNextStatus(status, lastRoll);
		
		for (BoardSpace boardSpace : gameSpaces) {
			if(boardSpace.getStatus()==nextStatus)
				return boardSpace;
		}
		return null;
	}

	public int playersnextypos(PlayerWrper player) {

		Status playerStatus = statusus.get(player);

		Status nextStatus = Status.getNextStatus(playerStatus, instance.getLastRoll());
		for (BoardSpace boardSpace : gameSpaces) {
			if (nextStatus == boardSpace.getStatus()) {
				return boardSpace.getyPos();

			}
		}

		return getYPosOfGO();
	}

	private int getYPosOfGO() {

		for (BoardSpace boardSpace : gameSpaces) {
			if (boardSpace.getStatus() == Status.GO) {
				return boardSpace.getyPos();
			}
		}
		System.out.println("no Go found");
		return -1;
	}

	public int playersnextxpos(PlayerWrper player) {

		Status playerStatus = statusus.get(player);

		Status nextStatus = Status.getNextStatus(playerStatus, instance.getLastRoll());

		for (BoardSpace boardSpace : gameSpaces) {
			if (nextStatus == boardSpace.getStatus()) {
				return boardSpace.getxPos();

			}
		}

		return getXPosOfGO();
	}

	private int getXPosOfGO() {

		for (BoardSpace boardSpace : gameSpaces) {
			if (boardSpace.getStatus() == Status.GO) {
				return boardSpace.getxPos();
			}
		}
		System.out.println("no Go found");
		return -1;
	}

	public void executeActions() {
		boolean resol = true;
		if (!actionResolved()) {
			for (RoundAction roundAction : actionsToTakeThisRound) {
				roundAction.execute();
				resol = resol && roundAction.isResolved();
			}
		}

		if (resol && GameDice.getInstance().nextTurn()) {
			actionsToTakeThisRound.clear();
		}

	}

	public void updatePlayerPosition() {
		for (PlayerWrper playerWrper : players) {
			int x = playerWrper.getX();
			int y = playerWrper.getY();
			ArrayList<BoardSpace> spaces = new ArrayList<BoardSpace>();

			BoardSpace bs = findSpaceGivenXY(x, y);
			if (bs != null) {
				spaces.add(bs);
				currentPositions.put(playerWrper, spaces);
				statusus.remove(playerWrper);
				statusus.put(playerWrper, bs.getStatus());
			}
		}

	}

	private BoardSpace findSpaceGivenXY(int x, int y) {

		for (BoardSpace boardSpace : gameSpaces) {
			int getxPos = boardSpace.getxPos();
			int getyPos = boardSpace.getyPos();

			if (x == getxPos && y == getyPos) {
				return boardSpace;
			}

		}
		return null;
	}

	public boolean actionResolved() {
		boolean resolved = true;
		for (RoundAction roundAction : actionsToTakeThisRound) {
			resolved = resolved && roundAction.isResolved();
		}
			return resolved && GameDice.getInstance().nextTurn();
	}

	public int getPlayerCurrentPositionX(PlayerWrper player) {
		ArrayList<BoardSpace> arrayList = currentPositions.get(player);
		return arrayList.get(0).getxPos();

	}

	public int getPlayerCurrentPositionY(PlayerWrper player) {
		ArrayList<BoardSpace> arrayList = currentPositions.get(player);
		return arrayList.get(0).getyPos();
	}

}
