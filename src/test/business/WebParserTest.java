package business;

import static org.junit.Assert.*;

import java.io.File;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class WebParserTest {
	String webPagePath = "testpage.html";
	String badModelPath = "resources/test/bad-ranking-page.html";
	
	@Rule
	public TestRule watcher = new TestWatcher() {
	   protected void starting(Description description) {
	      System.out.println("Starting test: " + description.getMethodName());
	   }
	};
	
	@After
	public void deletePageFile(){
		File file = new File(webPagePath);
		file.delete();
	}
	
	@Test(expected = WebRankingParseException.class)
	public void parseRankingPage_noTableId_throwsWebRankingParseException() throws Exception{
		List<Player> ranking = new LinkedList<Player>();
		ranking.add(new Player("Useless"));
		
		WebParser.parseRankingPage(ranking, webPagePath, badModelPath);
	}
	
	@Test
	public void parseRankingPage_validRanking_webPageCreated() throws Exception{
		List<Player> ranking = new LinkedList<Player>();
		Player player1 = new Player("Alice_nuk3");
		Player player2 = new Player("B0B");
		Player player3 = new Player("iCharlie");
		
		player1.addKills(28);
		player2.addKills(15);
		player3.addKills(12);
		
		ranking.add(player1);
		ranking.add(player2);
		ranking.add(player3);
		
		WebParser.parseRankingPage(ranking, webPagePath);
		
		File pageFile = new File(webPagePath);
		
		assertTrue(pageFile.exists());
		assertTrue(rankingCorrectlyDisplayed(ranking));
	}
	
	private boolean rankingCorrectlyDisplayed(List<Player> ranking) throws Exception{
		File file = new File(webPagePath);
		
		Document doc = null;
		doc = Jsoup.parse(file, "UTF-8");
		
		Element table = doc.getElementById("rankingTable");
		
		Element tbody = table.select("tbody").first();
		
		List<Element> rows = tbody.select("tr");
		
		Iterator<Element> rowsIterator = rows.iterator();
		Iterator<Player> rankingIterator = ranking.iterator();
		
		while(rankingIterator.hasNext()){
			Player player = rankingIterator.next();
			
			if(!rowsIterator.hasNext()){
				return false;
			}
			
			while(rowsIterator.hasNext()){
				Element row = rowsIterator.next();
				List<Element> td = row.select("td");
				
				if(td.isEmpty()){
					continue;
				}
				
				String playerKills = Integer.toString(player.getKillCount());
				
				String nameColumn = td.get(1).text();
				String killColumn = td.get(2).text();
				
				if(!(player.getName().equals(nameColumn) && playerKills.equals(killColumn))){
					return false;
				}

				break;
			}
		}
		
		return true;
	}
}
