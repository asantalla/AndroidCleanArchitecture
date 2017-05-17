package co.develoop.androidcleanarchitecturesample.screen;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import co.develoop.androidcleanarchitecturesample.R;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_view_pager)
    ViewPager mMainViewPager;

    @BindView(R.id.main_view_pager_tabs)
    TabLayout mMainViewPagerTabs;

    protected Unbinder ubinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ubinder = ButterKnife.bind(this);

        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        mMainViewPager.setAdapter(mainViewPagerAdapter);
        mMainViewPagerTabs.setupWithViewPager(mMainViewPager);

        mMainViewPagerTabs.getTabAt(0).setText("Infinite");
        mMainViewPagerTabs.getTabAt(1).setText("Simple");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        ubinder.unbind();
    }
}