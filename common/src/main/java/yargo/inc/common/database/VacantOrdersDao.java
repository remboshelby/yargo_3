package yargo.inc.common.database;

import yargo.inc.common.network.models.vacant_order.VacantOrderItem;

import java.util.List;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Flowable;

@Dao
public interface VacantOrdersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(VacantOrderItem vacantOrderItem);

    @Delete
    void remove(VacantOrderItem vacantOrderItem);

    @Query("Delete from vacant_orders")
    void removeAll();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<VacantOrderItem> vacantOrderItemList);

    @Query("Select *from vacant_orders")
    Flowable<List<VacantOrderItem>> getAllVacantOrders();

}
