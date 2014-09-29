
public class Game {
	Board board;
	int moveCount;
	char[] players = {'X', 'O'};
	
	public Game() {
		board = new Board();
		moveCount = 0;
	}
	
	public void reset() {
		board.reset();
	}
	
	public void makeMove(int row, int col) throws Board.MarkPlacementException {
		board.mark(row, col, players[moveCount%players.length]);
	}
	
	public char getCurrentPlayerMarking() {
		return players[moveCount%players.length];
	}
	
	public boolean hasWinner() {
		return board.isEndState();
	}
	
	public char getWinner() {
		return board.getWinner();
	}
}
