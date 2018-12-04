package mazeSolver;

import java.util.*;
import maze.Maze;
import maze.Cell;

/**
 * Implements WallFollowerSolver
 */

public class WallFollowerSolver implements MazeSolver {
	LinkedList<Cell> queue = new LinkedList<Cell>();
	private int exitR;
	private int exitC;

	@Override
	public void solveMaze(Maze maze) {
		Cell c = new Cell();
		c = maze.entrance;
		maze.entrance.isVisited = true;
		queue.add(c);
		int direction = NORTH;

		// random original direction can go
		for (int i = 0; i < NUM_DIR; ++i) {
			if (i == 1 || i == 4)
				continue;
			if (c.neigh[i] != null) {
				// if can move to the direction
				if (!c.wall[i].present) {
					// move to the neighbour cell
					direction = i;
					c = c.neigh[i];
					queue.add(c);
					break;
				}
			}
		}

		while(c.c != maze.exit.c || c.r != maze.exit.r) {
			for (int i = 0; i < NUM_DIR; ++i) {
				if (i == 1 || i == 4)
					continue;
				if (c.neigh[i] != null) {
					if (direction == EAST) {
						// try to turn left
						direction = NORTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else try direct
						direction = EAST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else try to turn right
						direction = SOUTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else -> no way to go, turn back
						direction = WEST;
						if (!c.wall[direction].present) {
							queue.remove(c);
							c = c.neigh[direction];
							break;
						}
					}
					if (direction == SOUTH) {
						// try to turn left
						direction = EAST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else try direct
						direction = SOUTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else try to turn right
						direction = WEST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						// else -> no way to go, turn back
						direction = NORTH;
						if (!c.wall[direction].present) {
							queue.remove(c);
							c = c.neigh[direction];
							break;
						}
					}
					if (direction == WEST) {
						direction = SOUTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = WEST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = NORTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = EAST;
						if (!c.wall[direction].present) {
							queue.remove(c);
							c = c.neigh[direction];
							break;
						}
					}
					if (direction == NORTH) {
						direction = WEST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = NORTH;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = EAST;
						if (!c.wall[direction].present) {
							c = c.neigh[direction];
							queue.add(c);
							break;
						}
						direction = SOUTH;
						if (!c.wall[direction].present) {
							queue.remove(c);
							c = c.neigh[direction];
							break;
						}
					}
				}
			}
		}

		for (int i = 0; i < queue.size(); ++i) {
			maze.drawFtPrt(queue.get(i));
		}

		// preparation for isSolved()
		exitR = maze.exit.r;
		exitC = maze.exit.c;
	} // end of solveMaze()

	@Override
	public boolean isSolved() {
		if(queue.get(queue.size() - 1).r == exitR && queue.get(queue.size() - 1).c == exitC)
			return true;
		return false;
	} // end if isSolved()
    
	@Override
	public int cellsExplored() {
		return queue.size();
	} // end of cellsExplored()

} // end of class WallFollowerSolver
