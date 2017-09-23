package co.develoop.androidcleanarchitecture.screen.presenter.actions.getter;

public class PresenterGetterError {

    private int mCode;
    private String mMessage;

    public PresenterGetterError(int code, String message) {
        mCode = code;
        mMessage = message;
    }

    public int getCode() {
        return mCode;
    }

    public String getMessage() {
        return mMessage;
    }
}