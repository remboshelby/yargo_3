package yargo.inc.common.base;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewAndroidModel extends AndroidViewModel {
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    public MutableLiveData<Boolean> isNoInternetConnection = new MutableLiveData<>();

    protected void addDisposible(Disposable disposable) {
        compositeDisposable.add(disposable);
    }
    public BaseViewAndroidModel(@NonNull Application application) {
        super(application);
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
