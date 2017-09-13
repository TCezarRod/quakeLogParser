package business;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.junit.Test;

import business.LogInterpreter;

public class LogInterpreterTest {
	private final File emptyFile = new File("resources/test/empty_file.txt");	
	private final File validLogFile = new File("resources/test/test_log.txt");
	
	@Test(expected = LogInterpretationException.class)
	public void getLogSummary_emptyFile_throwsLogInterpretationException() throws Exception{	
		LogInterpreter interpreter = new LogInterpreter(emptyFile);
		
		interpreter.getLogSummary();		
	}
	
	@Test(expected = LogInterpretationException.class)
	public void listRanking_emptyFile_throwsLogInterpretationException() throws Exception{	
		LogInterpreter interpreter = new LogInterpreter(emptyFile);
		
		interpreter.listRanking();		
	}
	
	@Test
	public void getLogSummary_getLogSummary_expectedSummary() throws Exception{	
		LogInterpreter interpreter = new LogInterpreter(validLogFile);
		GameSummary expectedSummary = buildExpectedSummary();
				
		List<GameSummary> summary = interpreter.getLogSummary();
		
		assertTrue(summary.size()==1);
		assertEquals(expectedSummary.getId(), summary.get(0).getId());
		assertEquals(expectedSummary.getKillCount(), summary.get(0).getKillCount());
		assertTrue(samePlayers(expectedSummary.getPlayers(), summary.get(0).getPlayers()));
	}
	
	@Test
	public void listRanking_getLogSummary_correctlyOrderedRanking() throws Exception{	
		LogInterpreter interpreter = new LogInterpreter(validLogFile);
		
		List<Player> ranking = interpreter.listRanking();

		assertTrue(rankingDecreasingByKills(ranking));
	}
	
	private boolean rankingDecreasingByKills(List<Player> ranking){
		Iterator<Player> itr = ranking.iterator();

		if(ranking.size()<1)
			return false;
		
		int prevKillCount = ranking.get(0).getKillCount();
		while(itr.hasNext()){
			int nextKillCount = itr.next().getKillCount();
			if(prevKillCount<nextKillCount)
				return false;
			
			prevKillCount = nextKillCount;
		}
		
		return true;
	}
	
	private GameSummary buildExpectedSummary(){
		GameSummary summary = new GameSummary("game_1");
		Player player1 = new Player("Dono da Bola");
		Player player2 = new Player("Mocinha");
		Player player3 = new Player("Isgalamido");
		Player player4 = new Player("Zeh");
		
		player1.addKills(2);
		player3.addKills(1);

		summary.addKills(5);
		summary.addPlayer(player1);
		summary.addPlayer(player2);
		summary.addPlayer(player3);
		summary.addPlayer(player4);
		
		return summary;		
	}
	
	private boolean samePlayers(List<Player> players1, List<Player> players2){
		Iterator<Player> itr1 = players1.iterator();
		Iterator<Player> itr2 = players2.iterator();
		
		while(itr1.hasNext() && itr2.hasNext()){
			Player p1 = itr1.next();
			Player p2 = itr2.next();
			if((!p1.getName().equals(p2.getName())) || (p1.getKillCount()!=p2.getKillCount())){
				return false;
			}
		}
		
		if(itr1.hasNext() || itr2.hasNext()){
			return false;
		}
		
	    return true;
	}
		
}
