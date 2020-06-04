//import java.io.IOException;
import java.io.PrintWriter;

public class Main {
    public static void main(String[] args) {
        PuzzleLoader puzzleLoader = new PuzzleLoader("input6.txt");
        Puzzle puzzle=new Puzzle(puzzleLoader);
        System.out.println("------- BEFORE -------");
        System.out.println(puzzle);
        long timeStart;
        long timeStop;
        timeStart = System.nanoTime();
//        Puzzle solvedPuzzle=Algorithms.BFS(puzzle,puzzleLoader.getOpen());
        Algorithms Solve=new Algorithms();
        System.out.println("BFS:\n"+Solve.BFS(puzzle,puzzleLoader.getOpen()));
        System.out.println("DFID:\n"+Solve.DFID(puzzle,puzzleLoader.getOpen()));
        System.out.println("IDA*:\n"+Solve.IDA(puzzle,puzzleLoader.getOpen()));
        System.out.println("A*:\n"+Solve.A(puzzle,puzzleLoader.getOpen()));
        System.out.println("DFBnB:\n"+Solve.DFBnB(puzzle,puzzleLoader.getOpen()));
//        Puzzle solvedPuzzle=Algorithms.DFID(puzzle,puzzleLoader.getOpen());
        timeStop = System.nanoTime();
        //System.out.println(Solve.getPath()+ "\n"+Solve.getCost()+"\n"+ Solve.getCount());
//        int[][] i= {{1},{2},{3},{4}};
//        int[][] j= {{1},{2},{3},{4}};
//        boolean test= false;
//        if(i.equals(j))
//        	test=true;
//        if(i==j)
//        	test=true;
//        System.out.println(test);
       // if(solvedPuzzle==null) {
//        	System.out.println("no path");
//        	return;
//        }
        System.out.println("------- AFTER -------");
       // System.out.println(solvedPuzzle);
        //System.out.println("Path: " + solvedPuzzle.getPath().substring(0,solvedPuzzle.getPath().length()-1));
        System.out.println("Path: " + Solve.getPath());
       // System.out.println("Path length: " + solvedPuzzle.getPathLength());
        System.out.println("Solved in: " + ((timeStop - timeStart) / 1000) / 1000.0 + "ms");
        System.out.println("Solved in: " + (timeStop - timeStart)/ 1000000000.0 + "s");
        System.out.println("Algorithm: "+ puzzleLoader.getAlgorithm());
//        System.out.println("Black tiles: "+ solvedPuzzle.getBlack());
//        System.out.println("Red tiles: "+ solvedPuzzle.getRed());
//        System.out.println("Cost: "+ solvedPuzzle.getCost());
        System.out.println("Time: "+ puzzleLoader.getTime() );
        System.out.println("Open: "+ puzzleLoader.getOpen() );
//        System.out.println("Sum of create: "+ solvedPuzzle.getSumOfPuzzles() );
        saveToFile("output1.txt",Solve.BFS(puzzle,puzzleLoader.getOpen()),timeStop-timeStart);
    }
    
    private static void saveToFile(String fileName, String solve, long time) {
    	try 
		{
			PrintWriter pw = new PrintWriter(fileName);
			pw.write(solve+ "\n" + time/1000000000.+" seconds");
			pw.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
    }
}
//    private static int[][] generateCorrectPuzzle(int ySize, int xSize) {
//        int[][] correctPuzzle = new int[ySize][xSize];
//        int counter = 1;
//        for (int y = 0; y < ySize; ++y) {
//            for (int x = 0; x < xSize; ++x) {
//                correctPuzzle[y][x] = counter;
//                ++counter;
//            }
//        }
//        correctPuzzle[ySize - 1][xSize - 1] = 0;
//        return correctPuzzle;
//    }
    
//    private static boolean checkTheBlack(Puzzle puzzle) {
//    	if(puzzle.getBlack()==null)
//    		return true;
//    	for(int n: puzzle.getBlack()) {
//    		for(int y=0; y<puzzle.getPuzzle().length; y++) {
//    			for(int x=0; x<puzzle.getPuzzle()[0].length; x++) {
//    				if(puzzle.getPuzzle()[y][x]==n) {
//    					if(puzzle.getCorrectPuzzle()[y][x]!=n)
//    						return false;
//    				}
//    			}
//    		}
//    	}
//    	return true;
//    }

//int[][] correctPuzzle;
//int[][] puzzleToSolve;
//puzzleToSolve = puzzleLoader.getPuzzle();
//correctPuzzle = generateCorrectPuzzle(puzzleToSolve.length, puzzleToSolve[0].length);
//puzzle = new Puzzle(puzzleToSolve, correctPuzzle);
//puzzle.setBlack(puzzleLoader.getBlack());
//puzzle.setRed(puzzleLoader.getRed());
//if(checkTheBlack(puzzle)== false) {
//	System.out.println("no path");
//	return;
//}
//SolverBFS solverBFS = new SolverBFS();
//Puzzle.DIRECTION[] strategy = {Puzzle.DIRECTION.LEFT, Puzzle.DIRECTION.UP, Puzzle.DIRECTION.RIGHT, Puzzle.DIRECTION.DOWN};
//Puzzle solvedPuzzle = solverBFS.solve(puzzle, strategy);