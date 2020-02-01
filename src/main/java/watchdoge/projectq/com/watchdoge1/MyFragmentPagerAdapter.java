package watchdoge.projectq.com.watchdoge1;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

class MyFragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static String computerJSON;


    public MyFragmentPagerAdapter(FragmentManager fm, String json) {

        super(fm);
        computerJSON = json;
    }

    @Override
    public Fragment getItem(int position) {
        return ComputerFragment.newInstance(position, computerJSON);
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        String out = "";
        switch (position) {
            case 0:
                out = "Статистика";
                break;
            case 1:
                out = "Характеристики";
                break;
        }
        return out;
    }
}

