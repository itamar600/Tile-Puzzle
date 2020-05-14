
public class Main {
    public static void main(String[] args) {
        PuzzleLoader puzzleLoader = new PuzzleLoader("input.txt");
        Puzzle puzzle;
        int[][] correctPuzzle;
        int[][] puzzleToSolve;
        puzzleToSolve = puzzleLoader.getPuzzle();
        correctPuzzle = generateCorrectPuzzle(puzzleToSolve.length, puzzleToSolve[0].length);
        puzzle = new Puzzle(puzzleToSolve, correctPuzzle);
        puzzle.setBlack(puzzleLoader.getBlack());
        puzzle.setRed(puzzleLoader.getRed());
        System.out.println("------- BEFORE -------");
        System.out.println(puzzle);
        long timeStart;
        long timeStop;
        SolverBFS solverBFS = new SolverBFS();
        Puzzle.DIRECTION[] strategy = {Puzzle.DIRECTION.LEFT, Puzzle.DIRECTION.UP, Puzzle.DIRECTION.RIGHT, Puzzle.DIRECTION.DOWN};
        timeStart = System.nanoTime();
        Puzzle solvedPuzzle = solverBFS.solve(puzzle, strategy);
        timeStop = System.nanoTime();
        System.out.println("------- AFTER -------");
        System.out.println(solvedPuzzle);
        System.out.println("Path: " + solvedPuzzle.getPath().substring(0,solvedPuzzle.getPath().length()-1));
//        System.out.println("Path length: " + solvedPuzzle.getPath().length()/3);
        System.out.println("Path length: " + solvedPuzzle.getPathLength());
        System.out.println("Solved in: " + ((timeStop - timeStart) / 1000) / 1000.0 + "ms");
        System.out.println("Algorithm: "+ puzzleLoader.getAlgorithm());
        System.out.println("Black tiles: "+ solvedPuzzle.getBlack());
        System.out.println("Red tiles: "+ solvedPuzzle.getRed());
        System.out.println("Cost: "+ solvedPuzzle.getCost());
        System.out.println("Time: "+ puzzleLoader.getTime() );
        System.out.println("Open: "+ puzzleLoader.getOpen() );
        System.out.println("Sum of create: "+ solvedPuzzle.getCount() );
    }
    private static int[][] generateCorrectPuzzle(int ySize, int xSize) {
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
