package Actions;

import java.util.ArrayList;
import java.util.HashMap;

import TheGame.BoardSpace;
import TheGame.Status;
import XMLLoader.PlayerWrper;

public class FreeParking implements Actn {

	private boolean resolved;
	private PlayerWrper player;
	private Status status;
	private ArrayList<BoardSpace> freeSpaces;
	private HashMap<PlayerWrper, ArrayList<BoardSpace>> takenSpaces;

	public FreeParking(PlayerWrper player, Status status, ArrayList<BoardSpace> freeSpaces,
			HashMap<PlayerWrper, ArrayList<BoardSpace>> takenSpaces) {
				this.player = player;
				this.status = status;
				this.freeSpaces = freeSpaces;
				this.takenSpaces = takenSpaces;
				
	}

	@Override
	public void execute() {
		System.out.println("landed on "+status.name());
		resolved = true;
	}

	@Override
	public boolean isResolved() {
		return resolved;
	}

}
