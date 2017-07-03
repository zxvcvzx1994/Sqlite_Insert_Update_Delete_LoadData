package com.example.kh.myapplication.Sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.support.annotation.RequiresApi;

import com.example.kh.myapplication.Module.ToDo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kh on 7/3/2017.
 */

public class MySqlite   {
    private static MySqlite mySqlite;
    private static final  String NAME=  "todoList.db";
    private static final  String TABLE=  "TODO";
    private static final  String TASK_ID=  "task_id";
    private static final  String TASK=  "task";
    private static final  String DOI=  "doi";
    private static final  int VERVSON=  2;

    private static final  String SQL=  "CREATE TABLE "+TABLE+"("+TASK_ID+" INTEGER PRIMARY KEY , "+TASK+" TEXT NOT NULL, "+DOI+" TEXT  )";
    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    private MySqlite(Context context){
        this.context  =context;
        sqLiteDatabase  =new MysqlHelper(context,NAME, null, VERVSON ).getWritableDatabase();
    }

    public static  synchronized MySqlite getMySqlite(Context context){
        if(mySqlite==null){
            mySqlite =new MySqlite(context);
        }
        return mySqlite;
    }

    public boolean insert(String values, String doi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, values);
        contentValues.put(DOI, doi);


        return sqLiteDatabase.insert(TABLE, null, contentValues)>0;
    }

    public boolean remove(int id){
        return sqLiteDatabase.delete(TABLE, TASK_ID+" = '"+id+"'", null)>0;
    }

    public boolean update(int id, String newvalue, String doi){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TASK, newvalue);
        contentValues.put(DOI, doi);
        return sqLiteDatabase.update(TABLE,contentValues,TASK_ID +" = '"+id+"'",null)>0;
    }

    public  List<ToDo> getAll(){
        List<ToDo> list = new ArrayList<ToDo>();
        Cursor cursor  = sqLiteDatabase.query(TABLE, new String[]{TASK_ID, TASK, DOI}, null,null,null, null,null);
        if(cursor!=null && cursor.getCount()>0){
            while(cursor.moveToNext()){
                ToDo toDo = new ToDo(cursor.getInt(0),cursor.getString(1), cursor.getString(2));
                list.add(toDo);
            }
        }
        return list;
    }

    public class MysqlHelper extends SQLiteOpenHelper {
        public MysqlHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
            super(context, name, factory, version);

        }

        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onConfigure(SQLiteDatabase db) {
            super.onConfigure(db);
            db.setForeignKeyConstraintsEnabled(true);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(SQL);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            switch (oldVersion){
                case 1:
                    db.execSQL("ALTER TABLE "+TABLE+" ADD COLUMN "+DOI+" TEXT");
                    break;
            }
        }
    }
}
