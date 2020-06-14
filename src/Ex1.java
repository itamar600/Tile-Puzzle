import java.io.PrintWriter;
import java.text.DecimalFormat;
/**
 * This is the class of the main that receives data from the puzzleLoader 
 * and runs the appropriate algorithm and saves data in a text file.
 * Have another option ,if in the algorithm variable write "ALL", the puzzle is solved by all 
 * the algorithms and prints appropriate details. 
 * @author Itamar Ziv-On
 * The structure of this program is based on http://theflyingkeyboard.net/java/java-15-puzzle-solver-using-bfs/
 */
public class Ex1 {
		
		public static void main(String[] args) {
		        PuzzleLoader puzzleLoader = new PuzzleLoader("input.txt");
		        Puzzle puzzle=new Puzzle(puzzleLoader);
		        long timeStart;
		        long timeStop;
		        Algorithms solve=new Algorithms();
		        String toSave="";
		        if(puzzleLoader.getAlgorithm().equals("BFS")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.BFS(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
		        	//if no time or solution not found
		        	if(puzzleLoader.getTime()==false || solve.getPath().equals("no path"))
		        		saveToFile("output.txt",toSave,-1);
		        	else
		        		saveToFile("output.txt",toSave,timeStop-timeStart);
		        }
		        else if(puzzleLoader.getAlgorithm().equals("DFID")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.DFID(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
		        	if(puzzleLoader.getTime()==false || solve.getPath().equals("no path"))
		        		saveToFile("output.txt",toSave,-1);
		        	else
		        		saveToFile("output.txt",toSave,timeStop-timeStart);
		        }
		        else if(puzzleLoader.getAlgorithm().equals("A*")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.A(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
		        	if(puzzleLoader.getTime()==false || solve.getPath().equals("no path"))
		        		saveToFile("output.txt",toSave,-1);
		        	else
		        		saveToFile("output.txt",toSave,timeStop-timeStart);
		        }
		        else if(puzzleLoader.getAlgorithm().equals("IDA*")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.IDA(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
		        	if(puzzleLoader.getTime()==false || solve.getPath().equals("no path"))
		        		saveToFile("output.txt",toSave,-1);
		        	else
		        		saveToFile("output.txt",toSave,timeStop-timeStart);
		        }
		        else if(puzzleLoader.getAlgorithm().equals("DFBnB")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.DFBnB(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
		        	if(puzzleLoader.getTime()==false || solve.getPath().equals("no path"))
		        		saveToFile("output.txt",toSave,-1);
		        	else
		        		saveToFile("output.txt",toSave,timeStop-timeStart);
		        }
		        else if(puzzleLoader.getAlgorithm().equals("ALL"))	{
		        	System.out.println("BFS:\n"+solve.BFS(puzzle,puzzleLoader.getOpen()));
			        System.out.println("DFID:\n"+solve.DFID(puzzle,puzzleLoader.getOpen()));
			        System.out.println("IDA*:\n"+solve.IDA(puzzle,puzzleLoader.getOpen()));
			        System.out.println("A*:\n"+solve.A(puzzle,puzzleLoader.getOpen()));
			        System.out.println("DFBnB:\n"+solve.DFBnB(puzzle,puzzleLoader.getOpen()));
		        }
		}
		    
			/**
			 * Saves the data to a text file named  fileName, if the time it receives is -1 saves without time.
			 * @param fileName
			 * @param solve
			 * @param time
			 */
		    private static void saveToFile(String fileName, String solve, long time) {
		    	try 
				{
					PrintWriter pw = new PrintWriter(fileName);
					if(time!=-1)
						pw.write(solve+ "\n" +new DecimalFormat("0.000").format( time/1000000000.)+" seconds");
					else 
						pw.write(solve);
					pw.close();
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
		    }

}
