package co.develoop.androidcleanarchitecture.screen.presenter;

public interface AdapterItem {

    void setType(Type type);

    Type getType();

    enum Type {
        ITEM, LOADING, FOOTER
    }
}