package com.inc.evil.common.database;

import com.inc.evil.common.network.models.order.OrdersItem;

import java.util.List;

import javax.inject.Qualifier;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface VacantOrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrdersItem ordersItem);

    @Delete
    void remove(OrdersItem ordersItem);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrdersItem> ordersItemList);

    @Query("Select *from vacant_orders")
    Flowable<List<OrdersItem>> getAllVacantOrders();
}
