package com.inc.evil.common.database;

import com.inc.evil.common.database.listofcitycoordinate.CityCoordinate;
import com.inc.evil.common.database.listofcitycoordinate.ManagerOfCityCoordinate;
import com.inc.evil.common.database.listofspeciality.ListOfSpeciality;
import com.inc.evil.common.database.listofspeciality.SpecialityManager;
import com.inc.evil.common.database.userslistorders.ListOfUserOrders;
import com.inc.evil.common.database.userslistorders.ManagerUserOrders;
import com.inc.evil.common.database.vacantlistorders.ManagerVanactOrders;
import com.inc.evil.common.database.vacantlistorders.VacantOrdersList;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {VacantOrdersList.class, ListOfSpeciality.class, ListOfUserOrders.class, CityCoordinate.class},
        version = 1, exportSchema = false)
public abstract class YargoDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "yargo.db";

    public abstract ManagerOfCityCoordinate managerOfCityCoordinate();

    public abstract SpecialityManager specialityManager();

    public abstract ManagerUserOrders managerUserOrders();

    public abstract ManagerVanactOrders managerVanactOrders();
}
