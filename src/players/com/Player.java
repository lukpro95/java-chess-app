package players.com;

import java.util.Random;

public class Player {
	
	private String 		name 	= "";
	private boolean 	real 	= false;
	private boolean		plays 	= false;
	private String 		team 	= "";
	private String 		condition = "";
	
	public Player() {
		randomName();
		this.real = false;
	}
	
	public Player(String name) {
		this.name = name;
		this.real = true;
	}
	
	private void randomName() {
		String[] names = {"Bob", "Slayer", "Sigimund", "Triss", "Van Korken", "Willhelm"};
		String[] adjectives = {"The Merciful", "The Powerful", "The Pitiful", "The Baddest"};
		
		Random random = new Random();
		int randomName = random.nextInt(names.length+0)-0;
		int randomAdjective = random.nextInt(adjectives.length+0)-0;
		
		this.name = names[randomName] + ", " + adjectives[randomAdjective];
	}
	
	// -------------------------- Setters ---------------------------
	
	public void setTeam(String team) {
		this.team = team;
	}
	
	public void setPlays(boolean plays) {
		this.plays = plays;
	}
	
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	// --------------------------- Getters ---------------------------

	public String getName() {
		return name;
	}
	
	public boolean doesPlay() {
		return plays;
	}
	
	public boolean isReal() {
		return real;
	}
	
	public String getTeam() {
		return team;
	}
	
	public String getCondition() {
		return condition;
	}
	
}
