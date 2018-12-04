package mazeSolver;

import maze.Maze;

/**
 * Interface of a maze solveer.
 * 
 * @author Youhan Xia
 * @author Jeffrey Chan
 * @author Yongli Ren
 */
public interface MazeSolver {

	/**
	 * constants which are common to any type of maze
	 */
	// types of maze;
	public final static int NORMAL = 0;
	public final static int TUNNEL = 1;
	public final static int HEX = 2;
	//	directions used for indices
	public final static int EAST = 0;
	public final static int NORTHEAST = 1;
	public final static int NORTHWEST = 2;
	public final static int NORTH = 2;
	public final static int WEST = 3;
	public final static int SOUTHWEST = 4;
	public final static int SOUTHEAST = 5;
	public final static int SOUTH = 5;
	public final static int NUM_DIR = 6;
	// used for move along a deriction, for both square and hexagon
	// e.g., the northeast neighbor of map[r][c] would be map[r + deltaR[NORTHEAST][c + deltaC[NORTHEAST]]
	public final static int deltaR[] = { 0, 1, 1, 0, -1, -1 };
	public final static int deltaC[] = { 1, 1, 0, -1, -1, 0 };
	public final static int oppoDir[] = { 3, 4, 5, 0, 1, 2 };

	/**
	 * Find the solusion on given maze.
	 * @param maze The maze to solve.
	 */
	public abstract void solveMaze(Maze maze);
	

	/**
	 * Use after solveMaze(maze), to check whether the maze is solved.
	 * @return True if solved. Otherwise false.
	 */
	public abstract boolean isSolved();

	
	/**
	 * Use after solveMaze(maze), counting the number of cells explored in solving process.
	 * @return The number of cells explored.
	 * It is not required to be accurate and no marks are given (or lost) on it. 
	 */
	public abstract int cellsExplored();
} // end of interface mazeGenerator
