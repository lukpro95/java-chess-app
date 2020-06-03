package pieces.com;

import java.util.ArrayList;

public class DetailedInformation {
	
	private ArrayList<Integer> allies = new ArrayList<>();
	private ArrayList<Integer> enemies = new ArrayList<>();
	private int activePiece;
	private String kind;
	private String team;
	
	public DetailedInformation(int activePiece) {
		this.activePiece = activePiece;
		
		setType();
		setTeam();
		setFriendliness();
	}
	
	private void setType() {
		
		int[] King = {16, -16};
		int[] Queen = {15, 51, 52, 53, 54, 55, 56, 57, 58, -15, -51, -52, -53, -54, -55, -56, -57, -58};
		int[] Rook = {13, 14, 41, 42, 43, 44, 45, 46, 47, 48, -13, -14, -41, -42, -43, -44, -45, -46, -47, -48};
		int[] Bishop = {11, 12, 31, 32, 33, 34, 35, 36, 37, 38, -11, -12, -31, -32, -33, -34, -35, -36, -37, -38};
		int[] Knight = {9, 10, 21, 22, 23, 24, 25, 26, 27, 28, -9, -10, -21, -22, -23, -24, -25, -26, -27, -28};
		int[] Pawn = {1, 2, 3, 4, 5, 6, 7, 8, -1, -2, -3, -4 , -5, -6, -7 , -8};
		
		for(Integer active: King) {
			if(active == activePiece) {
				kind = "King";
			}
		}
		
		for(Integer active: Queen) {
			if(active == activePiece) {
				kind = "Queen";
			}
		}
		
		for(Integer active: Rook) {
			if(active == activePiece) {
				kind = "Rook";
			}
		}
		
		for(Integer active: Bishop) {
			if(active == activePiece) {
				kind = "Bishop";
			}
		}
		
		for(Integer active: Knight) {
			if(active == activePiece) {
				kind = "Knight";
			}
		}
		
		for(Integer active: Pawn) {
			if(active == activePiece) {
				kind = "Pawn";
			}
		}
		
	}
	
	private void setTeam() {
		if(activePiece > 0) {
			team = "White";
		}
		else {
			team = "Black";
		}
	}
	
	private void setFriendliness() {
		
		if(team.equals("White")) {
			for(int x = 1; x <= 60; x++) {
				allies.add(x);
				enemies.add(-x);
			}
		}
		
		if(team.equals("Black")) {
			for(int x = -1; x >= -60; x--) {
				allies.add(x);
				enemies.add(-x);
			}
		}
		
	}
	
	public String getType() {
		return this.kind;
	}
	
	public String getTeam() {
		return this.team;
	}
	
	public ArrayList<Integer> getAllies() {
		return this.allies;
	}
	
	public ArrayList<Integer> getEnemies() {
		return this.enemies;
	}
	
}
