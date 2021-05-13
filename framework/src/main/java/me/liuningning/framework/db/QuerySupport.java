package me.liuningning.framework.db;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class QuerySupport<T> {
    private final List<Column> mColumns;
    private final Class<T> mEntryClass;
    private SQLiteDatabase mDataBase;
    private String mTableName;
    private String mQueryColumns[];
    private String mQuerySelection;
    private String mQuerySelectionArgs[];

    // 查询分组
    private String mQueryGroupBy;
    // 查询对结果集进行过滤
    private String mQueryHaving;
    // 查询排序
    private String mQueryOrderBy;
    private String mQueryLimit;


    QuerySupport(SQLiteDatabase database, List<Column> columnList, String tableName, Class<T> entryClass) {
        this.mDataBase = database;
        this.mColumns = columnList;
        this.mTableName = tableName;
        this.mEntryClass = entryClass;
    }


    public QuerySupport<T> setColumn(String... columns) {
        this.mQueryColumns = columns;
        return this;
    }

    public QuerySupport<T> setSelection(String selection) {
        this.mQuerySelection = selection;
        return this;
    }

    public QuerySupport<T> setQuerySelectionArgs(String... querySelectionArgs) {
        this.mQuerySelectionArgs = querySelectionArgs;
        return this;
    }

    public QuerySupport<T> setGroupBy(String groupBy) {
        this.mQueryGroupBy = groupBy;
        return this;
    }

    public QuerySupport<T> setHaving(String having) {
        this.mQueryHaving = having;
        return this;
    }

    public QuerySupport<T> seOrderBy(String orderBy) {
        this.mQueryOrderBy = orderBy;
        return this;
    }


    public QuerySupport<T> setLimt(String limit) {
        this.mQueryLimit = limit;
        return this;
    }


    public List<T> query() {
        Cursor cursor = mDataBase.query(mTableName, mQueryColumns, mQuerySelection,
                mQuerySelectionArgs, mQueryGroupBy, mQueryHaving, mQueryOrderBy, mQueryLimit);
        clearQueryParams();
        return cursorToList(cursor);
    }



    private List<T> cursorToList(Cursor cursor) {
        List<T> result = new ArrayList<>();

        while (cursor.moveToNext()) {
            T item = cursor2Entry(cursor, mEntryClass);
            if (item != null) {
                result.add(item);
            }
        }

        cursor.close();
        return result;
    }

    private T cursor2Entry(Cursor cursor, Class<T> entryClass) {
        T item = newInstance(entryClass);
        if (item != null) {
            for (Column column : mColumns) {
                Object value = column.getValueFromCursor(cursor);
                column.setValue(item, value);
            }
        }

        return item;


    }


    private T newInstance(Class<T> mEntryClass) {
        try {
            return mEntryClass.newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }

        return null;

    }

    private void clearQueryParams() {
        mQueryColumns = null;
        mQuerySelection = null;
        mQuerySelectionArgs = null;
        mQueryGroupBy = null;
        mQueryHaving = null;
        mQueryOrderBy = null;
        mQueryLimit = null;
    }
}
