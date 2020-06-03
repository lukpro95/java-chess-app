package movement.com;

public class ListOfMoves {

	private int active;
	private int x;
	private int y;
	
	public ListOfMoves(int active, int x, int y) {
		
		this.active = active;
		this.x = x;
		this.y = y;
		
	}
	
	public int getActive() {
		return this.active;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}