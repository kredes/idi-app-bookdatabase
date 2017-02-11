package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by kredes on 05/01/2017.
 */

public class MainFragment extends Fragment {
    private static BookData bookData;
    private RecyclerView listaLibros;
    //private AdaptadorBook adapter;
    private AdaptadorBook adapter;
    private DrawerLayout drawerLayout;
    private NavigationView menuLateral;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("LlibreBD");

        bookData = new BookData(getView().getContext());
        bookData.open();

        List<Book> values = bookData.getAllBooks();

        this.listaLibros = (RecyclerView) getView().findViewById(R.id.lista_libros);
        //adapter = new AdaptadorBook(values);
        adapter = new AdaptadorBook(values);
        adapter.reordenarPorTitulo();

        listaLibros.setAdapter(adapter);
        listaLibros.setLayoutManager(new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false));

        //listaLibros.addItemDecoration(new SimpleDividerItemDecoration(this));
        listaLibros.setItemAnimator(new DefaultItemAnimator());
    }

    // Life cycle methods. Check whether it is necessary to reimplement them
    @Override
    public void onResume() {
        bookData.open();
        adapter.setDatos(bookData.getAllBooks());
        adapter.reordenarPorTitulo();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    // Life cycle methods. Check whether it is necessary to reimplement them
    @Override
    public void onPause() {
        bookData.close();
        super.onPause();
    }
}
