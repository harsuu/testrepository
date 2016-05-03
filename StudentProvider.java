package com.example.example;
import java.util.HashMap;

import android.app.Activity;
import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.hardware.Camera.FaceDetectionListener;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnCreateContextMenuListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StudentProvider extends ContentProvider{
	//public static final Uri CONTENT_URI = null;
	//public static final String NAME = null;
	//public static final String GRADE = null;
	EditText edit1,edit2,edit3;
	Button btn1,btn2;
	static final String PROVIDER_NAME="com.example.exmple.college";
	static final String URL = "content://" + PROVIDER_NAME + "/students";
	static final Uri CONTENT_URI = Uri.parse(URL);
    //static final Uri CONTENT_URI= Uri.parse(URI);   
	   static final String _ID = "_id";
	   static final String NAME = "name";
	   static final String GRADE = "grade";
	   private HashMap<String,String>STUDENTS_PROJECTION_MAP;
	   static final int STUDENT=1;
	   static final int STUDENT_ID=3;
	   static final UriMatcher uriMatcher;
	   static{
		   uriMatcher=new UriMatcher(UriMatcher.NO_MATCH);
		   uriMatcher.addURI(PROVIDER_NAME,"student", STUDENT);
		   uriMatcher.addURI(PROVIDER_NAME, "students/#",STUDENT_ID);
	   }
	   private SQLiteDatabase db;
	   static final String DATABASE_NAME="college";
	   static final int DATABASE_VERSION=1;
	   static final String Student_TABLE_NAME="Student";
	   static final String CREATE_DB_TABLE=
			   "CREATE TABLE"+Student_TABLE_NAME+
			   "(_id INTEGER PRIMARY KEY AUTOINCREMENT,"+
				"name TEXT NOT Null,"+
			   "grade TEXT NOT Null);";
	   /*private static class DatabaseHelper extends SQliteOpenHelper{
		        DatabaseHelper(Context context) {
				// TODO Auto-generated constructor stub
		        	super
		        	(context,DATABASE_NAME,null,DATABASE_VERSION);
		        	
			}
	   }*/
	private static class DatabaseHelper extends SQLiteOpenHelper{
                  DatabaseHelper(Context context){
                	  super(context, DATABASE_NAME, null, DATABASE_VERSION);
                  }
		public DatabaseHelper(Context context, String name, CursorFactory factory, int version) {
			super(context, name, factory, version);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			// TODO Auto-generated method stub
			db.execSQL(CREATE_DB_TABLE);
		}
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			// TODO Auto-generated method stub
			db.execSQL("DROP TABLE IF EXISTS "+"Student_TABLE_NAME");
			onCreate(db);
		}
	}
	@Override
	public boolean onCreate() {
		// TODO Auto-generated method stub
		Context contet=getContext();
		DatabaseHelper dbHeler=new DatabaseHelper(contet);
		db=dbHeler.getWritableDatabase();
		return (db==null)?false:true;
	}
	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		 SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
	      qb.setTables(Student_TABLE_NAME);
	      
	      switch (uriMatcher.match(uri)) {
	         case STUDENT:
	         qb.setProjectionMap(STUDENTS_PROJECTION_MAP);
	         break;
	         
	         case STUDENT_ID:
	         qb.appendWhere( _ID + "=" + uri.getPathSegments().get(1));
	         //qb.appendWhere(_ID+"="+uri.getPathSegments().get(2));
	         
	         break;
	         
	         default:
	         throw new IllegalArgumentException("Unknown URI " + uri);
	      }
	      
	      if (sortOrder == null || sortOrder == ""){
	  
	         sortOrder = NAME;
	      }
	      Cursor c = qb.query(db,	projection,	selection, selectionArgs,null, null, sortOrder);
	      c.setNotificationUri(getContext().getContentResolver(), uri);
	      return c;
		// TODO Auto-generated method stub
	}
	@Override
	public String getType(Uri uri) {
		// TODO Auto-generated method stub
		switch(uriMatcher.match(uri)){
		case STUDENT:
	         return "vnd.android.cursor.dir/vnd.example.students";
	         
	         /** 
	         * Get a particular student
	         */
	         case STUDENT_ID:
	         return "vnd.android.cursor.item/vnd.example.students";
	         
	         default:
	         throw new IllegalArgumentException("Unsupported URI: " + uri);
		}
		//return null;
	}
	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowID=db.insert( Student_TABLE_NAME,"",values);
	    if(rowID>0){
	    	Uri _uri = ContentUris.withAppendedId(CONTENT_URI, rowID);
	         getContext().getContentResolver().notifyChange(_uri, null);
	         return _uri;
	    }
		// TODO Auto-generated method stub
		throw new  SQLException("failed to add new records"+uri); 
	}
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count=0;
	     switch (uriMatcher.match(uri)){
         case STUDENT:
         count = db.delete(Student_TABLE_NAME, selection, selectionArgs);
         break;
         
         case STUDENT_ID:
         String id = uri.getPathSegments().get(1);
         count = db.delete( Student_TABLE_NAME, _ID +  " = " + id + 
         (!TextUtils.isEmpty(selection) ? " AND (" + selection + ')' : ""), selectionArgs);
         break;
         
         default: 
         throw new IllegalArgumentException("Unknown URI " + uri);
      }
      
      getContext().getContentResolver().notifyChange(uri, null);
      return count;
	}
	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		// TODO Auto-generated method stub
		int count=0;
		switch(uriMatcher.match(uri)){
		case STUDENT:
	    count=db.update(Student_TABLE_NAME, values,selection, selectionArgs);
	    break;
	     case STUDENT_ID:
	         count = db.update(Student_TABLE_NAME, values, _ID + " = " + uri.getPathSegments().get(1) + 
	         (!TextUtils.isEmpty(selection) ? " AND (" +selection + ')' : ""), selectionArgs);
	         break;
	         
	         default: 
	         throw new IllegalArgumentException("Unknown URI " + uri );
	      }
	      getContext().getContentResolver().notifyChange(uri, null);
	      return count;
	}

}
