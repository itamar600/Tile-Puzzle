import java.io.PrintWriter;
import java.text.DecimalFormat;

public class Ex1 {

		public static void main(String[] args) {
		        PuzzleLoader puzzleLoader = new PuzzleLoader("input7.txt");
		        Puzzle puzzle=new Puzzle(puzzleLoader);
		        long timeStart;
		        long timeStop;
		        Algorithms solve=new Algorithms();
		        String toSave="";
		        if(puzzleLoader.getAlgorithm().equals("BFS")) {
		        	timeStart = System.nanoTime();
		        	toSave=solve.BFS(puzzle,puzzleLoader.getOpen());
		        	timeStop = System.nanoTime();
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
