package com.mia.customlistdemo;

import java.util.ArrayList;
import java.util.HashMap;
import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity {
	 
	  //ArrayList thats going to hold the search results
	  ArrayList<HashMap<String, Object>> searchResults;
	 
	  //ArrayList that will hold the original Data
	  ArrayList<HashMap<String, Object>> originalValues;
	  LayoutInflater inflater;
	 
	  @Override
	  public void onCreate(Bundle savedInstanceState) {
	 super.onCreate(savedInstanceState);
	  
	 setContentView(R.layout.activity_main);
	 final EditText searchBox=(EditText) findViewById(R.id.searchBox);
	 ListView playersListView=(ListView) findViewById(android.R.id.list);
	 
	 //get the LayoutInflater for inflating the customomView
	 //this will be used in the custom adapter
	 inflater=(LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	 
	 //these arrays are just the data that 
	 //I'll be using to populate the ArrayList
	 //You can use our own methods to get the data
	 String names[]={"FIESTA","XJ","BOXSTER"}; 
	 
	 String teams[]={"Ford","Jaguar","Porsche"};
	 Integer[] photos={R.drawable.picford,R.drawable.picjaguar,R.drawable.picporsche};
	 
	 originalValues=new ArrayList<HashMap<String,Object>>();
	 
	 //temporary HashMap for populating the 
	 //Items in the ListView
	 HashMap<String , Object> temp;
	 
	 //total number of rows in the ListView
	 int noOfPlayers=names.length;
	 
	 //now populate the ArrayList players
	 for(int i=0;i<noOfPlayers;i++)
	 {
	  temp=new HashMap<String, Object>();
	 
	  temp.put("name", names[i]);
	  temp.put("team", teams[i]);    
	  temp.put("photo", photos[i]);
	 
	  //add the row to the ArrayList
	  originalValues.add(temp);        
	 }
	 //searchResults=OriginalValues initially
	 searchResults=new ArrayList<HashMap<String,Object>>(originalValues);
	 
	 //create the adapter
	 //first param-the context
	 //second param-the id of the layout file 
	 //you will be using to fill a row
	 //third param-the set of values that
	 //will populate the ListView
	 final CustomAdapter adapter=new CustomAdapter(this, R.layout.activity_item,searchResults); 
	 
	 //finally,set the adapter to the default ListView
	 playersListView.setAdapter(adapter);
	 searchBox.addTextChangedListener(new TextWatcher() {
	 
	 public void onTextChanged(CharSequence s, int start, int before, int count) {
	   //get the text in the EditText
	   String searchString=searchBox.getText().toString();
	   int textLength=searchString.length();
	   searchResults.clear();
	 
	   for(int i=0;i<originalValues.size();i++)
	   {
	  String playerName=originalValues.get(i).get("name").toString();
	  if(textLength<=playerName.length()){
	  //compare the String in EditText with Names in the ArrayList
	    if(searchString.equalsIgnoreCase(playerName.substring(0,textLength)))
	    searchResults.add(originalValues.get(i));
	  }}
	   adapter.notifyDataSetChanged();
	 }
	 
	 public void beforeTextChanged(CharSequence s, int start, int count,
	     int after) {}
	   
	 public void afterTextChanged(Editable s) {}
	  });
	 }
	 
	 //define your custom adapter
	 private class CustomAdapter extends ArrayAdapter<HashMap<String, Object>>
	 {
	  public CustomAdapter(Context context, int textViewResourceId,
	    ArrayList<HashMap<String, Object>> Strings) {
	 
	   //let android do the initializing :)
	   super(context, textViewResourceId, Strings);
	  }
	 
	  //class for caching the views in a row  
	  private class ViewHolder
	  {
	   ImageView photo;
	   TextView name,team;
	  }
	 
	  ViewHolder viewHolder;
	 
	  @Override
	  public View getView(int position, View convertView, ViewGroup parent) {
	 
	   if(convertView==null)
	   {
	    convertView=inflater.inflate(R.layout.activity_item, null);
	    viewHolder=new ViewHolder();
	 
	     //cache the views
	     viewHolder.photo=(ImageView) convertView.findViewById(R.id.photo);
	     viewHolder.name=(TextView) convertView.findViewById(R.id.name);
	     viewHolder.team=(TextView) convertView.findViewById(R.id.team);
	 
	      //link the cached views to the convertview
	      convertView.setTag(viewHolder);
	   }
	   else
	    viewHolder=(ViewHolder) convertView.getTag();
	   int photoId=(Integer) searchResults.get(position).get("photo");
	 
	   //set the data to be displayed
	   viewHolder.photo.setImageDrawable(getResources().getDrawable(photoId));
	   viewHolder.name.setText(searchResults.get(position).get("name").toString());
	   viewHolder.team.setText(searchResults.get(position).get("team").toString());
	   //return the view to be displayed
	   return convertView;
	  }
	 }
		protected void onListItemClick(ListView l, View v, int position, long id) {
			// TODO Auto-generated method stub
			super.onListItemClick(l, v, position, id);
			String str = searchResults.get(position).get("name").toString();
			try{
				if(str=="XJ"){
					Toast.makeText(getApplicationContext(), "XJ\nJAGUAR", Toast.LENGTH_SHORT).show();
				}
				if(str=="FIESTA"){
					Toast.makeText(getApplicationContext(), "FIESTA\nFORD", Toast.LENGTH_SHORT).show();
				}
				if(str=="BOXSTER"){
					Toast.makeText(getApplicationContext(), "BOXSTER\nPORSCHE", Toast.LENGTH_SHORT).show();
				}
			}
				catch(Exception e){
					e.printStackTrace();
				}
			
}
}