package yargo.inc.common.database.listofspeciality;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
@Dao
public interface SpecialityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListOfSpeciality listOfSpeciality);

    @Delete
    void remove(ListOfSpeciality listOfSpeciality);
}
