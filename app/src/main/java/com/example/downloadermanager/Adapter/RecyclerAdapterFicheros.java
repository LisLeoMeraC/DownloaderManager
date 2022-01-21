package com.example.downloadermanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bumptech.glide.Glide;
import com.example.downloadermanager.MainActivity;
import com.example.downloadermanager.Model.Ficheros;
import com.example.downloadermanager.R;

import java.util.List;

public class RecyclerAdapterFicheros extends RecyclerView.Adapter<RecyclerAdapterFicheros.FicherosViewHolder> {
    Context ctx;
    public List<Ficheros> ficherosList;
    private RequestQueue request;
    MainActivity mainActivity;
    String link;
    public RecyclerAdapterFicheros(View.OnClickListener onClickListener, List<Ficheros> ficheros) {
        this.ficherosList= ficheros;
    }
    public static class FicherosViewHolder extends RecyclerView.ViewHolder{

        private TextView txtid,txtname, txtdes, txtautor,txtdate;


        public FicherosViewHolder(View itemView) {
            super(itemView);

            txtid= (TextView)itemView.findViewById(R.id.txtid);
            txtname= (TextView)itemView.findViewById(R.id.txtname);
            txtdes= (TextView)itemView.findViewById(R.id.txtdescripcion);
            txtdate= (TextView)itemView.findViewById(R.id.txtdate);
            txtautor= (TextView)itemView.findViewById(R.id.txtautor);


        }

    }


    public RecyclerAdapterFicheros(Context mCtx, List<Ficheros> FicherosLista) {
        this.ctx = mCtx;
        this.ficherosList = FicherosLista;
    }




    @Override
    public FicherosViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_main2,parent,false);
        FicherosViewHolder reViewHolder = new FicherosViewHolder(view);
        return reViewHolder;
    }

    //Modificación del contenido para cada cardView

    @Override
    public void onBindViewHolder(FicherosViewHolder holder, int position) {

        holder.txtid.setText(ficherosList.get(position).getId());
        holder.txtname.setText(ficherosList.get(position).getName());
        holder.txtdes.setText(ficherosList.get(position).getDescripcion());
        holder.txtautor.setText(ficherosList.get(position).getAutor());
        holder.txtdate.setText(ficherosList.get(position).getDate());


    }



    @Override
    public int getItemCount() {
        return ficherosList.size();
    }

}


