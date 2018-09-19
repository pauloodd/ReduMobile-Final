package br.com.redu.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

public final class BitmapManager {
	private LruCache<String, Bitmap> bitmaps;
	private final int MAX_SIZE = 5 * 1024 * 1024;

	public BitmapManager() {
		bitmaps = new LruCache<String, Bitmap>(MAX_SIZE);
	}

	private boolean contains(String key) {
		boolean contains = true;

		if (get(key) == null) {
			contains = false;
		}

		return contains;
	}

	public void displayBitmapAsync(final String url, final ImageView imageView) {
		if (imageView != null && (url != null && !url.equals(""))) {
			String newUrl = url;
			if(!url.startsWith("http") && !url.contains("openredu")){
				newUrl = "https://openredu.ufpe.br/"+url;
			}
			if (contains(newUrl)) {
				imageView.setImageBitmap(get(newUrl));
			} else {
				Bitmap bitmap = fetchBitmap(newUrl);
				imageView.setImageBitmap(bitmap);
			}
		}
	}

	private Bitmap fetchBitmap(String url) {
		Bitmap bitmap = null;

		try {
			if(!url.startsWith("http") && !url.contains("openredu")){
				url = "https://openredu.ufpe.br/"+url;
			}

			InputStream inputStream = new URL(url).openStream();

			bitmap = BitmapFactory.decodeStream(inputStream);

			inputStream.close();

			put(url, bitmap);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return bitmap;
	}

	private Bitmap get(String key) {
		synchronized (bitmaps) {
			return bitmaps.get(key);
		}
	}

	private void put(String key, Bitmap value) {
		if(key != null && !key.equals("") && value != null){
			synchronized (bitmaps) {
				bitmaps.put(key, value);
			}
		}
	}
}
