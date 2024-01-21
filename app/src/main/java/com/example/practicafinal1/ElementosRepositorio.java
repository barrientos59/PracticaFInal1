package com.example.practicafinal1;


import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.practicafinal1.Elemento;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class ElementosRepositorio {
    Executor executor = Executors.newSingleThreadExecutor();

        ElementosBaseDeDatos.ElementosDao elementosDao;

        ElementosRepositorio(Application application) {
            elementosDao = ElementosBaseDeDatos.obtenerInstancia(application).obtenerElementosDao();


        }
    LiveData<List<Elemento>> obtener(){
        return elementosDao.obtener();
    }


    void insertar(Elemento elemento){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elementosDao.insertar(elemento);
            }
        });
    }

    void eliminar(Elemento elemento) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elementosDao.eliminar(elemento);
            }
        });
    }

    public void actualizar(Elemento elemento, float valoracion) {
        executor.execute(new Runnable() {
            @Override
            public void run() {
                elemento.valoracion = valoracion;
                elementosDao.actualizar(elemento);
            }
        });
    }

  /*  List<Elemento> obtener() {
    }

    void insertar(Elemento elemento, Callback callback){

    }

    void eliminar(Elemento elemento, Callback callback) {

    }

    void actualizar(Elemento elemento, float valoracion, Callback callback) {

    } */
}
