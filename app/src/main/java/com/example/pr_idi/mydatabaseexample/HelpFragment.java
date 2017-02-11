package com.example.pr_idi.mydatabaseexample;

import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

/**
 * Created by kredes on 05/01/2017.
 */

public class HelpFragment extends Fragment {

    LinearLayout scrollView;
    ListView listaHelp;
    ArrayAdapter<String> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Help");

        scrollView = (LinearLayout) getView().findViewById(R.id.help_scroll);

        Resources res = getResources();
        String[] titulos = res.getStringArray(R.array.help_titulos);
        String[] contenidos = res.getStringArray(R.array.help_contenidos);

        for (int i = 0; i < titulos.length; ++i) {
            HelpTopic topic = new HelpTopic(getView().getContext());
            topic.setTitulo(titulos[i]);
            topic.setContenido(contenidos[i]);
            scrollView.addView(topic);
        }
    }

}
