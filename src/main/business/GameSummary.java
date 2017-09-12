package business;

import java.util.LinkedList;
import java.util.List;

public class GameSummary {
	private String gameId;
	private List<Player> players;
	private int killCount;
	
	public GameSummary(String id){
		this.gameId = id;
		players = new LinkedList<Player>();
	}
	
	public String getId(){
		return new String(gameId);
	}
	
	public void addPlayer(Player player){
		players.add(player);
	}
	
	public List<Player> getPlayers(){
		return new LinkedList<Player>(players);
	}
	
	public void plusOneKill(){
		killCount++;
	}
	
	public void addKills(int count){
		killCount+=count;
	}
	
	public int getKillCount(){
		return killCount;
	}
	
	public String toString(){
		String string = gameId+": {"
				+"\n\ttotal_kills: "+killCount
				+"\n\tplayers: "+players.toString()
				+"\n"+parseKills()
				+"\n}";
		return string;
	}
	
	private String parseKills(){
		String string = "\tkills: {";
		for(Player player : players){
			string += "\n\t\t"+player.toString()+": "+player.getKillCount();
		}
		string += "\n\t}";
		
		return string;
	}
}
