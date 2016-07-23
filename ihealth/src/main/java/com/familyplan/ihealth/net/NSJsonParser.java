package com.familyplan.ihealth.net;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;

import java.util.ArrayList;
import java.util.List;

class NSJsonParser {
    public static final String TAG = NSJsonParser.class.getSimpleName();

    public <T> void parse(String json, Class<T> clazz, NSCallback<T> callback) {
        try {
            Gson gson = new Gson();
            JsonResponse jr = gson.fromJson(json,
                    JsonResponse.class);
            if (jr.getCode() == 0) {// success
                if (clazz == null) {
                    Log.w(TAG, "null parse class");
                    callback.onSuccess(null);
                } else if (jr.getData() == null) {
                    Log.w(TAG, json);
                    callback.onSuccess(null);
                } else {
                    if (jr.getData().isJsonArray()) {
                        JsonArray array = jr.getData().getAsJsonArray();
                        List<T> result = new ArrayList<T>();
                        for (int i = 0; i < array.size(); i++) {
                            result.add(gson.fromJson(array.get(i), clazz));
                        }
                        callback.onSuccess(result, result.size());
                    } else if (jr.getData().isJsonObject()) {
                        callback.onSuccess(gson.fromJson(jr.getData(), clazz));
                    } else if (jr.getData().isJsonPrimitive()) {
                        callback.onSuccess(gson.fromJson(jr.getData(), clazz));
                    } else {
                        callback.onSuccess(null);
                    }
                }
            } else {
                callback.onFail(jr.getCode(), jr.getMsg());
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "解析异常");
            callback.onFail(-1,"");
        }
    }

    static class JsonResponse {
        int code;
        String msg;
        JsonElement data;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public JsonElement getData() {
            return data;
        }

        public void setData(JsonElement data) {
            this.data = data;
        }
    }
}