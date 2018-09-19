package br.com.redu.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
    ImageView bmImage;

    public DownloadImageTask(ImageView bmImage) {
        this.bmImage = bmImage;
    }

    protected Bitmap doInBackground(String... urls) {
        String urldisplay = urls[0];
        Bitmap mIcon11 = null;
        try {

            String urlTratada = tratarUrlDisplayThumb(urldisplay);
            Bitmap bitmap = procuraImagemCache(urlTratada);

            //Verifica se a imagem ja existe na cache da app
            if(bitmap == null){
                if(!urldisplay.startsWith("http") && !urldisplay.contains("openredu")){
                    urldisplay = "https://openredu.ufpe.br/"+urldisplay;
                }

                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);

                salvarFotoFile(urlTratada, mIcon11);
            }else{
                mIcon11 = bitmap;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return mIcon11;
    }

    private String tratarUrlDisplayThumb(String urldisplay) {
        String retorno = "";
        retorno = urldisplay.replace("/images/users/avatars/" ,"");
        retorno = retorno.replaceAll("/","_");

        //Tratando caso onde o thumb nao termina com a extenasao da midia. (ex. /images/users/avatars/9444/thumb_24/foto_perfil.jpg?1535398437)
        if(retorno.contains("?")){
            int index = retorno.indexOf("?");
            retorno = retorno.substring(0, index);
        }
        return retorno;
    }

    private void salvarFotoFile(String picName,  Bitmap bitmap) {

        String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
        File myDir = new File(root + "/req_images");
        myDir.mkdirs();
        File file = new File(myDir, picName);
        try {
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            FileOutputStream out = new FileOutputStream(file);
            if(picName.endsWith(".png")){
                bitmap.compress(Bitmap.CompressFormat.PNG, 90, out);
            }else{
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            }
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Bitmap procuraImagemCache(String urlImg){
        Bitmap bitmap = null;
        try {
            String root = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            bitmap = BitmapFactory.decodeFile(root+"/req_images/"+urlImg);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    protected void onPostExecute(Bitmap result) {
        bmImage.setImageBitmap(result);
    }

}
