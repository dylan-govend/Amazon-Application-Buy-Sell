package za.ac.ukzn.dbinterface;

import java.sql.*;

public class TestPage 
{
	public static void main(String[] args) 
	{
		getAllClient();
		insertClient();
		
		try
		{
			//establish connection
			Connection connect = getConnection();
			
			//create variable to hold data
			String id_number = "2703619176012";
			String name = "Kaylan";
			
			//write the UPDATE SQL statement
			String query = "UPDATE client_t SET first_name = ? WHERE id_number = ?";
			
			//create a PreparedStatement object
			PreparedStatement ps = connect.prepareStatement(query);
			
			ps.setString(1, name);
			ps.setString(2, id_number);
			
			//execute the prepared statement
			int rowsAffected = ps.executeUpdate();
			System.out.format("Number of rows affected: %d\n", rowsAffected);
			
			connect.close();
		}
		
		catch (Exception e)
		{
			System.out.println("Something went wrong while updating client table.");
			e.printStackTrace();
		}
	}
	
	private static void insertClient()
	{
		String idNumber = "1234567890123";
		String name = "Zuma";
		String surname = "Jacob";
		String gender = "M";
		String dob = "1980-12-10";
		double salary = 12_000;
		
		try
		{
			//establish connection
			Connection connect = getConnection();
			
			String query = "INSERT INTO client_t VALUES (?, ?, ?, ?, ?, ?)";
			PreparedStatement ps = connect.prepareStatement(query);
			
			ps.setString(1, idNumber);
			ps.setString(2, name);
			ps.setString(3, surname);
			ps.setString(4, gender);
			ps.setString(5, dob);
			ps.setDouble(6, salary);
			
			int rowsAffected = ps.executeUpdate();
			System.out.format("Number of rows affected: %d", rowsAffected);
			connect.close();
		}
		
		catch (Exception e)
		{
			System.out.println("Something went wrong while inserting data.");
			e.printStackTrace();
		}
	}
	
	private static void getAllClient()
	{
		try 
		{
			//establish connection
			Connection connect = getConnection();
			
			//create Statement Object to execute a Query
			Statement statement =  connect.createStatement();
					
			//write query as string
			String query = "SELECT * FROM client_t";
			
			//execute the query
			ResultSet rs = statement.executeQuery(query);
			
			//display result table
			while (rs.next())
			{
				String name = rs.getString("first_name");
				String surname = rs.getString("surname");
				Date dob = rs.getDate("date_of_birth");
				System.out.format("%s %s %s \n", name, surname, dob);
			}
			
			//close the connection
			connect.close();
		} 
		
		catch (SQLException e) 
		{
			System.out.println("Something went wrong with the SELECT statement.");
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection()
	{
		try
		{
			//load the driver
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			//create a connection object
			Connection connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/bank", "root", "13082002dg");
			
			//display a success message
			System.out.println("Connection established.");
			
			return connect;
		}
		
		catch (Exception e)
		{
			System.out.println("Something went wrong while trying to establish a connection.");
			e.printStackTrace();
			return null;
		}
	}
}