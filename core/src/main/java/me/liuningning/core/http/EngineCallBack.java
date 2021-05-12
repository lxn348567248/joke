package me.liuningning.core.http;

import java.util.Map;

public interface EngineCallBack {

    void onPreExecute(Map<String, Object> parameter);


    void onError(Exception e);

    void onSuccess(String result);

    EngineCallBack DEFAULT_ENGINE_CALL_BACK = new EngineCallBack() {
        @Override
        public void onPreExecute(Map<String, Object> parameter) {

        }

        @Override
        public void onError(Exception e) {

        }

        @Override
        public void onSuccess(String result) {

        }
    };
}
