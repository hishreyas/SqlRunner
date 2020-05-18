package com.sanedriod.sqlrunner;

import android.app.*;
import android.os.*;
import android.widget.MultiAutoCompleteTextView;
import android.view.View.*;
import android.view.*;
import java.sql.*;
import java.lang.Process;
import android.view.Window.*;
import android.graphics.*;
import android.graphics.drawable.*;
import java.io.*;
import android.widget.*;
import android.widget.MultiAutoCompleteTextView.*;
import android.text.*;
import android.content.ClipboardManager;
import android.content.*;
import android.net.*;
import java.util.*;
import java.security.acl.*;
import android.widget.AdapterView.*;
import android.view.accessibility.*;

public class MainActivity extends Activity 
{

	

	
	
	public static  String URL="";
	public static  String DBNAME="";
	public static  String USER="";
	public static String  PASSWORD="";
	public String query;
	public String count="1";
	public String Result="1";
	int countint=1;
	public static DbConnection mDbConnection;
	public static Query mQuery;
	public static Drawable draw;
	EditText mEditText;
	Button mButton;
	ActionMode.Callback actionmode;
	RelativeLayout root;
	TextView output;
	TextView countline;
	TextView path_name;
	TextView file_name;
	TextView character_count;
	String before="";
	ClipboardManager cp;
	Dialog dia;
	File file=null;
	String content="";
	static SharedPreferences msharedperf;
	static SharedPreferences.Editor editor;
	Set<String> set_str;
	String last;
	String defaultstr;
	ArrayList<String> list;
	Intent intent;
	String last_file_path;
	Menu menu;

	
	

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
		mEditText=findViewById(R.id.mainEditText);
		root=findViewById(R.id.rootRelativeLayout);
		output=findViewById(R.id.mainTextView);
		countline=findViewById(R.id.cnt);
		path_name=findViewById(R.id.file_path_text);
		file_name=findViewById(R.id.file_name_text);
		character_count=findViewById(R.id.char_count);
		cp=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
		draw=this.getResources(). getDrawable(R.drawable.ic_fiber_manual_record_black_24dp);
		dia=new Dialog(this);
		msharedperf=getSharedPreferences("Default",Context.MODE_PRIVATE);
		path_name.setText("path: "+Environment.getExternalStorageDirectory().toString()+"/"+"SqlRunner"+"/");
       	editor=msharedperf.edit();
		list=new ArrayList<String>();
		defaultstr=msharedperf.getString("Defaults",defaultstr);
		set_str=msharedperf.getStringSet("list",null);
		last_file_path=msharedperf.getString("last_file",last_file_path);
		initdata();
		initmenu();
		
		this.setTitleColor(Color.WHITE);
		
		if(mDbConnection==null)
		{
			draw.setTint(Color.RED);
		}
		else
		{
			draw.setTint(Color.GREEN);
		}
		
		
		mEditText.setCustomSelectionActionModeCallback(actionmode);
		try
		{
			file=new File(last_file_path);
			path_name.setText(last_file_path);
			file_name.setText(file.getName());
			getcontent(file);
			mEditText.setSelection(mEditText.getText().toString().length());
			character_count.setText("Total Characters: "+mEditText.getText().toString().length() +" "+"\n"+" Position: "+mEditText.getSelectionStart());
			countline.setText(getlines(mEditText.getText().toString()));

			
		}
		catch(Exception e)
		{
			if(defaultstr!=null)
			{

				mEditText.setText(defaultstr);
				mEditText.setSelection(defaultstr.length());
				character_count.setText("Total Characters: "+mEditText.getText().toString().length() +" "+"\n"+" Position: "+mEditText.getSelectionStart());
				countline.setText(getlines(defaultstr));
			}
		}
	
	
			
		
		
