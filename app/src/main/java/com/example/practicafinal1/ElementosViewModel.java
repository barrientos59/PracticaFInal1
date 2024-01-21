package com.example.practicafinal1;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.practicafinal1.Elemento;
import com.example.practicafinal1.ElementosRepositorio;

import java.util.List;

public class ElementosViewModel extends AndroidViewModel {

    ElementosRepositorio elementosRepositorio;

    MutableLiveData<Elemento> elementoSeleccionado = new MutableLiveData<>();
    public ElementosViewModel(@NonNull Application application) {
        super(application);
        elementosRepositorio = new ElementosRepositorio(application);


    }

    public LiveData<List<Elemento>> obtener(){
        return elementosRepositorio.obtener();
    }
    public LiveData<List<Elemento>> obtenerMasValorado(){
        return elementosRepositorio.elementosDao.obtenerMasValorado();
    }

    void insertar(Elemento elemento){
        elementosRepositorio.insertar(elemento);
    }

    public void eliminar(Elemento elemento){
        elementosRepositorio.eliminar(elemento);
    }

    public void actualizar(Elemento elemento, float valoracion){
        elementosRepositorio.actualizar(elemento, valoracion);
    }
    public void seleccionar(Elemento elemento){
        elementoSeleccionado.setValue(elemento);
    }

    MutableLiveData<Elemento> seleccionado(){
        return elementoSeleccionado;
    }
}
