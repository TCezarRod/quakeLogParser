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
			rankingList = interpreter.listRanking();
		} catch (LogInterpretationException e) {
			System.out.println("[ERROR: Ranking Command] "+e.getMessage());
		}
		
		if(rankingList!=null){
			String rankingStr = "Log Ranking:\n";
			int rank = 1;
			for(Player player : rankingList){
				rankingStr += "#"+ (rank++) +" "+player.toString()+": "+player.getKillCount()+" kills\n";
			}
			System.out.println(rankingStr);
		}
	}

}
