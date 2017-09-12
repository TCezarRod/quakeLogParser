package business;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.*;

public class WebParser {
	private static String pageModelPath = "base-ranking-page.html";
	public static void ParseRankingPage(List<Player> players, String outputPath) throws WebRankingParseException{
		File file = new File(pageModelPath);
		
			Document doc = null;
			try {
				doc = Jsoup.parse(file, "UTF-8");
			} catch (IOException e){
				throw new WebRankingParseException("The base ranking page could not be read.");
			}
			Element table = doc.getElementById("rankingTable");
			Element tbody = table.select("tbody").first();
			
			int rank = 1;
			for(Player player : players){
				Element newRow = new Element("tr");			
				newRow.append("<td>"+rank++ +"</td>");
				newRow.append("<td>"+player.getName()+"</td>");
				newRow.append("<td>"+player.getKillCount()+"</td>");
				
				tbody.append(newRow.toString());
			}
			
			try {
				saveDocument(doc, outputPath);
			} catch (IOException e) {
				throw new WebRankingParseException("Could not save output file: "+e.getMessage());
			}
	}
	
	private static void saveDocument(Document document, String path) throws IOException{
		BufferedWriter writer = new BufferedWriter(new FileWriter(path));	
		writer.write(document.toString());
		writer.close();
	}
}
