package me.liuningning.framework.db;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

class DaoSupport<T> implements IDaoSupport<T> {

    private static final String TAG = "DaoSupport";

    private SQLiteDatabase mDatabase;
    private Class<T> mEntryClass;
    private String mTableName;

    private List<Column> mColumns = new ArrayList<>();

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> entryClass) {
        this.mDatabase = sqLiteDatabase;
        this.mEntryClass = entryClass;
        this.mTableName = mEntryClass.getSimpleName();

        //init column
        mColumns.clear();

        Field[] fields = entryClass.getDeclaredFields();

        for (Field item : fields) {
            mColumns.add(new Column(item));
        }

        //create table
        String createSql = createTable(mTableName, mColumns);
        Log.e(TAG, createSql);
        this.mDatabase.execSQL(createSql);

    }

    private String createTable(String tableName, List<Column> columnList) {
        StringBuilder builder = new StringBuilder();
        builder.append("create table if not exists ")
                .append(tableName)
                .append("(id integer primary key autoincrement,");

        for (Column column : columnList) {
            builder.append(column.getName())
                    .append(" ")
                    .append(column.getDbType())
                    .append(",");
        }


        builder.deleteCharAt(builder.length() - 1);

        builder.append(")");


        return builder.toString();
    }


    @Override
    public int insert(T entry) {

        ContentValues contentValue = getContentValue(entry);

        Log.e(TAG, contentValue.toString());

        return (int) this.mDatabase.insert(mTableName, null, contentValue);
    }

    private ContentValues getContentValue(T entry) {

        ContentValues contentValues = new ContentValues();
        for (Column column : mColumns) {
            Object value = column.getValue(entry);
            String key = column.getName();
            if (value != null) {
                Field field = column.getField();
                Class fieldType = field.getType();
                if (fieldType == int.class || fieldType == Integer.class) {
                    contentValues.put(key, (Integer) value);
                } else if (fieldType == long.class || fieldType == Long.class) {
                    contentValues.put(key, (Long) value);
                } else if (fieldType == String.class) {
                    contentValues.put(key, (String) value);
                }
            }

        }

        return contentValues;
    }

    @Override
    public void insert(List<T> entries) {

        this.mDatabase.beginTransaction();
        for (T entry : entries) {
            insert(entry);
        }
        this.mDatabase.setTransactionSuccessful();
        this.mDatabase.endTransaction();

    }

    @Override
    public int update(T entry, String where, String... value) {
        if (entry == null) {
            return 0;
        }
        ContentValues contentValues = getContentValue(entry);
        return this.mDatabase.update(mTableName, contentValues, where, value);

    }

    @Override
    public int delete(String where, String... value) {
        return this.mDatabase.delete(mTableName, where, value);
    }

    @Override
    public QuerySupport<T> getQuerySupport() {
        return new QuerySupport<>(mDatabase, mColumns, mTableName, mEntryClass);
    }


}
