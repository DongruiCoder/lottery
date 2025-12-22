package com.businiao.lottery;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class SportteryClient {

    private final OkHttpClient client;

    public SportteryClient() {
        this.client = new OkHttpClient.Builder().build();
    }

    public String getHistoryPageList(int gameNo, int pageNo) throws IOException {
        Request request = new Request.Builder()
                .url("https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=" + gameNo + "&provinceId=0&pageSize=30&isVerify=1&pageNo=" + pageNo)
                .get()
                .build();

        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public String wW2YxL() throws IOException {
        Request request = new Request.Builder()
                .url("https://api.kuaichuti.net/api/v5/published/forms/wW2YxL")
                .get()
                .addHeader("authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJpZCI6IjY4ZjhiM2UxYmIwZDNhZThlMDEyNDlkYSIsImV4cCI6MTc2MzE5MDQxMX0.zdOqIc27AjZJkgW4JrBqH24vKfWoG9XlRapGbKBINho")
                .addHeader("priority", "u=1, i")
                .addHeader("x-requested-with", "XMLHttpRequest")
                .addHeader("content-type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            return response.body().string();
        }
    }

    public static void main(String[] args) {
        SportteryClient api = new SportteryClient();
        try {
            //String result = api.getHistoryPageList(1);
            String result = api.wW2YxL();
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
