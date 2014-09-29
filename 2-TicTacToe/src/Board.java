import java.util.LinkedList;
import java.util.List;


public class Board {
	char[][] data;
	char winner;
	
	public Board() {
		data = new char[3][3];
		reset();
	}
	
	public void mark(int row, int col, char marking) throws MarkPlacementException {
		if(data[row][col] != ' ')
			throw new MarkPlacementException(row, col, "Attempt to mark non-empty spot ("+row+", " + col + ") which currently contains " + data[row][col] + ".");
		data[row][col] = marking;
	}
	
	public char getMarking(int row, int col) {
		return data[row][col];
	}
	
	public boolean isSpotEmpty(int row, int col) {
		return getMarking(row, col) == ' ';
	}
	
	public void reset() {
		winner = ' ';
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				data[r][c] = ' ';
			}
		}
	}
	
	public class MarkPlacementException extends Exception {
		public int Row;
		public int Col;
		public MarkPlacementException(int row, int col, String message) {
			super(message);
			Row = row;
			Col = col;
		}
	}
	
	public boolean isEndState() {
		// Horizontal
		if(data[0][0] != ' ' && data[0][0] == data[0][1] && data[0][0] == data[0][2]) {
			winner = data[0][0];
			return true;
		}
		if(data[1][0] != ' ' && data[1][0] == data[1][1] && data[1][0] == data[1][2]) {
			winner = data[1][0];
			return true;
		}
		if(data[2][0] != ' ' && data[2][0] == data[2][1] && data[2][0] == data[2][2]) {
			winner = data[2][0];
			return true;
		}
		// Vertical
		if(data[0][0] != ' ' && data[0][0] == data[1][0] && data[0][0] == data[2][0]) {
			winner = data[0][0];
			return true;
		}
		if(data[0][1] != ' ' && data[0][1] == data[1][1] && data[0][1] == data[2][1]) {
			winner = data[0][1];
			return true;
		}
		if(data[0][2] != ' ' && data[0][2] == data[1][2] && data[0][2] == data[2][2]) {
			winner = data[0][2];
			return true;
		}
		// Diagonal
		if(data[0][0] != ' ' && data[0][0] == data[1][1] && data[0][0] == data[2][2]) {
			winner = data[0][0];
			return true;
		}
		if(data[2][0] != ' ' && data[2][0] == data[1][1] && data[2][0] == data[0][2]) {
			winner = data[2][0];
			return true;
		}
		// No winner
		winner = ' ';
		return false;
	}
	
	public char getWinner() {
		if(isEndState())
			return winner;
		else
			return ' ';
	}
	
	public List<Point> getEmptyCells() {
		List<Point> ans = new LinkedList<Point>();
		for(int r = 0; r < 3; r++) {
			for(int c = 0; c < 3; c++) {
				if(data[r][c] == ' ')
					ans.add(new Point(r,c));
			}
		}
		return ans;
	}
}
