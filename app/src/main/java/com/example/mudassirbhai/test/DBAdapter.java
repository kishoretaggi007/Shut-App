package com.example.mudassirbhai.test;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
//import android.util.Log;

public class DBAdapter {
	
public static final String pkgName="App_Name";
public static final String unId="unId";
public static final String Time="time";
public static final String NotCount="notCount";
public static final String dbName="ShutApp";

public static final String DATABASE_CREATE="create table if not exists blockedlist(id integer primary key autoincrement ,"+pkgName+" text UNIQUE,"+Time+" text,"+NotCount+" integer);";
public static final int dbVersion=1;
final Context context;
private DatabaseHelper DBHelper;
private SQLiteDatabase db;
//ArrayList<String> labels1;

DBAdapter(Context cntxt){
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
        db.execSQL("DROP TABLE IF EXISTS BLOCKEDLIST;");
        onCreate(db);
    }
}    


public DBAdapter open() throws SQLException{
	db=DBHelper.getWritableDatabase();
	
	return this;
}

public void close() 
{
    DBHelper.close();
}

public double insertRecord(String AppName,String timeRem) 
{
    ContentValues initialValues = new ContentValues();
    initialValues.put(pkgName,AppName);
    initialValues.put(Time,timeRem);
    initialValues.put(NotCount,0);
    return db.insert("blockedlist", null,initialValues);
    
    
}

String yi;
public String getRecord(String name) throws SQLException 
{
	String selectQuery = "SELECT "+Time+" FROM blockedlist WHERE "+pkgName+"='"+name+"';";
	
    
    Cursor cursor = db.rawQuery(selectQuery,null);
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	 yi= cursor.getString(cursor.getColumnIndex(Time));
        } while (cursor.moveToNext());
    }
    return yi;
}
String pname;
public String getP(String n) throws SQLException{

	String selectQuery = "SELECT "+pkgName+" FROM blockedlist WHERE "+Time+"='"+n+"';";
	
    
    Cursor cursor = db.rawQuery(selectQuery,null);
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	pname= cursor.getString(cursor.getColumnIndex(pkgName));
        } while (cursor.moveToNext());
    }
   
	return pname;
}

public void delete(String t) throws SQLException
{
	  //ContentValues initialValues = new ContentValues();
	  
	  String selectQuery = "DELETE FROM blockedlist WHERE "+Time+"='"+t+"';";
		db.execSQL(selectQuery);
	  //return db.delete("blockedlist", Time + "=" + t, null)>0;
	   
	  
   
}

public void delRecord(String t) throws SQLException
{
	  //ContentValues initialValues = new ContentValues();
	  
	  String selectQuery = "DELETE FROM blockedlist WHERE "+pkgName+"='"+t+"';";
		db.execSQL(selectQuery);
	  //return db.delete("blockedlist", pkgName + "=" + t, null)>0;
	   
	  
   
}

Integer y;
public int getCount(String name) throws SQLException 
{
	String selectQuery = "SELECT "+NotCount+" FROM blockedlist WHERE "+pkgName+"='"+name+"';";
	
    
    Cursor cursor = db.rawQuery(selectQuery,null);
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	y= cursor.getInt(cursor.getColumnIndex(NotCount));
        } while (cursor.moveToNext());
    }
    return y;
}
 public void updateRecord(String namPak) throws SQLException
{
	  ContentValues initialValues = new ContentValues();
	  Integer c=getCount(namPak);
	  c=c++;
	  initialValues.put(NotCount,c);
	  
	 
			  db.update("blockedlist",initialValues, pkgName+"='"+namPak+"'", null);
}

 public void updatRecord(String namPak,String ti) throws SQLException
 {
 	  ContentValues initialValues = new ContentValues();
 	  
 	  initialValues.put(pkgName,ti);
 	  
 	 
 			  db.update("blockedlist",initialValues, pkgName+"='"+namPak+"'", null);
 }

public List<String> getAllNames(){
 List<String> labels = new ArrayList<String>();
   
     
    // Select All Query
    String selectQuery = "SELECT * FROM blockedlist";
  
    
    Cursor cursor = db.rawQuery(selectQuery,null);
  
    // looping through all rows and adding to list
    if (cursor.moveToFirst()) {
        do {
        	
            labels.add(cursor.getString(cursor.getColumnIndex(pkgName)));
        } while (cursor.moveToNext());
    }
     
    return labels;
}

public List<String> getAllTimes(){
	 List<String> labels = new ArrayList<String>();
	   
	     
	    // Select All Query
	    String selectQuery = "SELECT * FROM blockedlist";
	  
	    
	    Cursor cursor = db.rawQuery(selectQuery,null);
	  
	    // looping through all rows and adding to list
	    if (cursor.moveToFirst()) {
	        do {
	        	
	            labels.add(cursor.getString(cursor.getColumnIndex(Time)));
	        } while (cursor.moveToNext());
	    }
	     
	    return labels;
	}



}
