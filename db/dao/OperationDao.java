package karimi.javad.accounting.db.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import karimi.javad.accounting.db.entity.Operation;

@Dao
public interface OperationDao {

    @Insert
    void insert(Operation operation);

    @Query("SELECT * FROM OperationTable")
    LiveData<List<Operation>> getAllOperations();

}
