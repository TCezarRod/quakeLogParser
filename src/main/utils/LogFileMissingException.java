package utils;

public class LogFileMissingException extends Exception{
	public LogFileMissingException(){}
	
	public LogFileMissingException(String message){
		super(message);
	}

}
