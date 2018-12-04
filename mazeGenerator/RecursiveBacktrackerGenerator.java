package mazeGenerator;

import java.util.*;
import maze.Maze;
import maze.Cell;

public class RecursiveBacktrackerGenerator implements MazeGenerator {
	private boolean[][] visited; // visited array for generation
	private int visitedCount; // how many empty spaces have been visited in the maze
	
	@Override
	public void generateMaze(Maze maze) {
		ArrayDeque<Cell> q = new ArrayDeque<>();//save the visited cell
		Cell cell = new Cell(maze.entrance.r, maze.entrance.c);
		q.push(cell);
		visited = new boolean[maze.sizeR][maze.sizeC];
		visited[maze.entrance.r][maze.entrance.c] = true;
		int visits = 0;
		int rand = 0;
		if (maze.type == NORMAL || maze.type == TUNNEL){
			while (visits < visitedCount || !q.isEmpty()) {
				while ((cell.c + 1 < maze.sizeC && !visited[cell.r][cell.c + 1]) ||
						(cell.c - 1 >= 0 && !visited[cell.r][cell.c - 1]) ||
						(cell.r + 1 < maze.sizeR && !visited[cell.r + 1][cell.c]) ||
						(cell.r - 1 >= 0 && !visited[cell.r - 1][cell.c]) ||
						(maze.map[cell.r][cell.c].tunnelTo != null && !visited[maze.map[cell.r][cell.c].tunnelTo.r][maze.map[cell.r][cell.c].tunnelTo.c])) {
//					System.out.println("Direction: " + rand + " x: " + cell.c + " y: " + cell.r);
//					System.out.println("--------------------------------");
					if (maze.type == NORMAL)
						rand = (int) (Math.random() * 4 + 1);
					if (maze.type == TUNNEL)
						rand = (int) (Math.random() * 5 + 1);

					// check up
					if (rand == 1 && cell.r + 1 < maze.sizeR && !visited[cell.r + 1][cell.c]) {
						// remove the upper wall
						maze.map[cell.r][cell.c].wall[NORTH].present = false;
						Cell nxCell = new Cell(cell.r + 1, cell.c);
						visited[cell.r + 1][cell.c] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
					// check down
					if (rand == 2 && cell.r - 1 >= 0 && !visited[cell.r - 1][cell.c]) {
						// remove the wall below
						maze.map[cell.r][cell.c].wall[SOUTH].present = false;
						Cell nxCell = new Cell(cell.r - 1, cell.c);
						visited[cell.r - 1][cell.c] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
					// check left
					if (rand == 3 && cell.c - 1 >= 0 && !visited[cell.r][cell.c - 1]) {
						// remove the west wall
						maze.map[cell.r][cell.c].wall[WEST].present = false;
						Cell nxCell = new Cell(cell.r, cell.c - 1);
						visited[cell.r][cell.c - 1] = true;
//						System.out.println("This is rand 3: x: " + nxCell.c + ", y: " + nxCell.r);
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
					// check right
					if (rand == 4 && cell.c + 1 < maze.sizeC && !visited[cell.r][cell.c + 1]) {
						// removed the wall
						maze.map[cell.r][cell.c].wall[EAST].present = false;
						Cell nxCell = new Cell(cell.r, cell.c + 1);
						visited[cell.r][cell.c + 1] = true;
//						System.out.println("This is rand 4: x: " + nxCell.c + ", y: " + nxCell.r);
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
					// if has tunnel
					if (maze.map[cell.r][cell.c].tunnelTo != null && !visited[maze.map[cell.r][cell.c].tunnelTo.r][maze.map[cell.r][cell.c].tunnelTo.c]) {
						visited[maze.map[cell.r][cell.c].tunnelTo.r][maze.map[cell.r][cell.c].tunnelTo.c] = true;
						q.add(maze.map[cell.r][cell.c].tunnelTo);
						visits++;
						cell = maze.map[cell.r][cell.c].tunnelTo;
					}
				}
				if (!q.isEmpty()) {
					cell = q.pop();
				}
			}
		}

		if(maze.type == HEX){
			while (visits < visitedCount || !q.isEmpty()){
				while((cell.c+(1/2) < maze.sizeC && !visited[cell.r][cell.c+(1/2)])||
						(cell.c-(1/2) >= 0 && !visited[cell.r][cell.c-(1/2)])||
						(cell.r+1 < maze.sizeR && !visited[cell.r+1][cell.c])||
						(cell.r-1 >= 0 && !visited[cell.r-1][cell.c])) {
					int HexRand	= (int)(Math.random()* 6 + 1);

					//check East
					//same row, one cell right, so + 1
					if (HexRand == 1 && cell.c+1 < maze.sizeC && !visited[cell.r][(cell.c+1)]) {
						// remove the east wall
						maze.map[cell.r][cell.c].wall[EAST].present = false;
						Cell nxCell = new Cell(cell.r, cell.c+1);
						visited[cell.r][cell.c+1] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
					//check NorthEast
					//one row up, half cell right, so r + 1, c + 1/2
					if (HexRand == 2 && cell.r+1 < maze.sizeR && !visited[cell.r+1][cell.c+(1/2)]) {
						// remove the northeast wall
						maze.map[cell.r][cell.c].wall[NORTHEAST].present = false;
						Cell nxCell = new Cell(cell.r+1, cell.c+(1/2));
						visited[cell.r+1][cell.c+(1/2)] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}

					//check NorthWest
					//one row up, half cell left
					if (HexRand == 3 && cell.r+1 < maze.sizeR && !visited[cell.r+1][cell.c-(1/2)]) {
						// remove the northwest wall
						maze.map[cell.r][cell.c].wall[NORTHWEST].present = false;
						Cell nxCell = new Cell(cell.r+1, cell.c-(1/2));
						visited[cell.r+1][cell.c+(1/2)] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}


					//check West
					//same row, one cell left
					if (HexRand == 4 && cell.c-1 >= 0 && !visited[cell.r][cell.c-1]) {
						// remove the west wall
						maze.map[cell.r][cell.c].wall[WEST].present = false;
						Cell nxCell = new Cell(cell.r, cell.c-1);
						visited[cell.r][cell.c-1] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}

					//check SouthWest
					//one row down, half cell left
					if (HexRand == 5 && cell.c-(1/2) >= 0 && !visited[cell.r-1][cell.c-(1/2)]) {
						// remove the west wall
						maze.map[cell.r][cell.c].wall[SOUTHWEST].present = false;
						Cell nxCell = new Cell(cell.r-1, cell.c-(1/2));
						visited[cell.r][cell.c-(1/2)] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}

					//check SouthEast
					//one row down, half cell right
					if (HexRand == 5 && cell.c+(1/2) >= 0 && !visited[cell.r-1][cell.c+(1/2)]) {
						// remove the west wall
						maze.map[cell.r][cell.c].wall[SOUTHEAST].present = false;
						Cell nxCell = new Cell(cell.r-1, cell.c+(1/2));
						visited[cell.r][cell.c+(1/2)] = true;
						q.add(nxCell);
						visits++;
						cell = nxCell;
					}
				}
			}
		}
	} // end of generateMaze()
} // end of class RecursiveBacktrackerGenerator