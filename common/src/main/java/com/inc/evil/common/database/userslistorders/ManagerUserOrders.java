package com.inc.evil.common.database.userslistorders;

import com.inc.evil.common.database.listofspeciality.ListOfSpeciality;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
@Dao
public interface ManagerUserOrders {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(ListOfUserOrders listOfUserOrders);

    @Delete
    void remove(ListOfUserOrders listOfUserOrders);
}
