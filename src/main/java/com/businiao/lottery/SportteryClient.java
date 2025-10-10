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

    public String getHistoryPageList(int pageNo) throws IOException {
        Request request = new Request.Builder()
                .url("https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=30&isVerify=1&pageNo=" + pageNo)
                .get()
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
            String result = api.getHistoryPageList(1);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
