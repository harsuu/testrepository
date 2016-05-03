package com.example.sqlitedatabse;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
public class DisplayContact extends Activity{ 
EditText name;
EditText phonenumber;
EditText email;
EditText place;
Button btn;
DBHelper mydb;
int id_To_Update=0;
@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_display_contact);
		name=(EditText)findViewById(R.id.edittxt1);
		phonenumber=(EditText)findViewById(R.id.edittxt1);
		email=(EditText)findViewById(R.id.edittxt1);
		place=(EditText)findViewById(R.id.edittxt1);
		btn=(Button)findViewById(R.id.btn);
		mydb = new DBHelper(this);
		Bundle extra = getIntent().getExtras();
		if(extra !=null);
		{
			int Value=extra.getInt("id");
			if(Value>0){
				 Cursor rs = mydb.getData(Value);
		            id_To_Update = Value;
		            rs.moveToFirst();
		            String nam = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_NAME));
		            String phon = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PHONE));
		            String emai = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_EMAIL));
		            String plac = rs.getString(rs.getColumnIndex(DBHelper.CONTACTS_COLUMN_PLACE));
		            if(!rs.isClosed()){
		            	rs.close();
		            }	
		            btn.setVisibility(View.INVISIBLE);
		            name.setText((CharSequence)nam);
		            name.setFocusable(false);
		            name.setClickable(false);
		            phonenumber.setText((CharSequence)nam);
		            phonenumber.setFocusable(false);
		            phonenumber.setClickable(false);
		            email.setText((CharSequence)nam);
		            email.setFocusable(false);
		            email.setClickable(false);
		            place.setText((CharSequence)nam);
		            place.setFocusable(false);
		            place.setClickable(false);
		            
			}
		}
	}
@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
	Bundle extra = getIntent().getExtras();
	if(extra!=null){
		int Value=extra.getInt("id");
		if(Value>0){
		     getMenuInflater().inflate(R.menu.display_contact, menu);
		}
		else{
		     getMenuInflater().inflate(R.menu.main, menu);
		}
	}
		return true;
	}

public boolean onOptionsItemSelected(MenuItem item) 
{ 
   super.onOptionsItemSelected(item); 
   switch(item.getItemId()) 
{ 
   case R.id.Edit_Contact: 
   //Button b = (Button)findViewById(R.id.button1);
   btn.setVisibility(View.VISIBLE);
   name.setEnabled(true);
   name.setFocusableInTouchMode(true);
   name.setClickable(true);

   phonenumber.setEnabled(true);
   phonenumber.setFocusableInTouchMode(true);
   phonenumber.setClickable(true);

   email.setEnabled(true);
   email.setFocusableInTouchMode(true);
   email.setClickable(true);

   //street.setEnabled(true);
   //street.setFocusableInTouchMode(true);
   //street.setClickable(true);

   place.setEnabled(true);
   place.setFocusableInTouchMode(true);
   place.setClickable(true);

   return true; 
   case R.id.Delete_Contact:

	   AlertDialog.Builder builder = new AlertDialog.Builder(this);
   builder.setMessage(R.string.deleteContact)
   .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
         mydb.deleteContact(id_To_Update);
         Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();  
         Intent intent = new Intent(getApplicationContext(),MainActivity.class);
         startActivity(intent);
      }
   })
   .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
      public void onClick(DialogInterface dialog, int id) {
         // User cancelled the dialog
      }
   });
   AlertDialog d = builder.create();
   d.setTitle("Are you sure");
   d.show();

   return true;
   default: 
   return super.onOptionsItemSelected(item); 

   } 
} 

public void run(View view)
{	
   Bundle extras = getIntent().getExtras();
   if(extras !=null)
   {
      int Value = extras.getInt("id");
      if(Value>0){
         if(mydb.updateContact(id_To_Update,name.getText().toString(), phonenumber.getText().toString(), email.getText().toString(), place.getText().toString())){
            Toast.makeText(getApplicationContext(), "Updated", Toast.LENGTH_SHORT).show();	
            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
         }		
         else{
            Toast.makeText(getApplicationContext(), "not Updated", Toast.LENGTH_SHORT).show();	
         }
      }
      else{
         if(mydb.insertContact(name.getText().toString(), phonenumber.getText().toString(), email.getText().toString(), place.getText().toString())){
            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();	
         }		
         
         else{
            Toast.makeText(getApplicationContext(), "not done", Toast.LENGTH_SHORT).show();	
         }
         Intent intent = new Intent(getApplicationContext(),MainActivity.class);
         startActivity(intent);
      }
   }
}
} 
