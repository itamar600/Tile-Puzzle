
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Puzzle {
	private static int countOfPuzzles=0;
	private int pathLength;
	private List<Integer> black;
	private List<Integer> red;
	private int cost;
    public enum DIRECTION {LEFT, UP, RIGHT, DOWN }//operators
    private final int[][] puzzle;
    private int[][] correctPuzzle;
    private String path = "";
    private int zeroX, zeroY;//The row(zeroY) and column(zeroX) of the empty tile 
    
    
    public Puzzle(int[][] puzzle, int[][] correctPuzzle) {
        this.puzzle = puzzle;
        this.correctPuzzle = correctPuzzle;
        pathLength=0;
        cost=0;
//        black=new ArrayList<Integer>();
//        red=new ArrayList<Integer>();
        findZeroTile();
        countOfPuzzles++;
    }
    
    
    //Deep copy of newPuzzle
    public Puzzle(Puzzle newPuzzle) {
        puzzle = new int[newPuzzle.puzzle.length][newPuzzle.puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++) {
            puzzle[i] = Arrays.copyOf(newPuzzle.puzzle[i], puzzle[i].length);
        }
        correctPuzzle = newPuzzle.correctPuzzle;
        zeroX = newPuzzle.zeroX;
        zeroY = newPuzzle.zeroY;
        path = newPuzzle.path;
        pathLength=newPuzzle.pathLength;
        black=newPuzzle.black;
        red=newPuzzle.red;
        cost=newPuzzle.cost;
        countOfPuzzles++;
    }
    
    
    //This method check if puzzle is the goal puzzle(equal to correctPuzzle)
    public boolean isSolved() {
        for (int y = 0; y < puzzle.length; ++y) {
            for (int x = 0; x < puzzle[y].length; ++x) {
                if (puzzle[y][x] != correctPuzzle[y][x]) {
                    return false;
                }
            }
        }
        return true;
    }
   
    
    //This method check to which direction the empty tile can move
    public boolean canMove(DIRECTION direction) {
        switch (direction) {
            case UP:
                if (zeroY != 0 && (black==null || !black.contains(getTile(zeroY - 1,zeroX)))) {//If the empty tile not in the first row. 
                    return true;
                }
                break;
            case DOWN:
                if (zeroY != puzzle.length - 1 && (black==null || !black.contains(getTile(zeroY + 1,zeroX)))) {//If the empty tile not in the last row.
                    return true;
                }
                break;
            case LEFT:
                if (zeroX != 0 && (black==null || !black.contains(getTile(zeroY,zeroX-1)))) {//If the empty tile not in the first column.
                    return true;
                }
                break;
            case RIGHT:
                if (zeroX != puzzle[zeroY].length - 1 && (black==null || !black.contains(getTile(zeroY,zeroX+1)))) {//If the empty tile not in the last column.
                    return true;
                }
                break;
        }
        return false;
    }
   
    
    //This method is swapping the zero tile and his nei tile according to the receive direction
    public void move(DIRECTION direction) {
        switch (direction) {
            case UP:
                swap(zeroY, zeroX, (zeroY - 1), zeroX);
                path += getTile(zeroY+1,zeroX)+"D-";
                pathLength++;
                if(red!=null && red.contains(getTile(zeroY+1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case DOWN:
                swap(zeroY, zeroX, (zeroY + 1), zeroX);
                path += getTile(zeroY-1,zeroX)+"U-";
                pathLength++;
                if(red!=null && red.contains(getTile(zeroY-1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case LEFT:
                swap(zeroY, zeroX, zeroY, (zeroX - 1));
                path += getTile(zeroY,zeroX+1)+"R-";
                pathLength++;
                if(red!=null && red.contains(getTile(zeroY,zeroX+1)))
                	cost+=30;
                else 
                	cost++;
                break;
            case RIGHT:
                swap(zeroY, zeroX, zeroY, (zeroX + 1));
                path += getTile(zeroY,zeroX-1)+"L-";
                pathLength++;
                if(red!=null && red.contains(getTile(zeroY,zeroX-1)))
                	cost+=30;
                else 
                	cost++;
                break;
        }
    }
    
    
    //This method is swapping puzzle[y1][x1] with puzzle[y2][x2] while puzzle[y1][x1] is the empty tile,
    //and update zeroY and zeroX.
    private void swap(int y1, int x1, int y2, int x2) {
        int previous = getTile(y1, x1);
        setTile(y1, x1, getTile(y2, x2));
        setTile(y2, x2, previous);
        zeroY = y2;
        zeroX = x2;
    }
    
    
    //This method find the empty tile
    private void findZeroTile() {
        for (int y = 0; y < puzzle.length; ++y) {
            for (int x = 0; x < puzzle[y].length; ++x) {
                if (puzzle[y][x] == 0) {
                    zeroY = y;
                    zeroX = x;
                }
            }
        }
    }
    
    public void setBlack(List<Integer> black) {
    	this.black=black;
    }
    
    public void setRed(List<Integer> red) {
    	this.red=red;
    }
    
    public List<Integer> getBlack(){
    	return black;
    }
    
    public List<Integer> getRed(){
    	return red;
    }
    
    public int getCount() {
    	return countOfPuzzles;
    }
    
    public int getPathLength() {
    	return pathLength;
    }
    
    private void setTile(int y, int x, int tile) {
        puzzle[y][x] = tile;
    }
    
    private int getTile(int y, int x) {
        return puzzle[y][x];
    }
    
    public String getPath() {
        return path;
    }
    
    public int[][] getPuzzle() {
        return puzzle;
    }
    
    public int getCost() {
    	return cost;
    }
    
    @Override
    public String toString() {
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < puzzle.length; ++y) {
            for (int x = 0; x < puzzle[y].length; ++x) {
            	if(puzzle[y][x]==0)
            		output.append(" ");
            	else
            		output.append(puzzle[y][x]).append(" ");
            }
            output.append(System.lineSeparator());
        }
        return output.toString();
    }
    public String toStringCorrectPuzzle() {
        StringBuilder output = new StringBuilder();
        for (int y = 0; y < correctPuzzle.length; ++y) {
            for (int x = 0; x < correctPuzzle[y].length; ++x) {
            	if(correctPuzzle[y][x]==0)
            		output.append(" ");
            	else
            		output.append(correctPuzzle[y][x]).append(" ");
            }
            output.append(System.lineSeparator());
        }
        return output.toString();
    }
}