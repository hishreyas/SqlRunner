package com.sanedriod.sqlrunner;
import android.app.*;
import android.os.*;
import android.widget.*;
import java.sql.*;
import android.util.*;

public class QueryResult extends Activity
{
	TableLayout tablelayout;
	TextView desc;
	TablesCreater tc;
	String query;
	String[] result;
	MainActivity db;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.result);
		tablelayout=(TableLayout)findViewById(R.id.resultTableLayout);
		desc=(TextView)findViewById(R.id.resultTextView);
		tc=new TablesCreater(tablelayout,this);
		db=new MainActivity();
		
		query=getIntent().getStringExtra("query");
		if(query.equals(""))
		{
			try
			{
				DatabaseMetaData data=db.mDbConnection.getconn().getMetaData();
				desc.setText(data.getDatabaseProductName()+ ": " + "Connected"+"\n"+"User: "+data.getUserName());


			}
			catch (SQLException e)
			{}}
		else
		{
		try
		{
			
			desc.setText(db.mQuery.getDescription(query));
			
			
			if(query.toLowerCase().contains("select"))
			{
				result = db.mQuery.getData(query);
				desc.setText(desc.getText() +"\n" +db.mQuery.getWarnings(query));
				tc.DisplayTable(result,db.mQuery.getRowCount(),db.mQuery.getColumnCount());
				
			}
			
			
			
		}
		catch (SQLException e)
		{
			Log.d("error",e.getMessage());
			
		}
        }
	}
	
}
