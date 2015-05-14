package god.forum.fantasgo;




import java.util.ArrayList;

import com.actionbarsherlock.app.SherlockActivity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class CommentType extends SherlockActivity {
	
	
	ListView listview;
	ArrayList<SingleRow> list;
	private static final String KEY_ID = "id";
	private static final String KEY_CAT_ID = "cat_id";
	String key_id,key_cat_id,posi,nege,gen;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{ 
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		setContentView(R.layout.comment_layout_listview);
		Bundle bundle=getIntent().getExtras();
		 key_id=bundle.getString(KEY_ID);
		 key_cat_id=bundle.getString(KEY_CAT_ID);
		 posi=bundle.getString("positive");
		 nege=bundle.getString("negative");
		 gen =bundle.getString("general");
		 
		listview=(ListView)findViewById(R.id.comment_layout_listview);
		listview.setAdapter(new myAdapter(getApplicationContext()));
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				
				Intent intent=new Intent(getApplicationContext(),CommentSection.class);
				intent.putExtra(KEY_ID, key_id);
				intent.putExtra(KEY_CAT_ID, key_cat_id);
				if(position==0)
					//intent.putExtra("choice_id", "http://10.0.2.2:8080/forum_fantasgo/positive_comments.php");
				intent.putExtra("choice_id", "http://www.groupofdevelopers.com/forum_fantasgo/positive_comments.php");
				else if(position==1)
					//intent.putExtra("choice_id", "http://10.0.2.2:8080/forum_fantasgo/negative_comments.php");
				intent.putExtra("choice_id", "http://www.groupofdevelopers.com/forum_fantasgo/negative_comments.php");
				else
					intent.putExtra("choice_id", "http://www.groupofdevelopers.com/forum_fantasgo/general.php");
				
				startActivity(intent);
			}
		});
		
		
	}
	
	class SingleRow
	{
		String title,no;
		
		public SingleRow(String large,String commentno) {
		this.title=large;
		this.no=commentno;
		}
	}
	
	class myAdapter extends BaseAdapter
	{

		Context context;
		public myAdapter(Context c) {
			context=c;
			list=new ArrayList<SingleRow>();
			list.add(new SingleRow("I Like", posi));
			list.add(new SingleRow("I dont like", nege));
			list.add(new SingleRow("General Comments", gen));

		}
		@Override
		public int getCount() {
			
			return list.size();
		}

		@Override
		public Object getItem(int pos) {
			
			return list.get(pos);
		}

		@Override
		public long getItemId(int pos) {
			
			return pos;
		}

		@Override
		public View getView(int position, View arg1, ViewGroup parent) {
			
			LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.comment_type, parent,false);
			TextView text=(TextView)row.findViewById(R.id.comment_type_textview_positive);
			TextView count=(TextView)row.findViewById(R.id.positive_count);
			
			SingleRow single=list.get(position);
			
			text.setText(single.title);
			count.setText(single.no);
			return row;
		}
		
	}
	
}
