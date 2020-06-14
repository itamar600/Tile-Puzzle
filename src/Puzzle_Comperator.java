import java.util.Comparator;


/**
 * This class defines the choice between two puzzles, first of all if they have different f values, 
 * while f its the cost of the puzzle + the value of its heuristic function, then you choose the puzzle with the low value, 
 * otherwise the puzzle whose depth is smaller, otherwise according to the order of directions if one puzzle is created 
 * by the left direction and the other one is not ,selected the first, Otherwise if the direction is up and the other one is not, 
 * selected the first, otherwise if the direction is right and the other one is not selected the first, otherwise the order of choice is not important.
 * @author Itamar Ziv-On
 *
 */
public class Puzzle_Comperator implements Comparator<Puzzle> { 

		public Puzzle_Comperator() {;}
		public int compare(Puzzle p1, Puzzle p2) {
			int dp = (p1.getCost()+Algorithms.heuristic(p1)) - (p2.getCost()+Algorithms.heuristic(p2));
			if(dp!=0) {
				return dp;
			}
			dp=p1.getDepth()-p2.getDepth();
			if(dp!=0)
				return dp;
			//the directions is the directions of the empty tile, therefore the directions are opposite.
			if(p1.getLastMove()=='R' && p2.getLastMove()!='R')
				return -1;
			if(p2.getLastMove()=='R' && p1.getLastMove()!='R')
				return 1;
			if(p1.getLastMove()=='D' && p2.getLastMove()!='D')
				return -1;
			if(p2.getLastMove()=='D' && p1.getLastMove()!='D')
				return 1;
			if(p1.getLastMove()=='L' && p2.getLastMove()!='L')
				return -1;
			if(p2.getLastMove()=='L' && p1.getLastMove()!='L')
				return 1;
			return 0;
		}
}
