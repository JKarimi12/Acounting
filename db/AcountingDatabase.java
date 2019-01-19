package karimi.javad.accounting.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import karimi.javad.accounting.db.dao.OperationDao;
import karimi.javad.accounting.db.entity.Operation;

@Database(entities = {Operation.class}, version = 1)
public abstract class AcountingDatabase extends RoomDatabase {
    public abstract OperationDao operationDao();

    private static AcountingDatabase INSTANSE;
    public static AcountingDatabase getINSTANSE(final Context context){
        if(INSTANSE == null){
            synchronized (AcountingDatabase.class) {
                if (INSTANSE == null) {
                    INSTANSE = Room.databaseBuilder(context.getApplicationContext(),
                            AcountingDatabase.class, "acounting_database")
                            // Wipes and rebuilds instead of migrating
                            // if no Migration object.
                            // Migration is not part of this practical.
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANSE;
    }
}
