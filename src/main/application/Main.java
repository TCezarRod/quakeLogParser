package application;

import java.io.File;

import presentation.*;

public class Main {

	public static void main(String[] args) {
		try {
			AppArguments arguments = InputParser.parse(args);
			
			if(arguments!=null){
				for(String file : arguments.getInputPaths()){
					
					for(ICommand cmd : arguments.getCommands()){
						cmd.execute(new File(file));
					}					
				}
			}			
			
		} catch (Exception e){
			System.out.println("[INPUT ERROR] "+e.getMessage());
		}
	}

}
