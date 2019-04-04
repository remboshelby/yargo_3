package com.inc.evil.common.di.modules;

import android.app.Application;
import android.content.Context;

import com.inc.evil.common.database.VacantOrdersDao;
import com.inc.evil.common.database.YargoDataBase;
import com.inc.evil.common.database.listofcitycoordinate.CityCoordinateDao;
import com.inc.evil.common.database.listofspeciality.SpecialityDao;
import com.inc.evil.common.database.userslistorders.UserOrdersDao;

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
