package me.liuningning.core.http;

import android.util.Log;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OKHttpEngine implements IHttpEngine {
    private OkHttpClient mOKHttpClient;

    private static final String TAG = "OKHttpEngine";

    public OKHttpEngine() {
        mOKHttpClient = new OkHttpClient.Builder().build();
    }

    @Override
    public void get(String url, Map<String, Object> parameter, EngineCallBack callback) {

        if (callback == null) {
            callback = EngineCallBack.DEFAULT_ENGINE_CALL_BACK;
        }
        final EngineCallBack finalCallback = callback;
        finalCallback.onPreExecute(parameter);

        url = joinUlr(url, parameter);
        Log.e(TAG, url);
        Request request = new Request.Builder().get()
                .url(url).build();


        mOKHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finalCallback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

                String data = response.body().string();
                if (response.isSuccessful()) {
                    finalCallback.onSuccess(data);
                } else {
                    int code = response.code();
                    finalCallback.onError(new RuntimeException("code is " + code));
                }
            }
        });

    }

    private String joinUlr(String url, Map<String, Object> parameter) {

        if (parameter == null || parameter.isEmpty()) {
            return url;
        }
        StringBuilder builder = new StringBuilder();
        builder.append(url);

        if (!url.contains("?")) {
            builder.append("?");
        } else {
            if (!url.endsWith("?")) {
                builder.append("&");
            }
        }
        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            builder.append(entry.getKey() + "=" + entry.getValue() + "&");
        }

        builder.deleteCharAt(builder.length() - 1);


        return builder.toString();
    }

    @Override
    public void post(String url, Map<String, Object> parameter, EngineCallBack callback) {
        //not handle file

        if (callback == null) {
            callback = EngineCallBack.DEFAULT_ENGINE_CALL_BACK;
        }


        final EngineCallBack finalCallback = callback;

        finalCallback.onPreExecute(parameter);

        Log.e(TAG, joinUlr(url, parameter));

        FormBody.Builder builder = new FormBody.Builder();

        for (Map.Entry<String, Object> entry : parameter.entrySet()) {
            String key = entry.getKey();
            Object value = entry.getValue();
            builder.addEncoded(key, String.valueOf(value));
        }


        Request request = new Request.Builder().post(builder.build())
                .url(url).build();

        mOKHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                finalCallback.onError(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String data = response.body().toString();
                finalCallback.onSuccess(data);
            }
        });

    }
}
