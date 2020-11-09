package uno_database;

import java.util.Scanner;

public class login_test 
{

	public static void main (String[] args) throws Exception
	{
		try {
			
			db_connect.addUser("Bill Gates", "microsoftIsKing");
			db_connect.addUser("Sheryl Sandberg", "facebookLikesData");
			
		} catch (Exception ex) {
			
			System.out.println("DB connection issue, please try again.");
			
		}
		Scanner scanner = new Scanner(System.in);
		System.out.println("Enter username");
		String username = scanner.nextLine();
		System.out.println("Enter password");
		String password = scanner.nextLine();
		
		System.out.println("1");
		
		boolean validated = false;
		try {
			
			System.out.println("2");
			validated = db_connect.validateLogin(username, password);
			System.out.println("3");
			
		} catch (Exception ex) {
			System.out.println("4");
			if(ex.getMessage().compareTo("User does not exist")==0)
			{
				System.out.println("User does not exist, input 0 to proceed as guest and 1 to create a user.");
			}
			else 
			{
				System.out.println("DB connection issue, please try again.");
			}
		} 
		
		System.out.println("5");
		
		if(validated)
		{
			System.out.println("User validated, logging in...");
		}
		else
		{
			System.out.println("Login failed.");
		}
	}
}
