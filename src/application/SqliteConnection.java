package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SqliteConnection {

	public static Connection Connector() {
		/*try {
			Class.forName("org.sqlite.JDBC");
			
			Connection conn=DriverManager.getConnection("jdbc:sqlite:\\musicappdatabase.sqlite");

			return conn;
		} catch (Exception e) {
			return null;
		}*/
		try{
			//String dbUrl="jdbc:mysql://localhost:3307/music";
			String dbUrl="jdbc:mysql://Anon-Laptop:3307/music";
			String user="root";
			String pass="root";
			Connection conn=DriverManager.getConnection(dbUrl, user, pass);
			return conn;
			
		}catch(Exception e){
			e.printStackTrace();
			return null;
			
		}
		

		
	}
}
