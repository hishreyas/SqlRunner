package com.sanedriod.sqlrunner;
import android.widget.*;
import android.widget.TableRow.*;
import android.content.*;
import android.util.*;
import android.graphics.*;
import android.view.*;

public class TablesCreater
{
	TableLayout table;
	TableRow tablerow;
	TableRow.LayoutParams mLayoutParams;
	TextView textview;
	Context context;
	public TablesCreater(TableLayout tablelayout,Context context)
	{
		this.table=tablelayout;
		tablelayout.setScrollContainer(true);
	
		tablelayout.setHorizontalScrollBarEnabled(true);
		this.context=context;
		mLayoutParams=new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT);
		
	}
	
	public void DisplayTable(String[] data,int row,int col)
	{
		Log.d("length",data.length+"");
		for(int i=0,index=0;i<row;i++)
		{
			tablerow=new TableRow(context);
			tablerow.setLayoutParams(mLayoutParams);
			if(index<col)
			{
				tablerow.setBackgroundColor(Color.parseColor("#185CC1"));
			}
			else
			{
				tablerow.setBackgroundColor(Color.WHITE);
			}
			for(int j=0;j<col;j++)
			{
			
				textview=new TextView(context);
				textview.setText(data[index]);
				textview.setGravity(Gravity.CENTER);
				textview.setPadding(8,8,8,8);
				tablerow.addView(textview);
				Log.d("counter",i+"");
				index++;
			}
			
			table.addView(tablerow,i);
			
			
		}
	}
	
}
