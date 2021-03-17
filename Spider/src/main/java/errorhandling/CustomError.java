package errorhandling;

public class CustomError extends Exception{
	public CustomError(String message){
        super(message);
    }
    public CustomError(String message, Throwable throwable){
        super(message, throwable);
    }
}
