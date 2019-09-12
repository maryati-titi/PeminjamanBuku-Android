package com.example.titi.peminjamanbuku.activity;

import android.content.Intent;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import android.widget.Toast;

import com.example.titi.peminjamanbuku.R;
import com.example.titi.peminjamanbuku.adapter.RecyclerViewAdapter;
import com.example.titi.peminjamanbuku.listener.RecyclerViewListener;
import com.example.titi.peminjamanbuku.model.Book;
import com.example.titi.peminjamanbuku.model.BookResponse;
import com.example.titi.peminjamanbuku.service.ApiClient;
import com.example.titi.peminjamanbuku.service.ApiInterface1;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private List<Book> books = new ArrayList<>();
    private RecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView rvBooks = findViewById(R.id.rvBooks);

        adapter = new RecyclerViewAdapter(books, listener);
        rvBooks.setAdapter(adapter);
        rvBooks.setLayoutManager(new LinearLayoutManager(this));
    }

    private RecyclerViewListener listener = new RecyclerViewListener() {
        @Override
        public void onBookClicked(Book book) {
            Toast.makeText(MainActivity.this, getString(R.string.selected, book.getName()), Toast.LENGTH_SHORT).show();
            startActivity(new Intent(MainActivity.this, BorrowActivity.class)
                    .putExtra(BorrowActivity.BOOK_ID, book.getId()));
        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        loadBooks();
    }

    private void loadBooks() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface1 apiInterface = ApiClient.getClient1().create(ApiInterface1.class);
        Call<BookResponse> call = apiInterface.getBooks();
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                BookResponse bookResponse = response.body();

                if (response.isSuccessful()) {
                    try {
                        books.clear();
                        books.addAll(bookResponse.getBooks());
                        adapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this, getString(R.string.book_error), Toast.LENGTH_SHORT).show();
                    } finally {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem item = menu.findItem(R.id.menuSearch);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(item);
        searchView.setQueryHint("Cari Judul Buku");
        searchView.setOnQueryTextListener(searchListener);
        return true;
    }

    private SearchView.OnQueryTextListener searchListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            final ProgressBar progressBar = findViewById(R.id.progressBar);

            progressBar.setVisibility(View.VISIBLE);
            ApiInterface1 apiInterface = ApiClient.getClient1().create(ApiInterface1.class);
            Call<BookResponse> call = apiInterface.getBookByName(newText);
            call.enqueue(new Callback<BookResponse>() {
                @Override
                public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                    BookResponse bookResponse = response.body();

                    if (response.isSuccessful()) {
                        try {
                            books.clear();
                            books.addAll(bookResponse.getBooks());
                            adapter.notifyDataSetChanged();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(MainActivity.this, getString(R.string.book_error), Toast.LENGTH_SHORT).show();
                        } finally {
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }

                @Override
                public void onFailure(Call<BookResponse> call, Throwable t) {
                    t.printStackTrace();
                    Toast.makeText(MainActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            });
            return true;
        }
    };
}
