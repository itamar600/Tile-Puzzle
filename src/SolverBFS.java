
import java.util.LinkedList;
import java.util.Queue;
public class SolverBFS {
    private final Queue<Puzzle> frontiers = new LinkedList<>();
    public Puzzle solve(Puzzle puzzleToSolve, Puzzle.DIRECTION[] strategy) {
        frontiers.add(puzzleToSolve);
        while (!frontiers.isEmpty()) {
            Puzzle puzzle = frontiers.poll();
            if (puzzle.isSolved()) {
                return puzzle;
            }
            for (int i = 0; i < strategy.length; i++) {
                if (puzzle.canMove(strategy[i])) {
                    Puzzle newPuzzle = new Puzzle(puzzle);
                    newPuzzle.move(strategy[i]);
                    frontiers.add(newPuzzle);
                }
            }
        }
        return null;
    }
}
