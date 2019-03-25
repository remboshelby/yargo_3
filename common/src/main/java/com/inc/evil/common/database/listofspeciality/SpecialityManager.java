package com.inc.evil.common.database.listofspeciality;

import com.inc.evil.common.database.listofcitycoordinate.CityCoordinate;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
@Dao
public interface SpecialityManager {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListOfSpeciality listOfSpeciality);

    @Delete
    void remove(ListOfSpeciality listOfSpeciality);
}
