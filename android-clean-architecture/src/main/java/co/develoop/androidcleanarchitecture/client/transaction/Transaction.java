package co.develoop.androidcleanarchitecture.client.transaction;

public final class Transaction<T> {

    private T mData;
    private TransactionStatus mStatus;

    public Transaction(TransactionStatus status) {
        mStatus = status;
    }

    public Transaction(T data, TransactionStatus status) {
        mData = data;
        mStatus = status;
    }

    public T getData() {
        return mData;
    }

    public void setData(T data) {
        mData = data;
    }

    public Boolean isSuccess() {
        return mStatus.equals(TransactionStatus.SUCCESS);
    }

    public TransactionStatus getStatus() {
        return mStatus;
    }
}