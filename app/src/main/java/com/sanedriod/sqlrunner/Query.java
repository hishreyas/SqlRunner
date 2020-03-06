package com.sanedriod.sqlrunner;
import java.sql.*;
import android.database.*;
import org.postgresql.core.*;
import android.util.*;
import android.graphics.drawable.*;


public class Query 
{
	private Statement statement;
	
	public ResultSet cur;
	DbConnection conn;
	String[] data;
	int rowcount=1;
	private String query;
	
	public Query(DbConnection conn){
		this.conn=conn;
	}
	public String execQuery(String query)  
	{
	 StringBuilder strbuilder=new StringBuilder("");
		try
		{
			statement = conn.getconn().createStatement();
		
		cur = statement.executeQuery(query);
		SQLWarning notice=statement.getWarnings();
		  
		ResultSetMetaData metadata=cur.getMetaData();
		while(cur.next())
		{
			for(int i=1;i<=metadata.getColumnCount();i++)
			{
			strbuilder.append(" "+cur.getString(i));
			}
			strbuilder.append("\n");
			
			rowcount++;
			
		}
		
		while(notice!=null)
			{
				strbuilder.append(" "+notice.toString());
				notice=notice.getNextWarning();
			}
		}
		catch (java.sql.SQLException e)
		{
			return e.getErrorCode()+"";
		}
		
	   return strbuilder.toString();
	}
	public String[] getData(String query)
	{
		this.query=query;
		try
		{
			statement = conn.getconn().createStatement();
		
		cur = statement.executeQuery(query);
		ResultSetMetaData metadata=cur.getMetaData();
		data=new String[(getColumnCount()*(getRowCount()))];
		Log.d("colrow",getColumnCount()+" "+getRowCount()+" "+data.length);
		for(int i=0;i<data.length;)
		{
			if(i<getColumnCount())
			{
				data[i]=metadata.getColumnName(i+1);
				Log.d("content",data[i]);
				i++;
			}
			else
			{
				Log.d("index",i+"");
				if( cur.next())
				{
				for(int j=1;j<=metadata.getColumnCount();j++)
				{  
				    
					data[i]=cur.getString(j);
					Log.d("content",data[i]);
					i++;
				}
				}
				
			}
			
		}
			return data;
		}
		catch (java.sql.SQLException e)
		{
			return null;
		}
		
	
	}
	public int getColumnCount() throws java.sql.SQLException
	{
		if(statement!=null)
		{
			return cur.getMetaData().getColumnCount();
		}
		else
		{
			return 0;
		}
	
		
	}
    public int getRowCount() throws java.sql.SQLException
	{
		rowcount=1;
		 Statement state=conn.getconn().createStatement();
		ResultSet cursor=state.executeQuery(query);
		if(statement!=null)
		{
			while(cursor.next())
				rowcount++;
				
				return rowcount;
		
		}
		else
		{
			return 0;
		}
	}
	public String getWarnings(String q)
	{
		StringBuilder strbuilder=new StringBuilder("");
		try
		{
			Statement state=conn.getconn().createStatement();
		    state.execute(q);
		    SQLWarning notice=state.getWarnings();
		
		while(notice!=null)
		{
			strbuilder.append(" "+notice.toString());
			notice=notice.getNextWarning();
		}
			return strbuilder.toString();
		}
		catch (java.sql.SQLException e)
		{
			
			return " ";
		}
		
	}
	public String getDescription(String q)
	{
		
		try
		{
			Statement state=conn.getconn().createStatement();
		    state.execute(q);
			q=q.trim();
			
			if(q.toLowerCase().startsWith("create table"))
			{
				return "CREATE TABLE";
			}
			else if(q.toLowerCase().startsWith("create  function") ||q.toLowerCase().startsWith("create or replace function"))
			{
				return "CREATE FUNCTION";
			}
			else if(q.toLowerCase().startsWith("create view"))
			{
				return "CREATE VIEW";
			}
			else if(q.toLowerCase().startsWith("create user"))
			{
				return "CREATE USER";
			}
			else if(q.toLowerCase().startsWith("update"))
			{
				return "UPDATE "+state.getUpdateCount();
			}
			else if(q.toLowerCase().startsWith("insert") )
			{
				return "INSERT "+state.getUpdateCount();
			}
			else if(q.toLowerCase().startsWith("delete"))
			{
				return "DELETE "+state.getUpdateCount();
			}
			else if(q.toLowerCase().startsWith("drop table"))
			{
				return "DROP TABLE";
			}
			
			else if(q.toLowerCase().startsWith("drop function"))
			{
				return "DROP FUNCTION";
			}
			else if(q.toLowerCase().startsWith("drop trigger"))
			{
				return "DROP TRIGGER";
			}
			else if(q.toLowerCase().startsWith("create trigger"))
			{
				return "CREATE TRIGGER";
			}
			else
			{
				return " ";
			}
		}
		catch (java.sql.SQLException e)
		{

			return e.getLocalizedMessage();
		}

	}
}
