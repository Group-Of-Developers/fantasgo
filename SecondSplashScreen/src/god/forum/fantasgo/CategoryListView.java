package god.forum.fantasgo;

import god.forum.fantasgo.library.JSONParser;
import god.forum.fantasgo.library.Networkcheck;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockFragment;

public class CategoryListView extends SherlockFragment {
	
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onPause()
	 */
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.Fragment#onResume()
	 */
	
	
	//public static final String KEY_URL = "http://10.0.2.2:8080/forum_fantasgo/";
	public static final String KEY_URL = "http://www.groupofdevelopers.com/forum_fantasgo/";
	public static final String KEY_CATEGORY = "category";
	public static final String KEY_LAST = "last_post";
	public static final int KEY_CAT_NAME = 1;
	public static final String KEY_CAT_TOTAL = "cat_id";
	protected static final String KEY_ID = "id";
	ListView listview;
	View v;
	ArrayList<SingleRow> list;
	ProgressDialog pDialog;
	JSONParser jsonParser = new JSONParser();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v= inflater.inflate(R.layout.list_view,container ,false);
		list=new ArrayList<SingleRow>();
        listview=(ListView)v.findViewById(R.id.listview);
        listview.setAdapter(new myAdapter(getActivity().getApplicationContext()));
        listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int position,
					long arg3) {
				if(Networkcheck.getInstance(getActivity().getApplicationContext()).isOnline(getActivity().getApplicationContext()))
				{
				SingleRow singlerowed=list.get(position);
				//Toast.makeText(getActivity().getApplicationContext(), singlerowed.catname+" "+singlerowed.cat_description+" "+singlerowed.cat_id+" "+singlerowed.cat_total, 4000).show();
				Intent intent=new Intent(getActivity().getApplicationContext(),ThreadCategory.class);
				intent.putExtra(KEY_ID,singlerowed.cat_id);
				intent.putExtra(KEY_CAT_TOTAL, singlerowed.cat_total);
				startActivity(intent);
				//singlerowed.lastcomment);
				}else{
					Toast.makeText(getActivity().getApplicationContext(), "Fantasgo in not able to connect ..Please check your Internet settings", Toast.LENGTH_LONG).show();
					
				}
				
			}
        	
		});
			
			new getcat().execute();
			
			 listview.setOnItemLongClickListener(new OnItemLongClickListener() {

					@Override
					public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						
						SingleRow singlerowed=list.get(position);
						Bundle bundle=new Bundle();
						bundle.putInt("VAR_ID", 1);
						bundle.putString("CAT_CATEGORY", "CATEGORY: "+singlerowed.catname);
						bundle.putString("CAT_DESCRIPTION", "DESCRIPTION: "+singlerowed.cat_description);
						bundle.putString("CAT_NOOFPOST", "POST(s): "+singlerowed.cat_total);
						bundle.putString("CAT_LASTPOST","LAST POST: "+singlerowed.lastcomment);
						Intent intent=new Intent(getActivity().getApplicationContext(),CategoryLongClick.class);
						intent.putExtras(bundle);
						startActivity(intent);
						return true;
					}
				});


		return v;
	} 
	/*
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.list_view);
    
        
	}
	*/
	
	class SingleRow
	{
		String catname;
		String lastcomment;
		String cat_id;
		String cat_description;
		String cat_total;
		public SingleRow(String big,String small,String id,String des,String total) {
		
			this.catname=big;
			this.lastcomment=small;
			this.cat_id=id;
			this.cat_description=des;
			this.cat_total=total;
		}
		
	}

	class myAdapter extends BaseAdapter
	{
		
		Context contest;
		public myAdapter(Context c) {
			
			contest=c;
			
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
			
			LayoutInflater inflater=(LayoutInflater)contest.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View row=inflater.inflate(R.layout.first_screen_list, parent,false);
			TextView catname=(TextView)row.findViewById(R.id.categoryname);
			TextView commentname=(TextView)row.findViewById(R.id.lastcomment);
			SingleRow singlerowed=list.get(position);
			
			catname.setText(singlerowed.catname);
			commentname.setText(singlerowed.lastcomment);
			
			return row;
		}
		
	}
	class getcat extends AsyncTask<Void, String, JSONObject>{

		@Override
		protected void onPostExecute(JSONObject result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			
			try {
				JSONArray array = result.getJSONArray(KEY_CATEGORY);
				JSONArray last_post = result.getJSONArray(KEY_LAST);
				listview.setAdapter(new myAdapter(getActivity().getApplicationContext()));
				
					for(int k=0;k<array.length();k++)
					{	
						JSONArray p = array.getJSONArray(k);
						JSONArray lp = last_post.getJSONArray(k);
						list.add(new SingleRow(p.getString(1), lp.getString(1),p.getString(0),p.getString(2),p.getString(3)));
						
					
					}
					
					//Toast.makeText(getActivity().getApplicationContext(), (CharSequence) p.getString(1), Toast.LENGTH_LONG).show();
					
					//list.add(new SingleRow("Mew", "hey buddy"));
				
				
				
					//String p = c.getString(k);
				
				//String abc = b.getString(0); 
	//			Toast.makeText(getApplicationContext(), ""+p,Toast.LENGTH_LONG ).show();	
				
				
			//Toast.makeText(getApplicationContext(), ""+abc,Toast.LENGTH_LONG ).show();	
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			pDialog.dismiss();
		}

		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			pDialog = new ProgressDialog(getSherlockActivity());
			pDialog.setMessage("Loading All Categories..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
			
		}

		@Override
		protected JSONObject doInBackground(Void... params) {
			// TODO Auto-generated method stub
			//int success = 1;
			List<NameValuePair> login_details = new ArrayList<NameValuePair>();
			login_details.add(new BasicNameValuePair("user_name", "1"));
			JSONObject jasono = jsonParser.makeHttpRequest(KEY_URL, "GET",login_details );
			
			return jasono;
		}
		
		
	}
	
	
	
	
}
