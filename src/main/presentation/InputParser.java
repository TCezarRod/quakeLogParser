package presentation;

import java.util.Arrays;
import java.util.List;

import org.apache.commons.cli.*;

public class InputParser {
	
	public static AppArguments parse(String[] args) throws LogFileNotSpecifiedException, InvalidOptionException{
		AppArguments arguments = null;
		
		Options options = buildOptions();
		final CommandLineParser clParser = new DefaultParser();
		CommandLine cmdLine = null;
		
		try{
			cmdLine = clParser.parse(options, args);
		}catch(ParseException e){
			throw new InvalidOptionException("Unable to parse command-line arguments "
					+ Arrays.toString(args) + " due to: "
					+ e);
		}
		
		if(cmdLine.hasOption("help")){
			printHelp(options);
		}else{
			List<String> logFiles = cmdLine.getArgList();
			arguments = new AppArguments(logFiles);
			if(logFiles.isEmpty()){
				throw new LogFileNotSpecifiedException("There is no log file specified.");
			}else{
				if(cmdLine.hasOption("summary")){
					arguments.addCommand(new SummaryCommand());
				}
				
				if(cmdLine.hasOption("ranking")){
					arguments.addCommand(new RankingCommand());
				}
				
				if(cmdLine.hasOption("web-ranking")){
					WebRankingCommand wrCommand = new WebRankingCommand();
					if(cmdLine.hasOption("o")){
						wrCommand.setOutputPath(cmdLine.getOptionValue("o"));
					}
					arguments.addCommand(wrCommand);
				}
			}			
		}
		
		return arguments;
	}

	private static Options buildOptions(){
		final Option helpOption = Option.builder("h")
				.longOpt("help")
				.required(false)
				.hasArg(false)
				.desc("Displays the application description and the available options.")
				.build();
		
		final Option summaryOption = Option.builder("s")
				.longOpt("summary")
				.required(false)
				.hasArg(false)
				.desc("Creates a summary of the games in the log file with information about kills and players.")
				.build();
		
		final Option rankingOption = Option.builder("r")
				.longOpt("ranking")
				.required(false)
				.hasArg(false)
				.desc("Displays the ranking of the players in the log file based on their kills in all the games.")
				.build();
		
		final Option webRankingOption = Option.builder("w")
				.longOpt("web-ranking")
				.required(false)
				.hasArg(false)
				.desc("Creates a web page showing the ranking of the players basesd on their kills in all the games.")
				.build();
		
		final Option outputOption = Option.builder("o")
				.required(false)
				.hasArg()
				.desc("The file path for the ranking web page to be created.")
				.build();
		
		final Options options = new Options();
		options.addOption(helpOption);
		options.addOption(summaryOption);
		options.addOption(rankingOption);
		options.addOption(webRankingOption);
		options.addOption(outputOption);
		return options;
	}
	
	private static void printHelp(Options options){
		final HelpFormatter formatter = new HelpFormatter();
		formatter.setOptionComparator(new OptionComparator());
		
		final String cmdSyntax = "java -jar quakeLogParser.jar <FILENAME> [-hsr] [-w [-o <OUTPUT>]";
		final String header = "Reads a log file from the game Quake III and creates summaries of the games and classification rankings of the players based on their kill count.\n"
							+"A web page may be created if a base is provided containing a table if id=rankingTable.";
		final String footer = "Created by Thalles Lima (tcrl at cin.ufpe.br)";
		formatter.printHelp(cmdSyntax, header, options, footer);
	}
}
