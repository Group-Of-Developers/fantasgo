package god.forum.fantasgo;


import java.util.ArrayList;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class SearchThread extends SherlockActivity {
	
	
	ListView listview;
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_thread);
   	
		listview=(ListView)findViewById(R.id.listview1);
		listview.setAdapter(new myAdapter(this));
		
	}
	
	class SingleRow
	{
		String questions,datess;
		public SingleRow(String first,String second ) {
			this.questions=first;
			this.datess=second;
		}
	}
	
	class myAdapter extends BaseAdapter
	{
		ArrayList<SingleRow> list;
		Context context;
		public myAdapter(Context c) {
			context=c;
		list=new ArrayList<SingleRow>();
		String question[]=new String[]{"h  ","jlalalalalalalalalalalalalalalalalaaja"};
		String dates[]=new String[]{"25/10/13","15/101666"};
		for(int i=0;i<2;i++)
			list.add(new SingleRow(question[i],dates[i]));
		}
		
		@Override
		public int getCount() {
		
			return list.size();
		}

		@Override
		public Object getItem(int arg0) {
		
			return list.get(arg0);
		}

		@Override
		public long getItemId(int position) {
		
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
		
			LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflator.inflate(R.layout.second_screen_list, parent,false);
			TextView ques=(TextView)row.findViewById(R.id.questions);
			TextView dat=(TextView)row.findViewById(R.id.dates);
			
			SingleRow temp=list.get(position);
			ques.setText(temp.questions);
			dat.setText(temp.datess);
			
			return row;
		}
		
	}

}
