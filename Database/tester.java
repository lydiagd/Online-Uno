import java.util.Random;

public class tester {
	
	/*
	 * NOTE: You will need to manually check the DB after running this program to make sure that
	 * everything worked out on that side
	 */
	
	public static void main (String[] args)
	{	
		// create a new user for testing purposes
		Random r = new Random();
		String user = "user" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
		String pwd = "pwd" + r.nextInt(10) + r.nextInt(10) + r.nextInt(10) + r.nextInt(10);
		int w = r.nextInt(20);
		int l = r.nextInt(20);
		
		// Try adding the user
		try {db_connect.addUser(user, pwd);} 
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// Try adding the same user again
		try {db_connect.addUser(user, pwd);} 
		catch (Exception ex) {System.out.println("addUser success");}
		
		// Try adding a win
		try {for(int i = 0; i < w; ++i) db_connect.addWin(user);} 
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// Try adding a loss
		try {for(int i = 0; i < l; ++i) db_connect.addLoss(user);} 
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// NOTE: For addWin and addLoss, this program does not throw any exceptions
		// if the user DNE.

		// Try getting the number of wins
		try {System.out.println("Should be " + w + ": " + db_connect.getWins(user));} 
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// Try getting the number of losses
		try {System.out.println("Should be " + l + ": " + db_connect.getLosses(user));} 
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// Try to get the wins for a user that DNE
		try {System.out.println(db_connect.getWins("2020 is a great year"));} 
		catch (Exception ex) {System.out.println("getWins success");}

		// Try to validate that a user/pwd combo that is correct
		try {System.out.println("Should be true: " + db_connect.validateLogin(user, pwd));} 
		catch (Exception ex) {System.out.println(ex.getMessage());}

		// Try to validate that a user/pwd combo that is incorrect
		try {System.out.println("Should be false: " + db_connect.validateLogin(user, "password mistake"));}
		catch (Exception ex) {System.out.println("vadidateLogin error");}
		
		// Try to validate that a user/pwd combo that is incorrect
		try {System.out.println(db_connect.validateLogin("2020 is a great year", "hahaha sike"));}
		catch (Exception ex) {System.out.println("vadidateLogin success");}
		
		// Check if real username exists
		try {System.out.println("Should be true: " + db_connect.userExists(user));}
		catch (Exception ex) {System.out.println(ex.getMessage());}
		
		// Check if fake username exists
		try {System.out.println("Should be false: " + db_connect.userExists("2020 is a great year"));}
		catch (Exception ex) {System.out.println(ex.getMessage());}
	}
	
}
