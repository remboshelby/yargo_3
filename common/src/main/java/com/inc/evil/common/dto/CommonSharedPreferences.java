package com.inc.evil.common.dto;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Modifier;

public class CommonSharedPreferences {
    public static final String SHARED_PREFERENCES = "shared_preferences";

    private Gson gson = new GsonBuilder().create();
    private SharedPreferences preferences;

    public CommonSharedPreferences(Context context) {
        this.preferences = context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE);
    }

    public void putObject(String serializedObjectKey, Object objectType){
        new Factory(serializedObjectKey, objectType.getClass()).putObject(objectType);
    }
    public Object getObject(String serializedObjectKey, Class<?> tClass){
        return new Factory(serializedObjectKey, tClass).getObject();
    }
    public class Factory{
        private String key;
        private Class<?> tClass;

        public Factory(String key, Class<?> tClass) {
            this.key = key;
            this.tClass = tClass;
        }

        private Object getObject(){
            String objectAsString = preferences.getString(key, "");
            return (Object)fromString(objectAsString, tClass);
        }
        private boolean putObject(Object object){
            String objectAsString = objectAsString(object);
            return  preferences.edit().putString(key, objectAsString).commit();
        }

        private String objectAsString(Object object) {
            if (object!=null){
                return gson.toJson(object);
            }
            return "";
        }

        private Object fromString(String objectAsString, Class<?> tClass) {
            if (objectAsString !=null && !objectAsString.isEmpty()){
                return (Object)gson.fromJson(objectAsString, tClass);
            }
            return null;
        }
    }
}
