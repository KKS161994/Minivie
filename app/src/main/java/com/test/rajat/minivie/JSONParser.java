package com.test.rajat.minivie;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * Created by Rajat on 7/10/2015.
 */
public class JSONParser {

private String response=null;
    public String getJSONFromUrl(String url){
        try {
            HttpClient client = new DefaultHttpClient();
            HttpGet get = new HttpGet(url);
            ResponseHandler<String> handler=new BasicResponseHandler();
            response = client.execute(get,handler);
        }
        catch(UnsupportedEncodingException e)
        {
            e.printStackTrace();
        }
        catch(ClientProtocolException e)
        {
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        catch(Exception e)
        {

        }
        return response;
    }
}
