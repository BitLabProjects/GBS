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
		
		final int[] dist = new int[mMap.NumRow * mMap.NumCol]; // shortest known distance from "start"
		final int[] pred = new int[mMap.NumRow * mMap.NumCol]; // preceeding node in path
		
		for (int i = 0; i < dist.length; i++) {
			dist[i] = Integer.MAX_VALUE;
			pred[i] = -1;
		}
		dist[start.Y * mMap.NumCol + start.X] = 0;
	    
		ArrayList<Integer> Q = new ArrayList<Integer>();
		for (int i = 0; i < dist.length; i++) {
			Q.add(i);
		}
		
		while (Q.size() > 0) {
			final int u = minVertex(Q, dist);
			Q.remove(Q.indexOf(u));
	        
			if (dist[u] == Integer.MAX_VALUE) {
	            break ;                                            // tutti i vertici rimanenti sono
			}                                                      // inaccessibili dal nodo sorgente
	        
			final int[] n = getNeighbors(u);
			for (int j = 0; j < n.length; j++) {
				final int v = n[j];
				final int distV = dist[u] + 1; //+ G.getWeight(next, v);
				if (dist[v] > distV) {
					dist[v] = distV;
					pred[v] = u;
					//TODO Ottimizzare con decrease-key v in Q;            // Riordina v nella coda
				}
			}
		}

		ArrayList<Coordinates> result = new ArrayList<Coordinates>();
		//result.add(start);
		
		int curr = end.Y * mMap.NumCol + end.X;
		while (curr != -1) {
			int x = curr % mMap.NumCol;
			int y = (curr - x) / mMap.NumCol;
			result.add(new Coordinates(x, y));
			curr = pred[curr];
		}
		
		return result;
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
		if (!mMap.get(x, y))
			neighbours.add(y * mMap.NumCol + x);
	}

	private static int minVertex(ArrayList<Integer> Q, int[] dist) {
		int x = Integer.MAX_VALUE;
		int y = Q.get(0); // graph not connected, or no unvisited vertices
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