		mEditText.setOnLongClickListener(new OnLongClickListener()
		{

				@Override
				public boolean onLongClick(View view)
				{
					
					startActionMode(actionmode);
					return true;
				}
				
			
		
		});
		mEditText.setAccessibilityDelegate(new View.AccessibilityDelegate(){
				
				@Override
				public void sendAccessibilityEvent(View host, int eventType) {
					super.sendAccessibilityEvent(host, eventType);
					if (eventType == AccessibilityEvent.TYPE_VIEW_TEXT_SELECTION_CHANGED){
						
						int start=mEditText.getSelectionStart();
						character_count.setText("Total Characters: "+mEditText.getText().toString().length()+" "+"\n"+" Position: "+(start));
						
					}
				}
			});
		
		
		

		mEditText.addTextChangedListener(new TextWatcher(){
			
			

				@Override
				public void beforeTextChanged(CharSequence p1, int start, int count, int after)
				{
					
					// TODO: Implement this method
				}

				@Override
				public void onTextChanged(CharSequence p1, int start, int count, int after)
				{
					
					
					// TODO: Implement this method
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					
					
					countline.setText(getlines(p1.toString()));
					// TODO: Implement this method
				}
				
			
		});

		
		
		
	
    }

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		// TODO: Implement this method
		if(requestCode==1)
		{
			String path;
			path=Environment.getExternalStorageDirectory().toString()+"/"+data.getData().getPath().substring(data.getData().getPath().indexOf(':')+1);
			
			path_name.setText("path:"+path);
			
			
			file=new File(path);
			if(file!=null)
			{
				file_name.setText(file.getName());
				getcontent(file);
			}
			else
			{
				homedata();
			}
			
			
		
		}
		
		super.onActivityResult(requestCode, resultCode, data);
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		
	   
		getMenuInflater().inflate(R.menu.settings,menu);
		this.menu=menu;
		if(file_name.getText().toString().equals("Home"))
		{
			showHome(false);
		}
		// TODO: Implement this method
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
	
		switch(item.getItemId())
		{
			case R.id.run:

				if(mDbConnection!=null)
				{
					    
						intent = new Intent(MainActivity.this, QueryResult.class);
						intent.putExtra("query",mEditText.getText().toString());
						startActivity(intent);
					    draw.setTint(Color.GREEN);
					    menu.findItem(R.id.connection_indicator).setIcon(draw);
					
				}
				else
				{
					
					draw.setTint(Color.RED);
					menu.findItem(R.id.connection_indicator).setIcon(draw);
					ShowMessege("Invalid Credentials To Connect Database");
				}
				
				
				break;
				
			case  R.id.connection_indicator:
				
				break;
				
		  case R.id.new_file:
			  showdialog(dia);
			  break;
			  
			case R.id.open_file:
				getFiles();
				break;
				
			case R.id.save_file:
				if(file!=null)
				{
					save_file(file);
					
				}
				else
				{
					editor.putString("Defaults",mEditText.getText().toString());
					editor.apply();
					ShowMessege("Saved");
				}
				break;
			case R.id.recent_files:
				
					
				
				if(set_str!=null)
				{
					getlist();
					listRecents();
				}
				
				break;
				
			case R.id.settings:
				intent = new Intent(MainActivity.this, Settings.class);
				startActivity(intent);
				break;

				
			case R.id.about:
				intent = new Intent(MainActivity.this, About.class);
				startActivity(intent);
				
				break;
				
				case R.id.save_file_option:
				if(file!=null)
				{
					save_file(file);
				}
				else
				{
					editor.putString("Defaults",mEditText.getText().toString());
					editor.apply();
					ShowMessege("Saved");
				}
					break;
			case R.id.home:
				homedata();
			    showHome(false);
				break;
		
			default:


		}
		return true;
	}
	
	
	
	public void ShowMessege(String messege)
	{
		
		Toast.makeText(MainActivity.this,messege,Toast.LENGTH_LONG).show();
	}
	
	private void initmenu()
	{
		actionmode = new ActionMode.Callback()
		{

			@Override
			public boolean onCreateActionMode(ActionMode mode, Menu menu)
			{
				
				
				mode.getMenuInflater().inflate(R.menu.action_mode,menu);
				
				
				return true;
			}

			@Override
			public boolean onPrepareActionMode(ActionMode p1, Menu p2)
			{
				// TODO: Implement this method
				return false;
			}

			@Override
			public boolean onActionItemClicked(ActionMode mode, MenuItem menuitem)
			{
				int start=mEditText.getSelectionStart();
				int end=mEditText.getSelectionEnd();
				switch(menuitem.getItemId())
				{
				
					
					case R.id.copy:
						
					cp.setText(mEditText.getText().toString().substring(start,end));
					break;
					
					case R.id.paste:
						mEditText.getText().replace(start,end,cp.getText());
					
					break;
						
					case R.id.cut:
						cp.setText(mEditText.getText().toString().substring(start,end));
						mEditText.getText().delete(start,end);
					break;
					
					case R.id.select_all:
						mEditText.selectAll();
						break;
			
						
					default:
					
					
				}
				return true;
			}

			@Override
			public void onDestroyActionMode(ActionMode p1)
			{
				// TODO: Implement this method
			}


		};
		
	}
	
	public void onrun()
	{
		query=mEditText.getText().toString();
		try
		{
			
				Result=mQuery.execQuery(mEditText.getText().toString());
				output.setText(Result);
		}
		catch (Exception e)
		{
			output.setText(e.getLocalizedMessage());
		}

	}
	
	public static void initconnection()
	{
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		

		try
		{ if(mDbConnection!=null)
		{
			mDbConnection.getconn().close();
			mDbConnection=null;
		}
		     
		
			mDbConnection = new DbConnection(URL, DBNAME, USER, PASSWORD);
			if(mDbConnection.getconn()!= null)
			{

				mQuery=new Query(mDbConnection);
				
				//draw.setTint(Color.GREEN);
				
			}
			else
			{
				
				
				
			}
		}
		catch (Exception e)
		{
			//draw.setTint(Color.RED);
			
		}

		
		
	}
	private String getlines(String text)
	{
		String lines="1";
		int count=1;
		for(char c:text.toCharArray())
		{
			if(c=='\n')
			{
				count++;
				lines+="\n"+count;
			}
		}
		return lines;

	}
	public void showdialog(final Dialog d)
	{
		
		d.setContentView(R.layout.create_file);
		d.setCancelable(true);
		d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		d.setTitle("Create New File");
		d.create();
		d.show();
		final EditText t=d.findViewById(R.id.file_name);
		
		d.setOnKeyListener(new Dialog.OnKeyListener(){

				@Override
				public boolean onKey(DialogInterface p1, int p2, KeyEvent p3)
				{
					if(p3.getKeyCode()==p3.KEYCODE_ENTER)
					{
						file=createfile(t.getText().toString());
						
						getcontent(file);
						path_name.setText("path:"+file.getAbsolutePath());
						file_name.setText(file.getName());
						addlist(file.getName());
					
						d.dismiss();
					}
					// TODO: Implement this method
					return false;
				}
				
			
		});
	}
	public File createfile(String name)
	{
		File directory=new File(Environment.getExternalStorageDirectory().toString()+"/"+"SqlRunner");
		File file=null;
		if(!directory.exists())
		{
			directory.mkdirs();
			
		}
		
		
		try
		{
		
			file = new File(directory.toString()+"/"+name+".txt");
			if(file.exists())
			{
				return file;
			}
			else
			{
			file.createNewFile();
			}
		}
		catch (IOException e)
		{}
	return file;
	}
	private void getFiles()
	{
		Intent intent=new Intent();
		intent.setAction(Intent.ACTION_GET_CONTENT);
		intent.setType("text/*");
		startActivityForResult(intent,1);
		
		
		
	}
	private void getcontent(File f)
	{
		
		content="";
		try
		{
			FileReader fr=new FileReader(f);
			
			BufferedReader buf=new BufferedReader(fr);

			try
			{
				String temp="";
				while ((temp=buf.readLine()) != null)
				{
					content+=temp+"\n";
					


				}
				
				mEditText.setText(content);
				countline.setText(getlines(content));
				mEditText.setSelection(mEditText.getText().toString().length());
				character_count.setText("Total Characters: "+mEditText.getText().toString().length() +" "+"\n"+" Position: "+mEditText.getSelectionStart());
				editor.putString("last_file",f.getPath());
				editor.apply();
				showHome(true);
			    
			}
			catch (IOException e)
			{
				ShowMessege(e.getLocalizedMessage());
			}


		
			
			
			
		}
		catch (FileNotFoundException e)
		{
			ShowMessege(e.getLocalizedMessage());
		}
		
		
	}
	private void save_file(File file)
	{
		
		try
		{
			FileWriter fw=new FileWriter(file);
			fw.write(mEditText.getText().toString());
			fw.close();
			
			ShowMessege("Succesfully Save");
		}
		catch (IOException e)
		{}

	}
	public void listRecents()
	{
		
		dia.setContentView(R.layout.recents_file);
		dia.setCancelable(true);
		dia.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
		dia.setTitle("Recents Files");
		dia.create();
		dia.show();
		
		ListView list_view=dia.findViewById(R.id.recents_list);
		
		
			ArrayAdapter<String> adaoter=new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,list);
			
		list_view.setAdapter(adaoter);
		
		
		list_view.setOnItemClickListener(new OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					TextView t=(TextView)p2;
					String path=Environment.getExternalStorageDirectory().toString()+"/"+"SqlRunner"+"/"+t.getText();			
					path_name.setText("path:"+path);
					file_name.setText(t.getText());
					file=new File(path);
					getcontent(file);
					dia.dismiss();
					// TODO: Implement this method
				}
				
			
		});
	
	}

	private void addlist(String name)
	{
		if(set_str!=null)
		{
		set_str=msharedperf.getStringSet("list",set_str);
		last=msharedperf.getString("last",last);
		}
		else
		{
			set_str=new TreeSet<>();
		}
		if(set_str.isEmpty())
		{
			last=name;
			set_str.add(name);
			
		}
		else if(set_str.size()>=6)
		{
			set_str.remove(last);
			last=name;
			set_str.add(name);
			
		}
		else
		{
			set_str.add(name);
			last=name;

		}
		editor.putString("last",last);
		editor.putStringSet("list",set_str);
		editor.apply();
	}
	private void   getlist()
	{
	
		
		
		if(!list.isEmpty())
		{
			list.clear();
		}
		
		int i=0;
		
		Iterator it= set_str.iterator();
		
		for(i=0;i<set_str.size();i++)
		{
			it.hasNext();
			list.add((String)it.next());
		
		}
		
			
		
	}
	public void homedata()
	{
		mEditText.setText("");
		file_name.setText("Home");
		path_name.setText("path: "+Environment.getExternalStorageDirectory().toString()+"/"+"SqlRunner"+"/");
		if(defaultstr!=null)
		{
			defaultstr=msharedperf.getString("Defaults",defaultstr); 
			mEditText.setText(defaultstr);
			mEditText.setSelection(defaultstr.length());
			character_count.setText("Total Characters: "+mEditText.getText().toString().length() +" "+"\n"+" Position: "+mEditText.getSelectionStart());
			countline.setText(getlines(defaultstr));
			editor.putString("last_file",null);
			editor.apply();
		}
	}
	public void showHome(boolean flag)
	{
		if(menu!=null)
		{
			menu.findItem(R.id.home).setVisible(flag);
			
		}
	}
	static void initdata()
	{
	DBNAME=msharedperf.getString("db",null);
	USER=msharedperf.getString("user",null);
	URL=msharedperf.getString("url",null);
	PASSWORD=msharedperf.getString("password",null);
	initconnection();

	}

	@Override
	protected void onDestroy()
	{
		try
		{
			if(mDbConnection!=null)
			{
				mDbConnection.getconn().close();
			}
			
		}
		catch (SQLException e)
		{
			
		}
		// TODO: Implement this method
		super.onDestroy();
	}
	}

	

	
	
	
