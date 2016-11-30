package com.egco428.a23260;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.transition.CircularPropagation;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Thammarit on 18/11/2559.
 */

public class MapsDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_USERNAME, MySQLiteHelper.COLUMN_PASSWORD, MySQLiteHelper.COLUMN_LATITUDE, MySQLiteHelper.COLUMN_LONGTITUDE};

    String query;
    String storePassword;

    public MapsDataSource(Context context){
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void read() throws SQLException {
        database = dbHelper.getReadableDatabase();
    }

    public boolean createMap(String username, String password, Double latitude, Double longtitude){
        database = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_USERNAME, username);
        values.put(MySQLiteHelper.COLUMN_PASSWORD, password);
        values.put(MySQLiteHelper.COLUMN_LATITUDE, latitude);
        values.put(MySQLiteHelper.COLUMN_LONGTITUDE, longtitude);

        long insertID = database.insert(MySQLiteHelper.TABLE_MAP, null,values);
        if(insertID == -1){
            return false;
        }
        else{
            return true;
        }

        //Cursor cursor = database.query(MySQLiteHelper.TABLE_MAP,allColumns, MySQLiteHelper.COLUMN_ID+ " = "+insertID, null, null, null, null);
        //cursor.moveToFirst();
        //Map newMap = cursorToMap(cursor);
        //cursor.close();
        //return newMap;
    }


    public void deleteMap(Map map){
        long id = map.getId();
        System.out.println("Comment deleted with id: "+id);
        database.delete(MySQLiteHelper.TABLE_MAP, MySQLiteHelper.COLUMN_ID+" = "+id, null);

    }

    public String checkLogin(String username){
        database= dbHelper.getReadableDatabase();

        query = "SELECT password FROM maps WHERE " + MySQLiteHelper.COLUMN_USERNAME + "='" + username +"'";

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                String storePassword = cursor.getString(cursor.getColumnIndex("password"));
                System.out.println(storePassword);
                return storePassword;
            }
        }
        cursor.close();
        return String.valueOf(0);
    }

    public String checkUsernameRecord(String checkUsername){
        database = dbHelper.getReadableDatabase();

        query = "SELECT username from maps WHERE " + MySQLiteHelper.COLUMN_USERNAME + "='" + checkUsername +"'";

        Cursor cursor = database.rawQuery(query, null);

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            if(cursor.moveToFirst()){
                String storeUsername = cursor.getString(cursor.getColumnIndex("username"));
                System.out.println(storeUsername);
                return storeUsername;
            }
        }
        cursor.close();
        return String.valueOf(0);
    }



    public List<Map> getAllMap(){
        List<Map> maps = new ArrayList<Map>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_MAP, allColumns,null,null, null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Map map = cursorToMap(cursor);
            maps.add(map);
            cursor.moveToNext();
        }
        cursor.close();
        return maps;
    }

    public Map cursorToMap(Cursor cursor){
        Map map = new Map();
        map.setId(cursor.getLong(0));
        map.setUsername(cursor.getString(1));
        map.setPassword(cursor.getString(2));
        map.setLatitude(cursor.getString(3));
        map.setLongtitude(cursor.getString(4));
        return map;
    }
}
