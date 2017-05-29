package co.develoop.androidcleanarchitecture.screen.presenter.recyclerview;

public interface AdapterItem {

    void setType(Type type);

    Type getType();

    enum Type {
        ITEM, LOADING, FOOTER, FAKE, ERROR
    }
}