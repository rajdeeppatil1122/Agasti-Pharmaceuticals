package com.rajdeep.agastipharmaceuticals;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.Random;

public class DBClass extends SQLiteOpenHelper {

    public static String dbname = "agasthi";


    public static String url = "http://agasthipharmacy-001-site1.htempurl.com/api/";
    public static String urlLogin= url + "a_login.php";
    public static String urlForgotPassword = url + "a_forgotPassword.php";
    public static String urlSignup= url + "a_signup.php";
    public static String urlNewOrders= url + "a_onlineorder.php";
    public static String urlMyOrders= url + "a_myorder.php";
    public static String urlProducts= url + "a_products.php";
    public static String urlMyOrderDetails= url + "a_myorderdetails.php";


    public static SQLiteDatabase database;


    public DBClass(Context context){
            super(context, DBClass.dbname, null, 1);
    }

    public void onCreate(SQLiteDatabase arg) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
        // TODO Auto-generated method stub

    }

    public static void execNonQuery(String query){
        //Execute Insert, Update, Delete, Create table queries
        //Log.e("Quesry", query);
        database.execSQL(query);
    }

    public static Cursor getCursorData(String query){
        //Log.d("SQuery", query);
        Cursor res =  database.rawQuery(query, null);
        return res;
    }

    public static String getSingleValue(String query) {
        try {
            Cursor res =  getCursorData(query);
            String value = "";
            if (res.moveToNext()) {
                return res.getString(0);
            }
            return value;
        }
        catch (Exception ex)
        {
            return "";
        }
    }

    public static int getNoOfRows(String query){
        try {
            Cursor res = database.rawQuery(query, null);
            return res.getCount();
        }catch (Exception ex)
        {
            return 0;
        }
    }

    public static String getRandomName() {
        Random generator = new Random();
        String x = String.valueOf (generator.nextInt(96) + 32);
        return x;
    }

    public static boolean checkIfRecordExist(String query){
        //Log.e("CheckQuery", query);
        Cursor res =  database.rawQuery(query, null );
        if(res.getCount() > 0)
            return true;
        else
            return false;
    }


    public static boolean doesTableExists(String tableName)
    {
        try{
            Cursor cursor = getCursorData("SELECT * FROM " + tableName);
            return true;
        }
        catch (Exception ex)
        {
            return  false;
        }
    }

    public static boolean doesFieldExist(String tableName, String fieldName)
    {
        try {
            String query = "SELECT " + fieldName + " FROM " + tableName;
            Cursor cursor = getCursorData(query);
            return  true;
        }
        catch (Exception ex)
        {
            return  false;
        }
    }



}
