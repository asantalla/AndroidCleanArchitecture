package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.view.RxView;
import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterItem;
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewHolder;
import co.develoop.androidcleanarchitecturesample.R;
import co.develoop.androidcleanarchitecturesample.RandomUsersApplication;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserPicture;
import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.injection.DaggerRandomUserInfiniteListAdapterComponent;

public class RandomUserInfiniteListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements RandomUserInfiniteListRecyclerViewAdapterPresenterView {

    @Inject
    public Context mContext;

    @Inject
    public RandomUserInfiniteListRecyclerViewAdapterPresenter mRandomUserInfiniteListRecyclerViewAdapterPresenter;

    private RecyclerView mRecyclerView;

    public RandomUserInfiniteListRecyclerViewAdapter() {
        DaggerRandomUserInfiniteListAdapterComponent.builder()
                .appComponent(RandomUsersApplication.daggerAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == RecyclerViewAdapterItem.Type.ITEM.ordinal()) {
            return new RandomUserListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_item, null, false));
        } else if (viewType == RecyclerViewAdapterItem.Type.FULLSCREEN_LOADING.ordinal()) {
            return new RandomUserListLoadingItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_loading_item, parent, false));
        } else if (viewType == RecyclerViewAdapterItem.Type.LOADING.ordinal()) {
            return new RandomUserListLoadingItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_loading_item, null, false));
        } else if (viewType == RecyclerViewAdapterItem.Type.FULLSCREEN_ERROR.ordinal()) {
            return new RandomUserListErrorItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_error_item, parent, false));
        } else if (viewType == RecyclerViewAdapterItem.Type.ERROR.ordinal()) {
            return new RandomUserListErrorItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_error_item, null, false));
        } else {
            return new RandomUserListFooterItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_footer_item, null, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.configure(mContext, mRandomUserInfiniteListRecyclerViewAdapterPresenter.getListData().get(position));
    }

    @Override
    public int getItemCount() {
        return mRandomUserInfiniteListRecyclerViewAdapterPresenter.getListData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRandomUserInfiniteListRecyclerViewAdapterPresenter.getListData().get(position).getType().ordinal();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;

        mRandomUserInfiniteListRecyclerViewAdapterPresenter.init(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mRandomUserInfiniteListRecyclerViewAdapterPresenter.clear();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return new PresenterBinder<DiffUtil.DiffResult>() {

            @Override
            public void setData(DiffUtil.DiffResult result) {
                result.dispatchUpdatesTo(RandomUserInfiniteListRecyclerViewAdapter.this);
            }
        };
    }

    @Override
    public void showName(final String name) {
        Toast.makeText(mContext, "Name: " + name, Toast.LENGTH_LONG).show();
    }

    class RandomUserListItemViewHolder extends RecyclerViewHolder<RandomUser> {

        @BindView(R.id.random_user_list_item_fullname)
        TextView fullnameTextView;

        @BindView(R.id.random_user_list_item_email)
        TextView emailTextView;

        @BindView(R.id.random_user_list_item_phone)
        TextView phoneTextView;

        @BindView(R.id.random_user_list_item_image)
        CircularImageView imageCircularImageView;

        RandomUserListItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, final RandomUser randomUser) {
            mRandomUserInfiniteListRecyclerViewAdapterPresenter.bindItemClick(itemView, randomUser);

            RandomUserPicture randomUserPicture = randomUser.getPicture();

            if (randomUserPicture != null && randomUserPicture.getMedium() != null) {
                Picasso.with(context)
                        .load(randomUser.getPicture().getMedium())
                        .placeholder(R.mipmap.ic_launcher)
                        .into(imageCircularImageView);
            }

            fullnameTextView.setText(randomUser.getName().toString());
            emailTextView.setText(randomUser.getEmail());
            phoneTextView.setText(randomUser.getPhone());
        }
    }

    private class RandomUserListErrorItemViewHolder extends RecyclerViewHolder<RandomUser> {

        RandomUserListErrorItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {
            mRandomUserInfiniteListRecyclerViewAdapterPresenter.bindReloadDataObservable(RxView.clicks(itemView.findViewById(R.id.retry_button)));
        }
    }

    private class RandomUserListLoadingItemViewHolder extends RecyclerViewHolder<RandomUser> {

        RandomUserListLoadingItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {
        }
    }

    private class RandomUserListFooterItemViewHolder extends RecyclerViewHolder<RandomUser> {

        RandomUserListFooterItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {

        }
    }
}