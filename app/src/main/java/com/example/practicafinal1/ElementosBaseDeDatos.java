package com.example.practicafinal1;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Database;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.List;

@Database(entities = { Elemento.class }, version = 1, exportSchema = false)
public abstract class ElementosBaseDeDatos extends RoomDatabase {

    private static volatile ElementosBaseDeDatos INSTANCIA;
    public abstract ElementosDao obtenerElementosDao();

    static ElementosBaseDeDatos obtenerInstancia(final Context context) {
        if (INSTANCIA == null) {
            synchronized (ElementosBaseDeDatos.class) {
                if (INSTANCIA == null) {
                    INSTANCIA = Room.databaseBuilder(context,
                                    ElementosBaseDeDatos.class, "elementos.db")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCIA;
    }
    @Dao
    interface ElementosDao {
        @Query("SELECT * FROM Elemento")
        LiveData<List<Elemento>> obtener();

            @Query("SELECT * FROM Elemento ORDER BY valoracion DESC")
            LiveData<List<Elemento>> obtenerMasValorado();

        @Insert
        void insertar(Elemento elemento);

        @Update
        void actualizar(Elemento elemento);

        @Delete
        void eliminar(Elemento elemento);
    }
}