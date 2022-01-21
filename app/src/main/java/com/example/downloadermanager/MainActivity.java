package com.example.downloadermanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.downloadermanager.Adapter.RecyclerAdapterFicheros;
import com.example.downloadermanager.Model.Ficheros;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewFicheros;
    private RecyclerAdapterFicheros adapterFicheros;
    JSONObject objectJson;


    private ImageView imgFind;
    private RequestQueue requestQue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewFicheros=(RecyclerView) findViewById(R.id.reclyclerFicheros);
        recyclerViewFicheros.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewFicheros.setItemAnimator(new DefaultItemAnimator());

        ArrayList<String> permisos = new ArrayList<String>();
        permisos.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        permisos.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        searchCover();

        getPermission(permisos);

    }
    private void searchCover(){

        String url="https://my-json-server.typicode.com/LisLeoMeraC/Servidor/files/";
        JsonObjectRequest requestJson=new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        JSONArray jsonArray= null;
                        try {
                            jsonArray = response.getJSONArray("ficheros");
                            showCoverText(jsonArray);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(),"Error al conectarse:"+error.getMessage(),Toast.LENGTH_LONG).show();
                    }
                }

        );
        requestQue= Volley.newRequestQueue(this);
        requestQue.add(requestJson);
    };

    private void showCoverText(JSONArray jArray){
        List<Ficheros> ficheros = new ArrayList<>();
        for(int i=0;i<jArray.length();i++){
            try{
                objectJson=new JSONObject(jArray.get(i).toString());

                ficheros.add(new Ficheros(objectJson.getString("id"),objectJson.getString("Name"),objectJson.getString("description"),objectJson.getString("Date"),objectJson.getString("Author"),objectJson.getString("URL")));

            }
            catch (JSONException e){
                Toast.makeText(this,"Error al cargar lista: "+e.getMessage(),Toast.LENGTH_LONG).show();
            }
        }
        adapterFicheros=new RecyclerAdapterFicheros(MainActivity.this, ficheros);

        int id = R.anim.layout_animation_down_up;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getApplicationContext(),
                id);
        recyclerViewFicheros.setLayoutAnimation(animation);

        recyclerViewFicheros.setAdapter(adapterFicheros);
    };

    //Método para descargar archivos

    public void Downloadpdf(View view){

        DownloadManager.Request request = null;
        try {
            request = new DownloadManager.Request(Uri.parse(objectJson.getString("URL")));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        request.setDescription("PDF");
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE | DownloadManager.Request.NETWORK_WIFI);
        request.setTitle("Descargando Archivo");
        request.setVisibleInDownloadsUi(true);
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        //}
        request.setDestinationInExternalFilesDir(this.getApplicationContext(), Environment.DIRECTORY_DOWNLOADS, "file.pdf");
        DownloadManager manager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        try {
            manager.enqueue(request);
        } catch (Exception e) {
            Toast.makeText(this.getApplicationContext(),"Error: "  + e.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    //Permisos

    private void getPermission(ArrayList<String> permisosSolicitados){

        ArrayList<String> listPermisosNOAprob = getPermisosNoAprobados(permisosSolicitados);
        if (listPermisosNOAprob.size()>0)
            if (Build.VERSION.SDK_INT >= 23)
                requestPermissions(listPermisosNOAprob.toArray(new String[listPermisosNOAprob.size()]), 1);

    }
    public ArrayList<String> getPermisosNoAprobados(ArrayList<String>  listaPermisos) {
        ArrayList<String> list = new ArrayList<String>();
        for(String permiso: listaPermisos) {
            if (Build.VERSION.SDK_INT >= 23)
                if(checkSelfPermission(permiso) != PackageManager.PERMISSION_GRANTED)
                    list.add(permiso);

        }
        return list;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        String s="";
        if(requestCode==1)    {
            for(int i =0; i<permissions.length;i++) {
                if (grantResults[i] == PackageManager.PERMISSION_GRANTED)
                    s=s + "OK " + permissions[i] + "\n";
                else
                    s=s + "NO  " + permissions[i] + "\n";
            }
            Toast.makeText(this.getApplicationContext(), s, Toast.LENGTH_LONG).show();
        }
    }


}