package com.spc.mygame.util;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.webkit.WebSettings;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.spc.mygame.model.TestModel;

import java.io.IOException;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class HttpUtil {

    /**
     * post 请求
     *
     * @param url
     * @param params
     */
    public static void sendHttpPost(final Activity activity, String url, Map<String, String> params, final Class mclass) {
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder bodyBuilder = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                bodyBuilder.add(key, params.get(key));
            }
        }
        final Request request = new Request.Builder()
                .url(url)
                .removeHeader("User-Agent")
                .addHeader("User-Agent", getUserAgent(activity))
//                .addHeader("token", ShareUtil.instance().getToken(activity))
                .addHeader("token", "d71f26dca56f3c2d930c12ce1c0f2238")
                .post(bodyBuilder.build()).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TestModel.instance().setObject(e.getMessage());
                        TestModel.instance().update();
                    }
                });
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String result = response.body().string();
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (response.isSuccessful()) {
                            JsonObject jso = new Gson().fromJson(result, JsonObject.class);
                            if (jso.has("code") && jso.get("code").getAsInt() == 1) {
                                TestModel.instance().setObject(new Gson().fromJson(jso.get("data"), mclass));
                            } else {
                                TestModel.instance().setObject("报错信息");
                            }
                        } else {
                            TestModel.instance().setObject("请检查网络");
                        }
                        TestModel.instance().update();
                    }
                });

            }
        });
    }

    /**
     * 网络请求 获取UserAgent
     *
     * @param context
     * @return
     */
    public static String getUserAgent(Context context) {
        String userAgent = "";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                userAgent = WebSettings.getDefaultUserAgent(context);
            } catch (Exception e) {
                userAgent = System.getProperty("http.agent");
            }
        } else {
            userAgent = System.getProperty("http.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }
}
