package presentation;

import java.io.File;
import java.util.List;

import business.LogInterpretationException;
import business.WebRankingParseException;
import business.LogInterpreter;
import business.Player;
import business.WebParser;

public class WebRankingCommand implements ICommand {

	private String outputPath;
	
	public WebRankingCommand(){
		outputPath = "./Ranking.html";
	}
	
	public void execute(File inputFile) {
		LogInterpreter interpreter = new LogInterpreter(inputFile);
		
		List<Player> rankingList = null;
		try {
			rankingList = interpreter.listRanking();
		} catch (LogInterpretationException e) {
			System.out.println("[ERROR: Web Ranking Command] "+e.getMessage());
		}
		
		if(rankingList != null){
			try {
				WebParser.parseRankingPage(rankingList, outputPath);
				System.out.println("Ranking Web Page successfully created.\n");
			} catch (WebRankingParseException e) {
				System.out.println("[ERROR: Web Ranking Command] "+e.getMessage());
			}			
		}
	}
	
	public void setOutputPath(String outputPath){
		this.outputPath = outputPath;
	}
	
	public String getOutputPath(){
		return new String(outputPath);
	}
}
