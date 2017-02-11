package com.example.pr_idi.mydatabaseexample;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.StyleSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


public class AboutFragment extends Fragment {
    public AboutFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("About");

        Editable str = Editable.Factory.getInstance().newEditable("Què és llibreBD?");
        str.setSpan(new StyleSpan(Typeface.BOLD), 7, 15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView lblQueEs = (TextView) getView().findViewById(R.id.lblQueEs);
        lblQueEs.setText(str);


        str = Editable.Factory.getInstance().newEditable("LlibreBD és una aplicació per a dispositius Android creada per dos alumnes de la Facultat d'Informàtica de Barcelona amb la finalitat de facilitar als usuaris una manera de classificar les seves lectures. \n \nHi ha molta gent que gaudeix de la lectura però es troben amb problemes a l'hora de classificar els llibres que han llegit. Per això, la nostra aplicació permet classificar els llibres en categories, valorar-los i fer cerques per autor. Tot això, es pot realitzar de manera molt senzilla gràcies a la intuïtiva interfície que ofereix l'aplicació. \n ");
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 81, 116, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView txtQueEs = (TextView) getView().findViewById(R.id.txtQueEs);
        txtQueEs.setText(str);


        str = Editable.Factory.getInstance().newEditable("LlibreBD està format per dos membres que treballem per a satisfer les necessitats dels nostres usuaris. \nL'equip està format per dos estudiants d'informàtica de la UPC: \nAndrés Insaurralde Borzani \nVíctor Sanchez Perez \n");
        str.setSpan(new StyleSpan(Typeface.BOLD), 0, 8, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 164, 167, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 169, 218, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);


        TextView txtQuiSom = (TextView) getView().findViewById(R.id.txtQuiSom);
        txtQuiSom.setText(str);


        str = Editable.Factory.getInstance().newEditable("Si heu de contactar amb algun membre de l'equip a continuació teniu els emails: \nAndrés Insaurralde: andres.insaurralde@est.fib.upc.edu \nVíctor Sanchez: victor.sanchez.perez@est.fib.upc.edu \n");
        str.setSpan(new StyleSpan(Typeface.BOLD), 101, 137, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        str.setSpan(new StyleSpan(Typeface.BOLD), 153, 189, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        TextView txtContacta = (TextView) getView().findViewById(R.id.txtContacta);
        txtContacta.setText(str);
    }
}
