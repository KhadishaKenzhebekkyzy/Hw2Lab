package com.example.workflow;

import camundajar.impl.com.google.gson.Gson;
import camundajar.impl.com.google.gson.reflect.TypeToken;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

@Component
public class MovieInformation implements JavaDelegate {

    private static Map<String, Object> jsonToMap(String str){
        Map<String, Object> map = new Gson().fromJson(str, new TypeToken<HashMap<String, Object>>(){}.getType());
        return map;
    }

    @Override
    public void execute(DelegateExecution delegateExecution) throws Exception {
        String key = "93008dcf46b83ee86df7dc148ace7c70";
        String movie = "200";
        String url = "https://api.themoviedb.org/3/movie/"+movie+"?api_key="+key;
        try {
            StringBuilder out = new StringBuilder();
            URL urlStr = new URL(url);
            URLConnection urlConnection = urlStr.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                out.append(line);
            }
            bufferedReader.close();
            Map<String, Object> res = jsonToMap(out.toString());
            System.out.println(res);
            String title = (String) res.get("title");
            delegateExecution.setVariable("movieTitle", title);
        } catch (IOException e){
            System.out.println(e);
        }
    }
}
