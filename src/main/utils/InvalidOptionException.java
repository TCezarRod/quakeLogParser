package utils;

public class InvalidOptionException extends Exception{
	public InvalidOptionException(){}
	
	public InvalidOptionException(String message){
		super(message);
	}
}
