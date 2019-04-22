package yargo.inc.common.database;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Flowable;
import yargo.inc.common.network.models.user_order.UserOrdersItem;

@Dao
public interface UserOrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(UserOrdersItem userOrdersItem);

    @Delete
    void remove(UserOrdersItem userOrdersItem);

    @Query("Delete from user_orders")
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<UserOrdersItem> vacantOrderItemList);

    @Query("Select *from user_orders")
    Flowable<List<UserOrdersItem>> getAllUserOrders();
}
