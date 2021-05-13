package me.liuningning.framework.db;

import android.database.Cursor;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class Column {


    private static Map<Class, String> sDbTypes = new HashMap<>();

    private static Map<Class, String> sCursorMethod = new HashMap<>();

    static {
        sDbTypes.put(int.class, "integer");
        sDbTypes.put(int.class, "integer");
        sDbTypes.put(Integer.class, "integer");
        sDbTypes.put(long.class, "long");
        sDbTypes.put(Long.class, "long");
        sDbTypes.put(float.class, "number");
        sDbTypes.put(Float.class, "number");
        sDbTypes.put(double.class, "number");
        sDbTypes.put(Double.class, "number");
        sDbTypes.put(String.class, "text");

    }

    public Column(Field field) {
        this.mName = field.getName();
        this.mField = field;
        this.mDbType = convert(field.getType());
    }


    private String mName;
    private Field mField;
    private String mDbType;

    public String getName() {
        return mName;
    }

    public Field getField() {
        return mField;
    }

    public String getDbType() {
        return mDbType;
    }


    @Override
    public String toString() {
        return "Column{" +
                "mName='" + mName + '\'' +
                ", mField=" + mField +
                ", mDbType='" + mDbType + '\'' +
                '}';
    }


    public Object getValue(Object entry) {
        this.mField.setAccessible(true);
        try {
            return this.mField.get(entry);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setValue(Object entry, Object value) {
        try {
            mField.setAccessible(true);
            mField.set(entry, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }


    private String convert(Class<?> type) {
        String dbType = sDbTypes.get(type);
        if (dbType == null) {
            dbType = "text";
        }
        return dbType;
    }

    public Object getValueFromCursor(Cursor cursor) {
        if (cursor == null) {
            return null;
        }
        int columnIndex = cursor.getColumnIndex(mName);

        Class<?> fieldType = mField.getType();

        if (fieldType == int.class || fieldType == Integer.class) {
            return cursor.getInt(columnIndex);
        } else if (fieldType == long.class || fieldType == Long.class) {
            return cursor.getLong(columnIndex);
        } else if (fieldType == String.class) {
            return cursor.getString(columnIndex);
        }

        return null;
    }
}
