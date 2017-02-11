package com.example.pr_idi.mydatabaseexample;


import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Victor on 28/11/2016.
 */
public class AdaptadorBook extends RecyclerView.Adapter<AdaptadorBook.BookViewHolder> implements View.OnClickListener {

    private List<Book> datos;
    private View.OnClickListener listener;

    public AdaptadorBook() {
        this.datos = new ArrayList<>();
    }

    public AdaptadorBook(List<Book> datos) {
        this.datos = datos;
    }

    public void add(Book b) {
        this.datos.add(b);
    }

    public void setDatos(List<Book> datos) { this.datos = datos; }

    public void reordenarPorTitulo() {
        Collections.sort(datos, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getTitle().toLowerCase().compareTo(o2.getTitle().toLowerCase());
            }
        });
    }

    public void reordenarPorAutor() {
        Collections.sort(datos, new Comparator<Book>() {
            @Override
            public int compare(Book o1, Book o2) {
                return o1.getAuthor().toLowerCase().compareTo(o2.getAuthor().toLowerCase());
            }
        });
    }

    @Override
    public BookViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.listitem_book_2, viewGroup, false);

        itemView.setOnClickListener(this);

        BookViewHolder bvh = new BookViewHolder(itemView);
        return bvh;
    }

    @Override
    public void onBindViewHolder(final BookViewHolder viewHolder, final int pos) {
        Book item = datos.get(pos);
        viewHolder.bindBook(item);

        viewHolder.toolbar.getMenu().clear();
        viewHolder.toolbar.inflateMenu(R.menu.menu_llibre_card);

        viewHolder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.listitem_eliminar: {
                        final int position = viewHolder.getAdapterPosition();
                        if (position >= 0) {
                            final Book book = datos.get(position);

                            AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.toolbar.getContext());

                            builder.setTitle("Confirmar eliminació")
                                    .setMessage("Segur que vols eliminar aquest llibre?")
                                    .setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.dismiss();
                                        }
                                    })
                                    .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            BookData bookData = new BookData(viewHolder.toolbar.getContext());
                                            bookData.open();
                                            bookData.deleteBook(book);
                                            bookData.close();
                                            datos.remove(position);
                                            notifyItemRemoved(position);
                                        }
                                    });
                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return true;
                        }
                        break;
                    }
                    case R.id.listitem_modificar: {
                        int position = viewHolder.getAdapterPosition();
                        if (position >= 0) {
                            final Book book = datos.get(position);

                            int selectedOption;
                            switch (book.getPersonal_evaluation()) {
                                case "Molt bo": selectedOption = 0; break;
                                case "Bo": selectedOption = 1; break;
                                case "Regular": selectedOption = 2; break;
                                case "Dolent": selectedOption = 3; break;
                                case "Molt dolent": selectedOption = 4; break;
                                default: selectedOption = 4; break;
                            }

                            AlertDialog.Builder builder = new AlertDialog.Builder(viewHolder.toolbar.getContext());

                            builder.setTitle("Modificar valoració")
                                    .setSingleChoiceItems(
                                            new CharSequence[]{"Molt bo (5)", "Bo (4)", "Regular (3)", "Dolent (2)", "Molt dolent (1)"},
                                            selectedOption, null)
                                    .setPositiveButton("Acceptar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            ListView elems = ((AlertDialog) dialog).getListView();
                                            String selected;
                                            switch (elems.getCheckedItemPosition()) {
                                                case 0: selected = "Molt bo"; break;
                                                case 1: selected = "Bo"; break;
                                                case 2: selected = "Regular"; break;
                                                case 3: selected = "Dolent"; break;
                                                case 4: selected = "Molt dolent"; break;
                                                default: selected = "Molt dolent"; break;
                                            }
                                            Log.i("Debug", String.valueOf(elems.getCheckedItemPosition()) + ": " + selected);
                                            book.setPersonal_evaluation(selected);
                                            viewHolder.ratingBar.setRating(valoracionAEstrellas(selected));
                                            notifyDataSetChanged();

                                            BookData bookData = new BookData(((AlertDialog) dialog).getContext());
                                            bookData.open();
                                            bookData.actualizarValoracion(book);
                                            bookData.close();

                                            dialog.dismiss();
                                        }
                                    })
                                    .setNegativeButton("Cancel·lar", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                            AlertDialog dialog = builder.create();
                            dialog.show();
                            return true;
                        }
                        break;
                    }
                }
                return false;
            }
        });
    }


    @Override
    public int getItemCount() {
        return datos.size();
    }

    public Book getItem (int id) {
        return datos.get(id);
    }


    public void setOnClickListener(View.OnClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View view) {
        if (listener != null)
            listener.onClick(view);
    }

    public static class BookViewHolder extends RecyclerView.ViewHolder {
        private Toolbar toolbar;
        private TextView txtAutor;
        private TextView txtAny;
        private TextView txtEditorial;
        private TextView txtCategoria;
        private TextView txtValoracio;
        private RatingBar ratingBar;

        public BookViewHolder (View itemView) {
            super(itemView);

            toolbar = (Toolbar) itemView.findViewById(R.id.listitem_toolbar);
            txtAutor = (TextView)itemView.findViewById(R.id.listitem_autor);
            txtAny = (TextView)itemView.findViewById(R.id.listitem_any);
            txtEditorial = (TextView)itemView.findViewById(R.id.listitem_editorial);
            txtCategoria = (TextView)itemView.findViewById(R.id.listitem_categoria);
            txtValoracio = (TextView)itemView.findViewById(R.id.listitem_valoracio);
            ratingBar = (RatingBar) itemView.findViewById(R.id.ratingBar);
        }

        public void bindBook(Book b) {
            toolbar.setTitle(b.getTitle());
            txtAutor.setText(b.getAuthor());
            txtAny.setText(String.valueOf(b.getYear()));
            txtEditorial.setText( "Ed. " + b.getPublisher() + ",");
            txtCategoria.setText(b.getCategory());
            txtValoracio.setText("(" + b.getPersonal_evaluation() + ")");

            ratingBar.setRating(valoracionAEstrellas(b.getPersonal_evaluation()));
            ratingBar.setIsIndicator(true);
        }
        public int getItem () {
            return getAdapterPosition();
        }
    }

    private static int valoracionAEstrellas(String valoracion) {
        switch (valoracion) {
            case "Molt bo": return 5;
            case "Bo": return 4;
            case "Regular": return 3;
            case "Dolent": return 2;
            case "Molt dolent": return 1;
            default: return 1;
        }
    }
}
