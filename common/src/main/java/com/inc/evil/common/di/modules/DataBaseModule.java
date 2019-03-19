package com.inc.evil.common.di.modules;

import android.content.Context;

import com.inc.evil.common.database.YargoDataBase;
import com.inc.evil.common.database.listofcitycoordinate.ManagerOfCityCoordinate;
import com.inc.evil.common.database.listofspeciality.SpecialityManager;
import com.inc.evil.common.database.userslistorders.ManagerUserOrders;
import com.inc.evil.common.database.vacantlistorders.ManagerVanactOrders;

import javax.inject.Singleton;

import androidx.room.Room;
import dagger.Module;
import dagger.Provides;

@Module
public class DataBaseModule {
    @Provides
    @Singleton
    public YargoDataBase provideYargoDataBase(Context context){
        return Room.databaseBuilder(context,YargoDataBase.class, YargoDataBase.DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }
    @Provides
    @Singleton
    public ManagerOfCityCoordinate provideManagerOfCityCoordinate(YargoDataBase yargoDataBase){
        return yargoDataBase.managerOfCityCoordinate();
    }

    @Provides
    @Singleton
    public SpecialityManager provideSpecialityManager(YargoDataBase yargoDataBase){
        return yargoDataBase.specialityManager();
    }

    @Provides
    @Singleton
    public ManagerUserOrders provideManagerUserOrders(YargoDataBase yargoDataBase){
        return yargoDataBase.managerUserOrders();
    }

    @Provides
    @Singleton
    public ManagerVanactOrders provideManagerVanactOrders(YargoDataBase yargoDataBase){
        return yargoDataBase.managerVanactOrders();
    }
}
