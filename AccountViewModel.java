package karimi.javad.accounting;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import karimi.javad.accounting.db.AcountingDatabase;
import karimi.javad.accounting.db.entity.Operation;

public class AccountViewModel extends AndroidViewModel {

    private LiveData<List<Operation>> liveData;
    private MutableLiveData<Integer> total;
    private AcountingDatabase database;

    public AccountViewModel(@NonNull Application application) {
        super(application);
        database = AcountingDatabase.getINSTANSE(application);
        liveData = database.operationDao().getAllOperations();
        total = new MutableLiveData<>();
    }

    public LiveData<List<Operation>> getLiveData() {
        return liveData;
    }

    public MutableLiveData<Integer> getTotal() {
        return total;
    }

    public void setTotal(MutableLiveData<Integer> total) {
        this.total = total;
    }
}
