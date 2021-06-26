package com.runekit.services;

import com.google.gson.Gson;
import com.runekit.models.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.net.URL;

@Slf4j
public class RunekitClient implements Runnable {

    private String username;
    private String token;
    private NotificationRequest request;

    public RunekitClient(String username, String token, NotificationRequest request) {
        this.username = username;
        this.token = token;
        this.request = request;
    }

    @Override
    public void run() {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/runelitenotification");

            String json = g.toJson(this.request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder().url(url)
                    .addHeader("Authorization", String.format("RuneLite %s", token))
                    .post(body).build();

            Call call = client.newCall(request);
            Response response = call.execute();

            if (response.code() == 200) {
                log.debug("Notification - Successful: " + response.code());
            } else {
                log.debug("Notification - Failed: " + response.code());
            }
            response.close();
            return;
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
    }
}
