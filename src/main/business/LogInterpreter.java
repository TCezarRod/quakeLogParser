package business;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.HashMap;


public class LogInterpreter {
	
	private File logFile;
	private List<GameSummary> summaryList;
	private List<Player> rankingList;
	
	public LogInterpreter(File file){
		this.logFile = file;
	}
	
	public List<GameSummary> GetLogSummary() throws LogInterpretationException{
		if(summaryList != null){
			return summaryList;
		}
		buildSummaryList();
				
		return summaryList;
	}
	
	public List<Player> ListRanking() throws LogInterpretationException{
		if(rankingList != null){
			return rankingList;
		}
		buildRankingList();		
		return rankingList;
	}
	
	private void buildRankingList() throws LogInterpretationException{
		if(summaryList == null){
			buildSummaryList();
		}
		
		HashMap<String, Player> players = new HashMap<String, Player>();
		
		for(GameSummary summary : summaryList){
			List<Player> summaryPlayers = summary.getPlayers();
			for(Player player : summaryPlayers){
				String playerName = player.getName();
				int killCount = player.getKillCount();
				
				if(players.containsKey(playerName)){
					players.get(playerName).addKills(killCount);
				}else{
					players.put(playerName, new Player(player));
				}
			}
		}		
		storeRanking(players);
	}
	
	private void buildSummaryList() throws LogInterpretationException{
		summaryList = new LinkedList<GameSummary>();
		HashMap<String, Player> players = null;
		
		BufferedReader bufReader = null;
		
		GameSummary summary = null;
		
		String userInfoPatternString = "(ClientUserinfoChanged:)( [0-9]+ n\\\\)(.*?)(\\\\t)";
		String playerKillPatternString = "([0-9]: )(.*)( killed )(.*)(?= by)";
	
		int gameCount=1;
			
		String line;
		boolean gameStarted = false;
		
		try {
			bufReader = new BufferedReader(new FileReader(logFile));
			
			try {
				while((line = bufReader.readLine()) != null){
					ELogLineType lineType = getLineType(line);
					
					switch(lineType){
						case INITGAME:
							if(gameStarted){
								storeSummary(summary, players);
							}					
							gameStarted = true;
							summary = new GameSummary("game_"+gameCount++);
							players = new HashMap<String, Player>();
							break;
						case SHUTDOWN:
							gameStarted = false;
							if(summary!=null){
								storeSummary(summary, players);
							}
							break;
						case USERINFO:
							Matcher userInfoMatcher = Pattern.compile(userInfoPatternString).matcher(line);
							
							if(userInfoMatcher.find()){							
								String playerName = userInfoMatcher.group(3);
								if(!players.containsKey(playerName)){
									players.put(playerName, new Player(playerName));
								}							
							}
							break;
						case KILL:
							Matcher killMatcher = Pattern.compile(playerKillPatternString).matcher(line);
							
							if(killMatcher.find()){
								summary.plusOneKill();
								
								String killerPlayerName = killMatcher.group(2);
								String deadPlayerName = killMatcher.group(4);
								
								Player killerPlayer;
								Player deadPlayer;
								
								deadPlayer = getPlayer(players, deadPlayerName);
								
								if(killerPlayerName.equals("<world>")){
									deadPlayer.reduceOneKill();
								}else if(!killerPlayerName.equals(deadPlayerName)){
									killerPlayer = getPlayer(players, killerPlayerName);
									killerPlayer.plusOneKill();
								}
							}	
							break;
						default:
							// Just keep going ;)
							break;
					}				
				}
			} finally{
				bufReader.close();
			}
		} catch (IOException e) {
			throw new LogInterpretationException("There was an error while reading the log file: "+e.getMessage());
		}
		

		if(summaryList.size()<1){
			throw new LogInterpretationException("No games were found in the log file.");
		}
	}
	
	private ELogLineType getLineType(String line){
		ELogLineType type = ELogLineType.UNKNOWN;
		
		String initGamePatternString = "[0-9]?[0-9]:[0-9][0-9] (InitGame:)";
		String shutdownGamePatternString = "[0-9]?[0-9]:[0-9][0-9] (ShutdownGame:)";
		String killLinePatternString = "[0-9]?[0-9]:[0-9][0-9] (Kill:)";
		String userInfoPatternString = "[0-9]?[0-9]:[0-9][0-9] (ClientUserinfoChanged:)";
		
		Matcher initMatcher = Pattern.compile(initGamePatternString).matcher(line);
		Matcher shutdownMatcher = Pattern.compile(shutdownGamePatternString).matcher(line);
		Matcher userInfoMatcher = Pattern.compile(userInfoPatternString).matcher(line);
		Matcher killMatcher = Pattern.compile(killLinePatternString).matcher(line);
		
		if(initMatcher.find()){
			type = ELogLineType.INITGAME;
		}else if (shutdownMatcher.find()){
			type = ELogLineType.SHUTDOWN;
		}else if (userInfoMatcher.find()){
			type = ELogLineType.USERINFO;
		}else if (killMatcher.find()){
			type = ELogLineType.KILL;
		}
		
		return type;
		
	}
	
	private Player getPlayer(HashMap<String, Player> players, String playerName){
		Player player;
		
		if(players.containsKey(playerName)){
			player = players.get(playerName);
		}else{
			player = new Player(playerName);
			players.put(playerName, player);
		}
		
		return player;
	}
	
	private void storeSummary(GameSummary summary, Map<String, Player> players){
		for(Player player : players.values()){
			summary.addPlayer(player);
		}
		summaryList.add(summary);
	}
	
	private void storeRanking(Map<String, Player> players){
		rankingList = new ArrayList<Player>();
		for(Player player : players.values()){
			rankingList.add(player);
		}
		rankingList.sort(new PlayerRankingComparator());
	}
}
