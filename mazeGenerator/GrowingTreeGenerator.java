package mazeGenerator;

import java.util.*;

import maze.Maze;
import maze.Cell;

public class GrowingTreeGenerator implements MazeGenerator {
	// Growing tree maze generator. As it is very general, here we implement
	// as "usually pick the most recent cell, but occasionally pick a random cell"

	double threshold = 0.1;
	private int[][] visited;

	@Override
	public void generateMaze(Maze maze) {
		ArrayList cells = new ArrayList();
		visited = new int[maze.sizeR][maze.sizeC];
		// initialize the grid
		for (int i = 0; i < maze.sizeC; ++i) {
			for (int j = 0; j < maze.sizeR; ++j) {
				visited[j][i] = 0;
			}
		}

		// Set random cell as the starting point
		Cell startCell = new Cell((int)(Math.random() * maze.sizeR), (int)(Math.random() * maze.sizeC));
		cells.add(startCell);
		// maze entrance is visited.
		visited[startCell.r][startCell.c] = 1;

		// start of growing tree algorithm
		while (cells.size() > 0) {
			// backtrack to newest cell
//			int index = cells.size() - 1;
			int index = chooseIndex(cells);
			Cell cell = (Cell) cells.get(index);
//				System.out.println("index; " + index);
			int x = cell.c;
			int y = cell.r;

			// shuffle possible directions
			if (maze.type == NORMAL) {
				Integer[] directions = new Integer[4];
				directions[0] = new Integer(EAST);
				directions[1] = new Integer(NORTH);
				directions[2] = new Integer(WEST);
				directions[3] = new Integer(SOUTH);
				java.util.Collections.shuffle(java.util.Arrays.asList(directions));

				digPass(4, x, y, maze, index, cells, directions);
			} else if (maze.type == HEX) {
				Integer[] directions = new Integer[NUM_DIR];
				for (int i = 0; i < NUM_DIR; ++i) {
					directions[i] = new Integer(i);
				}
				java.util.Collections.shuffle(java.util.Arrays.asList(directions));

				digPass(6, x, y, maze, index, cells, directions);
			} else {
				System.out.println("Unknown maze type.");
				break;
			}
		}

	}

	private void digPass(int dirNum, int x, int y, Maze maze, int index, ArrayList cells, Integer[] directions) {

		// Try remove a wall of a random direction
		for (int k = 0; k < dirNum; ++k) {
			int dir = directions[k].intValue();
			int nx = x + deltaC[dir];
			int ny = y + deltaR[dir];

			// if new cell is unvisited carve passage
			if (nx >= 0 && ny >= 0 && nx < maze.sizeC && ny < maze.sizeR && visited[ny][nx] == 0) {
//				System.out.println("Before: visited[y][x]: " + visited[y][x] + ", dir: " + dir);
				visited[y][x] |= dir;
//				System.out.println("After: visited[y][x]: " + visited[y][x]);
//				System.out.println("Before: visited[ny][nx]: " + visited[ny][nx] + ", oppoDir: " + oppoDir[dir]);
				visited[ny][nx] |= oppoDir[dir];
//				System.out.println("After: visited[ny][nx]: " + visited[ny][nx]);

				cells.add(new Cell(ny, nx));
				visited[ny][nx] = 1;
				visited[ny][nx] = 1;
//				maze.drawFtPrt(currCell);
				index = -1;

				Cell lastCell = new Cell(y, x);
//				System.out.println("lastCell.wall[dir] != null = " + (maze.map[y][x].wall[dir] != null));
				if (maze.map[y][x].wall[dir] != null)
					maze.map[y][x].wall[dir].present = false;
				break;
			}
		}

//		System.out.println("index: " + index);
		// dead end: remove cell from list
		if (index != -1) {
			cells.remove(index);
		}
	}

	private int chooseIndex(ArrayList cells) {
		Random rand = new Random();
		if (Math.random() > threshold) {
			return cells.size() - 1;
		} else {
			return rand.nextInt(cells.size());
		}
	}
}
