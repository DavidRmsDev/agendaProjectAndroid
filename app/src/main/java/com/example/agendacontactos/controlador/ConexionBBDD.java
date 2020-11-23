package com.example.agendacontactos.controlador;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.agendacontactos.modelo.Contacto;
import com.example.agendacontactos.modelo.Notas;
import com.example.agendacontactos.modelo.Recordatorio;
import com.example.agendacontactos.modelo.Usuario;

import java.util.ArrayList;
import java.util.List;

public class ConexionBBDD extends SQLiteOpenHelper {
    private final String CREAR_TABLA_CONTACTO="CREATE TABLE contactos(id INTEGER PRIMARY KEY AUTOINCREMENT,user INTEGER NOT NULL" +
            ",nombre TEXT NOT NULL,apellidos TEXT,telefono INTEGER UNIQUE,direccion TEXT,email TEXT)";
    private final String CREAR_TABLA_NOTAS="CREATE TABLE nota(id INTEGER PRIMARY KEY AUTOINCREMENT,user INTEGER NOT NULL" +
            ",titulo TEXT NOT NULL,notas TEXT,fecha TEXT)";
    private final String CREAR_TABLA_USUARIO="CREATE TABLE usuario(user INTEGER PRIMARY KEY AUTOINCREMENT,nickname TEXT NOT NULL UNIQUE" +
            ",password TEXT NOT NULL)";
    private final String CREAR_TABLA_RECORDATORIO="CREATE TABLE recordatorio(id INTEGER PRIMARY KEY AUTOINCREMENT,user INTEGER NOT NULL" +
            ",titulo TEXT NOT NULL,fecha TEXT,hora TEXT,descripcion TEXT)";
    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "agendadb.db";

    public ConexionBBDD(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREAR_TABLA_CONTACTO);
        db.execSQL(CREAR_TABLA_NOTAS);
        db.execSQL(CREAR_TABLA_USUARIO);
        db.execSQL(CREAR_TABLA_RECORDATORIO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS contactos");
            db.execSQL("DROP TABLE IF EXISTS nota");
            db.execSQL("DROP TABLE IF EXISTS usuario");
            db.execSQL("DROP TABLE IF EXISTS recordatorio");
            onCreate(db);
    }

    public Long insertarContacto(Contacto c){
        ContentValues values = new ContentValues();
        values.put("id",c.getId());
        values.put("nombre",c.getNombre());
        values.put("apellidos",c.getApellidos());
        values.put("telefono",c.getTelefono());
        values.put("direccion",c.getDireccion());
        values.put("email",c.getEmail());
        values.put("user",c.getUser());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("contactos",null,values);
    }

    public Long insertarNota(Notas c){
        ContentValues values = new ContentValues();
        values.put("id",c.getId());
        values.put("titulo",c.getTitulo());
        values.put("notas",c.getNotas());
        values.put("fecha",c.getFecha());
        values.put("user",c.getUser());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("nota",null,values);
    }

    public Long insertarUsuario(Usuario c){
        ContentValues values = new ContentValues();
        values.put("user",c.getUser());
        values.put("nickname",c.getNickname());
        values.put("password",c.getPasssword());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("usuario",null,values);
    }

    public Long insertarRecordatorio(Recordatorio c){
        ContentValues values = new ContentValues();
        values.put("id",c.getId());
        values.put("titulo",c.getTitulo());
        values.put("hora",c.getHora());
        values.put("fecha",c.getFecha());
        values.put("user",c.getUser());
        values.put("descripcion",c.getDescripcion());
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.insert("recordatorio",null,values);
    }


    public boolean borrarContacto(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("contactos","id=?",new String[]{Integer.toString(id)})>0;
    }

    public boolean borrarNota(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("nota","id=?",new String[]{Integer.toString(id)})>0;
    }

    public boolean borrarRecordatorio(int id){
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.delete("recordatorio","id=?",new String[]{Integer.toString(id)})>0;
    }

    public boolean modificarContacto(String id,String nombre,String apellidos,String direccion,String telefono,String email){
        ContentValues values = new ContentValues();
        values.put("nombre",nombre);
        values.put("apellidos",apellidos);
        values.put("telefono",telefono);
        values.put("direccion",direccion);
        values.put("email",email);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("contactos",values,"id="+id,null)>0;
    }

    public boolean modificarNota(String id,String titulo,String notas){
        ContentValues values = new ContentValues();
        values.put("titulo",titulo);
        values.put("notas",notas);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("nota",values,"id="+id,null)>0;
    }

    public boolean modificarRecordatorio(String id,String titulo,String fecha,String hora,String descripcion){
        ContentValues values = new ContentValues();
        values.put("titulo",titulo);
        values.put("fecha",fecha);
        values.put("hora",hora);
        values.put("descripcion",descripcion);
        SQLiteDatabase sqLiteDatabase = getWritableDatabase();
        return sqLiteDatabase.update("recordatorio",values,"id="+id,null)>0;
    }

