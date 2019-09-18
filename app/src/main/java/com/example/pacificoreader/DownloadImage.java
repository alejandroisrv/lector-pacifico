package com.example.pacificoreader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;



public class DownloadImage extends AsyncTask<String[], Integer, Bitmap> {
    ProgressDialog pDialog;
    private String TAG = "DownloadImage";
    private Activity activity;

    public DownloadImage( Activity activity) {
        this.activity = activity;
        if(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,200)){
            this.execute(new String[]{"https://institutopacifico.pe/assets/uploads/biblioteca/Libros/9071d77e-3672-4513-a530-05722a897091_original.png","https://actualidadempresarial.pe/images/publicidad-portada-ae.png","https://institutopacifico.pe/assets/uploads/biblioteca/Libros/cb802f5e-467c-438c-b057-61c198fc9017_original.png"});
        }
    }

    public boolean checkPermission(String permission, int requestCode) {
        // Checking if permission is not grante
        if (ContextCompat.checkSelfPermission(activity,permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            return false;

        } else {
            Toast.makeText(activity, "Permission already granted", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    @Override
    protected Bitmap doInBackground(String[]... urls) {
        return downloadImageBitmap(urls[0]);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(activity.getString(R.string.DescargandoImagen));
        pDialog.setCancelable(true);
        pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pDialog.show();
    }

    private Bitmap downloadImageBitmap(String[] sUrl) {
        Bitmap bitmap = null;
        try {
            InputStream inputStream = new URL(sUrl[0]).openStream();   // Download Image from URL
            bitmap = BitmapFactory.decodeStream(inputStream);       // Decode Bitmap
            inputStream.close();
        } catch (Exception e) {
            Log.d(TAG, "Exception 1, Something went wrong!");
            e.printStackTrace();
        }
        pDialog.dismiss();
        // Aquí se puede añadir el compartir imagen
        // Aquí se puede añadir el establecer el fondo de pantalla
        return bitmap;
    }


    protected void onPostExecute(Bitmap result) {
        String date = (DateFormat.format("yyyyMMdd_hhmmss", new java.util.Date()).toString());
        saveImageExtPath(result,date + ".jpg","/Pacifico/Libros/covers");
    }


    public void saveImageExtPath(Bitmap bipmap, String nameImage, String path){
        FileOutputStream fileOutputStream = null;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
        File dir = new File(file_path);
        if (!dir.exists()){
            dir.mkdirs();
        }
        File new_file = new File(dir,nameImage);
        try {
            fileOutputStream = new FileOutputStream(new_file);
            bipmap.compress(Bitmap.CompressFormat.JPEG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            // añade la imagen a la galería de fotos
            MediaScannerConnection.scanFile(activity, new String[] { new_file.getPath() }, new String[] { "image/jpeg" }, null);
            Toast.makeText(activity,activity.getString(R.string.mensajeImagenGuardada) + " " + path + "/" + nameImage + ")",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
