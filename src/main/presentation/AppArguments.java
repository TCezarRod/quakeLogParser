package presentation;

import java.util.LinkedList;
import java.util.List;

public class AppArguments {
	private List<ICommand> commands;
	private List<String> inputPaths;
	
	public AppArguments(List<String> inputPaths){
		commands = new LinkedList<ICommand>();
		this.inputPaths = inputPaths;	
	}
	
	public List<String> getInputPaths(){
		return new LinkedList<String>(inputPaths);
	}
	
	public void addCommand(ICommand command){
		this.commands.add(command);
	}
	
	public List<ICommand> getCommands(){
		return new LinkedList<ICommand>(commands);
	}
}
