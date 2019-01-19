package karimi.javad.accounting;

import android.app.Application;
import android.app.ListActivity;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import karimi.javad.accounting.db.AcountingDatabase;
import karimi.javad.accounting.db.entity.Operation;

public class TotalNotebookViewModel extends AndroidViewModel {
    private LiveData<List<Operation>> liveData;
    private AcountingDatabase database;
    public TotalNotebookViewModel(@NonNull Application application) {
        super(application);
        database = AcountingDatabase.getINSTANSE(application);
        liveData = database.operationDao().getAllOperations();
    }

    public LiveData<List<Operation>> getLiveData() {
        return liveData;
    }
}
