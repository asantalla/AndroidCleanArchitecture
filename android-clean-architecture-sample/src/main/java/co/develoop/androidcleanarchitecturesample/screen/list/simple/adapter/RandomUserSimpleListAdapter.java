package co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter;

import android.content.Context;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.pkmmte.view.CircularImageView;
import com.squareup.picasso.Picasso;

import javax.inject.Inject;

import butterknife.BindView;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterAction;
import co.develoop.androidcleanarchitecture.screen.presenter.actions.PresenterBinder;
import co.develoop.androidcleanarchitecture.screen.view.recycler.RecyclerViewHolder;
import co.develoop.androidcleanarchitecturesample.R;
import co.develoop.androidcleanarchitecturesample.RandomUsersApplication;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUser;
import co.develoop.androidcleanarchitecturesample.domain.model.user.RandomUserPicture;
import co.develoop.androidcleanarchitecturesample.screen.list.simple.adapter.injection.DaggerRandomUserSimpleListAdapterComponent;

public class RandomUserSimpleListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements RandomUserSimpleListAdapterPresenterView {

    @Inject
    public Context mContext;

    @Inject
    public RandomUserSimpleListAdapterPresenter mRandomUserSimpleListAdapterPresenter;

    public RandomUserSimpleListAdapter() {
        DaggerRandomUserSimpleListAdapterComponent.builder()
                .appComponent(RandomUsersApplication.daggerAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_item, null);
        return new RandomUserListItemViewHolder(view);
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
                result.dispatchUpdatesTo(RandomUserSimpleListAdapter.this);
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

    class RandomUserListItemViewHolder extends RecyclerViewHolder<RandomUser> {

        @BindView(R.id.random_user_list_item_fullname)
        TextView fullnameTextView;

        @BindView(R.id.random_user_list_item_email)
        TextView emailTextView;

        @BindView(R.id.random_user_list_item_phone)
        TextView phoneTextView;

        @BindView(R.id.random_user_list_item_image)
        CircularImageView imageCircularImageView;

        public RandomUserListItemViewHolder(View itemView) {
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
}