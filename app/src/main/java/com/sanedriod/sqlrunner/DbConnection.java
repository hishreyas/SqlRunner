package com.sanedriod.sqlrunner;
import java.sql.*;
import android.database.sqlite.*;

public class DbConnection 
{
	private String host;
	private String dbname;
	private String user;
	private String password;
	private Connection conn;
	
	public DbConnection(String host,String dbname,String user,String password) throws ClassNotFoundException,SQLException
	{
		
	
		this.host=host;
		this.dbname=dbname;
		this.user=user;
		this.password=password;
		
	
		try
		{
			Class.forName("org.postgresql.Driver");
			conn = DriverManager.getConnection("jdbc:postgresql://" + host + "/" + dbname, user, password);
		}
		
		finally
		{
			
		}
		
		

	}


	
	public Connection getconn(){
		return conn;
	}
	
	
}
