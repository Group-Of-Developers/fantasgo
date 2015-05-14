package god.forum.fantasgo;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class PagerAdapters extends FragmentStatePagerAdapter {

	public final int PAGES=2;
	public PagerAdapters(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int pos) {
		switch(pos)
		{
			case 0:
			return new CategoryListView();
			case 1:
			return new LastTest();
			default:
			 throw new IllegalArgumentException("The item position should be less or equal to:" + PAGES);
			
		}
	}

	@Override
	public int getCount() {
		return PAGES;
	}

}
