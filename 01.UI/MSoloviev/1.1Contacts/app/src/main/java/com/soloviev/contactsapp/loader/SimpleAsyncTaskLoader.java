package com.soloviev.contactsapp.loader;
import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;

public abstract class SimpleAsyncTaskLoader<E> extends AsyncTaskLoader<E> {
    private E mData;
    private boolean mListenerUnregistered = true;

    public SimpleAsyncTaskLoader(Context context) {
        super(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (mData != null) {
            deliverResult(mData);
        } else {
            if (mListenerUnregistered) {
                registerListeners();
                mListenerUnregistered = false;
            }

            if (takeContentChanged() || mData == null) {
                forceLoad();
            }
        }
    }

    @Override
    public void onContentChanged() {
        mData = null;
        super.onContentChanged();
    }

    @Override
    public void deliverResult(E data) {
        if (isReset()) {
            releaseResources(data);
            return;
        }

        E oldData = mData;
        mData = data;

        if (isStarted()) {
            super.deliverResult(data);
        }

        if (oldData != null && oldData != data) {
            releaseResources(oldData);
        }
    }

    protected E getData() {
        return mData;
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        onStopLoading();

        if (mData != null) {
            releaseResources(mData);
            mData = null;
        }

        unregisterListeners();
        mListenerUnregistered = true;
    }

    protected void registerListeners() {
    }

    protected void unregisterListeners() {
    }

    @Override
    public void onCanceled(E data) {
        super.onCanceled(data);
        releaseResources(data);
    }

    protected void releaseResources(E data) {
    }
}
