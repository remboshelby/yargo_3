package com.inc.evil.common.base;

import androidx.lifecycle.ViewModel;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class BaseViewModel extends ViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    protected void addDisposible(Disposable disposable){compositeDisposable.add(disposable);}

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
