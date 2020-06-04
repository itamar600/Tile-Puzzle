import java.util.Comparator;



public class Puzzle_Comperator implements Comparator<Puzzle> { 

		public Puzzle_Comperator() {;}
		public int compare(Puzzle p1, Puzzle p2) {
			int dp = (p1.getCost()+Algorithms.heuristic(p1)) - (p2.getCost()+Algorithms.heuristic(p2));
			if(dp!=0) {
				return dp;
			}
			dp=p1.getPathLength()-p2.getPathLength();
			if(dp!=0)
				return dp;
			if(p1.getLastMove()=='R')
				return -1;
			if(p2.getLastMove()=='R')
				return 1;
			if(p1.getLastMove()=='D')
				return -1;
			if(p2.getLastMove()=='D')
				return 1;
			if(p1.getLastMove()=='L')
				return -1;
			if(p2.getLastMove()=='L')
				return 1;
			return 0;
		}
}
