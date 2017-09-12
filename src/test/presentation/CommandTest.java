package test.presentation;

import static org.junit.Assert.assertTrue;

import java.io.ByteArrayOutputStream;
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

	@Before
	public void setUpStreams() {
	    System.setOut(new PrintStream(outContent));
	}
	
	@After
	public void cleanUpStreams() {
	    System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
	}
		
	@Test
	public void execute_SummaryCommand_expectedSummaryDisplayed() throws Exception{
		
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
