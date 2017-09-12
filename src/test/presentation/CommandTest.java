package presentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import presentation.ICommand;
import presentation.InputParser;
import presentation.SummaryCommand;

public class CommandTest {

	private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
	private final File emptyFile = new File("src/test/empty_file.txt");	
	private final File validLogFile = new File("src/test/test_log.txt");
	private final String expectedSummary = 
			"game_1: {"
			+ "\n\ttotal_kills: 5"
			+ "\n\tplayers: [\"Dono da Bola\", \"Mocinha\", \"Isgalamido\", \"Zeh\"]"
			+ "\n\tkills: {"
			+ "\n\t\t\"Dono da Bola\": 2"
			+ "\n\t\t\"Mocinha\": 0"
			+ "\n\t\t\"Isgalamido\": 1"
			+ "\n\t\t\"Zeh\": 0"
			+ "\n\t}"
			+ "\n}"
			+ "\n\r\n";
	private final String expectedRanking = 
			"Log Ranking:"
			+"\n#1 \"Dono da Bola\": 2 kills"
			+"\n#2 \"Isgalamido\": 1 kills"
			+"\n#3 \"Mocinha\": 0 kills"
			+"\n#4 \"Zeh\": 0 kills"
			+"\n\r\n";
	
	
	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
	
	@Test
	public void execute_commandOnEmptyFile_errorDisplayed() throws Exception{	
		ICommand command = new SummaryCommand();
		
		command.execute(emptyFile);
		
		assertTrue(outContent.toString().startsWith("[ERROR:"));
	}
		
	@Test
	public void execute_SummaryCommand_expectedSummaryDisplayed() throws Exception{
		ICommand command = new SummaryCommand();
	
		command.execute(validLogFile);
		
		assertEquals(expectedSummary, outContent.toString());
	}
	
	
	@Test
	public void execute_RankingCommand_expectedRankingDisplayed() throws Exception{
		
	}
	
	@Test
	public void execute_WebRankingCommand_expectedRankingPageCreated() throws Exception{
		
	}
	
	@Test
	public void execute_MultipleCommands_expectedOutputDisplayed() throws Exception{
		
	}
}
