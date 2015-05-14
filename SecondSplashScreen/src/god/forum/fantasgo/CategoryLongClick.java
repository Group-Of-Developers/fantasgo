package god.forum.fantasgo;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;

public class CategoryLongClick extends SherlockActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		setTheme(R.style.Theme_Sherlock_Light_Dialog);
		super.onCreate(savedInstanceState);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Bundle getBundle=getIntent().getExtras();
		if(getBundle.getInt("VAR_ID")==1)
		{
		setContentView(R.layout.category_long_click);
		TextView category,description,nofpost,last_post;		
		category=(TextView)findViewById(R.id.category_topic);
		description=(TextView)findViewById(R.id.category_description);
		nofpost=(TextView)findViewById(R.id.category_nofpost);
		last_post=(TextView)findViewById(R.id.category_last_post);
		
		category.setText(getBundle.getString("CAT_CATEGORY"));
		description.setText(getBundle.getString("CAT_DESCRIPTION"));
		nofpost.setText(getBundle.getString("CAT_NOOFPOST"));
		last_post.setText(getBundle.getString("CAT_LASTPOST"));
		}
		
		else if(getBundle.getInt("VAR_ID")==2)
		{
			setContentView(R.layout.thread_long_click);
			TextView thread_question,threaddate;		
			thread_question=(TextView)findViewById(R.id.thread_full_version);
			threaddate=(TextView)findViewById(R.id.thread_date);
			//threadtime=(TextView)findViewById(R.id.thread_time);
			
			thread_question.setText(getBundle.getString("THREAD_QUESTION"));
			threaddate.setText(getBundle.getString("THREAD_DATE"));
			//threadtime.setText(getBundle.getString("THREAD_TIME"));
			
			
		}
		else if(getBundle.getInt("VAR_ID")==3)
		{
			setContentView(R.layout.comment_long_click);
			TextView comment_by,comment_full,commentdate;		
			comment_by=(TextView)findViewById(R.id.comment_by);
			comment_full=(TextView)findViewById(R.id.comment_full_version);
			commentdate=(TextView)findViewById(R.id.comment_date);
			//commenttime=(TextView)findViewById(R.id.comment_time);
			
			comment_by.setText(getBundle.getString("COM_USER"));
			comment_full.setText(getBundle.getString("COM_COMMENT"));
			commentdate.setText(getBundle.getString("COM_DATE"));
		//	commenttime.setText(getBundle.getString("CAT_TIME"));
			
			
		}
	}

}
