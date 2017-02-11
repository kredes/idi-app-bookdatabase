package com.example.pr_idi.mydatabaseexample;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by kredes on 03/01/2017.
 */

public class HelpTopic extends LinearLayout {
    LinearLayout layout;
    TextView titulo;
    TextView contenido;

    public HelpTopic(Context context) {
        super(context);
        String infService = Context.LAYOUT_INFLATER_SERVICE;
        LayoutInflater li = (LayoutInflater) getContext().getSystemService(infService);
        li.inflate(R.layout.help_topic, this, true);

        layout = (LinearLayout) findViewById(R.id.help_topic_layout);
        titulo = (TextView) findViewById(R.id.helpTopicTitulo);
        contenido = (TextView) findViewById(R.id.helpTopicContenido);

        layout.setPadding(0, 0, 0, 10);
    }

    public void setTitulo(String titulo) {
        this.titulo.setText(titulo);
    }

    public void setContenido(String contenido) {
        this.contenido.setText(contenido);
    }
}
