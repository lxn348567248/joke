package me.liuningning.framework.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class DaoFactory {

    private SQLiteDatabase mDatabase;

    private static DaoFactory sInstance = new DaoFactory();
    private Context mContext;


    public static DaoFactory getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        //create database
        mDatabase = this.mContext.openOrCreateDatabase("nhdz.db", Context.MODE_PRIVATE, null);

    }

    public <T> IDaoSupport<T> getDao(Class<T> entryClass) {
        DaoSupport<T> daoSupport = new DaoSupport<>();
        daoSupport.init(mDatabase, entryClass);
        return daoSupport;
    }
}
