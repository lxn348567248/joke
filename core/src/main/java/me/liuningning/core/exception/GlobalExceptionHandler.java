package me.liuningning.core.exception;

import android.content.Context;
import android.nfc.Tag;
import android.os.Build;
import android.os.Environment;
import android.os.Process;
import android.util.Log;

import androidx.annotation.NonNull;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;


public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = "GlobalExceptionHandler";

    private static GlobalExceptionHandler sInstance = new GlobalExceptionHandler();
    private Thread.UncaughtExceptionHandler mDefaultExceptionHandler;

    private static final String DIRECTORY_CRASH = "crash";
    private Context mContext;

    private String mDumpFileFullPath;


    private GlobalExceptionHandler() {

    }

    public static GlobalExceptionHandler getInstance() {
        return sInstance;
    }

    public void init(Context context) {
        this.mContext = context.getApplicationContext();
        mDefaultExceptionHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.currentThread().setUncaughtExceptionHandler(this);

        Log.d(TAG, Environment.getExternalStorageState());
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            mDumpFileFullPath = mContext.getExternalFilesDir(null).getAbsolutePath() + File.separator + DIRECTORY_CRASH;
        } else {
            mDumpFileFullPath = mContext.getFilesDir().getAbsolutePath() + File.separator + DIRECTORY_CRASH;
        }

        File rootPath = new File(mDumpFileFullPath);
        if (!rootPath.exists()) {
            rootPath.mkdirs();
        }


    }


    public String getCrashDirectory() {
        return mDumpFileFullPath;
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {


        Map<String, String> deviceInformation = dumpDeviceInformation();
        String exceptionInformation = dumpException(e);

        StringBuilder information = new StringBuilder();

        for (Map.Entry<String, String> entry : deviceInformation.entrySet()) {
            information.append(entry.getKey()).append("=")
                    .append(entry.getValue())
                    .append("\n");
        }

        information.append(exceptionInformation);

        String fileName = generalFileName();
        File crashFile = new File(mDumpFileFullPath, fileName);

        save(information.toString(), crashFile.getAbsolutePath());

        if (mDefaultExceptionHandler != null) {
            mDefaultExceptionHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private String dumpException(Throwable e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        pw.flush();
        return sw.toString();
    }

    private String generalFileName() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String today = simpleDateFormat.format(new Date());
        return today + ".txt";
    }

    private Map<String, String> dumpDeviceInformation() {
        //save data - > file

        Map<String, String> deviceInformation = new HashMap<>();
        deviceInformation.put("manufacturer", Build.MANUFACTURER);
        deviceInformation.put("brand", Build.BRAND);
        deviceInformation.put("abi", Build.CPU_ABI);
        deviceInformation.put("sdk", String.valueOf(Build.VERSION.SDK_INT));

        return deviceInformation;

    }


    private void save(String data, String filePath) {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(data.getBytes());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
