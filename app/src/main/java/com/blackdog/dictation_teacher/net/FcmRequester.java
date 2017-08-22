package com.blackdog.dictation_teacher.net;

import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by DH on 2017-08-21.
 */

public class FcmRequester {
    String TAG = "FcmRequester.java";
    private static FcmRequester instance;

    private FcmRequester() {
    }

    public static FcmRequester getInstance() {
        if (instance == null) {
            instance = new FcmRequester();
        }
        return instance;
    }

    private OkHttpClient client = new OkHttpClient();

    public void requestDictationStart(int quizNumber, String quizHistoryId) throws IOException {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"to\": \"/topics/teacherId\",\r\n  \"data\": \r\n  {\r\n    \"message\": \"start\",\r\n    \"quizNumber\": \"" + quizNumber + "\",\r\n    \"quizHistoryId\": \"" + quizHistoryId + "\"\r\n  }\r\n}");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("authorization", "key=AAAAq9488uM:APA91bHMQV7aghImSINMlEI39rlEkCyFQ8VE43qEPJX3O2iW0KWx__VnuP1azQRGYZf58ry31n_-E-J77LjW3bjpxYg8cu9Boh_EYL7tqySvE6wPcgWqzIcy28NAGJIVWUagV38a4_Jz")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "c65bc801-e8f9-ee31-66be-523cfd076d8b")
                .build();

        client.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        call.cancel();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        if (response.body() != null) {
                            final String myResponse = response.body().toString();
                        }
                    }
                }
        );
    }

    public void requestDictationEnd() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"to\": \"/topics/teacherId\",\r\n  \"data\": \r\n  {\r\n    \"message\": \"end\"\r\n  }\r\n}");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("authorization", "key=AAAAq9488uM:APA91bHMQV7aghImSINMlEI39rlEkCyFQ8VE43qEPJX3O2iW0KWx__VnuP1azQRGYZf58ry31n_-E-J77LjW3bjpxYg8cu9Boh_EYL7tqySvE6wPcgWqzIcy28NAGJIVWUagV38a4_Jz")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "0a62b5ba-3de1-798b-ac8e-b92b5969979c")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }

    public void requestMoveToNext() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"to\": \"/topics/teacherId\",\r\n  \"data\": \r\n  {\r\n    \"message\": \"next\"\r\n  }\r\n}");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("authorization", "key=AAAAq9488uM:APA91bHMQV7aghImSINMlEI39rlEkCyFQ8VE43qEPJX3O2iW0KWx__VnuP1azQRGYZf58ry31n_-E-J77LjW3bjpxYg8cu9Boh_EYL7tqySvE6wPcgWqzIcy28NAGJIVWUagV38a4_Jz")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "e2f83b52-3139-ad0b-7e8a-23005fc0465e")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }

    public void requestMoveToPrevious() {
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, "{\r\n  \"to\": \"/topics/teacherId\",\r\n  \"data\": \r\n  {\r\n    \"message\": \"previous\"\r\n  }\r\n}");
        Request request = new Request.Builder()
                .url("https://fcm.googleapis.com/fcm/send")
                .post(body)
                .addHeader("authorization", "key=AAAAq9488uM:APA91bHMQV7aghImSINMlEI39rlEkCyFQ8VE43qEPJX3O2iW0KWx__VnuP1azQRGYZf58ry31n_-E-J77LjW3bjpxYg8cu9Boh_EYL7tqySvE6wPcgWqzIcy28NAGJIVWUagV38a4_Jz")
                .addHeader("content-type", "application/json")
                .addHeader("cache-control", "no-cache")
                .addHeader("postman-token", "639073ea-47da-8dea-5a42-4e7c5c1a9a1a")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }


}
