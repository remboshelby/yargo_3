package yargo.inc.common.database.listofcitycoordinate;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;

@Dao
public interface CityCoordinateDao {
    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insert(CityCoordinate cityCoordinate);

    @Delete
    void remove(CityCoordinate cityCoordinate);
}
