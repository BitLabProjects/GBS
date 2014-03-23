package shortestpath;

import java.util.ArrayList;
import java.util.Random;

import engine.Vector;

public class Map {
	private boolean mMatrix[][];
	public final int NumRow, NumCol;

	public Map(int numRow, int numCol) {
		NumRow = numRow;
		NumCol = numCol;
		mMatrix = new boolean[NumCol][NumRow];
	}

	public void initRandom() {
		Random r = new Random();
		for (int i = 0; i < NumRow; i++) {
			for (int j = 0; j < NumCol; j++) {
				mMatrix[i][j] = r.nextBoolean() ? r.nextBoolean() : false;
			}
		}
	}
	
	public boolean get(int i, int j) {
		return mMatrix[i][j];
	}

	public void addObstacle(int x, int y, int dimX, int dimY) {
		if (x + dimY <= NumRow && y + dimX <= NumCol) {
			for (int i = x; i < x + dimY; i++) {
				for (int j = y; j < y + dimX; j++) {
					mMatrix[j][i] = true;
				}
			}
		}
	}
	
	public void print(ArrayList<Coordinates> path)
	{
		for(int i = 0 ; i < NumRow ; i++)
		{
			for(int j = 0 ; j < NumCol ; j++)
			{
				if (mMatrix[i][j])
					System.out.print("*");
				else {
					Boolean isInPath = false;
					for(Coordinates c : path) {
						if (c.equals(new Coordinates(i,j))) {
							isInPath = true;
							break;
						}
					}
					System.out.print(isInPath ? "x" : " ");
				}
			}
			System.out.println("");
		}
	}

	public ArrayList<Vector> getPathInWorld(ArrayList<Coordinates> pathInMap, int scale) {
		ArrayList<Vector> result = new ArrayList<Vector>();
		for (Coordinates coord : pathInMap) {
			result.add(new Vector(coord.X * scale + scale / 2,
					              coord.Y * scale + scale / 2));
		}
		return result;
	}
}