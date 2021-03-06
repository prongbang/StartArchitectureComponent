package com.prongbang.startroom.api;

import android.util.Log;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by mdev on 7/13/2017.
 */

public class ServiceGenerator {

    private static final String TAG = ServiceGenerator.class.getSimpleName();

    /**
     * Create Service Request to Server
     *
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T createService(String url, Class<T> clazz) {

        OkHttpClient.Builder httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS);

        httpClient.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {

                Request original = chain.request();

                try {
                    Log.i(TAG, "URL ---> " + original.url());
                    Log.i(TAG, original.method() + " ---> " + ServiceGenerator.toString(original));
                } catch (Exception e) {
                    Log.e(TAG, "Message ---> " + e.getMessage());
                }

                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
                Request request = requestBuilder.build();
                try {
                    String header = request.header("Authorization");
                    Log.i(TAG, "Authorization  " + header);
                } catch (Exception e) {
                    Log.e(TAG, "Message ---> " + e.getMessage());
                }
                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(url)
                .client(client)
                .build();

        return retrofit.create(clazz);
    }

    /**
     * GET Value Request
     *
     * @param request
     * @return
     */
    private static String toString(Request request) {
        try {
            if (request.method().equals("GET")) {
                return request.url().query();
            } else {
                final Request copy = request.newBuilder().build();
                if (copy != null) {
                    final Buffer buffer = new Buffer();
                    RequestBody body = copy.body();
                    if (body != null) {
                        body.writeTo(buffer);
                        return buffer.readUtf8();
                    }
                }
            }
            return "null";
        } catch (final IOException e) {
            return "did not work";
        }
    }

}
