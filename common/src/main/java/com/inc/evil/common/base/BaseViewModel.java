package com.inc.evil.common.base;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> isError = new MutableLiveData<>();

    protected void addDisposible(Disposable disposable) {
        compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }

    public CompositeDisposable getCompositeDisposable() {
        return compositeDisposable;
    }

    public void onViewCreated() {
    }
}
