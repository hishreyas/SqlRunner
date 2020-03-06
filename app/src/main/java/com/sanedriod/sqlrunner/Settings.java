package com.sanedriod.sqlrunner;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import android.view.View.*;
import android.graphics.drawable.*;
import android.graphics.*;

public class Settings extends Activity
{

	private EditText dbname,url,user,password;
	Button mButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setContentView(R.layout.settings);
		super.onCreate(savedInstanceState);
		dbname=(EditText)findViewById(R.id.db_name);
		url=(EditText)findViewById(R.id.host_name);
		user=(EditText)findViewById(R.id.user_name);
		password=(EditText)findViewById(R.id.pass);
		mButton=(Button)findViewById(R.id.save);
		
		dbname.setText(MainActivity.DBNAME);
		dbname.setSelection(dbname.getText().toString().length());
		url.setText(MainActivity.URL);
		user.setText(MainActivity.USER);
		password.setText(MainActivity.PASSWORD);
		mButton.setOnClickListener(new OnClickListener()
			{

				@Override
				public void onClick(View p1)
				{
					MainActivity.DBNAME=dbname.getText().toString();
					MainActivity.URL=url.getText().toString();
					MainActivity.USER=user.getText().toString();
					MainActivity.PASSWORD=password.getText().toString();
					MainActivity.initconnection();
					 
					if(MainActivity.mDbConnection!=null)
					{
						
						 
						MainActivity.editor.putString("db",dbname.getText().toString());
						MainActivity.editor.putString("url",url.getText().toString());
						MainActivity.editor.putString("user",user.getText().toString());
						MainActivity.editor.putString("password",password.getText().toString());
						MainActivity.editor.apply();
						
						finish();
					}
					else
					{
						
						
						MainActivity.editor.putString("db",dbname.getText().toString());
						MainActivity.editor.putString("url",url.getText().toString());
						MainActivity.editor.putString("user",user.getText().toString());
						MainActivity.editor.putString("password",password.getText().toString());
						MainActivity.editor.apply();
						MainActivity.initconnection();
						finish();
					}
					// TODO: Implement this method
				}
				
			
		});
	}
	
	
}
