package com.example.agendacontactos.vista;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.agendacontactos.R;
import com.example.agendacontactos.api.service.RecordatorioService;
import com.example.agendacontactos.controlador.ConexionRetrofit;
import com.example.agendacontactos.modelo.DatePickerFragment;
import com.example.agendacontactos.modelo.Recordatorio;

import java.io.IOException;
import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModificarborrarRecordatorioActivity extends AppCompatActivity implements View.OnClickListener {

    private int id;
    private EditText titulo;
    private EditText fecha;
    private EditText hora;
    private EditText descripcion;
    private Button borrar;
    private Button modificar;
    private ImageView calendario,reloj;
    private Recordatorio recordatorio;
    private ConexionRetrofit retrofit;
    private RecordatorioService recordatorioService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modificarborrar_recordatorio);
        retrofit = new ConexionRetrofit();
        recordatorioService = retrofit.getRetrofit().create(RecordatorioService.class);

        id= Integer.parseInt(getIntent().getStringExtra("id"));
        devolverRecordatorio();
    }

    public void devolverRecordatorio(){
        Call<Recordatorio> call = recordatorioService.seleccionaUnRecordatorio(id);

        call.enqueue(new Callback<Recordatorio>() {
            @Override
            public void onResponse(Call<Recordatorio> call, Response<Recordatorio> response) {
                if (response.isSuccessful()){
                    recordatorio = new Recordatorio(response.body());

                    titulo = (EditText) findViewById(R.id.Rectituloid);
                    fecha = (EditText) findViewById(R.id.Recfechaid);
                    hora = (EditText) findViewById(R.id.Rechoraid);
                    descripcion = (EditText) findViewById(R.id.Recdescripcionid);
                    borrar = (Button) findViewById(R.id.btn_borrRec);
                    modificar = (Button) findViewById(R.id.btn_modRec);
                    calendario = (ImageView) findViewById(R.id.calendariomod);
                    reloj = (ImageView) findViewById(R.id.horamod);
                    cargarRecordatorio(recordatorio);
                    borrar.setOnClickListener(ModificarborrarRecordatorioActivity.this);
                    modificar.setOnClickListener(ModificarborrarRecordatorioActivity.this);
                    reloj.setOnClickListener(ModificarborrarRecordatorioActivity.this);
                    calendario.setOnClickListener(ModificarborrarRecordatorioActivity.this);
                }
                else{
                    try {
                        Toast.makeText(ModificarborrarRecordatorioActivity.this,response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<Recordatorio> call, Throwable t) {
                Toast.makeText(ModificarborrarRecordatorioActivity.this,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.btn_borrRec){
            crearDialogo();
        }
        else if(v.getId()==R.id.btn_modRec){
            if(titulo.getText().toString().isEmpty()){
                Toast.makeText(this,"Debe rellenar campo de título",Toast.LENGTH_LONG).show();
            }
            else {
                if (fecha.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Debe rellenar campo de fecha", Toast.LENGTH_LONG).show();
                } else if (hora.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Debe rellenar campo de hora", Toast.LENGTH_LONG).show();
                } else
                    modificar();
            }
        }
        else if(v.getId()==R.id.horamod){
            obtenerHora();
        }
        else if(v.getId()==R.id.calendariomod){
            showDatePickerDialog();
        }
    }

    public void modificar(){
        Call<Boolean> call = recordatorioService.modificarRecordatorio(new Integer(id),titulo.getText().toString(),fecha.getText().toString(),hora.getText().toString(),descripcion.getText().toString());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarRecordatorioActivity.this, "Recordatorio modificado", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarRecordatorioActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarRecordatorioActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarRecordatorioActivity.this, "No se ha podido modificar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void cargarRecordatorio(Recordatorio c){
        titulo.setText(c.getTitulo());
        fecha.setText(c.getFecha());
        hora.setText(c.getHora());
        descripcion.setText(c.getDescripcion());
    }

    public void cargarIntent(Class ventana){
        Intent intent = new Intent(this,ventana);
        startActivity(intent);
    }

    public void crearDialogo() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);

        dialog.setTitle("Borrar");
        dialog.setMessage("¿Desea borrar el recordatorio");
        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                borrar();
            }
        });
        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        dialog.show();

    }

    public void borrar(){
        Call<Boolean> call = recordatorioService.borrarRecordatorio(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarRecordatorioActivity.this, "Recordatorio eliminado", Toast.LENGTH_SHORT).show();
                        cargarIntent(VisualizarRecordatorioActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarRecordatorioActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {}
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarRecordatorioActivity.this, "Error de conexion, no se ha podido borrar el recordatorio", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onBackPressed() {
        cargarIntent(VisualizarRecordatorioActivity.class);
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


}
