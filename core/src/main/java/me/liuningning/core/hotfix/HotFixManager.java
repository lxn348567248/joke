package me.liuningning.core.hotfix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

import dalvik.system.DexClassLoader;

public class HotFixManager {

    private static final String TAG = "HotFixManager";

    private static final String DEX_DIRECTORY = "out_dex";
    private static final String OPTIMIZED_DIRECTORY = "opt_dex";

    private File mDexDirectory;
    private File mOptimizedDirectory;
    private Context mContext;

    private static volatile HotFixManager mInstance;

    public static final int LOAD_FAILED = -1;
    public static final int LOAD_FILE_NOT_EXIST = -2;
    public static final int LOAD_FILE_COPY_FAILED = -3;
    public static final int LOAD_SUCCESS = 1;

    private HotFixManager() {

    }

    public static HotFixManager getInstance() {
        if (mInstance == null) {
            synchronized (HotFixManager.class) {
                if (mInstance == null) {
                    mInstance = new HotFixManager();
                }
            }
        }

        return mInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();

        mDexDirectory = mContext.getDir(DEX_DIRECTORY, Context.MODE_PRIVATE);
        mOptimizedDirectory = mContext.getDir(OPTIMIZED_DIRECTORY, Context.MODE_PRIVATE);

        if (!mDexDirectory.exists()) {
            mDexDirectory.mkdirs();
        }
        if (!mOptimizedDirectory.exists()) {
            mOptimizedDirectory.mkdirs();
        }

    }

    /**
     * load hot fix
     */
    public int load() {

        File[] dexFiles = mDexDirectory.listFiles();
        if (dexFiles == null || dexFiles.length == 0) {
            return LOAD_SUCCESS;
        }
        return loadFile(Arrays.asList(dexFiles));

    }

    public int load(String dexPath) {
        //cope file to dest
        File dexFile = new File(dexPath);
        String fileName = dexFile.getName();
        String targetPath = mDexDirectory.getAbsolutePath() + File.separator + fileName;

        if (!dexFile.exists()) {
            Log.w(TAG, "dexPath(" + dexPath + ") is exist");
            return LOAD_FILE_NOT_EXIST;
        }

        copyDex2Target(dexPath, targetPath);

        File targetFile = new File(targetPath);
        if (!targetFile.exists()) {
            Log.w(TAG, "copy dex file to " + targetPath + " failed");
            return LOAD_FILE_COPY_FAILED;
        }


        return loadFile(Arrays.asList(new File(mDexDirectory.getAbsolutePath(), fileName)));
    }

    private int loadFile(List<File> dexFiles) {
        ClassLoader parent = mContext.getClassLoader();
        Object originDexElements = dexElements(parent);
        if (originDexElements == null) {
            Log.w(TAG, "reflect dexElements filed failed");
            return LOAD_FAILED;
        }

        boolean needInject = false;
        for (File dexFile : dexFiles) {
            if (dexFile.isFile() && dexFile.getName().toLowerCase().endsWith(".dex")) {
                DexClassLoader dexClassLoader = new DexClassLoader(dexFile.getAbsolutePath(), mOptimizedDirectory.getAbsolutePath(), null, parent);
                Object dexElements = dexElements(dexClassLoader);
                if (dexElements != null) {
                    originDexElements = mergeElements(dexElements, originDexElements);
                    needInject = true;
                }
            }
        }

        if (needInject) {
            injectDexElements(parent, originDexElements);
        }

        return LOAD_SUCCESS;
    }

    private Object mergeElements(Object dexElements, Object originDexElements) {

        int leftSize = Array.getLength(dexElements);
        int originSize = Array.getLength(originDexElements);
        int targetSize = leftSize + originSize;
        Class elementClass = dexElements.getClass().getComponentType();
        Object targetElement = Array.newInstance(elementClass, targetSize);
        int index = 0;

        for (int i = 0; i < leftSize; i++) {
            Array.set(targetElement, index, Array.get(dexElements, i));
            index++;
        }

        for (int i = 0; i < originSize; i++) {
            Array.set(targetElement, index, Array.get(originDexElements, i));
            index++;
        }

        return targetElement;

    }

    private Object dexElements(ClassLoader classLoader) {
        Object pathList = getFiledValue("pathList", classLoader);
        Object dexElements = getFiledValue("dexElements", pathList);
        return dexElements;

    }

    private void injectDexElements(ClassLoader classLoader, Object elements) {
        Object pathList = getFiledValue("pathList", classLoader);
        Field dexElementsField = getField("dexElements", pathList);
        if (dexElementsField != null) {
            try {
                dexElementsField.setAccessible(true);
                dexElementsField.set(pathList, elements);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }

    }

    private Object getFiledValue(String fieldName, Object instance) {

        Field field = getField(fieldName, instance);
        try {
            if (field != null) {
                field.setAccessible(true);
                return field.get(instance);
            }
        } catch (IllegalAccessException e) {

        }


        return null;

    }

    private Field getField(String fieldName, Object instance) {

        if (instance == null) {
            return null;
        }

        Class<?> cls = instance.getClass();

        Field field;
        while (cls != null) {
            try {
                field = cls.getDeclaredField(fieldName);
                return field;
            } catch (NoSuchFieldException e) {

            }

            cls = cls.getSuperclass();
        }

        return null;
    }


    private void copyDex2Target(String dexPath, String targetPath) {

        try (FileInputStream inputStream = new FileInputStream(dexPath);
             FileOutputStream outputStream = new FileOutputStream(targetPath)) {
            byte[] buffer = new byte[8096];
            int size;
            while ((size = inputStream.read(buffer, 0, buffer.length)) > 0) {
                outputStream.write(buffer, 0, size);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
