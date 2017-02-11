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
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Victor on 10/01/2017.
 */
public class BuscarPorTituloFragment extends Fragment {
    private SearchView searchView;
    private RecyclerView listaLibros;
    private TextView textAviso;
    //private AdaptadorBook adapter;
    private AdaptadorBook adapter;
    private BookData bookData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_buscar_por_titulo, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Cercar llibre per títol");

        bookData = new BookData(getView().getContext());
        adapter = new AdaptadorBook();
        listaLibros = (RecyclerView) getView().findViewById(R.id.lista_libros_por_titulo);
        searchView = (SearchView) getView().findViewById(R.id.search_view);
        textAviso = (TextView) getView().findViewById(R.id.text_aviso);

        listaLibros.setAdapter(this.adapter);
        listaLibros.setLayoutManager(new LinearLayoutManager(getView().getContext(), LinearLayoutManager.VERTICAL, false));

        listaLibros.setItemAnimator(new DefaultItemAnimator());

        searchView.setSubmitButtonEnabled(true);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                List<Book> values = bookData.getAllBooks();

                Iterator it = values.iterator();
                while (it.hasNext()) {
                    Book b = (Book) it.next();
                    if (!b.getTitle().toLowerCase().contains(query.toLowerCase())) it.remove();
                }

                if (values.isEmpty()) {
                    adapter.setDatos(new ArrayList<Book>());
                    adapter.notifyDataSetChanged();
                    textAviso.setText("No s'han trobat resultats");
                    textAviso.setVisibility(View.VISIBLE);
                    return true;
                }

                textAviso.setVisibility(View.INVISIBLE);

                adapter.setDatos(values);
                adapter.reordenarPorTitulo();
                adapter.notifyDataSetChanged();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

    }

    @Override
    public void onResume() {
        bookData.open();
        super.onResume();
    }

    @Override
    public void onPause() {
        bookData.close();
        super.onPause();
    }
}
