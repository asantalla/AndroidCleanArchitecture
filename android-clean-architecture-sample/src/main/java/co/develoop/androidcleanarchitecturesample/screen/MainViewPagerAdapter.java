package co.develoop.androidcleanarchitecturesample.screen;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import co.develoop.androidcleanarchitecturesample.screen.list.infinite.RandomUserInfiniteListFragment;
import co.develoop.androidcleanarchitecturesample.screen.list.simple.RandomUserSimpleListFragment;

public class MainViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public MainViewPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);

        mFragments = new ArrayList<>();
        mFragments.add(RandomUserInfiniteListFragment.newInstance());
        mFragments.add(RandomUserSimpleListFragment.newInstance());
    }

    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}