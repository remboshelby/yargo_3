package com.inc.evil.common.database;

import com.inc.evil.common.database.listofcitycoordinate.CityCoordinate;
import com.inc.evil.common.database.listofcitycoordinate.CityCoordinateDao;
import com.inc.evil.common.database.listofspeciality.ListOfSpeciality;
import com.inc.evil.common.database.listofspeciality.SpecialityDao;
import com.inc.evil.common.database.userslistorders.ListOfUserOrders;
import com.inc.evil.common.database.userslistorders.UserOrdersDao;
import com.inc.evil.common.network.models.order.OrdersItem;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {OrdersItem.class, ListOfSpeciality.class, ListOfUserOrders.class, CityCoordinate.class},
        version = 1, exportSchema = false)
public abstract class YargoDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "yargo.db";

    public abstract CityCoordinateDao cityCoordinateDao();

    public abstract SpecialityDao specialityDao();

    public abstract UserOrdersDao userOrdersDao();

    public abstract VacantOrdersDao vacantOrdersDao();
}
