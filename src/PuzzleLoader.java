
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 
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
    
    private boolean ifWith(String line) {
    	String [] lineSplit=line.split(cvsSplitBy[2]);
        if(lineSplit[0].equals("with"))
        	return true;
        return false;
    }
    
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
    
    public String getAlgorithm() {
    	return algorithm;
    }
    
    public boolean getTime() {
    	return time;
    }
    
    public boolean getOpen() {
    	return open;
    }
    
    public int[][] getPuzzle(){
    	return puzzle;
    }
    
    public List<Integer> getBlack(){
    	return black;
    }
    
    public List<Integer> getRed(){
    	return red;
    }
    
}

