package presentation;

import java.io.File;
import java.util.List;

import business.LogInterpretationException;
import business.LogInterpreter;
import business.Player;

public class RankingCommand implements ICommand {

	public void execute(File inputFile) {
		LogInterpreter interpreter = new LogInterpreter(inputFile);
		
		List<Player> rankingList=null;
		try {
			rankingList = interpreter.ListRanking();
		} catch (LogInterpretationException e) {
			System.out.println("[ERROR: Ranking Command] "+e.getMessage());
		}
		
		if(rankingList!=null){
			System.out.println("Log Ranking:");
			int rank = 1;
			for(Player player : rankingList){
				System.out.println("#"+ (rank++) +" "+player.toString()+": "+player.getKillCount()+" kills");
			}
			System.out.println();			
		}
	}

}
