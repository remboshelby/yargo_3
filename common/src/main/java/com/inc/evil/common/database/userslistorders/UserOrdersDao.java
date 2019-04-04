package com.inc.evil.common.database.userslistorders;

import com.inc.evil.common.database.listofspeciality.ListOfSpeciality;
import com.inc.evil.common.network.models.order.OrdersItem;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
@Dao
public interface UserOrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrdersItem ordersItem);

    @Delete
    void remove(OrdersItem ordersItem);
}
