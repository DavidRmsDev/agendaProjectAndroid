package com.example.agendacontactos.vista;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.controlador.ConexionBBDD;
import com.example.agendacontactos.modelo.DatePickerFragment;
import com.example.agendacontactos.modelo.Recordatorio;
import com.example.agendacontactos.vista.visualizarContactoActivity;

import java.util.Calendar;

public class AniadirCalendarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText titulo,descripcion;
    private EditText fecha,hora;
    private ImageView calendario,reloj;
    private Button aniadir;
    private int user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aniadircalendario);
        titulo = (EditText) findViewById(R.id.tituloedittextid);
        fecha = (EditText) findViewById(R.id.fechatextoid);
        hora = (EditText) findViewById(R.id.horatextoid);
        descripcion = (EditText) findViewById(R.id.descripcionrecordatorioid);
        calendario = (ImageView) findViewById(R.id.calendario);
        reloj = (ImageView) findViewById(R.id.hora);
        aniadir = (Button) findViewById(R.id.btnaniadirrecordatorio);
        calendario.setOnClickListener(this);
        reloj.setOnClickListener(this);
        aniadir.setOnClickListener(this);
        cargarPreferencias();
    }

    public void cargarPreferencias(){
        SharedPreferences preferences = getSharedPreferences("usuario", Context.MODE_PRIVATE);
        user = preferences.getInt("user",0);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.calendario:
                  showDatePickerDialog();
                 break;

            case R.id.hora:
                  obtenerHora();
                break;
            case R.id.btnaniadirrecordatorio:
                  comprobarCamposRecordatorio();
                break;
        }
    }


    private void showDatePickerDialog() {
        DatePickerFragment newFragment = DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // +1 because January is zero
                final String selectedDate = day + " / " + (month+1) + " / " + year;
                fecha.setText(selectedDate);
            }
        });

        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void obtenerHora(){
        Calendar c = Calendar.getInstance();

        //Variables para obtener la hora hora
        final int horas = c.get(Calendar.HOUR_OF_DAY);
        final int minutos = c.get(Calendar.MINUTE);
        TimePickerDialog recogerHora = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                //Formateo el hora obtenido: antepone el 0 si son menores de 10
                String horaFormateada =  (hourOfDay < 10)? String.valueOf("0" + hourOfDay) : String.valueOf(hourOfDay);
                //Formateo el minuto obtenido: antepone el 0 si son menores de 10
                String minutoFormateado = (minute < 10)? String.valueOf("0" + minute):String.valueOf(minute);
                //Obtengo el valor a.m. o p.m., dependiendo de la selección del usuario
                String AM_PM;
                if(hourOfDay < 12) {
                    AM_PM = "a.m.";
                } else {
                    AM_PM = "p.m.";
                }
                //Muestro la hora con el formato deseado
                hora.setText(horaFormateada + ":" + minutoFormateado + " " + AM_PM);
            }
            //Estos valores deben ir en ese orden
            //Al colocar en false se muestra en formato 12 horas y true en formato 24 horas
            //Pero el sistema devuelve la hora en formato 24 horas
        }, horas, minutos, false);

        recogerHora.show();
    }

    public void comprobarCamposRecordatorio(){
        if(titulo.getText().toString().isEmpty()){
            Toast.makeText(this,"Debe añadir un título",Toast.LENGTH_LONG).show();
        }
        else if(fecha.getText().toString().isEmpty()){
            Toast.makeText(this,"Debe añadir una fecha",Toast.LENGTH_LONG).show();
        }
        else if(hora.getText().toString().isEmpty()){
            Toast.makeText(this,"Debe añadir una hora",Toast.LENGTH_LONG).show();
        }
        else {
            if(new ConexionBBDD(this).insertarRecordatorio(aniadirRecordatorio())!=-1) {
                Toast.makeText(this, "Recordatorio insertado con éxito", Toast.LENGTH_LONG).show();
                cargarIntent(VisualizarRecordatorioActivity.class);
            }
            else
                Toast.makeText(this,"No se ha podido agregar el recordatorio.",Toast.LENGTH_LONG).show();
        }
    }

    public Recordatorio aniadirRecordatorio(){
        Recordatorio r = new Recordatorio();
        r.setTitulo(titulo.getText().toString());
        r.setFecha(fecha.getText().toString());
        r.setHora(hora.getText().toString());
        r.setUser(user);
        if(!descripcion.getText().toString().isEmpty()){
            r.setDescripcion(descripcion.getText().toString());
        }
        return r;
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarRecordatorioActivity.class);
    }

}
