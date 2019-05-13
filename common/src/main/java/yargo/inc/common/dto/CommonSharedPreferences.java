package yargo.inc.common.dto;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;

import yargo.inc.common.R;
import yargo.inc.common.network.models.login.User;

public class CommonSharedPreferences {
    public static final String APP_ID = "app_id";
    public static final String AUTH_KEY = "auth_key";
    public static final String USER_ABOUT_RESPONSE = "user_about_response";
    public static final String FILTERED_CITY = "filtered_city";



    public static final String SHARED_PREFERENCES = "shared_preferences";

    private Gson gson = new GsonBuilder().create();
    private SharedPreferences preferences;
    private Context context;

    public CommonSharedPreferences(Context context) {
        this.preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
        this.context = context;
    }

    public void putObject(String serializedObjectKey, Object objectType) {
        new Factory(serializedObjectKey, objectType.getClass()).putObject(objectType);
    }

    public String getFilteredCityName() {
        int filteredIdCity = (int) getIntObject(FILTERED_CITY, int.class);
        if (filteredIdCity ==0) {
            return getUserCityName();
        }
        else {
            String[] stringArray = context.getResources().getStringArray(R.array.citiesId);
            int position = new ArrayList<>(Arrays.asList(stringArray)).indexOf(String.valueOf(filteredIdCity));
            return context.getResources().getStringArray(R.array.citiesName)[position];
        }

    }
    public String getUserCityName(){
        User user = (User) getObject(USER_ABOUT_RESPONSE, User.class);
        putObject(FILTERED_CITY, user.getIdCity());
        String[] stringArray = context.getResources().getStringArray(R.array.citiesId);
        int position = new ArrayList<>(Arrays.asList(stringArray)).indexOf(String.valueOf(user.getIdCity()));
        return context.getResources().getStringArray(R.array.citiesName)[position];
    }

    public Object getObject(String serializedObjectKey, Class<?> tClass) {
        return new Factory(serializedObjectKey, tClass).getObject();
    }
    public Object getBooleanObject(String serializedObjectKey, Class<?> tClass) {
        return new Factory(serializedObjectKey, tClass).getBooleanObject();
    }
    public Object getIntObject(String serializedObjectKey, Class<?> tClass) {
        return new Factory(serializedObjectKey, tClass).getIntObject();
    }

    public class Factory {
        private String key;
        private Class<?> tClass;

        public Factory(String key, Class<?> tClass) {
            this.key = key;
            this.tClass = tClass;
        }

        private Object getObject() {
            String objectAsString = preferences.getString(key, "");
            return (Object) fromString(objectAsString, tClass);
        }
        public Object getBooleanObject(){
            String objectAsString = preferences.getString(key, "false");
            return (Object) fromString(objectAsString, tClass);
        }
        public Object getIntObject(){
            String objectAsString = preferences.getString(key, "0");
            return (Object) fromString(objectAsString, tClass);
        }

        private boolean putObject(Object object) {
            String objectAsString = objectAsString(object);
            return preferences.edit().putString(key, objectAsString).commit();
        }

        private String objectAsString(Object object) {
            if (object != null) {
                return gson.toJson(object);
            }
            return "";
        }

        private Object fromString(String objectAsString, Class<?> tClass) {
            if (objectAsString != null && !objectAsString.isEmpty()) {
                return (Object) gson.fromJson(objectAsString, tClass);
            }
            return null;
        }
    }
}
