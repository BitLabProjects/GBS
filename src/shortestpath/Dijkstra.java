package shortestpath;

import java.util.ArrayList;

public class Dijkstra {
	private Map mMap;
	private Coordinates mStart, mEnd;

	public Dijkstra(Map map) {
		mMap = map;
	}
	
	
	public ArrayList<Coordinates> getPath(Coordinates start, Coordinates end) {
		mStart = start;
		mEnd = end;
		
		final float[] dist = new float[mMap.NumRow * mMap.NumCol]; // shortest known distance from "start"
		final int[] pred = new int[mMap.NumRow * mMap.NumCol]; // preceeding node in path
		
		for (int i = 0; i < dist.length; i++) {
			dist[i] = Float.MAX_VALUE;
			pred[i] = -1;
		}
		dist[start.Y * mMap.NumCol + start.X] = 0;
	    
		ArrayList<Integer> Q = new ArrayList<Integer>();
		for (int i = 0; i < dist.length; i++) {
			Q.add(i);
		}
		
		int endCoord = end.Y * mMap.NumCol + end.X;
		
		while (Q.size() > 0) {
			final int u = minVertex(Q, dist);
			Q.remove(Q.indexOf(u));
	        
			if (dist[u] == Float.MAX_VALUE || u == endCoord) {
	            break ;                                            // tutti i vertici rimanenti sono
			}                                                      // inaccessibili dal nodo sorgente
	        
			final int[] n = getNeighbors(u);
			for (int j = 0; j < n.length; j++) {
				final int v = n[j];
				final float distV = dist[u] + getWeight(u, v);
				if (dist[v] > distV) {
					dist[v] = distV;
					pred[v] = u;
					//TODO Ottimizzare con decrease-key v in Q;            // Riordina v nella coda
				}
			}
		}

		ArrayList<Coordinates> result = new ArrayList<Coordinates>();
		//result.add(start);
		
		int curr = endCoord;
		while (curr != -1) {
			int x = curr % mMap.NumCol;
			int y = (curr - x) / mMap.NumCol;
			result.add(new Coordinates(x, y));
			curr = pred[curr];
		}
		
		return result;
	}
	
	private float getWeight(int u, int v) {
		int x1 = u % mMap.NumCol;
		int y1 = (u - x1) / mMap.NumCol;
		
		int x2 = v % mMap.NumCol;
		int y2 = (v - x2) / mMap.NumCol;
		
		if (x1 != x2 && y1 != y2)
			return (float) Math.sqrt(2);
		return 1;
	}

	private int[] getNeighbors(int next) {
		int x = next % mMap.NumCol;
		int y = (next - x) / mMap.NumCol;
		
		ArrayList<Integer> neighbours = new ArrayList<Integer>();
		maybeAddNeighbour(neighbours, x - 1, y);
		maybeAddNeighbour(neighbours, x + 1, y);
		maybeAddNeighbour(neighbours,     x, y - 1);
		maybeAddNeighbour(neighbours,     x, y + 1);
		maybeAddNeighbour(neighbours, x - 1, y - 1);
		maybeAddNeighbour(neighbours, x - 1, y + 1);
		maybeAddNeighbour(neighbours, x + 1, y - 1);
		maybeAddNeighbour(neighbours, x + 1, y + 1);
		
		int[] result = new int[neighbours.size()];
		for (int j = 0; j < neighbours.size(); j++) {
			result[j] = neighbours.get(j);
		}
		return result;
	}
	
	private void maybeAddNeighbour(ArrayList<Integer> neighbours, int x, int y) {
		if (x < 0 || y < 0 || x >= mMap.NumCol || y >= mMap.NumRow)
			return;
		try {
			if (!mMap.get(x, y))
				neighbours.add(y * mMap.NumCol + x);			
			
		} catch (Exception e) {
			System.out.println();
		}

	}

	private static int minVertex(ArrayList<Integer> Q, float[] dist) {
		int y = Q.get(0);
		float x = dist[y];
		
		for (int i = 1; i < Q.size(); i++) {
			int node = Q.get(i);
			if (dist[node] < x) {
				y = node;
				x = dist[node];
			}
		}
		return y;
	}
}
