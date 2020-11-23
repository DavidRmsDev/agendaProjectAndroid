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
import com.example.agendacontactos.modelo.Contacto;

import java.util.List;

public class ContactoAdapter extends ArrayAdapter<Contacto> {

    Context ctx;
    int layoutTemplate;
    List<Contacto> ContactoList;
    public TextView nombre;
    public TextView apellidos;
    public TextView telefono;
    public TextView email;
    public TextView direccion;


    public ContactoAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Contacto> objects) {
        super(context, resource, objects);
        this.ctx = context;
        this.layoutTemplate = resource;
        this.ContactoList = objects;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = LayoutInflater.from(ctx).inflate(layoutTemplate, parent, false);

        Contacto elementoActual = ContactoList.get(position);

        apellidos = (TextView) v.findViewById(R.id.textviewapellidos);
        nombre = (TextView) v.findViewById(R.id.textviewnombre);
        telefono = (TextView) v.findViewById(R.id.textviewtelefono);
        email = (TextView) v.findViewById(R.id.textviewemail);
        direccion = (TextView) v.findViewById(R.id.textviewdireccion);


        nombre.setText(elementoActual.getNombre());
        apellidos.setText(elementoActual.getApellidos());
        telefono.setText(elementoActual.getTelefono());
        email.setText(elementoActual.getEmail());
        direccion.setText(elementoActual.getDireccion());

        return v;
    }
}
