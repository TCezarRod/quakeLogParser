package business;

import java.util.Comparator;

class PlayerRankingComparator implements Comparator<Player>{

	@Override
	public int compare(Player p0, Player p1) {
		return Integer.valueOf(p1.getKillCount()).compareTo(p0.getKillCount());
	}

}
