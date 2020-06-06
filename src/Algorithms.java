import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
/**
 * This class represents algorithms for calculating tile puzzle with colors, each algorithm gets a starting puzzle and if to print the open list and 
 * returns a string that includes the path to solve, if any, how many puzzles were created and what the cost of the path.
 * @author itamar Ziv On
 *
 */
public class Algorithms {

	private final Puzzle.DIRECTION[] strategy = {Puzzle.DIRECTION.RIGHT, Puzzle.DIRECTION.DOWN, Puzzle.DIRECTION.LEFT, Puzzle.DIRECTION.UP};
	private int count;
	private int cost;
	private int[][] correctPuzzle;
	private String path;
	private boolean isOpen; 
	
	//constructor
	public Algorithms() {
		count=0;
		path="no path";
		cost=0;
		isOpen=false;
	}
	
	/**
	 * 
	 * @param puzzleToSolve
	 * @param isOpen
	 * @return
	 */
	public String BFS(Puzzle puzzleToSolve, boolean isOpen) {
		initData(puzzleToSolve, isOpen);
		count++;
		if(!blackCheck(puzzleToSolve)) 
    		return toString();
		Queue<Puzzle> frontiers = new LinkedList<>();
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
//	                	if (newPuzzle.isSolved()) {
//	                		newPuzzle.setSumOfPuzzles(++count);
	                	if (isSolved(newPuzzle)) {
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
	
	 
	 /**
	  * 
	  * @param puzzleToSolve
	  * @param isOpen
	  * @return
	  */
	 public String DFID(Puzzle puzzleToSolve, boolean isOpen ) {
		 initData(puzzleToSolve, isOpen);
		 count++;
		 if(!blackCheck(puzzleToSolve))  
	    		return toString();
		 HashMap<String, Puzzle> openList;
		 String cutoff="cutoff";
		 String result="";
		 for(int depth=1; depth<Integer.MAX_VALUE; depth++) {
			 openList= new HashMap<String, Puzzle>();
			 result=limited_DFS(puzzleToSolve, depth, openList);
			 if(result==null ||  result!=cutoff)
				 return toString();
		 }
		 return toString();
	 }
	 /**
	  * 
	  * @param puzzle
	  * @param depth
	  * @param openList
	  * @return
	  */
	 private String limited_DFS(Puzzle puzzle,int depth,HashMap<String, Puzzle> openList) {
		 if(isSolved(puzzle)) {
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
	                	if(result!= null && result.equals("cutoff"))
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
			 System.out.println(openList.keySet()+"\n\n");
		 openList.remove(puzzle.toString(), puzzle);
		 if(isCutoff==true)
			 return "cutoff";
//			 return puzzle;
		 return null;
	 }
	 
	 /**
	  * 
	  * @param puzzleToSolve
	  * @param isOpen
	  * @return
	  */
	 public String A(Puzzle puzzleToSolve, boolean isOpen){
		initData(puzzleToSolve, isOpen);
		count++;
		if(!blackCheck(puzzleToSolve)) 
			return toString();
		PriorityQueue<Puzzle> frontiers=new PriorityQueue<Puzzle>(new Puzzle_Comperator());
		HashMap<String, Puzzle> openList= new HashMap<String, Puzzle>();
		HashMap<String, Puzzle> closeList= new HashMap<String, Puzzle>();
		frontiers.add(puzzleToSolve);
		openList.put(puzzleToSolve.toString(), puzzleToSolve);
		Puzzle puzzle;
		while(!frontiers.isEmpty()) {
			if(isOpen)
				 System.out.println(openList.keySet()+"\n\n");
			puzzle=frontiers.poll();
			openList.remove(puzzle.toString(), puzzle);
			if(isSolved(puzzle)) {
				this.cost=puzzle.getCost();
				findPath(puzzle);
				return toString();
			}
			closeList.put(puzzle.toString(), puzzle);
			for (int i = 0; i < strategy.length; i++) {
				 if (puzzle!= null && puzzle.canMove(strategy[i])) {
		        		Puzzle newPuzzle = new Puzzle(puzzle);
		                count++;
		                newPuzzle.move(strategy[i]);
		                if(!openList.containsKey(newPuzzle.toString()) && !closeList.containsKey(newPuzzle.toString())) {
		                	frontiers.add(newPuzzle);
		            		openList.put(newPuzzle.toString(), newPuzzle);
		                }
		                else if(openList.containsKey(newPuzzle.toString())) {
		                	Puzzle temp;
		                	temp=openList.get(newPuzzle.toString());
		                	if(heuristic(newPuzzle)+newPuzzle.getCost()<heuristic(temp)+temp.getCost()) {
		                		frontiers.remove(temp);
		                		openList.remove(temp.toString(), temp);
		                		frontiers.add(newPuzzle);
			            		openList.put(newPuzzle.toString(), newPuzzle);
		                	}
		                }
				 }
			}
		}
		return toString();
	 }
	 
	 /**
	  * 
	  * @param puzzleToSolve
	  * @param isOpen
	  * @return
	  */
	 public String IDA(Puzzle puzzleToSolve, boolean isOpen){
		 initData(puzzleToSolve, isOpen);
		 count++;
		 if(!blackCheck(puzzleToSolve))  
	    		return toString();
		 int minF;
		 Stack<Puzzle> frontiers=new Stack<Puzzle>();
		 HashMap<String, Puzzle> openList= new HashMap<String,Puzzle>();
		 Puzzle puzzle, temp;
		 int f, t= heuristic(puzzleToSolve);
		 while(t!=Integer.MAX_VALUE) {
			 minF=Integer.MAX_VALUE;
			 initIDA(frontiers,openList, puzzleToSolve);
//			 frontiers.clear();
//			 openList.clear();
//			 frontiers.add(puzzleToSolve);
//			 openList.put(puzzleToSolve.toString(), puzzleToSolve);
			 while(!frontiers.isEmpty()) {
				 if(isOpen)
					 System.out.println(openList.keySet()+"\n\n");
				 puzzle=frontiers.pop();
				 if(puzzle.isOut()) {
					 openList.remove(puzzle.toString(), puzzle);
					 //System.out.println("out\n"+puzzle);
				 }
				 else {
					 //System.out.println("not out\n"+puzzle);
					 puzzle.setOut(true);
					 frontiers.push(puzzle);
					 for (int i = 0; i < strategy.length; i++) {
						 if (puzzle!= null && puzzle.canMove(strategy[i])) {
				        		Puzzle newPuzzle = new Puzzle(puzzle);
				                count++;
				                newPuzzle.move(strategy[i]);
				                f=newPuzzle.getCost()+heuristic(newPuzzle);
				                if(f>t) {
				                	minF=Math.min(minF, f);
				                	continue;
				                }
				                if(openList.containsKey(newPuzzle.toString())) {
				                	temp=openList.get(newPuzzle.toString());
				                	if(temp.isOut())
				                		continue;
				                	if(temp.getCost()+heuristic(temp)>f) {
				                		frontiers.remove(temp);
				                		openList.remove(temp.toString(), temp);
				                	}
				                	else 
				                		continue;
				                }
				                if(isSolved(newPuzzle)) {
				                	//System.out.println("open list\n"+openList);
				                	findPathOut(frontiers, newPuzzle);
				                	//findPath(newPuzzle);
				                	cost=newPuzzle.getCost();
				                	return toString();
				                }
				                frontiers.push(newPuzzle);
				                openList.put(newPuzzle.toString(), newPuzzle);
						 } 
					 }
					
				 }
			 } 
			 t=minF;
		 }
		 return toString();
	 }
	
	 
	 /**
	  * 
	  * @param puzzleToSolve
	  * @param isOpen
	  * @return
	  */
	 public String DFBnB(Puzzle puzzleToSolve, boolean isOpen) {
		 initData(puzzleToSolve, isOpen);
		 count++;
		 if(!blackCheck(puzzleToSolve))  
	    		return toString();
		 puzzleToSolve.setOut(false);
		 //Integer.MAX_VALUE;
		 int f, t;
		 t=tForStart();
		 Puzzle puzzle,child, temp;
		 Stack<Puzzle> frontiers=new Stack<Puzzle>();
		 HashMap<String, Puzzle> openList= new HashMap<String, Puzzle>();
//		 PriorityQueue<Puzzle> children=new PriorityQueue<Puzzle>(3,new Puzzle_Comperator());
		 frontiers.push(puzzleToSolve);
		 openList.put(puzzleToSolve.toString(), puzzleToSolve);
		 ArrayList<Puzzle> children=new ArrayList<Puzzle>();
		 while(!frontiers.isEmpty()) {
			if(isOpen)
				 System.out.println(openList.keySet()+"\n\n");
			puzzle=frontiers.pop();
			if(puzzle.isOut()) {
				 openList.remove(puzzle.toString(), puzzle);
				// System.out.println("out: "+puzzle.getMove());
				 //System.out.println("out\n"+puzzle);
			 }
			 else {
				 //System.out.println("not out\n"+puzzle);
				 puzzle.setOut(true);
				 //System.out.println("newout: "+puzzle.getMove());
				 frontiers.push(puzzle);
				 children=childrenArr(puzzle);
				 //System.out.println(children);
				 //System.out.println(children.size());
				 for (int i = 0; i < children.size(); i++) {
					 child=children.get(i);
			         f=child.getCost()+heuristic(child);
			        // System.out.println(f);
			         if(f>=t) {
			        	 while(i<children.size()) {
			            	// System.out.println("1: "+f+" , "+i+" ,"+child.getMove());
			            	 children.remove(i);
			             }
			         }
			         //System.out.println("start: "+f+" , "+i+" ,"+child.getMove());
			         if(openList.containsKey(child.toString())) {
			        	 temp=openList.get(child.toString());
			             if(temp.isOut()) {
			            	 //System.out.print(children.size()+"  ");
			            	 children.remove(child);
			            	 //System.out.println(children.size());
			            	 //System.out.println("2: "+f+" , "+i+" ,"+child.getMove());
			            	 i--;
			            	 continue;
			             }
			             if(temp.getCost()+heuristic(temp)>f) {
			            	 frontiers.remove(temp);
			            	 openList.remove(temp.toString(), temp);
			            	 //System.out.println("3: "+f+" , "+i+" ,"+child.getMove());
			             }
			             else {
			            	 //System.out.print(children.size()+"  ");
			            	 children.remove(child);
			            	// System.out.println("4: "+f+" , "+i+" ,"+child.getMove());
			            	 //System.out.print(children.size());
			            	 i--;
			            	 continue;
			             }
			         }
			         else if(isSolved(child)) {
			        	 //System.out.println("open list\n"+openList);
			             findPathOut(frontiers, child);
			             //findPath(child);
			             cost=child.getCost();
			             t=f;
			             while(i<children.size()) {
			            	// System.out.println("5: "+f+" , "+i+" ,"+child.getMove());
			            	 children.remove(i);
			             }
			         }
				 }
				 //System.out.println();
				 children= reverseChildren(children);
	             for(int i=0; i<children.size(); i++) {
	            	 //System.out.println(heuristic(children.get(i))+children.get(i).getCost());
	            	 frontiers.push(children.get(i));
	            	// System.out.println("peek: "+frontiers.peek().getMove());
		             openList.put(children.get(i).toString(), children.get(i));
	                }
	             //System.out.println();
	             children.clear();
				 }
			 }
		 return toString();
		 }
                                         ///////////////////////////////////////////////////////////////////////////////
	                                          ///////////////////////////TOOLS/////////////////////////////////////
	                                     ///////////////////////////////////////////////////////////////////////////////
	 
	 
	 
      ///////////////////////
        ////FOR ALL/////
     //////////////////////
	 	
	 
	   /**
	 	 * Initialize the variables at the beginning of each algorithm.
	 	 * @param puzzle- the puzzle to solve, needed for generate the correct puzzle.
	 	 * @param isOpen
	 	 */
	 	private void initData(Puzzle puzzle, boolean isOpen) {
			generateCorrectPuzzle(puzzle.getPuzzle().length , puzzle.getPuzzle()[0].length);
			path="no path";
			count=0;
			cost=0;
			this.isOpen=isOpen;
		 }
		 
	    /**
	     * Checks if the numbers in the black are in the correct position otherwise the puzzle cannot be solved.
	     * @param puzzle
	     * @return
	     */
	 	private boolean blackCheck(Puzzle puzzle) {
		    	if(puzzle.getBlack()==null)
		    		return true;
		    	for(int n: puzzle.getBlack()) {
		    		for(int y=0; y<puzzle.getPuzzle().length; y++) {
		    			for(int x=0; x<puzzle.getPuzzle()[0].length; x++) {
		    				if(puzzle.getPuzzle()[y][x]==n) {
		    					if(correctPuzzle[y][x]!=n)
		    						return false;
		    				}
		    			}
		    		}
		    	}
		    	return true;
		    }
		 
	 	 /**
	 	  * Generate the correct puzzle.
	 	  * @param ySize- number of rows
	 	  * @param xSize- number of columns
	 	  */
		 private void generateCorrectPuzzle(int ySize, int xSize) {
		        int[][] correctPuzzle = new int[ySize][xSize];
		        int counter = 1;
		        for (int y = 0; y < ySize; ++y) {
		            for (int x = 0; x < xSize; ++x) {
		                correctPuzzle[y][x] = counter;
		                ++counter;
		            }
		        }
		        correctPuzzle[ySize - 1][xSize - 1] = 0;
		        this.correctPuzzle=correctPuzzle;
		    }
		 
		 /**
		     * Checks if our puzzle is the goal puzzle(equal to correctPuzzle)
		     * @return true if it is the goal puzzle, else false.
		     */
		 private boolean isSolved(Puzzle puzzle) {
		        for (int y = 0; y < puzzle.getPuzzle().length; ++y) {
		            for (int x = 0; x < puzzle.getPuzzle()[y].length; ++x) {
		                if (puzzle.getPuzzle()[y][x] != correctPuzzle[y][x]) {
		                    return false;
		                }
		            }
		        }
		        return true;
		 }
	 
		 
        //////////////////////////////////
        ////PATH FOR BFS DFID AND A*/////
       /////////////////////////////////
		 
		 
		 /**
		  * Find the way we came to a solution by ascending the tree until it reached the root.
		  * @param puzzle- the puzzle of the goal we reached
		  */
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
	 
        ////////////////////////////////
        ////PATH FOR IDA* AND DFBnB////
        ///////////////////////////////
	 
	  /**
	   * Finding the way to solve by the puzzles marked out in the stack.
	   * @param frontiers
	   * @param puzzle- the puzzle of the goal we reached. Not in the stack
	   */
	  private void findPathOut(Stack<Puzzle> frontiers, Puzzle puzzle) {
		 Stack<Puzzle> temp= new Stack<Puzzle>();
		 temp.addAll(frontiers);
		 ArrayList<String> moves= new ArrayList<String>();
		 moves.add(puzzle.getMove());
		 while(!temp.isEmpty()) {
			puzzle=temp.pop();
			if(puzzle.isOut())
				moves.add(puzzle.getMove());
		 }
		 StringBuilder path = new StringBuilder();
		 for(int i=moves.size()-2; i>=0;i--) {
			 if(i!=0)
				 path.append(moves.get(i)).append("-");
			 else
				 path.append(moves.get(i));
		 }
		 this.path=path.toString();
	 }
	
	 
	   /////////////////////////
	   ///HEURISTIC FUNCTION///
	   /////////////////////////
	 
	 /**
	  * A heuristic function, built on calculating Manhattan distance according to the colors of the numbers.
	  * @param puzzle
	  * @return the heuristic number of puzzle
	  * The method "public static" for Puzzle_Comperator.
	  */
	 public static int heuristic(Puzzle puzzle) {
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
				 if(puzzle.getRed()!=null &&puzzle.getRed().contains(num))
					 h+=30*(Math.abs(row-correctRow)+Math.abs(col-correctCol));
				 else 
					 h+=Math.abs(row-correctRow)+Math.abs(col-correctCol);
				 
			 }
				 
		 }
		 return h;
	 }
	 
	 
     ///////////////////////
       ////FOR IDA*/////
    //////////////////////
	
	 /**
	  * Initialize the variables at the beginning of each iteration.
	  * @param frontiers
	  * @param openList
	  * @param puzzleToSolve
	  */
	 private void initIDA(Stack<Puzzle> frontiers, HashMap<String, Puzzle> openList, Puzzle puzzleToSolve){
		 puzzleToSolve.setOut(false);
		 frontiers.clear();
		 openList.clear();
		 frontiers.push(puzzleToSolve);
		 openList.put(puzzleToSolve.toString(), puzzleToSolve);
	 }
	 
	 
	 
	 ///////////////////////
	   ////FOR DFBnB/////
	 //////////////////////
	 
	 /**
	  * Creates an array of father's children according to their f-value in increasing order
	  * @param father
	  * @return the array of the children 
	  */
	 private ArrayList<Puzzle> childrenArr(Puzzle father){
		 PriorityQueue<Puzzle> children=new PriorityQueue<Puzzle>(4,new Puzzle_Comperator());
		 ArrayList<Puzzle> childrenArr=new ArrayList<Puzzle>();
		 for (int i = 0; i < strategy.length; i++) {
			 if (father!= null && father.canMove(strategy[i])) {
	        		Puzzle newPuzzle = new Puzzle(father);
	                count++;
	                newPuzzle.move(strategy[i]);
	                children.add(newPuzzle);
			 }
		 }
		 while(!children.isEmpty()) {
			 childrenArr.add(children.poll());
		 }
		 return childrenArr;
	 }
	 
	 
	 /**
	  * Reverse the array of children.
	  * @param children
	  * @return the reverse array
	  */
	 private ArrayList<Puzzle> reverseChildren(ArrayList<Puzzle> children){
	        int size=children .size();
	        ArrayList<Puzzle> temp= new ArrayList<Puzzle>();
	        for(int i=size-1; i>=0; i--) {
	        	temp.add(children.get(i));
	        }
	        return temp;
	        
		 }
	 
	 /**
	  * Calculates the initial threshold. 
	  * @return If the number of slots in correctPuzzle is greater than 12 return Integer.MAX_VALUE, otherwise returns n!.
	  */
	 private int tForStart() {
		 int n=correctPuzzle.length*correctPuzzle[0].length;
		 int factorialN=1;
		 if(n>12)
			 return Integer.MAX_VALUE;
		 else {
			 for(int i=2; i<=n; i++)
				 factorialN*=i;
		 }
		 return factorialN;
			 
	 }
	 
                                  ///////////////////////////////////////////////////////////////////////////////
                                        ///////////////////////////GETTERS/////////////////////////////////////
                                 ///////////////////////////////////////////////////////////////////////////////
	 /**
	  * @return the path to the solution 
	  */
	 public String getPath() {
		 return path;
	 }
	 
	 /**
	  * @return the number of puzzles that created 
	  */
	 public int getCount() {
		 return count;
	 }
	 
	 /**
	  * @return the cost of the path to the solution.
	  */
	 public int getCost() {
		 return cost;
	 }
	 
	 
	 
                                ///////////////////////////////////////////////////////////////////////////////
                                    ///////////////////////////TOSTRING/////////////////////////////////////
                               ///////////////////////////////////////////////////////////////////////////////
	 
	 
	 /**
	  * @return String. If path not found, return "no path" and the number of puzzles that created, otherwise return the path + number of puzzles that created
	  * + the cost of the path.
	  */
	 public String toString() {
		 if(path.equals("no path"))
			 return  path+ "\nNum: "+ count;
		 return path+ "\nNum: "+ count +"\nCost: "+ cost; 
	 }
	 
	 /**
	  * @return String of the correct puzzle.
	  */
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
