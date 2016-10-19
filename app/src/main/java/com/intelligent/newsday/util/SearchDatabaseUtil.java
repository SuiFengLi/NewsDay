package com.intelligent.newsday.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.intelligent.newsday.bean.SearchInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ljh on 2016/9/20.
 */
public class SearchDatabaseUtil {

    private Context context;
    private SQLiteDatabase sqLiteDatabase;

    public SearchDatabaseUtil(Context context) {
        this.context = context;
        sqLiteDatabase = new MyDB(context).getReadableDatabase();

    }


    public  boolean addMsg(SearchInfo searchInfo) {
        if (!searchUrl(searchInfo.getUrl())){
            ContentValues cv = new ContentValues();
            cv.put("url", searchInfo.getUrl());
            cv.put("title",searchInfo.getTitle());
            sqLiteDatabase.insert("search", null, cv);
            return true;
        }
        return false;
    }


    public boolean searchUrl(String url) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from search", null);
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex("url"));
            if (str.equals(url))
                return true;
        }
        return false;
    }

    public void delete(String title){
        sqLiteDatabase.delete("search","title =?",new String[]{title});
    }

    public List<SearchInfo> showAll(){
        List<SearchInfo> data = new ArrayList<>();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from search", null);
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex("url"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            data.add(new SearchInfo(str,title));
        }
        return data;
    }


    public List<String> getTitle(String title) {
        Cursor cursor = sqLiteDatabase.rawQuery("select * from search where title Like ?", new String[]{'%' + title + '%'});
        List<String> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String str = cursor.getString(cursor.getColumnIndex("title"));
            Log.i("Util", str);
            list.add(str);
        }
        return list;
    }

    class MyDB extends SQLiteOpenHelper {
        public MyDB(Context context) {
            super(context, "searchData.db", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table search(url text,title text) ");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }


}
