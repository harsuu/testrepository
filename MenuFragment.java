package com.example.fregment;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment extends Fragment{
	//TextView text,vers;
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
		 
        View view = inflater.inflate(R.layout.activity_main, container, false);
       // text= (TextView) view.findViewById(R.id.AndroidOs);
        //vers= (TextView)view.findViewById(R.id.Version);
 
 
        return view;
 
    }
}



