package com.example.titi.peminjamanbuku.activity;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.titi.peminjamanbuku.R;
import com.example.titi.peminjamanbuku.model.Book;
import com.example.titi.peminjamanbuku.model.BookResponse;
import com.example.titi.peminjamanbuku.model.BorrowResponse;
import com.example.titi.peminjamanbuku.service.ApiClient;
import com.example.titi.peminjamanbuku.service.ApiInterface1;
import com.example.titi.peminjamanbuku.service.ApiInterface2;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BorrowActivity extends AppCompatActivity {

    public static String BOOK_ID = "bookId";

    private String bookId = "";
    private String date = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrow);

        TextView tvDate = findViewById(R.id.tvDate);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.US);
        date = simpleDateFormat.format(new Date());
        tvDate.setText(getString(R.string.date, date));

        FloatingActionButton fabBorrow = findViewById(R.id.fabBorrow);
        fabBorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                borrowBook();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        bookId = getIntent().getStringExtra(BOOK_ID);
        loadBook(bookId);
    }


    private void loadBook(String id) {
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface1 apiInterface = ApiClient.getClient1().create(ApiInterface1.class);
        Call<BookResponse> call = apiInterface.getBook(id);
        call.enqueue(new Callback<BookResponse>() {
            @Override
            public void onResponse(Call<BookResponse> call, Response<BookResponse> response) {
                BookResponse bookResponse = response.body();

                if (response.isSuccessful()) {
                    try {
                        Book book = bookResponse.getBooks().get(0);

                        TextView tvBookId = findViewById(R.id.tvBookId);
                        TextView tvBookName = findViewById(R.id.tvBookName);
                        TextView tvBookType = findViewById(R.id.tvBookType);
                        TextView tvBookYear = findViewById(R.id.tvBookYear);

                        tvBookId.setText(book.getId());
                        tvBookName.setText(book.getName());
                        tvBookType.setText(book.getType());
                        tvBookYear.setText(book.getYear());

                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(BorrowActivity.this, getString(R.string.book_error), Toast.LENGTH_SHORT).show();
                    } finally {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BookResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(BorrowActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    private void borrowBook() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        TextInputEditText etNik = findViewById(R.id.etNik);
        TextInputEditText etName = findViewById(R.id.etName);

        String nik = etNik.getText().toString();
        String name = etName.getText().toString();

        progressBar.setVisibility(View.VISIBLE);
        ApiInterface2 apiInterface = ApiClient.getClient2().create(ApiInterface2.class);
        Call<BorrowResponse> call = apiInterface.borrowBook(bookId, nik, name, date);
        call.enqueue(new Callback<BorrowResponse>() {
            @Override
            public void onResponse(Call<BorrowResponse> call, Response<BorrowResponse> response) {
                BorrowResponse borrowResponse = response.body();

                if (response.isSuccessful()) {
                    try {
                        if (borrowResponse.isError()) {
                            Toast.makeText(BorrowActivity.this, getString(R.string.borrow_error), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        Toast.makeText(BorrowActivity.this, getString(R.string.borrow_success), Toast.LENGTH_SHORT).show();
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(BorrowActivity.this, getString(R.string.book_error), Toast.LENGTH_SHORT).show();
                    } finally {
                        progressBar.setVisibility(View.GONE);
                    }
                }
            }

            @Override
            public void onFailure(Call<BorrowResponse> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(BorrowActivity.this, getString(R.string.connection_error), Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }
}
