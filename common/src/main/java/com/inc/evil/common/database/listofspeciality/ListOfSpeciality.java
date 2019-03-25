package com.inc.evil.common.database.listofspeciality;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "list_of_speciality")
public class ListOfSpeciality {
    @PrimaryKey(autoGenerate = true)
    private long idSpec;

    @Nullable
    private String idSpecBack;

    public long getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(long idSpec) {
        this.idSpec = idSpec;
    }

    @Nullable
    public String getIdSpecBack() {
        return idSpecBack;
    }

    public void setIdSpecBack(@Nullable String idSpecBack) {
        this.idSpecBack = idSpecBack;
    }

    @Nullable
    public String getNameType() {
        return nameType;
    }

    public void setNameType(@Nullable String nameType) {
        this.nameType = nameType;
    }

    @Nullable
    public String getOnameType() {
        return onameType;
    }

    public void setOnameType(@Nullable String onameType) {
        this.onameType = onameType;
    }

    @Nullable
    private String nameType;

    @Nullable
    private String onameType;

}
