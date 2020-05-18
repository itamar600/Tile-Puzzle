
import java.util.LinkedList;
import java.util.Queue;
public class SolverBFS {
    private final Queue<Puzzle> frontiers = new LinkedList<>();
    public Puzzle solve(Puzzle puzzleToSolve, Puzzle.DIRECTION[] strategy) {
    	int count=0;
        frontiers.add(puzzleToSolve);
        while (!frontiers.isEmpty()) {
            Puzzle puzzle = frontiers.poll();
            if (puzzle.isSolved()) {
            	System.out.println("Sum of puzzles: "+count);
                return puzzle;
            }
            for (int i = 0; i < strategy.length; i++) {
                if (puzzle.canMove(strategy[i])) {
                    Puzzle newPuzzle = new Puzzle(puzzle);
                    count++;
                    newPuzzle.move(strategy[i]);
                    frontiers.add(newPuzzle);
                }
            }
        }
        return null;
    }
}
