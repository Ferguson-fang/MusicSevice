package wtbu.xiaomai.musicgo.getNetRequest;

import android.os.Handler;
import android.os.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

public class postRequest {
    public void sendPostNetRequest(String mUrl, Handler handler, HashMap<String, String> params) {
        new Thread(() -> {
            try {
                String m_url=mUrl+"?timestamp="+System.currentTimeMillis();
                URL url = new URL(mUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(8000);
                connection.setReadTimeout(8000);
                connection.setDoOutput(true);
                connection.setDoInput(true);
                StringBuilder stringBuilder = new StringBuilder();
                for(String Key:params.keySet()){
                    stringBuilder.append(Key).append("=").append(params.get(Key)).append("&");
                }
                connection.connect();
                OutputStream out = connection.getOutputStream();
                out.write(stringBuilder.substring(0, stringBuilder.length() - 1).getBytes());
                InputStream in = connection.getInputStream();
                String responseData = StreamToString(in);
                Message message = new Message();
                message.obj = responseData;
                handler.sendMessage(message);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    public String StreamToString(InputStream in) {
        StringBuilder sb = new StringBuilder();
        String oneline;
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        try {
            while ((oneline = reader.readLine()) != null) {
                sb.append(oneline).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }
}