    public List<Contacto> devolverContactos(int user){
        List<Contacto> lista = new ArrayList<Contacto>();
        //String [] columns=new String[]{"id","nombre","apellidos","telefono","direccion","email"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("SELECT id,nombre,apellidos,telefono,direccion,email FROM contactos where user="+user,null);
        return cursor(c);
    }
    public List<Notas> devolverNotas(int user){
        List<Notas> lista = new ArrayList<Notas>();
        String [] columns=new String[]{"id","notas","fecha","titulo"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("SELECT id,notas,fecha,titulo FROM nota where user="+user,null);
        return cursorNota(c);
    }

    public List<Recordatorio> devolverRecordatorios(int user){
        List<Recordatorio> lista = new ArrayList<Recordatorio>();
        String [] columns=new String[]{"id","hora","fecha","titulo","descripcion"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.rawQuery("SELECT id,hora,fecha,titulo,descripcion FROM recordatorio where user="+user,null);
        return cursorRecordatorio(c);
    }

    public Contacto devolverContacto(int id){
        List<Contacto> lista = new ArrayList<Contacto>();
        String [] columns=new String[]{"id","nombre","apellidos","telefono","direccion","email"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.query("contactos",columns,"id="+id,null,null,null,null);
        return cursorAContacto(c);
    }
    public Notas devolverNota(int id){
        List<Notas> lista = new ArrayList<Notas>();
        String [] columns=new String[]{"id","titulo","notas","fecha"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.query("nota",columns,"id="+id,null,null,null,null);
        return cursorANota(c);
    }

    public Recordatorio devolverRecordatorio(int id){
        List<Recordatorio> lista = new ArrayList<Recordatorio>();
        String [] columns=new String[]{"id","titulo","fecha","hora","descripcion"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
        Cursor c=sqLiteDatabase.query("recordatorio",columns,"id="+id,null,null,null,null);
        return cursorARecordatorio(c);
    }

    public String devolverUsuario(String nickname,String password){
        String [] columns=new String[]{"user","nickname","password"};
        SQLiteDatabase sqLiteDatabase = getReadableDatabase();
       // Cursor c=sqLiteDatabase.query("usuario",columns,"nickname="+nickname+"and password="+password,null,null,null,null);
        Cursor c=sqLiteDatabase.rawQuery("SELECT user from usuario where nickname='"+nickname+"' AND password='"+password+"'",null);
        return cursorAUsuario(c);
    }

    public List<Contacto> cursor(Cursor c){
        List<Contacto> lista = new ArrayList<Contacto>();
        Contacto con;
        while(c.moveToNext()){
            con=new Contacto();
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setNombre(c.getString(c.getColumnIndex("nombre")));
            con.setApellidos(c.getString(c.getColumnIndex("apellidos")));
            con.setTelefono(c.getString(c.getColumnIndex("telefono")));
            con.setDireccion(c.getString(c.getColumnIndex("direccion")));
            con.setEmail(c.getString(c.getColumnIndex("email")));
            lista.add(con);
        }
        return  lista;
    }

    public List<Notas> cursorNota(Cursor c){
        List<Notas> lista = new ArrayList<Notas>();
        Notas con;
        while(c.moveToNext()){
            con=new Notas();
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setNotas(c.getString(c.getColumnIndex("notas")));
            con.setTitulo(c.getString(c.getColumnIndex("titulo")));
            con.setFecha(c.getString(c.getColumnIndex("fecha")));
            lista.add(con);
        }
        return  lista;
    }

    public List<Recordatorio> cursorRecordatorio(Cursor c){
        List<Recordatorio> lista = new ArrayList<Recordatorio>();
        Recordatorio con;
        while(c.moveToNext()){
            con=new Recordatorio();
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setTitulo(c.getString(c.getColumnIndex("titulo")));
            con.setFecha(c.getString(c.getColumnIndex("fecha")));
            con.setHora(c.getString(c.getColumnIndex("hora")));
            con.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
            lista.add(con);
        }
        return  lista;
    }

    public Contacto cursorAContacto(Cursor c){
        Contacto con = new Contacto();
        while(c.moveToNext()){
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setNombre(c.getString(c.getColumnIndex("nombre")));
            con.setApellidos(c.getString(c.getColumnIndex("apellidos")));
            con.setTelefono(c.getString(c.getColumnIndex("telefono")));
            con.setDireccion(c.getString(c.getColumnIndex("direccion")));
            con.setEmail(c.getString(c.getColumnIndex("email")));
        }
        return  con;
    }

    public Notas cursorANota(Cursor c){
        Notas con = new Notas();
        while(c.moveToNext()){
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setTitulo(c.getString(c.getColumnIndex("titulo")));
            con.setNotas(c.getString(c.getColumnIndex("notas")));
            con.setFecha(c.getString(c.getColumnIndex("fecha")));
        }
        return  con;
    }

    public String cursorAUsuario(Cursor c){
        Usuario con = new Usuario();
        while(c.moveToNext()){
            con.setUser((long)c.getInt(c.getColumnIndex("user")));
        }
        return  String.valueOf(con.getUser());
    }
    public Recordatorio cursorARecordatorio(Cursor c){

        Recordatorio con = null;
        while(c.moveToNext()){
            con=new Recordatorio();
            con.setId(c.getLong(c.getColumnIndex("id")));
            con.setTitulo(c.getString(c.getColumnIndex("titulo")));
            con.setFecha(c.getString(c.getColumnIndex("fecha")));
            con.setHora(c.getString(c.getColumnIndex("hora")));
            con.setDescripcion(c.getString(c.getColumnIndex("descripcion")));
        }
        return  con;
    }
}
