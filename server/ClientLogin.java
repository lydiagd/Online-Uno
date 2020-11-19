package server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Scanner;

public class ClientLogin {
	
	ObjectOutputStream oos;
	ObjectInputStream ois;
	Scanner sc;
	
	public String getLoginCred(ObjectOutputStream outputStr, ObjectInputStream inputStr, Scanner scan)
	{
		oos = outputStr;
		ois = inputStr;
		sc = scan;
		String username = null;
		
		//fill out all credential stuff here
		 //enter username
		try {
	        System.out.println("Are you a guest or a user? ");
	        String response = sc.nextLine();
	        
	        if(response.equals("user"))
	        {
	        	oos.writeObject("user");
	        	oos.flush();
	        	while(true)
	        	{
	       
	            	System.out.print("Username: ");
	            	username = sc.nextLine();
	            	oos.writeObject(username);
	            	oos.flush();
	            	System.out.print("Password: ");
	            	String passResponse = sc.nextLine();
	            	oos.writeObject(passResponse);
	            	oos.flush();
	            	
	            	String dbResult = (String) ois.readObject();
	            	if(dbResult.equals("authenticated"))
	            	{
	            		System.out.println("Thank you " + username + ", You are authenticated!");
	            		break;
	            	}
	            	else
	            	{
	            		System.out.println("Sorry, your username or password did not work");
	            	}
	        	}
	        }
	        else {
	        	oos.writeObject("guest");
	        	oos.flush();
	        	
	        	username = (String) ois.readObject();
	        	System.out.println("Your guest username is " + username);
	        }
	
	        Integer num = (Integer) ois.readObject();
	
	        System.out.println("Welcome to Online Uno! There are "+num+" players on the server");

		} 
		
		finally {
			return username;
		}
	}

}
