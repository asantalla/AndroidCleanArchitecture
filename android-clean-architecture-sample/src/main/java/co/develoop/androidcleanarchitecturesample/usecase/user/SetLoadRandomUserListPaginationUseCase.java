package co.develoop.androidcleanarchitecturesample.usecase.user;

import co.develoop.androidcleanarchitecture.usecase.UseCaseParams;
import co.develoop.androidcleanarchitecture.usecase.UseCaseWithParams;
import co.develoop.androidcleanarchitecturesample.domain.service.RandomUserService;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class SetLoadRandomUserListPaginationUseCase implements UseCaseWithParams<Observable<Object>, SetLoadRandomUserListPaginationUseCase.Params> {

    private RandomUserService mRandomUserService;

    public SetLoadRandomUserListPaginationUseCase(RandomUserService randomUserService) {
        mRandomUserService = randomUserService;
    }

    @Override
    public Observable<Object> bind(Params params) {
        return mRandomUserService.setUserListPagination(params.getPage())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public static class Params extends UseCaseParams {

        private Integer mPage;

        public Params(Integer page) {
            mPage = page;
        }

        private Integer getPage() {
            return mPage;
        }
    }
}