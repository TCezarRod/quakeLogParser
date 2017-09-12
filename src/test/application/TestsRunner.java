package application;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import presentation.InputParserTest;

public class TestsRunner {

	public static void main(String[] args) {

		System.out.println("Testing:");
		Result result = JUnitCore.runClasses(InputParserTest.class);
		
		for(Failure fail : result.getFailures()){
			System.out.println(fail.toString());
		}
		
		System.out.println(result.wasSuccessful());
	}
	
	private void runInputParserTests(){
		
	}
	
	private void runCommandTests(){
		
	}
	
	private void runWebParserTests(){
		
	}
	
	private void runLogInterpreterTests(){
		
	}
	
}
