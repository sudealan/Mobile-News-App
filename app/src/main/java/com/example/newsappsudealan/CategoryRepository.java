package com.example.newsappsudealan;

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
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;

public class CategoryRepository {

    public void getAllNewsCategories(ExecutorService srv, Handler uiHandler){

        srv.execute(()->{

            try {

                URL url = new URL("http://10.3.0.14:8080/newsapp/getallnewscategories");

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
                List<CategoryModel> data = new ArrayList<>();

                for (int i = 0; i < arr.length(); i++) {

                    JSONObject obj = arr.getJSONObject(i);
                    CategoryModel catObj = new CategoryModel(obj.getInt("id"),
                            obj.getString("name"));

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
}
