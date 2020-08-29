package com.project.newzyfi.sqlmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.project.newzyfi.model.SavedNewsModel;
import com.project.newzyfi.response.TrendingResponse;

import java.util.ArrayList;

public class SQLiteManager extends SQLiteOpenHelper {


    public SQLiteManager(Context context) {
        super(context, "newzyfi_db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE IF NOT EXISTS saved_news("+
                "title TEXT NOT NULL," +
                "description TEXT NOT NULL,url TEXT NOT NULL," +
                "url_image TEXT NOT NULL,published TEXT NOT NULL,id INTEGER PRIMARY KEY AUTOINCREMENT)";

        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE saved_news");
        onCreate(db);

    }

    public void addNewsData(ArrayList<String> arrayList){

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("title",arrayList.get(0));
        contentValues.put("description",arrayList.get(1));
        contentValues.put("url",arrayList.get(2));
        contentValues.put("url_image",arrayList.get(3));
        contentValues.put("published",arrayList.get(4));

        sqLiteDatabase.insert("saved_news",null,contentValues);
        sqLiteDatabase.close();

    }

    public ArrayList<SavedNewsModel> getSavedNews(){

        ArrayList<SavedNewsModel> data = new ArrayList<>();

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM saved_news",null);

        if(cursor.moveToFirst()){
            do{
                SavedNewsModel savedNewsModel = new SavedNewsModel();
                savedNewsModel.setTitle(cursor.getString(0));
                savedNewsModel.setDescription(cursor.getString(1));
                savedNewsModel.setUrl(cursor.getString(2));
                savedNewsModel.setUrl_image(cursor.getString(3));
                savedNewsModel.setPublished(cursor.getString(4));
                savedNewsModel.setId(cursor.getInt(5));

               data.add(savedNewsModel);

            }while (cursor.moveToNext());
        }
        return data;
    }

    public boolean deleteSavedItem(int id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        return sqLiteDatabase.delete("saved_news","id"+ " = '" + id +"'",null)>=0;


    }
}
