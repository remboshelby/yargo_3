package yargo.inc.common.database;

import yargo.inc.common.database.listofcitycoordinate.CityCoordinate;
import yargo.inc.common.database.listofcitycoordinate.CityCoordinateDao;
import yargo.inc.common.database.listofspeciality.ListOfSpeciality;
import yargo.inc.common.database.listofspeciality.SpecialityDao;
import yargo.inc.common.network.models.order_list.OrderItem;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {OrderItem.class, ListOfSpeciality.class, CityCoordinate.class},
        version = 3, exportSchema = false)
public abstract class YargoDataBase extends RoomDatabase {
    public static final String DATABASE_NAME = "yargo.db";

    public abstract CityCoordinateDao cityCoordinateDao();

    public abstract SpecialityDao specialityDao();

    public abstract OrdersDao OrdersDao();
}
