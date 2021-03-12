package spiderProject;

import twitter4j.TwitterException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

   public class TwitterDriver
   {
      public static void main (String []args) throws TwitterException, IOException
      {
         Twitterer bigBird = new Twitterer();
         bigBird.initialize();
         bigBird.saQuery("covid");
      }     
         
   }
