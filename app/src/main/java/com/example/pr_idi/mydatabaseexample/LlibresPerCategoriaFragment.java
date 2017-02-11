package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class LlibresPerCategoriaFragment extends Fragment {
    private static BookData bookData;
    private RecyclerView recView;


    public LlibresPerCategoriaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_llibres_per_categoria, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Ordenar per categoria");

        bookData = new BookData(getView().getContext());
        bookData.open();
        List<List<Book>> datosAux = bookData.getCatBooks();

        List<Book> datos = new ArrayList<>();

        for (int i = 0; i < datosAux.size(); ++i) {
            for (int j = 0; j < datosAux.get(i).size(); ++j) {
                datos.add(datosAux.get(i).get(j));
            }
        }

        this.recView = (RecyclerView) getView().findViewById(R.id.recView);
        final AdaptadorBook adaptador = new AdaptadorBook(datos);


        recView.setAdapter(adaptador);
        recView.setLayoutManager(new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false));

        recView.setItemAnimator(new DefaultItemAnimator());
    }
}
