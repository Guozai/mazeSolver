package mazeGenerator;

import maze.Maze;

/**
 * Interface of a maze generator.
 * 
 * @author Youhan Xia
 * @author Jeffrey Chan
 * @author Yongli Ren
 */
public interface MazeGenerator {

	/**
	 * constants which are common to any type of mazes
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
	 * Function that generate a perfect maze from an all-wall initialized maze.
	 * @param maze The reference of Maze object to generate. 
	 */
	public void generateMaze(Maze maze);
} // end of interface mazeGenerator