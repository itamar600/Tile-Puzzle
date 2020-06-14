
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * This class receives a text file that describes the initial puzzle state that needs solving 
 * and the color of the numbers and whether to print the open list, and whether to calculate the time that 
 * the algorithm solved the puzzle, it reads it and passes the data to the appropriate variables.
 * @author Itamar Ziv-On
 *
 */
public class PuzzleLoader {
	private String algorithm;
	private List<Integer> black;
	private List<Integer> red;
	private boolean open;
	private boolean time;
	private int[][] puzzle;
	private String [] cvsSplitBy= {",",":"," ","x"};
    public PuzzleLoader(String fileName) {
        BufferedReader br;
        String line1, line2, line3, line4, line5, line6, matrixRow;
        try {
            br = new BufferedReader(new FileReader(fileName));
            line1 = br.readLine();
            line2 = br.readLine();
            line3 = br.readLine();
            line4 = br.readLine();
            line5 = br.readLine();
            line6 = br.readLine();
            algorithm = line1;
            this.time= ifWith(line2);
            this.open= ifWith(line3);
            this.black=initColorList(line5);
            this.red=initColorList(line6);
            String[] size = line4.split(cvsSplitBy[3]);
            //from line 7 its the matrix of the puzzle
            puzzle = new int[Integer.parseInt(size[0])][Integer.parseInt(size[1])];
            int puzzleLine = 0;
            while ((matrixRow = br.readLine()) != null) {
                String[] tiles = matrixRow.split(cvsSplitBy[0]);
                for (int i = 0; i < puzzle[0].length; ++i) {
                	if(tiles[i].equals("_"))
                		puzzle[puzzleLine][i] = 0;
                	else
                		puzzle[puzzleLine][i] = Integer.parseInt(tiles[i]);
                }
                ++puzzleLine;
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Received a line and return if the first word in this line is "with".
     * @param line
     * @return true if the first word in this line is "with", otherwise false.
     */
    private boolean ifWith(String line) {
    	String [] lineSplit=line.split(cvsSplitBy[2]);
        if(lineSplit[0].equals("with"))
        	return true;
        return false;
    }
    /**
     * Received a line of numbers and puts the numbers in this line(if any) in a list.
     * @param line
     * @return list of numbers if were numbers in the line, otherwise null.
     */
    private List<Integer> initColorList(String line) {
    	line=line.replace(" ","");
    	String [] color= line.split(cvsSplitBy[1]);
    	if(color.length==1)
        	return null;
    	List<Integer> colorTiles= new ArrayList<Integer>();
    	String[] tiles=color[1].split(cvsSplitBy[0]);
    	for(int i=0; i< tiles.length; i++) {
    		colorTiles.add(Integer.parseInt(tiles[i]));
    	}
    	return colorTiles;
    	
    }
    
                                 ////////////////////////////////////////////////
                                 /////////////////////GETTERS////////////////////
                                 ////////////////////////////////////////////////  
    
    /**
     * 
     * @return the algorithm to use to solve the puzzle
     */
    public String getAlgorithm() {
    	return algorithm;
    }
    
    /**
     * 
     * @return if to save the time that it took to the algorithm to solve the puzzle.
     */
    public boolean getTime() {
    	return time;
    }
    
    /**
     * 
     * @return if to print the open list in each iteration of the algorithm.
     */
    public boolean getOpen() {
    	return open;
    }
    
    /**
     * 
     * @return the matrix of the puzzle state
     */
    public int[][] getPuzzle(){
    	return puzzle;
    }
    
    /**
     * 
     * @return the list of the black numbers.
     */
    public List<Integer> getBlack(){
    	return black;
    }
    
    /**
     * 
     * @return the list of the red numbers.
     */
    public List<Integer> getRed(){
    	return red;
    }
    
}

