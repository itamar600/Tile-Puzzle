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
	private String path;
	private boolean isOpen; 
	public Algorithms() {
		frontiers = new LinkedList<>();
		count=0;
		path="";
		cost=0;
		isOpen=false;
	}
	public Puzzle BFS(Puzzle puzzleToSolve, boolean isOpen) {
		if(!checkTheBlack(puzzleToSolve)) 
    		return null;
		this.isOpen=isOpen;
		HashMap<String, Puzzle> openList= new HashMap<String, Puzzle>();
		HashMap<String, Puzzle> closeList= new HashMap<String, Puzzle>();
	    count=1;
	    frontiers.add(puzzleToSolve);
	    openList.put(puzzleToSolve.toString(), puzzleToSolve);
	    while (!frontiers.isEmpty()) {
	    	if(this.isOpen)
	    		System.out.println(frontiers+"\n\n");
	    	Puzzle puzzle = frontiers.poll();
	    	closeList.put(puzzle.toString(), puzzle);
	    	openList.remove(puzzle.toString(), puzzle);
	        for (int i = 0; i < strategy.length; i++) {
	        	if (puzzle.canMove(strategy[i])) {
	        		Puzzle newPuzzle = new Puzzle(puzzle);
	                count++;
	                newPuzzle.move(strategy[i]);
	                if(!openList.containsKey(newPuzzle.toString()) && !closeList.containsKey(newPuzzle.toString())) {
	                	if (newPuzzle.isSolved()) {
	                		System.out.println("Sum of puzzles: "+count);
	                		newPuzzle.setSumOfPuzzles(++count);
	                		findPath(newPuzzle);
	                		return newPuzzle;
	                	}
	                	else {
	                		frontiers.add(newPuzzle);
	                		openList.put(newPuzzle.toString(), newPuzzle);
	                	}
	                }
	            }
	        }
	    }
	    return null;
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
	 public void DFID(Puzzle puzzleToSolve, boolean isOpen ) {
		 if(!checkTheBlack(puzzleToSolve)) 
	    		this.path="no path";
		 this.isOpen=isOpen;
		 HashMap<String, Puzzle> openList;
		 count=1;
//		 int[][] result={{-1}};
//		 int[][] cutoff= {{0}};
		 String cutoff="cutoff";
		 String result="";
		 Puzzle solve;
		 for(int depth=1; depth<Integer.MAX_VALUE; depth++) {
			 openList= new HashMap<String, Puzzle>();
//			 solve=limited_DFS(puzzleToSolve, depth, openList);
			 result=limited_DFS(puzzleToSolve, depth, openList);
//			 if (solve.getPuzzle() != cutoff)
//				 return solve;
			 if(result==null || result!=cutoff) {
				 return;
			 }
		 }
//		 return null;
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
}
