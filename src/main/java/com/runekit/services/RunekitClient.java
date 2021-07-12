package com.runekit.services;

import com.google.gson.Gson;
import com.runekit.models.NotificationRequest;
import com.runekit.models.RaidParty;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.net.URL;

@Slf4j
public class RunekitClient implements Runnable {

    private String username;
    private String token;

    public RunekitClient(String username, String token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public void run() {
        return;
    }

    public void createBirdhouseNotification(NotificationRequest request)
    {
        try {
            Gson g = new Gson();
            URL url = new URL("http://localhost:60000/api/runelitenotification");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .addHeader("Authorization", String.format("RuneLite %s", token))
                    .post(body).build();

            Call call = client.newCall(OK_request);
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

    public void createFarmPatchNotification(NotificationRequest request)
    {
        try {
            Gson g = new Gson();
            URL url = new URL("http://localhost:60000/api/runelitenotification");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .addHeader("Authorization", String.format("RuneLite %s", token))
                    .post(body).build();

            Call call = client.newCall(OK_request);
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

    public void joinedParty(RaidParty request) {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/partyhelper");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .addHeader("Authorization", String.format("RuneLite %s", token))
                    .post(body).build();

            Call call = client.newCall(OK_request);
            Response response = call.execute();

            if (response.code() == 200) {
                log.debug("Party - Successful: " + response.code());
            } else {
                log.debug("Party - Failed: " + response.code());
            }
            response.close();
            return;
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
    }

    public void healthCheck(RaidParty request) {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/partyhelper");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .addHeader("Authorization", String.format("RuneLite %s", token))
                    .post(body).build();

            Call call = client.newCall(OK_request);
            Response response = call.execute();

            if (response.code() == 200) {
                log.debug("Health - Successful: " + response.code());
            } else {
                log.debug("Health - Failed: " + response.code());
            }
            response.close();
            return;
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
    }
}
