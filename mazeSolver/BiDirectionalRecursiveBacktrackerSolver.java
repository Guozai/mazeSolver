package mazeSolver;

import maze.Maze;

/**
 * Implements the BiDirectional recursive backtracking maze solving algorithm.
 */
public class BiDirectionalRecursiveBacktrackerSolver implements MazeSolver {
	// modified maze
	private char[][] grid;

 	private String solution;

	@Override
	public void solveMaze(Maze maze) {
		grid = new char[3 * maze.sizeC][3 * maze.sizeR];
		initializeGrid(maze);
		for (int i = 0; i < 3*maze.sizeC; ++i) {
			for (int j = 0; j < 3*maze.sizeR; ++j) {
				System.out.print(" " + grid[j][i]);
			}
			System.out.println();
		}
		int[] startPoint = findStart(grid);
		solution = findPath(grid, startPoint);
		System.out.println("The solution for the maze is: " + solution + "\n");

//		entranceX = maze.entrance.c;
//		entranceY = maze.entrance.r;
	} // end of solveMaze()

	private int[] findStart(char[][] grid) {
		int[] start = {0, 0};
		for (int i = 0; i < grid.length; ++i) {
			for (int j = 0; j < grid.length; ++j) {
				if (grid[j][i] == 'e') {
					start[0] = i;
					start[1] = j;
				}
			}
		}
		if (start[0] == 0 && start[1] == 0) {
			return null;
		}
		return start;
	}

	public String findPath(char[][] grid, int[] startPoint) {
		int r = startPoint[0];
		int c = startPoint[1];
		String s = recPath(grid, r, c);
		grid[r][c] = 'e';
		return s;
	}

	// recursive backtracking algorithm
	private String recPath(char[][] grid, int r, int c) {
		String string = "";
		if (r > grid.length || r > grid[0].length || r < 0 || c < 0) {
			return "";
		}
		if (grid[r][c] == 'w' || grid[r][c] == '.') {
			return "";
		}
		if (grid[r][c] == 'f') {
			return "f";
		}
		else {
			grid[r][c] = '.';
			string = recPath(grid, r-1, c);
			if (string.contains("f")) {
				return "U" + string;
			}
			string = recPath(grid, r, c+1);
			if (string.contains("f")) {
				return "R" + string;
			}
			string = recPath(grid, r+1, c);
			if (string.contains("f")) {
				return "D" + string;
			}
			string = recPath(grid, r, c-1);
			if (string.contains("f")) {
				return "L" + string;
			}
			else {
				grid[r][c] = ' ';
				return "";
			}
		}
	}

	/**
	 * Convert each cell of the original maze to 3 * 3 array for normal maze
	 * Also will convert the hex maze map
	 */
	private void initializeGrid(Maze maze) {
		// initialize byteMap as blank
		for (int x = 0; x < 3*maze.sizeC; ++x) {
			for (int y =0; y < 3*maze.sizeR; ++y) {
				grid[y][x] = 'w';
			}
		}

		for (int x = 0; x < maze.sizeC; ++x) {
            for (int y = 0; y < maze.sizeR; ++y) {
            	grid[1+3*y][1+3*x] = 'p';
                for (int i = 0; i < NUM_DIR; ++i) {
                    if (maze.map[y][x].wall[i] != null && !maze.map[y][x].wall[i].present) {
                        if (i == 0) {
                            grid[1+3*y][1+3*x + 1] = 'p';
                        }
                        if (i == 2) {
                            grid[1+3*y + 1][1+3*x] = 'p';
                        }
                        if (i == 3) {
                            grid[1+3*y][1+3*x - 1] = 'p';
                        }
                        if (i == 5) {
                            grid[1+3*y - 1][1+3*x] = 'p';
                        }
                    }
                }
				if (x == maze.entrance.c && y == maze.entrance.r) {
					grid[1+3*y][1+3*x] = 'e';
				}
				if (x == maze.exit.c && y == maze.exit.r) {
                	grid[1+3*y][1+3*x] = 'f';
				}
			}
        }
	}

	@Override
	public boolean isSolved() {
		if(solution.contains("f"))
			return true;
		return false;
	} // end if isSolved()


	@Override
	public int cellsExplored() {
		return solution.length() - 1;
	} // end of cellsExplored()

} // end of class BiDirectionalRecursiveBackTrackerSolver
