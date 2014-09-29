import java.util.Random;


public class RandomMoveGenerator implements MoveGenerator {

	Random rand;
	
	public RandomMoveGenerator() {
		rand = new Random();
	}
	
	@Override
	public Point getMove(Board board, char Player) {
		
		int row = 0, col = 0;
		do {
			row = rand.nextInt(3);
			col = rand.nextInt(3);
		} while(!board.isSpotEmpty(row, col));
		return new Point(row, col);
	}

}
