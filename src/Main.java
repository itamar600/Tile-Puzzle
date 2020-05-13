
public class Main {
    public static void main(String[] args) {
        PuzzleLoader puzzleLoader = new PuzzleLoader();
        Puzzle puzzle;
        int[][] correctPuzzle;
        int[][] puzzleToSolve;
        puzzleToSolve = puzzleLoader.load("puzzleToSolve.txt");
        correctPuzzle = generateCorrectPuzzle(puzzleToSolve.length, puzzleToSolve[0].length);
        puzzle = new Puzzle(puzzleToSolve, correctPuzzle);
        System.out.println("------- BEFORE -------");
        System.out.println(puzzle);
        long timeStart;
        long timeStop;
        SolverBFS solverBFS = new SolverBFS();
        Puzzle.DIRECTION[] strategy = {Puzzle.DIRECTION.RIGHT, Puzzle.DIRECTION.DOWN, Puzzle.DIRECTION.UP, Puzzle.DIRECTION.LEFT};
        timeStart = System.nanoTime();
        Puzzle solvedPuzzle = solverBFS.solve(puzzle, strategy);
        timeStop = System.nanoTime();
        System.out.println("------- AFTER -------");
        System.out.println(solvedPuzzle);
        System.out.println("Path: " + solvedPuzzle.getPath());
        System.out.println("Path length: " + solvedPuzzle.getPath().length()/3);
        System.out.println("Solved in: " + ((timeStop - timeStart) / 1000) / 1000.0 + "ms");
    }
    private static int[][] generateCorrectPuzzle(int xSize, int ySize) {
        int[][] correctPuzzle = new int[ySize][xSize];
        int counter = 1;
        for (int y = 0; y < ySize; ++y) {
            for (int x = 0; x < xSize; ++x) {
                correctPuzzle[y][x] = counter;
                ++counter;
            }
        }
        correctPuzzle[ySize - 1][xSize - 1] = 0;
        return correctPuzzle;
    }
}
