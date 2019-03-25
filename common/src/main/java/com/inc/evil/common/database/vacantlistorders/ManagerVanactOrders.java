package com.inc.evil.common.database.vacantlistorders;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
@Dao
public interface ManagerVanactOrders {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VacantOrdersList vacantOrdersList);

    @Delete
    void remove(VacantOrdersList vacantOrdersList);

}
