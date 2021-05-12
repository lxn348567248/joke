package me.liuningning.core.http;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * HttpUtils
 */
public class HttpUtils {

    private static IHttpEngine sGlobalEngine = new OKHttpEngine();
    private final Context mContext;

    private IHttpEngine mHttpEngine = null;
    private static final int METHOD_GET = 0x01;
    private static final int METHOD_POST = 0x02;
    private int requestMethod = METHOD_GET;
    private Map<String, Object> mParameter = new HashMap<>();
    private String mUrl;

    public HttpUtils(Context context) {
        this.mContext = context;
    }


    public static HttpUtils with(Context context) {
        return new HttpUtils(context);

    }

    public static void initHttpEngin(IHttpEngine httpEngine) {
        sGlobalEngine = httpEngine;
    }

    public void exchange(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }


    public HttpUtils get() {
        this.requestMethod = METHOD_GET;
        return this;
    }

    public HttpUtils post() {
        this.requestMethod = METHOD_POST;
        return this;
    }

    public HttpUtils url(String url) {
        this.mUrl = url;
        return this;
    }

    public HttpUtils addParameter(String key, Object value) {
        this.mParameter.put(key, value);
        return this;
    }

    public HttpUtils addParameters(Map<String, Object> params) {
        this.mParameter.putAll(params);
        return this;
    }

    public void execute(EngineCallBack callback) {

        IHttpEngine httpEngine = mHttpEngine;

        if (httpEngine == null) {
            httpEngine = sGlobalEngine;
        }

        switch (requestMethod) {
            case METHOD_GET:
                httpEngine.get(mUrl, mParameter, callback);
                break;
            case METHOD_POST:
                httpEngine.post(mUrl, mParameter, callback);
                break;
            default:
                break;
        }

    }
}
