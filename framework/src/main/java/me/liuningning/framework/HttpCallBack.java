package me.liuningning.framework;

import com.google.gson.Gson;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import me.liuningning.core.http.EngineCallBack;

public abstract class HttpCallBack<T> implements EngineCallBack {


    @Override
    public void onPreExecute(Map<String, Object> parameter) {

        parameter.put("app_name", "joke_essay");
        parameter.put("version_name", "5.7.0");
        parameter.put("ac", "wifi");
        parameter.put("device_id", "30036118478");
        parameter.put("device_brand", "Xiaomi");
        parameter.put("update_version_code", "5701");
        parameter.put("manifest_version_code", "570");
        parameter.put("longitude", "113.000366");
        parameter.put("latitude", "28.171377");
        parameter.put("device_platform", "android");

        onPreExecute();


    }

    public void onPreExecute() {

    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        T objResult = (T) gson.fromJson(result, analysisClass(this));
        onSuccess(objResult);
    }


    public abstract void onSuccess(T result);

    private Class<?> analysisClass(Object object) {
        Type genType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();
        return (Class<?>) params[0];
    }
}
