package application;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import business.LogInterpreterTest;
import business.WebParserTest;
import presentation.CommandTest;
import presentation.InputParserTest;

public class TestsRunner {

	public static void main(String[] args) {
		System.out.println("Initialising tests.");
		runInputParserTests();
		runCommandTests();
		runLogInterpreterTests();
		runWebParserTests();		
	}
	
	private static void runTests(Class<?> testClass){
		Result result = JUnitCore.runClasses(testClass);

		int count = result.getRunCount();
		if(result.wasSuccessful()){
			System.out.println(count+" tests executed successfully");
		}else{
			int errorCount = result.getFailureCount();
			System.out.println(errorCount+" out of "+count+" tests failed:");
			for(Failure fail : result.getFailures()){
				System.out.println(fail.toString());
			}
		}
	}
	
	private static void runInputParserTests(){
		System.out.println("Testing InputParser");
		runTests(InputParserTest.class);		
	}
	
	private static void runCommandTests(){
		System.out.println("Testing Commands");
		runTests(CommandTest.class);
	}

	private static void runLogInterpreterTests(){
		System.out.println("Testing LogInterpreter");
		runTests(LogInterpreterTest.class);
	}
	
	private static void runWebParserTests(){
		System.out.println("Testing WebParser");
		runTests(WebParserTest.class);
	}
	
	
}
