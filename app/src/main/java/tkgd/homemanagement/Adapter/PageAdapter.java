package tkgd.homemanagement.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

public class PageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments;
    private int mtype;

    public PageAdapter(FragmentManager fm, List<Fragment> fragments, int mtype) {

        super(fm);

        this.fragments = fragments;
        this.mtype = mtype;
    }

    @Override

    public Fragment getItem(int position) {

        return this.fragments.get(position);

    }

    @Override

    public int getCount() {

        return this.fragments.size();

    }
    @Override
    public CharSequence getPageTitle(int position) {
        return fragments.get(position).getArguments().getString("EXTRA_ROOM_NAME");
//        return "Room" + position;
    }
}
