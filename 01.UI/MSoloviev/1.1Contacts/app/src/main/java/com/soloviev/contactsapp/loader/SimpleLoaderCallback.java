package com.soloviev.contactsapp.loader;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;

public class SimpleLoaderCallback<E> implements LoaderManager.LoaderCallbacks<E> {

	@Override
	public Loader<E> onCreateLoader(int id, Bundle args) {
		return null;
	}

	@Override
	public void onLoadFinished(Loader<E> eLoader, E e) {
	}

	@Override
	public void onLoaderReset(Loader<E> loader) {
	}
}
