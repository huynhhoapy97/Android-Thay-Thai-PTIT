package com.example.bai1tuan9;

import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class ReadJson {
    private static final String TAG = ReadJson.class.getSimpleName();
    public ReadJson() {
    }

    /***
     * Hàm lấy dữ liệu Json từ webservice
     * @param sUrl
     * @return
     */
    public String getJSONStringFromURL(String sUrl) {
        String response = null;
        try {
            URL url = new URL(sUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();// Khởi tạo đối tượng HttpURLConnection
            conn.setRequestMethod("GET");// Phương thức lấy dữ liệu
            InputStream in = new BufferedInputStream(conn.getInputStream());// Tạo luồng đọc dữ liệu
            response = convertStreamToString(in);// Chuyển đổi dữ liệu thu được
        } catch (MalformedURLException e) {
            Log.e(TAG, "MalformedURLException: " + e.getMessage());
        } catch (ProtocolException e) {
            Log.e(TAG, "ProtocolException: " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "IOException: " + e.getMessage());
        } catch (Exception e) {
            Log.e(TAG, "Exception: " + e.getMessage());
        }
        return response;
    }

    /***
     * Chuyển đổi dữ liệu thành chuỗi khi đã lấy về
     * @param is
     * @return
     */
    private String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));// Tạo bộ đệm để đọc dòng dữ liệu
        StringBuilder sb = new StringBuilder();// Đối tượng xây dựng chuỗi từ những dữ liệu đã được đọc

        String line;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line).append('\n');// đọc và thêm các dữ liệu đã đọc được từ luồng vào chuỗi.
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
