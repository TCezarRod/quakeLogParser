package presentation;

@SuppressWarnings("serial")
public class LogFileNotSpecifiedException extends Exception{
	public LogFileNotSpecifiedException(){}
	
	public LogFileNotSpecifiedException(String message){
		super(message);
	}

}
