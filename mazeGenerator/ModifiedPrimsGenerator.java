package mazeGenerator;

import java.util.ArrayList;
import java.util.*;
import maze.Maze;
import maze.Cell;


public class ModifiedPrimsGenerator implements MazeGenerator {

	@Override
	public void generateMaze(Maze maze) {
		Random random = new Random();

		// add start cell
		ArrayList<Cell> C = new ArrayList<>();

		C.add(maze.entrance); //add to cell
		maze.entrance.isVisited = true;

		//available neighbor of cell
		ArrayList<Cell> temp = new ArrayList<>();
		//create frontier to store all neighbors
		ArrayList<Cell> frontier = new ArrayList<>();

		//store start cell
		for (int i = 0; i < 6; i++) {
			if (maze.entrance.neigh[i] != null) {
				frontier.add(maze.entrance.neigh[i]);
			}
		}

		while(frontier.size() != 0){
			//randomly select a cell from the frontier and remove it
			int indexF = random.nextInt(frontier.size());

			Cell neighborF = frontier.get(indexF);
			frontier.remove(neighborF);
			neighborF.isVisited = true;

			//find available neighbor of current neighbourF
			for (int i = 0; i < 6; i++) {
				if (neighborF.neigh[i] != null && !(neighborF.neigh[i].isVisited)) {
					temp.add(neighborF.neigh[i]);
				}
			}

			//remove the wall
			if (maze.type == NORMAL || maze.type == TUNNEL) {
				Integer[] directions = new Integer[4];
				directions[0] = new Integer(EAST);
				directions[1] = new Integer(NORTH);
				directions[2] = new Integer(WEST);
				directions[3] = new Integer(SOUTH);
				java.util.Collections.shuffle(java.util.Arrays.asList(directions));

				if (neighborF != null) {
					for (int i = 0; i < 4; i++) {
						int dir = directions[i].intValue();
						if (neighborF.neigh[dir] != null && neighborF.neigh[dir].isVisited) {
							neighborF.wall[dir].present = false;
							neighborF.neigh[dir].wall[oppoDir[dir]].present = false;
							break;
						}
					}
				}
			}
			if (maze.type == HEX) {
				Integer[] directions = new Integer[6];
				for (int i = 0; i < NUM_DIR; ++i) {
					directions[i] = new Integer(i);
				}
				java.util.Collections.shuffle(java.util.Arrays.asList(directions));

				if (neighborF != null) {
					for (int i = 0; i < NUM_DIR; ++i) {
						int dir = directions[i].intValue();
						if (neighborF.neigh[dir] != null && neighborF.neigh[dir].isVisited) {
							neighborF.wall[dir].present = false;
							neighborF.neigh[dir].wall[oppoDir[dir]].present = false;
							break;
						}
					}
				}
			}

			//Add cell to Cell set C
			C.add(neighborF);

			boolean isDuplicate = false;
			for (int i = 0; i < temp.size(); i++) {
				for (int j = 0; j < frontier.size(); j++) {
					if ((temp.get(i).r == frontier.get(j).r) && (temp.get(i).c == frontier.get(j).c)) {
						isDuplicate = true;
					}
				}
				if (!isDuplicate) {
					if (!temp.get(i).isVisited) {
						frontier.add(temp.get(i));
					}
				}
			}

			temp.clear();

			for (int i = 0; i < 6; i++) {
				if (neighborF.neigh[i] != null) {
					if (!neighborF.neigh[i].isVisited && (!frontier.contains(neighborF.neigh[i]))) {
						frontier.add(neighborF.neigh[i]);
					}
				}
			}
		}
	} //end of generateMaze()
} // end of class ModifiedPrimsGeneratortor
