package com.example.agendacontactos.controlador;

import com.example.agendacontactos.api.service.ContactoService;

public class ContactoController {

    private ConexionRetrofit retrofit;
    private ContactoService contactoService;

    public ContactoController() {
        retrofit = new ConexionRetrofit();
        contactoService = retrofit.getRetrofit().create(ContactoService.class);
    }

    /*public void llenarLista(int user, VisualizarContactoActivity VisualizarContactoActivity){

        Call<List<Contacto>> call = contactoService.listarContactos(user);
        call.enqueue(new Callback<List<Contacto>>() {
            @Override
            public void onResponse(Call<List<Contacto>> call, Response<List<Contacto>> response) {
                try{
                    if(response.isSuccessful()){
                        VisualizarContactoActivity.cargarContactos(response.body());
                    }
                } catch (Exception ex){
                    Toast.makeText(VisualizarContactoActivity, ex.getMessage(), Toast.LENGTH_SHORT);
                }
            }
            @Override
            public void onFailure(Call<List<Contacto>> call, Throwable t) {
                Toast.makeText(VisualizarContactoActivity,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void añadir(Contacto c, AniadirContactoActivity AniadirContactoActivity){
        Contacto contacto = new Contacto(c);

        Call<Boolean> call = contactoService.insertarContacto(contacto.getUser(),contacto.getNombre(),contacto.getApellidos(),
                Integer.parseInt(contacto.getTelefono()),contacto.getDireccion(),contacto.getEmail());

        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    Toast.makeText(AniadirContactoActivity, "Contacto insertado con éxito", Toast.LENGTH_LONG).show();
                    AniadirContactoActivity.cargarIntent(VisualizarContactoActivity.class);
                }
                else {
                    try {
                        Toast.makeText(AniadirContactoActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(AniadirContactoActivity, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void devolverContacto(int id, ModificarborrarContactoActivity ModificarborrarContactoActivity){
        Call<Contacto> call = contactoService.seleccionaUnContacto(id);

        call.enqueue(new Callback<Contacto>() {
            @Override
            public void onResponse(Call<Contacto> call, Response<Contacto> response) {
                if (response.isSuccessful()){
                    Contacto c = new Contacto(response.body());
                    ModificarborrarContactoActivity.setNombre(ModificarborrarContactoActivity.findViewById(R.id.nombretextview));
                    ModificarborrarContactoActivity.setApellidos(ModificarborrarContactoActivity.findViewById(R.id.apellidostextview));
                    ModificarborrarContactoActivity.setDireccion(ModificarborrarContactoActivity.findViewById(R.id.direcciontextview));
                    ModificarborrarContactoActivity.setEmail(ModificarborrarContactoActivity.findViewById(R.id.emailtextview));
                    ModificarborrarContactoActivity.setTelefono(ModificarborrarContactoActivity.findViewById(R.id.telefonotextview));
                    ModificarborrarContactoActivity.cargarContacto(c);
                    ModificarborrarContactoActivity.setBorrar(ModificarborrarContactoActivity.findViewById(R.id.borrarbtn));
                    ModificarborrarContactoActivity.setModificar(ModificarborrarContactoActivity.findViewById(R.id.modificarbtn));
                    ModificarborrarContactoActivity.getBorrar().setOnClickListener(ModificarborrarContactoActivity);
                    ModificarborrarContactoActivity.getModificar().setOnClickListener(ModificarborrarContactoActivity);
                }
                else{
                    try {
                        System.out.println(response.errorBody().string());
                    } catch (IOException e) {}
                }
            }

            @Override
            public void onFailure(Call<Contacto> call, Throwable t) {
                Toast.makeText(ModificarborrarContactoActivity,"Error de conexión", Toast.LENGTH_SHORT);
            }
        });
    }

    public void borrar(int id, ModificarborrarContactoActivity ModificarborrarContactoActivity){
        Call<Boolean> call = contactoService.borrarContacto(id);
        call.enqueue(new Callback<Boolean>() {
            @Override
            public void onResponse(Call<Boolean> call, Response<Boolean> response) {
                if(response.isSuccessful()){
                    if(response.body()) {
                        Toast.makeText(ModificarborrarContactoActivity, "Contacto eliminado", Toast.LENGTH_SHORT).show();
                        ModificarborrarContactoActivity.cargarIntent(VisualizarContactoActivity.class);
                    }
                }
                else {
                    try {
                        Toast.makeText(ModificarborrarContactoActivity, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        System.out.println(e.getMessage());
                    }
                }
                return;
            }

            @Override
            public void onFailure(Call<Boolean> call, Throwable t) {
                Toast.makeText(ModificarborrarContactoActivity, "Error de conexion", Toast.LENGTH_SHORT).show();
            }
        });
    }*/

}
