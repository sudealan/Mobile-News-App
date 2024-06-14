package com.example.newsappsudealan;

import static java.sql.DriverManager.println;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class NewsRepository {

    public void getAllNews(ExecutorService srv, Handler uiHandler){

        srv.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getall");

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
                List<NewsModel> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);
                    NewsModel newsObj = new NewsModel(obj.getInt("id"),
                            obj.getString("title"),
                            obj.getString("text"),
                            obj.getString("categoryName"),
                            obj.getString("image"),
                            obj.getString("date"));

                    data.add(newsObj);

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

    public void downloadImage(ExecutorService srv, Handler uiHandler,String path){
        srv.execute(()->{
            try {
                URL url = new URL(path);
                HttpURLConnection conn = (HttpURLConnection)url.openConnection();

                Bitmap bitmap =  BitmapFactory.decodeStream(conn.getInputStream());

                Message msg = new Message();
                msg.obj = bitmap;
                uiHandler.sendMessage(msg);


            } catch (IOException e) {
                e.printStackTrace();
            }

        });

    }

    public void getNewsByCatId(ExecutorService srv, Handler uiHandler,int category_id){

        srv.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getbycategoryid/" + category_id);

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
                List<NewsModel> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);
                    NewsModel newsObj = new NewsModel(obj.getInt("id"),
                            obj.getString("title"),
                            obj.getString("text"),
                            obj.getString("categoryName"),
                            obj.getString("image"),
                            obj.getString("date"));

                    data.add(newsObj);

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

    public void getNewsById(ExecutorService srv, Handler uiHandler,int news_id){
        srv.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getnewsbyid/" + news_id);

                HttpURLConnection conn=
                        (HttpURLConnection)url.openConnection();

                //InputStreamReader -> read char by char, supports encoding
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

                JSONObject obj = arr.getJSONObject(0);



                NewsModel newsObj = new NewsModel(obj.getInt("id"),
                        obj.getString("title"),
                        obj.getString("text"),
                        obj.getString("categoryName"),
                        obj.getString("image"),
                        obj.getString("date"));

                conn.disconnect();


                Message msg = new Message();
                msg.obj = newsObj;

                uiHandler.sendMessage(msg);

            } catch (IOException | JSONException e) {


                e.printStackTrace();
            }


        });
    }
}
