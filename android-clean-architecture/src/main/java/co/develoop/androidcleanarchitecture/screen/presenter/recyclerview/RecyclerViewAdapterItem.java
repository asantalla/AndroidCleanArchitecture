package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

public interface RecyclerViewAdapterItem {

    void setType(Type type);

    Type getType();

    enum Type {
        ITEM, FULLSCREEN_LOADING, LOADING, FULLSCREEN_ERROR, ERROR, FOOTER
    }
}