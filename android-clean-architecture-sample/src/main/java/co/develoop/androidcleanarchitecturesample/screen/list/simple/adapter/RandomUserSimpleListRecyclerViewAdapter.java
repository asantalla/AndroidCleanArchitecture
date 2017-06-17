package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

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
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;
import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.AdapterItem;
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewHolder;
import co.develoop.androidcleanarchitecturesample.R;
import co.develoop.androidcleanarchitecturesample.RandomUsersApplication;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserPicture;
import co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.injection.DaggerRandomUserSimpleListAdapterComponent;

public class RandomUserSimpleListRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements RandomUserSimpleListRecyclerViewAdapterPresenterView {

    @Inject
    public Context mContext;

    @Inject
    public RandomUserSimpleListRecyclerViewAdapterPresenter mRandomUserSimpleListAdapterPresenter;

    public RandomUserSimpleListRecyclerViewAdapter() {
        DaggerRandomUserSimpleListAdapterComponent.builder()
                .appComponent(RandomUsersApplication.daggerAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterItem.Type.ITEM.ordinal()) {
            return new RandomUserListItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_item, parent, false));
        } else if (viewType == AdapterItem.Type.FAKE.ordinal()) {
            return new RandomUserListFakeItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_fake_item, parent, false));
        } else if (viewType == AdapterItem.Type.LOADING.ordinal()) {
            return new RandomUserListLoadingItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_loading_item, parent, false));
        } else {
            return new RandomUserListErrorItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_error_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.configure(mContext, mRandomUserSimpleListAdapterPresenter.getListData().get(position));
    }

    @Override
    public int getItemCount() {
        return mRandomUserSimpleListAdapterPresenter.getListData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRandomUserSimpleListAdapterPresenter.getListData().get(position).getType().ordinal();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRandomUserSimpleListAdapterPresenter.init(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mRandomUserSimpleListAdapterPresenter.clear();
    }

    @Override
    public PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return new PresenterBinder<DiffUtil.DiffResult>() {

            @Override
            public void setData(DiffUtil.DiffResult result) {
                result.dispatchUpdatesTo(RandomUserSimpleListRecyclerViewAdapter.this);
            }
        };
    }

    @Override
    public PresenterAction showName(final String name) {
        return new PresenterAction() {

            @Override
            public void execute() {
                Toast.makeText(mContext, "Name: " + name, Toast.LENGTH_LONG).show();
            }
        };
    }

    public class RandomUserListItemViewHolder extends RecyclerViewHolder<RandomUser> {

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
            mRandomUserSimpleListAdapterPresenter.bindItemClick(itemView, randomUser);

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

    private class RandomUserListFakeItemViewHolder extends RecyclerViewHolder<RandomUser> {

        RandomUserListFakeItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {
        }
    }

    private class RandomUserListErrorItemViewHolder extends RecyclerViewHolder<RandomUser> {

        RandomUserListErrorItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {
            mRandomUserSimpleListAdapterPresenter.bindReloadDataObservable(RxView.clicks(itemView.findViewById(R.id.retry_button)));
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
}