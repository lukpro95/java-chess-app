package settings.com;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Settings {
	
	private File 				myFile 			= null;
	private BufferedReader 		reader			= null;
	private String 				cfgPresets 		= null;
	
	// Player Settings
	private String name;
	
	// Board Settings (GUI)
	private String whiteSquares;
	private String blackSquares;
	private String portServer;
	
	public Settings() {
		
		try {
			createPresets();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		loadPresets();
		
	}
	
	private void createPresets() throws FileNotFoundException {
		
		myFile = new File("CFG//Settings.CFG");
		
		// Creating an essential CFG file
		createCFG();
	    
	}
	
	private void createCFG() {
	    try {
	        if (myFile.createNewFile()) {
	          System.out.println("Setting CFG created: " + myFile.getName());
	          
	  	    // Getting important String to save into CFG
	  	    createImportantLines();
	  	    
	  		reader = new BufferedReader(new FileReader(myFile));
	  	    
	  	    // Writing to the CFG file
	  	    writeToFile();
	          
	        } else {
	          System.out.println("Setting CFG already exists!");
	        }
	    } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
	}
	    
	private void createImportantLines() {
		
		cfgPresets = "";
		
		cfgPresets += "/// Player Settings";
		cfgPresets += "\n\n";
		cfgPresets += "p_name	Player";
		cfgPresets += "\n\n\n";
		
		cfgPresets += "/// Board Settings";
		cfgPresets += "\n\n";
		cfgPresets += "b_white	#F0F0F0";
		cfgPresets += "\n";
		cfgPresets += "b_black	#606069";
		cfgPresets += "\n\n\n";
		
		cfgPresets += "/// Server Settings";
		cfgPresets += "\n\n";
		cfgPresets += "s_port	5599";
		
	}
	
	private void writeToFile() {
	    try {
	        FileWriter myWriter = new FileWriter(myFile);
	        myWriter.write(cfgPresets);
	        reader.close();
	        myWriter.close();
	        System.out.println("Successfully wrote to the file.");
	    } catch (IOException e) {
	        System.out.println("An error occurred.");
	        e.printStackTrace();
	    }
	}
	
	public void loadPresets() {
		
		int x = 0;
		
		try {
			while(x < Files.readAllLines(Paths.get("CFG/Settings.CFG")).size()) {
				String line = Files.readAllLines(Paths.get("CFG/Settings.CFG")).get(x);
				
				if(line.contains("p_name")) {
					String name = line;
					this.name = name.substring(name.indexOf("	")+1, name.length());
					
					System.out.println("Name: " + this.name);
				}
				
				if(line.contains("b_white")) {
					String squares = line;
					this.whiteSquares = squares.substring(squares.indexOf("	")+1, squares.length());
					
					System.out.println("White squares: " + this.whiteSquares);
				}
				
				if(line.contains("b_black")) {
					String squares = line;
					this.blackSquares = squares.substring(squares.indexOf("	")+1, squares.length());
					
					System.out.println("Black squares: " + this.blackSquares);
				}
				
				if(line.contains("s_port")) {
					String port = line;
					this.portServer = port.substring(port.indexOf("	")+1, port.length());
					
					System.out.println("Servers's PORT: " + this.portServer);
				}
				
				x++;
			}
		} catch (NumberFormatException | IOException e) {
			e.printStackTrace();
		}
		
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWhiteSquares() {
		return whiteSquares;
	}

	public void setWhiteSquares(String whiteSquares) {
		this.whiteSquares = whiteSquares;
	}

	public String getBlackSquares() {
		return blackSquares;
	}

	public void setBlackSquares(String blackSquares) {
		this.blackSquares = blackSquares;
	}
	
	public String getPortServer() {
		return portServer;
	}

	public void setPortServer(String newPort) {
		this.portServer = newPort;
	}

}
