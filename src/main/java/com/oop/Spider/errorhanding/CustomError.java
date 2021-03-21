package com.oop.Spider.errorhanding;

/** Custom Exception that handle errors within our program that do not fall within the java Exception Class
* @version 1.0
*/
public class CustomError extends Exception{
	public CustomError(String message){
        super(message);
    }
}
