package yargo.inc.common.database;

import yargo.inc.common.network.models.order_list.OrderItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface OrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(OrderItem orderItem);

    @Delete
    void remove(OrderItem orderItem);

    @Query("Delete from orders")
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<OrderItem> orderItemList);

    @Query("Select *from orders where idUser is null")
    Flowable<List<OrderItem>> getAllVacantOrders();

    @Query("Select *from orders where idOrderStatus = :idOrderStatus")
    Flowable<List<OrderItem>> getAllUserOrdersStatus(int idOrderStatus);

    @Query("Select *from orders")
    Flowable<List<OrderItem>> getAllUserOrders();
}
