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
import com.example.agendacontactos.modelo.Recordatorio;

import java.util.List;

public class RecordatorioAdapter extends ArrayAdapter<Recordatorio> {

        Context ctx;
        int layoutTemplate;
        List<Recordatorio> RecordatorioList;
        public TextView titulo;
        public TextView fecha;
        public TextView hora;

public RecordatorioAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Recordatorio> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.layoutTemplate = resource;
        this.RecordatorioList = objects;
        }
@NonNull
@Override
public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false);

        Recordatorio elementoActual = RecordatorioList.get(position);

        titulo = (TextView) v.findViewById(R.id.titulorecordatorioitem);
        fecha = (TextView) v.findViewById(R.id.fecharecordatorioitem);
        hora = (TextView) v.findViewById(R.id.horarecordatorioitem);


        titulo.setText(elementoActual.getTitulo());
        fecha.setText(elementoActual.getFecha());
        hora.setText(elementoActual.getHora());


        return v;
        }
        }
