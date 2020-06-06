
import java.util.Arrays;
import java.util.List;

/**
 * This class represents a puzzle state. The class contains the puzzle, list of the black tiles, list of the red tiles,
 * the cost from the root puzzle until this state,char of the last move, pointer to the father puzzle, The row(zeroY) and column(zeroX) of the empty tile,
 * string of the move that led to this state, and boolean variables if have the string of this state in puzzleString and if this puzzle marked out.
 * To this class have the ability to check the directions that the empty tile can move, to moving the empty tile,
 * and convert to string our puzzle.
 * 
 * @author Itamar Ziv-On
 *
 */
public class Puzzle {
	
	
	private int depth;
	private List<Integer> black;
	private List<Integer> red;
	private char lastMove;
	private int cost;
	private String move;
	private Puzzle father;
    public static enum DIRECTION {LEFT, UP, RIGHT, DOWN }
    private final int[][] puzzle;
    private int zeroX, zeroY;
    private String puzzleString;
    private boolean ifStringYet,out;
    
    
                                                   //////////////////////////////////////////////////////////
                                                   //////////////////CONSTRUCTORS////////////////////////////
                                                   //////////////////////////////////////////////////////////
    
    
    /**
     * Receives a puzzle matrix and initializes this.puzzle points on accordingly.
     * @param puzzle
     */
    public Puzzle(int[][] puzzle) {
        this.puzzle = puzzle;
        depth=0;
        cost=0;
        black= null;
        red= null;
        father=null;
        findZeroTile();
        puzzleString="";
        ifStringYet=false;
        move="";
        out=false;
    }
    
    /**
     * Receives a puzzleLoader and initializes this.puzzle, this.black and this.red accordingly.
     * @param puzzle
     */
    public Puzzle(PuzzleLoader puzzle) {
    	this(puzzle.getPuzzle());
        black= puzzle.getBlack();
        red= puzzle.getRed();
    }
    
 
    /**
     * Deep copy of father's puzzle matrix for changing the puzzle state.
     * @param father
     */
    public Puzzle(Puzzle father) {
        puzzle = new int[father.puzzle.length][father.puzzle[0].length];
        for (int i = 0; i < puzzle.length; i++) {
            puzzle[i] = Arrays.copyOf(father.puzzle[i], puzzle[i].length);
        }
        zeroX = father.zeroX;
        zeroY = father.zeroY;
        depth=father.depth;
        black=father.black;
        red=father.red;
        cost=father.cost;
        lastMove=father.lastMove;
        ifStringYet=false;
        this.father=father;
        out=false;
        move="";
        ifStringYet=false;
    }
    
                           /////////////////////////////////////
                           //////////END CONSTRUCTORS///////////
                           /////////////////////////////////////
    
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
   
    
    
    /**
     * This method is swapping the zero tile and his neighbor tile according to the receive direction
     * @param direction
     */
    public void move(DIRECTION direction) {
        switch (direction) {
            case UP:
                swap(zeroY, zeroX, (zeroY - 1), zeroX);
                move=getTile(zeroY+1,zeroX)+"D";
                depth++;
                lastMove='U';
                if(red!=null && red.contains(getTile(zeroY+1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case DOWN:
                swap(zeroY, zeroX, (zeroY + 1), zeroX);
                move=getTile(zeroY-1,zeroX)+"U";
                depth++;
                lastMove='D';
                if(red!=null && red.contains(getTile(zeroY-1,zeroX)))
                	cost+=30;
                else 
                	cost++;
                break;
            case LEFT:
                swap(zeroY, zeroX, zeroY, (zeroX - 1));
                move=getTile(zeroY,zeroX+1)+"R";
                depth++;
                lastMove='L';
                if(red!=null && red.contains(getTile(zeroY,zeroX+1)))
                	cost+=30;
                else 
                	cost++;
                break;
            case RIGHT:
                swap(zeroY, zeroX, zeroY, (zeroX + 1));
                move=getTile(zeroY,zeroX-1)+"L";
                depth++;
                lastMove='R';
                if(red!=null && red.contains(getTile(zeroY,zeroX-1)))
                	cost+=30;
                else 
                	cost++;
                break;
        }
    }
    
    
    
    /**
     * This method is swapping puzzle[y1][x1] with puzzle[y2][x2] while puzzle[y1][x1] is the empty tile,
     * and update zeroY and zeroX.
     * @param y1
     * @param x1
     * @param y2
     * @param x2
     */
    private void swap(int y1, int x1, int y2, int x2) {
        setTile(y1, x1, getTile(y2, x2));
        setTile(y2, x2, 0);
        zeroY = y2;
        zeroX = x2;
    }
    
    
    
    /**
     * This method find the empty tile
     */
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
    
    
                /////////////////////////////////////////////////////////////////////////////////
                //////////////////////////////GETTERS AND SETTERS////////////////////////////////
                /////////////////////////////////////////////////////////////////////////////////    
    
    /**
     * 
     * @return the list of black numbers
     */
    public List<Integer> getBlack(){
    	return black;
    }
    
    /**
     * 
     * @return the list of red numbers
     */
    public List<Integer> getRed(){
    	return red;
    }
    
    
    /**
     * 
     * @return the move of number that led to this state, e.g. "6U".
     */
    public String getMove() {
    	return move;
    }
    
    /**
     * 
     * @return char of the direction of the empty tile that led to this state, e.g. 'U'.
     */
    public char getLastMove() {
    	return lastMove;
    }
    
    /**
     * 
     * @return the depth of this puzzle state
     */
    public int getDepth() {
    	return depth;
    }

    /**
     * 
     * @param y
     * @param x
     * @return the value in puzzle[y][x].
     */
    private int getTile(int y, int x) {
        return puzzle[y][x];
    }
    
    /**
     * 
     * @return the previous state 
     */
    public Puzzle getFather() {
    	return father;
    }
    
    /**
     * 
     * @return the matrix of this puzzle state
     */
    public int[][] getPuzzle() {
        return puzzle;
    }
    
    /**
     * 
     * @return the cost from the root puzzle until this state.
     */
    public int getCost() {
    	return cost;
    }
    
    /**
     * 
     * @return if this puzzle marked "out"
     */
    public boolean isOut() {
    	return out;
    }
    
    /**
     * Put number "tile" in puzzle[y][x].
     * @param y
     * @param x
     * @param tile
     */
    private void setTile(int y, int x, int tile) {
        puzzle[y][x] = tile;
    }
    
    /**
     * put in this.out the value "out"
     * @param out
     */
    public void setOut(boolean out) {
    	this.out=out;
    }
    
    
    
    /////////////////////////////////////////////
    //////////////TO STRING//////////////////////
    /////////////////////////////////////////////
    
    /**
     * If the string is already done(ifStringYet=true) it is not done again, otherwise(ifStringYet=false) make a string of matrix of this state 
     * and save it in puzzleString and change ifStringYet to true.
     * @return string of matrix of this state
     */
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
    
}