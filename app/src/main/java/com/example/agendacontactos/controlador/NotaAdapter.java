package com.example.agendacontactos.controlador;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.agendacontactos.R;
import com.example.agendacontactos.modelo.Notas;

import java.util.List;

public class NotaAdapter extends ArrayAdapter<Notas> {
    Context ctx;
    int layoutTemplate;
    List<Notas> NotaList;
    public TextView fecha;
    public TextView titulo;



    public NotaAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Notas> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.layoutTemplate = resource;
        this.NotaList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false);

        Notas elementoActual = NotaList.get(position);

        //notas = (TextView) v.findViewById(R.id.textviewapellidos);
        fecha = (TextView) v.findViewById(R.id.FechaItemId);
        titulo = (TextView) v.findViewById(R.id.tituloItemId);


        fecha.setText(elementoActual.getFecha());
        titulo.setText(elementoActual.getTitulo());

        return v;
    }
}

