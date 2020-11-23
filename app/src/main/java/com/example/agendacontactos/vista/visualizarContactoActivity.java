package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.controlador.ContactoAdapter;
import com.example.agendacontactos.modelo.Contacto;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class visualizarContactoActivity extends AppCompatActivity implements View.OnClickListener {
    ListView lista;
    private List<Contacto> items;
    private FloatingActionButton fab;
    private int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualizar_contacto);
        inicializar();
    }
    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }
    public void inicializar(){
        cargarPreferencias();
        items=new ConexionBBDD(this).devolverContactos(user);
        lista = (ListView)findViewById(R.id.listContacto);
        fab = (FloatingActionButton) findViewById(R.id.fabid);
        fab.setOnClickListener(this);
        final ContactoAdapter adaptador= new ContactoAdapter(this,R.layout.contacto_item,items);

    lista.setAdapter(adaptador);

    lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if(items.get(position).getId()!=null){
               marcador(String.valueOf(items.get(position).getTelefono()));
            }

        }
    });
        lista.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                if(items.get(position).getId()!=null){
                    cargarIntent(modificarborrarContactoActivity.class,String.valueOf(items.get(position).getId()));
                }

                return false;
            }
        });
    }

    public void cargarIntent(Class ventana,String id){
        Intent intent = new Intent(this,ventana);
        intent.putExtra("id",id);
        startActivity(intent);
    }

    public void marcador(String numero){
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:"+numero));
        if(intent.resolveActivity(getPackageManager())!=null)
            startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.fabid){
            Intent intent = new Intent(this, aniadirContactoActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
        cargarIntent(MainActivity.class,null);
    }

}
