package com.runekit.services;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.runekit.models.NotificationRequest;
import com.runekit.models.PartyResponse;
import com.runekit.models.RaidParty;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

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

    public List<PartyResponse> getPossibleDCPlayersInParty(String partyId) {
        try {
            Gson g = new Gson();
            URL url = new URL(String.format(
                    "https://api.runekit.net/api/PossibleDissconnectedPlayer?partyId=%s", partyId));

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .get().build();

            Call call = client.newCall(OK_request);
            Response response = call.execute();

            if (response.code() == 200) {
                Type raidPartyListType = new TypeToken<ArrayList<PartyResponse>>(){}.getType();
                List<PartyResponse> members = g.fromJson(response.body().string(), raidPartyListType);
                return members;
            } else {
                log.debug("Notification - Failed: " + response.code());
            }
            response.close();
            return new ArrayList<>();
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return new ArrayList<>();
        }
    }

    public void createBirdhouseNotification(NotificationRequest request)
    {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/runelitenotification");

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
            URL url = new URL("https://api.runekit.net/api/runelitenotification");

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

    public void ping(RaidParty request) {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/PartyHealthCheck");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();

            Request OK_request = new Request.Builder().url(url)
                    .post(body).build();

            Call call = client.newCall(OK_request);
            Response response = call.execute();

            if (response.code() == 200) {
                log.debug("Successful: " + response.code());
            } else {
                log.debug("Failed: " + response.code());
            }
            response.close();
            return;
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
    }

    public void leaveParty(RaidParty request) {
        try {
            Gson g = new Gson();
            URL url = new URL("https://api.runekit.net/api/PartyHealthCheck");

            String json = g.toJson(request);
            RequestBody body = RequestBody.create(
                    MediaType.parse("application/json"), json);

            OkHttpClient client = new OkHttpClient();
            Request OK_request = new Request.Builder().url(url)
                    .delete(body).build();

            Call call = client.newCall(OK_request);
            Response response = call.execute();

            if (response.code() == 200) {
                log.debug("Successful: " + response.code());
            } else {
                log.debug("Failed: " + response.code());
            }
            response.close();
            return;
        } catch (Exception ex) {
            log.debug(ex.getMessage());
            return;
        }
    }
}
