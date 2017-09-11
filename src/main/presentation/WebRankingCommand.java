package presentation;

import java.io.File;

public class WebRankingCommand implements ICommand {

	private String outputPath;
	
	public WebRankingCommand(){
		outputPath = "./Ranking.html";
	}
	
	public void Execute(File inputFile) {
		// TODO Auto-generated method stub
	}
	
	public void setOutputPath(String outputPath){
		this.outputPath = outputPath;
	}
	
	public String getOutputPath(){
		return new String(outputPath);
	}
}
