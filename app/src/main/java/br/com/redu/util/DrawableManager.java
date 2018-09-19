package br.com.redu.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.ImageView;

public class DrawableManager {
    private final Map<String, Drawable> drawableMap;

    public DrawableManager() {
        drawableMap = new HashMap<String, Drawable>();
    }

    public Drawable fetchDrawable(String urlString) {
        if (drawableMap.containsKey(urlString)) {
            return drawableMap.get(urlString);
        }

        Log.d(this.getClass().getSimpleName(), "image url:" + urlString);
        try {
            InputStream is = fetch(urlString);
            Drawable drawable = Drawable.createFromStream(is, "src");


            if (drawable != null) {
                drawableMap.put(urlString, drawable);
                Log.d(this.getClass().getSimpleName(), "got a thumbnail drawable: " + drawable.getBounds() + ", "
                        + drawable.getIntrinsicHeight() + "," + drawable.getIntrinsicWidth() + ", "
                        + drawable.getMinimumHeight() + "," + drawable.getMinimumWidth());
            } else {
              Log.w(this.getClass().getSimpleName(), "could not get thumbnail");
            }

            return drawable;
        } catch (Exception e) {
            Log.e(this.getClass().getSimpleName(), "fetchDrawable failed", e);
            return null;
        }
    }

    public void fetchDrawableOnThread(final String urlString, final ImageView imageView) {
        if (drawableMap.containsKey(urlString)) {
            imageView.setImageDrawable(drawableMap.get(urlString));
        }else{

	        final Handler handler = new Handler() {
	            @Override
	            public void handleMessage(Message message) {
	            	Drawable img = (Drawable) message.obj;
	            	if(img != null){
	            		imageView.setImageDrawable(img);
	            	}
	            }
	        };
	
	        Thread thread = new Thread() {
	            @Override
	            public void run() {
	            	 try{
	            		 //Colocar carregando na img
//		            	imageView.setImageDrawable(Aplicacao.contexto.getResources().getDrawable(R.drawable.loading));
		                
		            	Drawable drawable = fetchDrawable(urlString);
		                Message message = handler.obtainMessage(1, drawable);
		                handler.sendMessage(message);
	            	 }catch (Exception e) {
	            		 
					}
	            }
	        };
	        thread.start();
        }
    }

    private InputStream fetch(String urlString) {
        InputStream inputStream = null;
        try{
            URL url = new URL(urlString);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestProperty("User-Agent", "");
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.connect();

            inputStream = connection.getInputStream();
        }
        catch (IOException e) {
            // Writing exception to log
            e.printStackTrace();
        }
        return inputStream;
    }

}