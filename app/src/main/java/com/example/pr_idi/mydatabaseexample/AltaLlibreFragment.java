package com.example.pr_idi.mydatabaseexample;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * Created by kredes on 05/01/2017.
 */

public class AltaLlibreFragment extends Fragment {
    private EditText titol;
    private EditText autor;
    private EditText any;
    private EditText editorial;
    private EditText categoria;
    private RadioGroup grupValoracio;
    private RadioButton moltbo;
    private RadioButton bo;
    private RadioButton regular;
    private RadioButton dolent;
    private RadioButton moltdolent;
    private Button btnCancela;
    private Button btnAccepta;

    private BookData bookData;


    public AltaLlibreFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alta_llibre, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Afegir llibre");

        //Referencia als controladors de l'interficie
        titol = (EditText) getView().findViewById(R.id.txtTitol);
        autor = (EditText) getView().findViewById(R.id.txtAutor);
        any = (EditText) getView().findViewById(R.id.txtAny);
        editorial = (EditText) getView().findViewById(R.id.txtEditorial);
        categoria = (EditText) getView().findViewById(R.id.txtCategoria);
        grupValoracio = (RadioGroup) getView().findViewById(R.id.radioGroup);
        moltbo = (RadioButton) getView().findViewById(R.id.rbtnMoltbo);
        bo = (RadioButton) getView().findViewById(R.id.rbtnBo);
        regular = (RadioButton) getView().findViewById(R.id.rbtnRegular);
        dolent = (RadioButton) getView().findViewById(R.id.rbtnDolent);
        moltdolent = (RadioButton) getView().findViewById(R.id.rbtnMoltdolent);
        btnAccepta = (Button) getView().findViewById(R.id.btnAccepta);
        btnCancela = (Button) getView().findViewById(R.id.btnCancela);

        bookData = new BookData(getView().getContext());

        btnCancela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getFragmentManager().popBackStackImmediate();
                Toast toast2 = Toast.makeText(view.getContext(), "No s'ha afegit cap llibre.", Toast.LENGTH_SHORT);
                toast2.show();
            }
        });

        btnAccepta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((titol.getText().length() != 0) && (autor.getText().length() != 0) && (any.getText().length() != 0) && (editorial.getText().length() != 0) && (categoria.getText().length() != 0)) {
                    //HA INTRODUIT VALORACIO
                    if (grupValoracio.getCheckedRadioButtonId() != -1) {
                        String valoracio = "";
                        switch (grupValoracio.getCheckedRadioButtonId()) {
                            case R.id.rbtnBo: valoracio = "Bo"; break;
                            case R.id.rbtnDolent: valoracio = "Dolent"; break;
                            case R.id.rbtnMoltbo: valoracio = "Molt bo"; break;
                            case R.id.rbtnMoltdolent: valoracio = "Molt dolent"; break;
                            case R.id.rbtnRegular: valoracio = "Regular"; break;
                        }

                        bookData.open();
                        bookData.createBook(
                                titol.getText().toString(),
                                autor.getText().toString(),
                                editorial.getText().toString(),
                                any.getText().toString(),
                                categoria.getText().toString(),
                                valoracio
                        );
                        bookData.close();
                        getFragmentManager().popBackStackImmediate();
                        Toast toast1 = Toast.makeText(view.getContext(), "El llibre s'ha afegit correctament.", Toast.LENGTH_SHORT);
                        toast1.show();

                    } else {
                        //MOSTRAR ERROR: S'HAN D'OMPLIR TOTS ELS CAMPS DE TEXT
                        Toast toast1 = Toast.makeText(view.getContext(), "Has de seleccionar la valoració del llibre.", Toast.LENGTH_SHORT);
                        toast1.show();
                    }
                } else {
                    //MOSTRAR ERROR: NO S'HA INDICAT LA VALORACIÓ
                    Toast toast2 = Toast.makeText(view.getContext(), "Has d'omplir tots els camps.", Toast.LENGTH_SHORT);
                    toast2.show();
                }
            }
        });
    }
}
