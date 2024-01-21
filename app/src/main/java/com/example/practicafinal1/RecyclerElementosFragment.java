package com.example.practicafinal1;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import com.example.practicafinal1.databinding.FragmentRecyclerElementosBinding;
import com.example.practicafinal1.databinding.ViewholderElementoBinding;

import java.util.List;

public class RecyclerElementosFragment extends Fragment {

    private FragmentRecyclerElementosBinding binding;
    private ElementosViewModel elementosViewModel;
    private NavController navController;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return (binding = FragmentRecyclerElementosBinding.inflate(inflater, container, false)).getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        elementosViewModel = new ViewModelProvider(requireActivity()).get(ElementosViewModel.class);
        navController = Navigation.findNavController(view);



        binding.irANuevoElemento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navController.navigate(R.id.action_recyclerElementosFragment_to_nuevoElementoFragment);
            }
        });
        // crear el Adaptador
        ElementosAdapter elementosAdapter = new ElementosAdapter();

        // asociar el Adaptador con el RecyclerView
        binding.recyclerView.setAdapter(elementosAdapter);

        // obtener el array de Elementos, y pasarselo al Adaptador
        elementosViewModel.obtener().observe(getViewLifecycleOwner(), new Observer<List<Elemento>>() {
            @Override
            public void onChanged(List<Elemento> elementos) {
                elementosAdapter.establecerLista(elementos);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                ItemTouchHelper.RIGHT  | ItemTouchHelper.LEFT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return true;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                int posicion = viewHolder.getAdapterPosition();
                Elemento elemento = elementosAdapter.obtenerElemento(posicion);
                elementosViewModel.eliminar(elemento);

            }
        }).attachToRecyclerView(binding.recyclerView);


    }
    class ElementoViewHolder extends RecyclerView.ViewHolder {
        private final ViewholderElementoBinding binding;

        public ElementoViewHolder(ViewholderElementoBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
    class ElementosAdapter extends RecyclerView.Adapter<ElementoViewHolder> {

        List<Elemento> elementos;

        @NonNull
        @Override
        public ElementoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new ElementoViewHolder(ViewholderElementoBinding.inflate(getLayoutInflater(), parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull ElementoViewHolder holder, int position) {

            Elemento elemento = elementos.get(position);

            holder.binding.nombre.setText(elemento.nombre);
            holder.binding.valoracion.setRating(elemento.valoracion);

            holder.binding.valoracion.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                @Override
                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                    if(fromUser) {
                        elementosViewModel.actualizar(elemento, rating);
                    }
                }
            });
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    elementosViewModel.seleccionar(elemento);
                    navController.navigate(R.id.action_recyclerElementosFragment_to_mostrarElementoFragment);
                }
            });
        }
        public Elemento obtenerElemento(int posicion){
            return elementos.get(posicion);
        }

        @Override
        public int getItemCount() {
            return elementos != null ? elementos.size() : 0;
        }

        public void establecerLista(List<Elemento> elementos){
            this.elementos = elementos;
            notifyDataSetChanged();
        }
    }

}