public class example {
	
	public static void main (String[] args)
	{
		/*
		 * addUser
		 */
		try {
			
			db_connect.addUser("Bill Gates", "microsoftIsKing");
			db_connect.addUser("Sheryl Sandberg", "facebookLikesData");
			
		} catch (Exception ex) {
			
			// The only exceptions that can be thrown here are DB connection issues
			
		}
		
		/*
		 * addWin, addLoss
		 */
		try {
			
			db_connect.addWin("Sheryl Sandberg");
			db_connect.addLoss("Bill Gates");
			
		} catch (Exception ex)
		{
			
			// The only exceptions that can be thrown here are DB connection issues
			
		}
		
		/*
		 * getWins, getLosses
		 */
		int winTotal;
		int lossTotal;
		try {
			
			winTotal = db_connect.getWins("Sheryl Sandberg");
			lossTotal = db_connect.getLosses("Bill Gates");
			
		} catch (Exception ex) {
			
			// If the username is not in our database
			if(ex.getMessage().compareTo("User does not exist")==0)
			{
				
			}
			
			// Else there probably is a DB connection issue
			else 
			{
				
			}
						
		}
		
		
		/*
		 * validate login
		 */
		boolean validLogin;
		try {
			
			// true means that user exists
			// false means that user exists but incorrect password
			validLogin = db_connect.validateLogin("bill gtes", "pswd");
			
		} catch (Exception ex) {
			
			// If the username is not in our database
			if(ex.getMessage().compareTo("User does not exist")==0)
			{
				
			}
			
			// Else there probably is a DB connection issue
			else 
			{
				
			}
		}
		
		/*
		 * user in the database
		 */
		boolean exists;
		try {
			
			exists = db_connect.userExists("Bill Gates");
			
		} catch (Exception ex) {
			
			// The only exceptions that can be thrown here are DB connection issues
			
		}
	}
	
}
