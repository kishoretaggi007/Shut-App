package com.example.mudassirbhai.test;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBAdapter1 {
	
	public static final String color="COLOR";
	public static final String unId="unId";
	public static final String DATABASE_CREATE="create table if not exists colour( "+unId+" integer primary key,"+color+" text UNIQUE);";
	public static final String dbName="M";
	public static final int dbVersion=1;
	final Context context;
	private DatabaseHelper DBHelper;
	private SQLiteDatabase db;

	DBAdapter1(Context cntxt){
		this.context=cntxt;
		DBHelper = new DatabaseHelper(context);
		}

	private static class DatabaseHelper extends SQLiteOpenHelper 
	{
	    DatabaseHelper(Context context) 
	    {
	        super(context,dbName, null, dbVersion);
	    }

	    @Override
	    public void onCreate(SQLiteDatabase db) 
	    {
	        db.execSQL(DATABASE_CREATE);
	        
	    }

	    @Override
	    public void onUpgrade(SQLiteDatabase db, int oldVersion,int newVersion) 
	    {
	        db.execSQL("DROP TABLE IF EXISTS COLOUR;");
	        onCreate(db);
	    }
	}    

	public DBAdapter1 open() throws SQLException{
		db=DBHelper.getWritableDatabase();
		
		return this;
	}

	public void close() 
	{
	    DBHelper.close();
	}
	public double iC() 
	{
		
	    ContentValues initialValues = new ContentValues();
	    initialValues.put(color,"#034240");
	    
	    return db.insert("colour", null,initialValues);
	    
	}
	public void uC(String cn) throws SQLException
	{
		  ContentValues initialValues = new ContentValues();
		  int x=1;
		  initialValues.put(color,cn);
		  
		 
				  db.update("colour",initialValues,unId+"="+x , null);
	}
	public String gC() throws SQLException 
	{
		int y=1;
		String  cname = null;
		String selectQuery = "SELECT * FROM colour ;";
		
	    
	    Cursor cursor = db.rawQuery(selectQuery,null);
	  
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	cname= cursor.getString(cursor.getColumnIndex(color));
	        } while (cursor.moveToNext());
	    }
	    return cname;
	}

}
