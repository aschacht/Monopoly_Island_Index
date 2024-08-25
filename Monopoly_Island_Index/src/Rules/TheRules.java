package Rules;

import TheGame.Board;
import TheGame.BoardSpace;
import XMLLoader.PlayerWrper;

public interface TheRules {
	public void check(PlayerWrper player,BoardSpace boardSpace,Board board);
}
