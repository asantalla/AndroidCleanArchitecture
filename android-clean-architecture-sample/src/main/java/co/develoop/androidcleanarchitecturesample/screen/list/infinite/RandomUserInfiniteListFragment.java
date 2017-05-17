package co.develoop.androidcleanarchitecturesample.screen.list.infinite;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.BindView;
import co.develoop.androidcleanarchitecturesample.BaseFragment;
import co.develoop.androidcleanarchitecturesample.R;
import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.RandomUserInfiniteListAdapter;

public class RandomUserInfiniteListFragment extends BaseFragment {

    @BindView(R.id.random_user_recycler_view)
    public RecyclerView mRandomUserRecyclerView;

    public static RandomUserInfiniteListFragment newInstance() {
        return new RandomUserInfiniteListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.random_user_list, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        configureRecyclerView();
    }

    private void configureRecyclerView() {
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        mRandomUserRecyclerView.setLayoutManager(layoutManager);
        mRandomUserRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRandomUserRecyclerView.setAdapter(new RandomUserInfiniteListAdapter());
    }
}