package com.example.titi.peminjamanbuku.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.titi.peminjamanbuku.R;
import com.example.titi.peminjamanbuku.listener.RecyclerViewListener;
import com.example.titi.peminjamanbuku.model.Book;

import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {

    private List<Book> books;
    private RecyclerViewListener listener;

    public RecyclerViewAdapter(List<Book> books, RecyclerViewListener listener) {
        this.books = books;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_view, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
        holder.bind(books.get(position), listener);
    }

    @Override
    public int getItemCount() {
        return books.size();
    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private Button btnBorrow;
        private TextView tvBookId, tvBookName, tvBookType, tvBookYear;

        RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            btnBorrow = itemView.findViewById(R.id.btnBorrow);
            tvBookId = itemView.findViewById(R.id.tvBookId);
            tvBookName = itemView.findViewById(R.id.tvBookName);
            tvBookType = itemView.findViewById(R.id.tvBookType);
            tvBookYear = itemView.findViewById(R.id.tvBookYear);
        }

        void bind(final Book book, final RecyclerViewListener listener) {
            tvBookId.setText(book.getId());
            tvBookName.setText(book.getName());
            tvBookType.setText(book.getType());
            tvBookYear.setText(book.getYear());

            btnBorrow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onBookClicked(book);
                }
            });
        }
    }
}
