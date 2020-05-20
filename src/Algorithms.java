import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Algorithms {
//	private final Queue<Puzzle> frontiers = new LinkedList<>();
	private Queue<Puzzle> frontiers;
	private final Puzzle.DIRECTION[] strategy = {Puzzle.DIRECTION.RIGHT, Puzzle.DIRECTION.DOWN, Puzzle.DIRECTION.LEFT, Puzzle.DIRECTION.UP};
	private int count;
	private int cost;
	private int[][] correctPuzzle;
	private String path;
	private boolean isOpen; 
	public Algorithms() {
		frontiers = new LinkedList<>();
		count=0;
		path="no path";
		cost=0;
		isOpen=false;
	}
	public String BFS(Puzzle puzzleToSolve, boolean isOpen) {
		initData(puzzleToSolve, isOpen);
		count++;
		if(!checkTheBlack(puzzleToSolve)) 
    		return toString();
		HashMap<String, Puzzle> openList= new HashMap<String, Puzzle>();
		HashMap<String, Puzzle> closeList= new HashMap<String, Puzzle>();
	    frontiers.add(puzzleToSolve);
	    openList.put(puzzleToSolve.toString(), puzzleToSolve);
	    while (!frontiers.isEmpty()) {
	    	if(this.isOpen)
	    		System.out.println(frontiers+"\n\n");
	    	Puzzle puzzle = frontiers.poll();
	    	//System.out.println(puzzle+ ", "+heuristic(puzzle));
	    	closeList.put(puzzle.toString(), puzzle);
	    	openList.remove(puzzle.toString(), puzzle);
	        for (int i = 0; i < strategy.length; i++) {
	        	if (puzzle.canMove(strategy[i])) {
	        		Puzzle newPuzzle = new Puzzle(puzzle);
	                count++;
	                newPuzzle.move(strategy[i]);
	                if(!openList.containsKey(newPuzzle.toString()) && !closeList.containsKey(newPuzzle.toString())) {
	                	if (newPuzzle.isSolved()) {
//	                		newPuzzle.setSumOfPuzzles(++count);
	                		findPath(newPuzzle);
	                		cost=newPuzzle.getCost();
	                		return toString();
	                	}
	                	else {
	                		frontiers.add(newPuzzle);
	                		openList.put(newPuzzle.toString(), newPuzzle);
	                	}
	                }
	            }
	        }
	    }
	    return toString();
	}
	
	 private void initData(Puzzle puzzle, boolean isOpen) {
		frontiers.clear();
		correctPuzzle = generateCorrectPuzzle(puzzle.getPuzzle().length , puzzle.getPuzzle()[0].length);
		path="no path";
		count=0;
		cost=0;
		this.isOpen=isOpen;
	 }
	 
	 private boolean checkTheBlack(Puzzle puzzle) {
	    	if(puzzle.getBlack()==null)
	    		return true;
	    	for(int n: puzzle.getBlack()) {
	    		for(int y=0; y<puzzle.getPuzzle().length; y++) {
	    			for(int x=0; x<puzzle.getPuzzle()[0].length; x++) {
	    				if(puzzle.getPuzzle()[y][x]==n) {
	    					if(puzzle.getCorrectPuzzle()[y][x]!=n)
	    						return false;
	    				}
	    			}
	    		}
	    	}
	    	return true;
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
	 
	 public String DFID(Puzzle puzzleToSolve, boolean isOpen ) {
		 initData(puzzleToSolve, isOpen);
		 count++;
		 if(!checkTheBlack(puzzleToSolve))  
	    		return toString();
		 HashMap<String, Puzzle> openList;
//		 int[][] result={{-1}};
//		 int[][] cutoff= {{0}};
		 String cutoff="cutoff";
		 String result="";
		 for(int depth=1; depth<Integer.MAX_VALUE; depth++) {
			 openList= new HashMap<String, Puzzle>();
//			 solve=limited_DFS(puzzleToSolve, depth, openList);
			 result=limited_DFS(puzzleToSolve, depth, openList);
//			 if (solve.getPuzzle() != cutoff)
//				 return solve;
			 if(result==null ||  result!=cutoff)
				 return toString();
//			 else if( result!=cutoff)
//				 return toString();
		 }
		 return toString();
	 }
	 
	 private String limited_DFS(Puzzle puzzle,int depth,HashMap<String, Puzzle> openList) {
		 if(puzzle.isSolved()) {
//			 return puzzle;
			 this.cost=puzzle.getCost();
			 findPath(puzzle);
			 return path;
		 }
		 if (depth==0)
		 	return "cutoff";
//			 return puzzle;
		 String result;
		 openList.put(puzzle.toString(), puzzle);
		 boolean isCutoff=false;
		 for (int i = 0; i < strategy.length; i++) {
	        	if (puzzle!= null && puzzle.canMove(strategy[i])) {
	        		Puzzle newPuzzle = new Puzzle(puzzle);
	                count++;
	                newPuzzle.move(strategy[i]);
	                if(!openList.containsKey(newPuzzle.toString())) {
	                	result= limited_DFS(newPuzzle, depth-1, openList);
	                	if(result!= null && result=="cutoff")
	                		isCutoff=true;
	                	else if(result!=null)
	                		return result;
//	                	if(puzzle!=null) {
//	                		if(puzzle.isSolved())
//	                			return puzzle;
//	                		isCutoff=true;
//	                	}
	                }
	        	}
		 }
//		 if(puzzle!= null)
		 if(isOpen)
			 System.out.println(openList.keySet());
		 openList.remove(puzzle.toString(), puzzle);
		 if(isCutoff==true)
			 return "cutoff";
//			 return puzzle;
		 return null;
	 }
	 
	 private void findPath(Puzzle puzzle) {
		 if(puzzle== null) {
			 this.path="no path";
			 return;
		 }
		 String move=puzzle.getMove();
		 ArrayList<String> moves= new ArrayList<String>();
		 while(move!="") {
		 moves.add(move);
		 puzzle=puzzle.getFather();
		 move =puzzle.getMove();
		 }
		 StringBuilder path = new StringBuilder();
		 for(int i=moves.size()-1; i>=0;i--) {
			 if(i!=0)
				 path.append(moves.get(i)).append("-");
			 else
				 path.append(moves.get(i));
		 }
		 this.path=path.toString();
	 }
	 
	 public String getPath() {
		 return path;
	 }
	 
	 public int getCount() {
		 return count;
	 }
	 
	 public int getCost() {
		 return cost;
	 }
	 
	 public String toString() {
		 return path+ "\nNum: "+ count +"\nCost: "+ cost; 
	 }
	 
	 private int heuristic(Puzzle puzzle) {
		 int[][] matrix= puzzle.getPuzzle();
		 int num, correctCol, correctRow, h=0;
		 int rowSum= matrix.length;
		 int colSum = matrix[0].length;
		 for(int row=0; row<rowSum; row++) {
			 for(int col=0; col<colSum; col++) {
				 num=matrix[row][col];
				 if(num==0 || (puzzle.getBlack()!=null && puzzle.getBlack().contains(num)))
					 continue;
				 correctRow= (num-1)/colSum;
				 correctCol= (num-1)%colSum;
				 if(matrix[correctRow][correctCol]==num)
					 continue;
				 if(puzzle.getRed().contains(num))
					 h+=30*(Math.abs(row-correctRow)+Math.abs(col-correctCol));
				 else 
					 h+=Math.abs(row-correctRow)+Math.abs(col-correctCol);
				 
			 }
				 
		 }
		 return h;
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
