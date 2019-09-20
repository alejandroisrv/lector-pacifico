package com.example.pacificoreader;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;


public class DownloadImage extends AsyncTask<String[], Integer, Void> {
    ProgressDialog pDialog;
    private String TAG = "DownloadImage";
    private Activity activity;
    private String Pass ="873147cbn9x5'2 79'79314",salt ="t784";
    String imageUrl = "https://actualidadempresarial.pe/images/publicidad-portada-ae.png";

    public DownloadImage( Activity activity) {

        this.activity = activity;
        if(checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE,200)){
            this.execute(new String[]{"http://172.16.2.30:8088/uno.epub","http://172.16.2.30:8088/dos.epub","http://172.16.2.30:8088/prueba.epub"});
        }
    }

    public boolean checkPermission(String permission, int requestCode) {
        // Checking if permission is not grante
        if (ContextCompat.checkSelfPermission(activity,permission) == PackageManager.PERMISSION_DENIED) {
            ActivityCompat.requestPermissions(activity, new String[]{permission}, requestCode);
            return false;

        } else {
            return true;
        }
    }


    @Override
    protected Void doInBackground(String[]... sUrl) {
        //return downloadImageBitmap(sUrl[0]);
        return downloadEpub(sUrl[0]);
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();
        pDialog = new ProgressDialog(activity);
        pDialog.setMessage(activity.getString(R.string.DescargandoImagen));
        pDialog.setCancelable(false);
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
        return bitmap;
    }

    public Void downloadEpub(String[] sUrl){
        InputStream inputStream = null;

        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Pacifico/Libros/content";
        File dir = new File(file_path);

        if (!dir.exists()){
            dir.mkdirs();
        }

        try{
            for(int i = 0; i < sUrl.length; i++){
                File new_file = new File(dir,"libro"+i+".epub");
                OutputStream outputStream = new FileOutputStream(new_file);
                inputStream = new URL(sUrl[i]).openStream();
                byte[] b = new byte[2048];
                int longitud;
                while ((longitud = inputStream.read(b)) != -1) {
                    outputStream.write(b, 0, longitud);
                }
                outputStream.close();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        pDialog.dismiss();

        return null;
    }


    protected void onPostExecute(Bitmap result) {
        String date = "IMG-LIBRO" + (DateFormat.format("yyyyMMdd_hhmmss", new java.util.Date()).toString());
        //saveImageExtPath(result,date + ".jpg","/Pacifico/Libros/covers");
    }

    public void saveImageExtPath(Bitmap bipmap, String nameImage, String path){

        FileOutputStream fileOutputStream = null;
        String file_path = Environment.getExternalStorageDirectory().getAbsolutePath() + path;
        File dir = new File(file_path);

        if (!dir.exists()){
            dir.mkdirs();
        }

        String nameFile = dir+"/"+nameImage;

        File new_file = new File(dir,nameImage);

        try {
            fileOutputStream = new FileOutputStream(new_file);
            bipmap.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // añade la imagen a la galería de fotos
            //MediaScannerConnection.scanFile(activity, new String[] { new_file.getPath() }, new String[] { "image/jpeg" }, null);
            Toast.makeText(activity,activity.getString(R.string.mensajeImagenGuardada) + " " + path + "/" + nameImage + ")",Toast.LENGTH_LONG).show();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void ecryptFile (String path) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException {

        FileInputStream fis = new FileInputStream(path);
        FileOutputStream fos = new FileOutputStream(path.concat(".crypt"));
        byte[] key = (salt + Pass).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, sks);
        CipherOutputStream cos = new CipherOutputStream(fos, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = fis.read(d)) != -1) {
            cos.write(d, 0, b);
        }
        cos.flush();
        cos.close();
        fis.close();
    }

    public void decryptFile(String path, String outPath) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException{
        FileInputStream fis = new FileInputStream(path.concat(".crypt"));
        FileOutputStream fos = new FileOutputStream(path);
        byte[] key = (salt + Pass).getBytes("UTF-8");
        MessageDigest sha = MessageDigest.getInstance("SHA-1");
        key = sha.digest(key);
        key = Arrays.copyOf(key,16);
        SecretKeySpec sks = new SecretKeySpec(key, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, sks);
        CipherInputStream cis = new CipherInputStream(fis, cipher);
        int b;
        byte[] d = new byte[8];
        while((b = cis.read(d)) != -1) {
            fos.write(d, 0, b);
        }
        fos.flush();
        fos.close();
        cis.close();
    }
}
