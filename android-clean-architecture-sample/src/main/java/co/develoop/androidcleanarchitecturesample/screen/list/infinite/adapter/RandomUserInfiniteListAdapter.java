package co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

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
import co.develoop.androidcleanarchitecturesample.screen.list.infinite.adapter.injection.DaggerRandomUserInfiniteListAdapterComponent;

public class RandomUserInfiniteListAdapter extends RecyclerView.Adapter<RecyclerViewHolder> implements RandomUserInfiniteListAdapterPresenterView {

    @Inject
    public Context mContext;

    @Inject
    public RandomUserInfiniteListAdapterPresenter mRandomUserListAdapterPresenter;

    private RecyclerView mRecyclerView;

    private Snackbar mSnackbar;

    public RandomUserInfiniteListAdapter() {
        DaggerRandomUserInfiniteListAdapterComponent.builder()
                .appComponent(RandomUsersApplication.daggerAppComponent())
                .build()
                .inject(this);
    }

    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == AdapterItem.Type.LOADING.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_progress_item, null);
            return new RandomUserListProgressItemViewHolder(view);
        } else if (viewType == AdapterItem.Type.FOOTER.ordinal()) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_footer_item, null);
            return new RandomUserListFooterItemViewHolder(view);
        } else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.random_user_list_item, null);
            return new RandomUserListItemViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolder holder, int position) {
        holder.configure(mContext, mRandomUserListAdapterPresenter.getListData().get(position));
    }

    @Override
    public int getItemCount() {
        return mRandomUserListAdapterPresenter.getListData().size();
    }

    @Override
    public int getItemViewType(int position) {
        return mRandomUserListAdapterPresenter.getListData().get(position).getType().ordinal();
    }

    @Override
    public void onAttachedToRecyclerView(final RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        mRecyclerView = recyclerView;

        mSnackbar = Snackbar.make(mRecyclerView, "There was a network error ...", Snackbar.LENGTH_INDEFINITE);
        mSnackbar.setAction("Retry", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        mRandomUserListAdapterPresenter.init(this);
    }

    @Override
    public void onDetachedFromRecyclerView(RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);

        mRandomUserListAdapterPresenter.clear();
    }

    @Override
    public RecyclerView getRecyclerView() {
        return mRecyclerView;
    }

    @Override
    public Snackbar getSnackbar() {
        return mSnackbar;
    }

    @Override
    public Boolean showLoadingView() {
        return true;
    }

    @Override
    public Boolean showFooterView() {
        return true;
    }

    @Override
    public PresenterBinder<DiffUtil.DiffResult> getDiffResultBinder() {
        return new PresenterBinder<DiffUtil.DiffResult>() {

            @Override
            public void setData(DiffUtil.DiffResult result) {
                result.dispatchUpdatesTo(RandomUserInfiniteListAdapter.this);
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
            mRandomUserListAdapterPresenter.bindItemClick(itemView, randomUser);

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

    class RandomUserListProgressItemViewHolder extends RecyclerViewHolder<RandomUser> {

        @BindView(R.id.random_user_list_item_progress_bar)
        ProgressBar progressBar;

        public RandomUserListProgressItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {
            progressBar.setIndeterminate(true);
        }
    }

    class RandomUserListFooterItemViewHolder extends RecyclerViewHolder<RandomUser> {

        public RandomUserListFooterItemViewHolder(View itemView) {
            super(itemView);
        }

        @Override
        public void configure(Context context, RandomUser item) {

        }
    }
}