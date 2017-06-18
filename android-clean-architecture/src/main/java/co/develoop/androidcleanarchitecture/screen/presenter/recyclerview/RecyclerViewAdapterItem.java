package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

public interface RecyclerViewAdapterItem {

    void setType(Type type);

    Type getType();

    enum Type {
        ITEM, LOADING, FOOTER, FAKE, ERROR
    }
}