package shortestpath;

public class Coordinates {
	public int X, Y;
	
	public Coordinates(int x, int y) {
		this.X = x;
		this.Y = y;
	}
	
	public Boolean equals(Coordinates other) {
		return (X == other.X) && (Y == other.Y);
	}
}
