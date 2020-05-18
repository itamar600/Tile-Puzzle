
//import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a puzzle state. The class contains the puzzle, the correct state for this puzzle,
 * list of the black tiles, list of the red tiles, the cost from the root puzzle until this state,
 * the path from the root puzzle until this state, char of the last move, The row(zeroY) and 
 * column(zeroX) of the empty tile.
 * To this class have the ability to make the correct puzzle, to check if the puzzle equal to 
 * the correct puzzle, to check the directions that the empty tile can move, to moving the empty tile,
 * and convert to string our puzzle and the correct puzzle.
 * 
 * @author Itamar Ziv-On
 *
 */
public class Puzzle {
	private int sumOfPuzzles;
	private int pathLength;
	private List<Integer> black;
	private List<Integer> red;
	private char lastMove;
	private int cost;
	private String move;
	private Puzzle father;
    public static enum DIRECTION {LEFT, UP, RIGHT, DOWN }
    private final int[][] puzzle;
    private int[][] correctPuzzle;
    private String path;
    private int zeroX, zeroY;
    private String puzzleString;
    private boolean ifStringYet;
    
    
    public Puzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        this.correctPuzzle = generateCorrectPuzzle(puzzle.length , puzzle[0].length);
        pathLength=0;
        cost=0;
        sumOfPuzzles=0;
        path="";
        black= null;
        red= null;
        father=null;
        findZeroTile();
        puzzleString="";
        ifStringYet=false;
        move="";
//        countOfPuzzles++;
    }
    
    public Puzzle(PuzzleLoader puzzle) {
    	this(puzzle.getPuzzle());
        black= puzzle.getBlack();
        red= puzzle.getRed();
//        countOfPuzzles++;
    }
    
    //Deep copy of father
    public Puzzle(Puzzle father) {
        puzzle = new int[father.puzzle.length][father.puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++) {
            puzzle[i] = Arrays.copyOf(father.puzzle[i], puzzle[i].length);
        }
        correctPuzzle = father.correctPuzzle;
        zeroX = father.zeroX;
        zeroY = father.zeroY;
        path = father.path;
        pathLength=father.pathLength;
        black=father.black;
        red=father.red;
        cost=father.cost;
        lastMove=father.lastMove;
        sumOfPuzzles=father.sumOfPuzzles;
        ifStringYet=false;
        this.father=father;
//        countOfPuzzles++;
    }
    
    
    /**
     * Checks if our puzzle is the goal puzzle(equal to correctPuzzle)
     * @return true if is the goal puzzle, else false.
     */
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
    /**
     * Makes the correct puzzle according to the puzzle's size.
     * @param ySize number of puzzle's rows
     * @param xSize number of puzzle's columns
     * @return the correct puzzle
     */
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
    
    /**
     * Checks if the empty tile can move to the receive direction.
     * Checks whether this direction does not return the puzzle to its previous state, 
     * if the empty cell can be moved in that direction at all, and if the number to be 
     * moved is not in the blacklist. 
     * @param direction 
     * @return true if can, else false.
     */
    public boolean canMove(DIRECTION direction) {
        switch (direction) {
            case UP:
                if (lastMove != 'D' && zeroY != 0 && (black==null || !black.contains(getTile(zeroY - 1,zeroX)))) {//If the empty tile not in the first row. 
                    return true;
                }
                break;
            case DOWN:
                if (lastMove != 'U' && zeroY != puzzle.length - 1 && (black==null || !black.contains(getTile(zeroY + 1,zeroX)))) {//If the empty tile not in the last row.
                    return true;
                }
                break;
            case LEFT:
                if (lastMove != 'R' && zeroX != 0 && (black==null || !black.contains(getTile(zeroY,zeroX-1)))) {//If the empty tile not in the first column.
                    return true;
                }
                break;
            case RIGHT:
                if (lastMove != 'L' && zeroX != puzzle[zeroY].length - 1 && (black==null || !black.contains(getTile(zeroY,zeroX+1)))) {//If the empty tile not in the last column.
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
                move=getTile(zeroY+1,zeroX)+"D";
                pathLength++;
                lastMove='U';
                if(red!=null && red.contains(getTile(zeroY+1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case DOWN:
                swap(zeroY, zeroX, (zeroY + 1), zeroX);
                path += getTile(zeroY-1,zeroX)+"U-";
                move=getTile(zeroY-1,zeroX)+"U";
                pathLength++;
                lastMove='D';
                if(red!=null && red.contains(getTile(zeroY-1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case LEFT:
                swap(zeroY, zeroX, zeroY, (zeroX - 1));
                path += getTile(zeroY,zeroX+1)+"R-";
                move=getTile(zeroY,zeroX+1)+"R";
                pathLength++;
                lastMove='L';
                if(red!=null && red.contains(getTile(zeroY,zeroX+1)))
                	cost+=30;
                else 
                	cost++;
                break;
            case RIGHT:
                swap(zeroY, zeroX, zeroY, (zeroX + 1));
                path += getTile(zeroY,zeroX-1)+"L-";
                move=getTile(zeroY,zeroX-1)+"L";
                pathLength++;
                lastMove='R';
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
    
//    public void setBlack(List<Integer> black) {
//    	this.black=black;
//    }
//    
//    public void setRed(List<Integer> red) {
//    	this.red=red;
//    }
    
    public void setSumOfPuzzles(int sumOfPuzzles) {
    	this.sumOfPuzzles = sumOfPuzzles;
    }
    
    public List<Integer> getBlack(){
    	return black;
    }
    
    public List<Integer> getRed(){
    	return red;
    }
    
//    public int getCount() {
//    	return countOfPuzzles;
//    }
    
    public String getMove() {
    	return move;
    }
    
    public int getPathLength() {
    	return pathLength;
    }
    
    public int getSumOfPuzzles() {
    	return sumOfPuzzles;
    }
    
    private void setTile(int y, int x, int tile) {
        puzzle[y][x] = tile;
    }
    
    private int getTile(int y, int x) {
        return puzzle[y][x];
    }
    
    public Puzzle getFather() {
    	return father;
    }
    
    public String getPath() {
        return path;
    }
    
    public int[][] getPuzzle() {
        return puzzle;
    }
    
    public int[][] getCorrectPuzzle() {
        return correctPuzzle;
    }
    
    public int getCost() {
    	return cost;
    }
    
    @Override
    public String toString() {
    	if(ifStringYet==true)
    		return puzzleString;
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
        ifStringYet=true;
        puzzleString= output.toString();
        return puzzleString;
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