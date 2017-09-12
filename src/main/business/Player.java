package business;

public class Player {
	private String name;
	private int kills;
	
	public Player(Player player){
		this.name = player.getName();
		this.kills = player.getKillCount();
	}
	
	public Player(String name){
		this.name = name;
		kills = 0;
	}
	
	public String getName(){
		return new String(name);
	}
	
	public void plusOneKill(){
		kills++;
	}
	
	public void reduceOneKill(){
		if(kills>0)
			kills--;
	}
	
	public void addKills(int count){
		kills+=count;
	}
	
	public int getKillCount(){
		return kills;
	}
	
	@Override
	public String toString(){
		return "\""+name+"\"";
	}
}
