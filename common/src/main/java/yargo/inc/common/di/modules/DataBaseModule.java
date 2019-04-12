package yargo.inc.common.di.modules;

import android.app.Application;

import yargo.inc.common.database.VacantOrdersDao;
import yargo.inc.common.database.YargoDataBase;
import yargo.inc.common.database.listofcitycoordinate.CityCoordinateDao;
import yargo.inc.common.database.listofspeciality.SpecialityDao;
import yargo.inc.common.database.userslistorders.UserOrdersDao;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    @Singleton
    public YargoDataBase provideYargoDataBase(Application application){
        return Room.databaseBuilder(application,YargoDataBase.class, YargoDataBase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build();
    }
    @Provides
    @Singleton
    public CityCoordinateDao provideManagerOfCityCoordinate(YargoDataBase yargoDataBase){
        return yargoDataBase.cityCoordinateDao();
    }

    @Provides
    @Singleton
    public SpecialityDao provideSpecialityManager(YargoDataBase yargoDataBase){
        return yargoDataBase.specialityDao();
    }

    @Provides
    @Singleton
    public UserOrdersDao provideManagerUserOrders(YargoDataBase yargoDataBase){
        return yargoDataBase.userOrdersDao();
    }

    @Provides
    @Singleton
    public VacantOrdersDao provideManagerVanactOrders(YargoDataBase yargoDataBase){
        return yargoDataBase.vacantOrdersDao();
    }
}
