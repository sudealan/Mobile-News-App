package com.example.newsappsudealan;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentRepository {

    public void getCommentsByNewsId(ExecutorService srv, Handler uiHandler, int news_id){

        srv.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getcommentsbynewsid/" + news_id);

                HttpURLConnection conn=
                        (HttpURLConnection)url.openConnection();

                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(conn.getInputStream())
                );

                StringBuilder buffer = new StringBuilder();
                String line ="";

                while((line = reader.readLine())!=null){
                    buffer.append(line);

                }

                JSONObject json = new JSONObject(buffer.toString());
                JSONArray arr = json.getJSONArray("items");
                List<CommentModel> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);
                    CommentModel catObj = new CommentModel(obj.getInt("id"),
                            obj.getInt("news_id"), obj.getString("name"),
                            obj.getString("text"));

                    data.add(catObj);

                }

                conn.disconnect();

                Message msg = new Message();
                msg.obj = data;

                uiHandler.sendMessage(msg);

            } catch (IOException | JSONException e) {

                e.printStackTrace();
            }


        });



    }

    public void postCommenterino(ExecutorService srv, Handler uiHandler,String name,
                                 String comment, int news_id){

        srv.execute(()->{

            try {
                URL url = new URL("http://10.3.0.14:8080/newsapp/savecomment");

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();

                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type","application/JSON");


                JSONObject outputData  = new JSONObject();

                outputData.put("name",name);
                outputData.put("text",comment);
                outputData.put("news_id",news_id);

                BufferedOutputStream writer =
                        new BufferedOutputStream(conn.getOutputStream());


                writer.write(outputData.toString().getBytes(StandardCharsets.UTF_8));
                writer.flush();

                BufferedReader reader
                        = new BufferedReader(new InputStreamReader(conn.getInputStream()));

                StringBuilder buffer = new StringBuilder();

                String line ="";

                while((line=reader.readLine())!=null){

                    buffer.append(line);

                }

                JSONObject retVal = new JSONObject(buffer.toString());

                conn.disconnect();

                int retValInt = retVal.getInt("serviceMessageCode");

                Message msg = new Message();
                msg.obj = retValInt;

                uiHandler.sendMessage(msg);

            } catch (IOException | JSONException e) {
                Message msg = new Message();
                msg.obj = 0;

                uiHandler.sendMessage(msg);
                e.printStackTrace();
            }


        });



    }

}
