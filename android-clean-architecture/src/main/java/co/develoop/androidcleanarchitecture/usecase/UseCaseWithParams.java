package co.develoop.androidcleanarchitecture.usecase;

public interface UseCaseWithParams<T, P extends UseCaseParams> {

    T bind(P params);
}