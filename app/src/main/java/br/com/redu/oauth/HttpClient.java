package br.com.redu.oauth;

import org.scribe.model.Token;
import org.scribe.oauth.OAuthService;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class HttpClient {


	public String makeGetRequest(String urlStr,
			HashMap<String, String> params,Token token, ThreadLocal<OAuthService> service){

        String resposta = null;
        String parameters = "";
        try {
            urlStr += "?access_token="+token.getToken();

            if (params != null) {
			Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    parameters += "&"+entry.getKey()+"="+entry.getValue();
                    urlStr += "&"+entry.getKey()+"="+entry.getValue();
                }
		    }
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestMethod("GET");
            String line = "";

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if(resposta == null){
                    resposta = "";
                }
                resposta += line;
            }
            isr.close();
            br.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resposta;

	}

	public String makePostRequest(String urlStr,
                                  HashMap<String, String> params, Token token, ThreadLocal<OAuthService> service){

        String resposta = null;
        String parameters = "";
        try {
            urlStr += "?access_token="+token.getToken();

            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    parameters += "&"+entry.getKey()+"="+entry.getValue();
                    urlStr += "&"+entry.getKey()+"="+entry.getValue();
                }
            }
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection = (HttpURLConnection) url.openConnection();
            if (params != null) {
                Set<Map.Entry<String, String>> entrySet = params.entrySet();
                for (Map.Entry<String, String> entry : entrySet) {
                    connection.setRequestProperty(entry.getKey(),entry.getValue());
                }
            }

            connection.setDoOutput(true);
//            connection.setRequestProperty("Content-Type",
//                    "application/x-www-form-urlencoded");
            connection.setRequestMethod("POST");
            String line = "";

            InputStreamReader isr = new InputStreamReader(connection.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                if(resposta == null){
                    resposta = "";
                }
                resposta += line;
                System.out.println("###"+line);
            }
            isr.close();
            br.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return resposta;
	}
}
