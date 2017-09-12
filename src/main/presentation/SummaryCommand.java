package presentation;

import java.io.File;
import java.util.List;

import business.LogInterpretationException;
import business.GameSummary;
import business.LogInterpreter;

public class SummaryCommand implements ICommand {

	public void execute(File inputFile) {
		LogInterpreter interpreter = new LogInterpreter(inputFile);
		
		List<GameSummary> summaryList=null;
		try {
			summaryList = interpreter.GetLogSummary();
		} catch (LogInterpretationException e) {
			System.out.println("[ERROR: Log Summary Command] "+e.getMessage());
		}
		
		if(summaryList != null){
			for(GameSummary summary : summaryList){
				System.out.println(summary.toString()+'\n');
			}
		}
	}

}
