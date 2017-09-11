package test.presentation;

import utils.LogFileMissingException;
import utils.InvalidOptionException;

import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.hamcrest.CoreMatchers.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import presentation.AppArguments;
import presentation.ICommand;
import presentation.InputParser;
import presentation.RankingCommand;
import presentation.SummaryCommand;
import presentation.WebRankingCommand;
import static org.junit.Assert.*;

public class InputParserTest {
		
	@Test(expected = LogFileMissingException.class)
	public void parse_commandWithoutFilePath_throwsLogFileMissingException() throws Exception{
		String[] args = {"-s"};
		
		AppArguments arguments = InputParser.parse(args);		
	}
	
	@Test(expected = InvalidOptionException.class)
	public void parse_invalidOptions_throwsInvalidOptionException() throws Exception{
		String[] args = {"-x"};
		
		AppArguments arguments = InputParser.parse(args);		
	}
	
	@Test
	public void parse_shortSummaryOption_summaryCommandCreated() throws Exception{
		String[] args = {"filepath", "-s"};
		
		AppArguments arguments = InputParser.parse(args);
		
		assertTrue(listContainsCommand(arguments.getCommands(), SummaryCommand.class));
	}
	
	@Test
	public void parse_longSummaryOption_summaryCommandCreated() throws Exception{
		String[] args = {"filepath", "--summary"};
		
		AppArguments arguments = InputParser.parse(args);
		
		assertTrue(listContainsCommand(arguments.getCommands(), SummaryCommand.class));
	}
	
	@Test
	public void parse_shortRankingOption_rankingommandCreated() throws Exception{
		String[] args = {"filepath", "-r"};

		AppArguments arguments = InputParser.parse(args);
		
		assertTrue(listContainsCommand(arguments.getCommands(), RankingCommand.class));
	}
	
	@Test
	public void parse_longRankingOption_rankingCommandCreated() throws Exception{
		String[] args = {"filepath", "--ranking"};

		AppArguments arguments = InputParser.parse(args);
		
		assertTrue(listContainsCommand(arguments.getCommands(), RankingCommand.class));
		
	}
	
	@Test
	public void parse_shortWebRankingOption_webRankingCommandCreated() throws Exception{
		String[] args = {"filepath", "-w"};

		AppArguments arguments = InputParser.parse(args);
		
		assertTrue(listContainsCommand(arguments.getCommands(), WebRankingCommand.class));
		
	}
	
	@Test
	public void parse_longWebRankingOption_webRankingCommandCreated() throws Exception{
		String[] args = {"filepath", "--web-ranking"};

		AppArguments arguments = InputParser.parse(args);
	
		assertTrue(listContainsCommand(arguments.getCommands(), WebRankingCommand.class));
		
	}
	
	
	private boolean listContainsCommand(List<ICommand> list, Class<? extends ICommand> concreteCmd){
		for(ICommand cmd : list){
			if(concreteCmd.isInstance(cmd))
				return true;
		}		
		return false;
	}
}
