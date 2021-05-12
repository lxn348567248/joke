package me.liuningning.core.http;

import java.util.Map;

public interface IHttpEngine {

    void get(String url, Map<String, Object> parameter, EngineCallBack callback);

    void post(String url, Map<String, Object> parameter, EngineCallBack callback);
}
