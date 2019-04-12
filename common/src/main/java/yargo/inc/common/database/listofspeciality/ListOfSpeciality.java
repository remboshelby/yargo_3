package yargo.inc.common.database.listofspeciality;

import androidx.annotation.Nullable;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity (tableName = "list_of_speciality")
public class ListOfSpeciality {
    @PrimaryKey
    private long idSpec;


    public long getIdSpec() {
        return idSpec;
    }

    public void setIdSpec(long idSpec) {
        this.idSpec = idSpec;
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
