package com.example.pr_idi.mydatabaseexample;

/**
 * BookData
 * Created by pr_idi on 10/11/16.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class BookData {

    // Database fields
    private SQLiteDatabase database;

    // Helper to manipulate table
    private MySQLiteHelper dbHelper;

    // Here we only select Title and Author, must select the appropriate columns
    private String[] allColumns = { MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE, MySQLiteHelper.COLUMN_AUTHOR, MySQLiteHelper.COLUMN_YEAR, MySQLiteHelper.COLUMN_PUBLISHER, MySQLiteHelper.COLUMN_CATEGORY, MySQLiteHelper.COLUMN_PERSONAL_EVALUATION};

    public BookData(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public Book createBook(String title, String author, String editorial, String any, String categoria, String valoracio) {
        ContentValues values = new ContentValues();
        Log.d("Creating", "Creating " + title + " " + author);

        // Add data: Note that this method only provides title and author
        // Must modify the method to add the full data
        values.put(MySQLiteHelper.COLUMN_TITLE, title);
        values.put(MySQLiteHelper.COLUMN_AUTHOR, author);

        // Invented data
        values.put(MySQLiteHelper.COLUMN_PUBLISHER, editorial);
        values.put(MySQLiteHelper.COLUMN_YEAR, any);
        values.put(MySQLiteHelper.COLUMN_CATEGORY, categoria);
        values.put(MySQLiteHelper.COLUMN_PERSONAL_EVALUATION, valoracio);

        // Actual insertion of the data using the values variable
        long insertId = database.insert(MySQLiteHelper.TABLE_BOOKS, null,
                values);

        // Main activity calls this procedure to create a new book
        // and uses the result to update the listview.
        // Therefore, we need to get the data from the database
        // (you can use this as a query example)
        // to feed the view.

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BOOKS,
                allColumns, MySQLiteHelper.COLUMN_ID + " = " + insertId, null,
                null, null, null);
        cursor.moveToFirst();
        Book newBook = cursorToBook(cursor);

        // Do not forget to close the cursor
        cursor.close();

        // Return the book
        return newBook;
    }

    public void actualizarValoracion(Book book) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_PERSONAL_EVALUATION, book.getPersonal_evaluation());

        int updatedRows = database.update(MySQLiteHelper.TABLE_BOOKS,
                values, MySQLiteHelper.COLUMN_ID + "=?",
                new String[]{String.valueOf(book.getId())});

        Log.d("DB", String.valueOf(updatedRows) + " filas actualizadas");
    }

    public void deleteBook(Book book) {
        long id = book.getId();
        System.out.println("Book deleted with id: " + id);
        database.delete(MySQLiteHelper.TABLE_BOOKS, MySQLiteHelper.COLUMN_ID
                + " = " + id, null);
    }

    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BOOKS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);
            books.add(book);
            cursor.moveToNext();
        }
        // make sure to close the cursor
        cursor.close();
        return books;
    }

    public List<List<Book>> getCatBooks() {
        List<List<Book>> books = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_BOOKS,
                allColumns, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Book book = cursorToBook(cursor);

            String cat = book.getCategory();
            int numCat = catExist(cat, books);

            if (numCat != -1) {
                books.get(numCat).add(book);
            } else {
                List<Book> bookCat = new ArrayList<>();
                bookCat.add(book);
                books.add(bookCat);
            }

            cursor.moveToNext();
        }

        Collections.sort(books, new Comparator<List<Book>>() {
            @Override
            public int compare(List<Book> l1, List<Book> l2) {
                return l1.get(0).getCategory().toLowerCase().compareTo(l2.get(0).getCategory().toLowerCase());
            }
        });

        cursor.close();
        return books;
    }

    private int catExist(String cat, List<List<Book>> books) {
        int numCat = -1;

        for (int i = 0; (i < books.size()) && (numCat == -1); ++i) {
            if (books.get(i).get(0).getCategory().equals(cat)) {
                numCat = i;
            }
        }

        return numCat;
    }

    private Book cursorToBook(Cursor cursor) {
        Book book = new Book();
        book.setId(cursor.getLong(0));
        book.setTitle(cursor.getString(1));
        book.setAuthor(cursor.getString(2));
        book.setYear(cursor.getInt(3));
        book.setPublisher(cursor.getString(4));
        book.setCategory(cursor.getString(5));
        book.setPersonal_evaluation(cursor.getString(6));
        return book;
    }
}