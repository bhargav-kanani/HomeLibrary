package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.models.BookModel;
import com.example.myapplication.models.BooksResponseModel;
import com.example.myapplication.network.NetworkClient;
import com.example.myapplication.network.NetworkService;
import com.squareup.picasso.Picasso;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BooksActivity extends AppCompatActivity {

    RecyclerView booksRecyclerView;
    TextView textToolbarTitle;
    ImageView imageBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_activvity);
        textToolbarTitle = (TextView) findViewById(R.id.text_toolbar_title);
        textToolbarTitle.setText(getIntent().getStringExtra("category"));

        imageBack = (ImageView) findViewById(R.id.image_back);
        imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        booksRecyclerView = (RecyclerView) findViewById(R.id.books_recycler_view);
        booksRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        booksRecyclerView.setHasFixedSize(true);

        getBooks();
    }

    private void getBooks() {

        ProgressDialog progressDialog = new ProgressDialog(BooksActivity.this);
        progressDialog.setTitle("Please wait");
        progressDialog.setMessage("Getting books");
        progressDialog.setCancelable(false);
        progressDialog.show();

        NetworkService networkService = NetworkClient.getClient().create(NetworkService.class);
        Call<BooksResponseModel> getBooks = networkService.getBooksByCategories(getIntent().getStringExtra("category"));
        getBooks.enqueue(new Callback<BooksResponseModel>() {
            @Override
            public void onResponse(Call<BooksResponseModel> call, Response<BooksResponseModel> response) {

                progressDialog.cancel();
                BooksAdapter booksAdapter = new BooksAdapter(response.body().getBooks());
                booksRecyclerView.setAdapter(booksAdapter);
            }

            @Override
            public void onFailure(Call<BooksResponseModel> call, Throwable t) {
                progressDialog.cancel();

            }
        });
    }

    public class BooksAdapter extends RecyclerView.Adapter<BooksAdapter.BookViewHolder> {

        List<BookModel> books;

        BooksAdapter(List<BookModel> books) {
            this.books = books;
        }

        @Override
        public int getItemCount() {
            return books.size();
        }

        @NonNull
        @Override
        public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new BookViewHolder(LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.book_item_container, parent, false
            ));
        }

        @Override
        public void onBindViewHolder(BookViewHolder holder, int position) {

            if (books.get(position).getName() != null && !books.get(position).getName().equals("")) {
                holder.textName.setText(books.get(position).getName());

            } else {
                holder.textName.setVisibility(View.GONE);
            }

            if (books.get(position).getPrice() != null && !books.get(position).getPrice().equals("")) {
                holder.textPrice.setText("\u20B9" + books.get(position).getPrice());

            } else {
                holder.textPrice.setVisibility(View.GONE);
            }

            if (books.get(position).getAuthor() != null && !books.get(position).getAuthor().equals("")) {
                holder.textAuthor.setText(books.get(position).getAuthor());

            } else {
                holder.textAuthor.setVisibility(View.GONE);
            }

            if (books.get(position).getDescription() != null && !books.get(position).getDescription().equals("")) {
                holder.textDescription.setText(books.get(position).getDescription());

            } else {
                holder.textDescription.setVisibility(View.GONE);
            }


            if (books.get(position).getImage() != null && !books.get(position).getImage().equals("")) {
                Picasso.with(getApplicationContext()).load(
                        books.get(position).getImage()
                ).into(holder.imageBook);
            } else {
                holder.imageBook.setVisibility(View.GONE);
            }

            holder.cardBook.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), BookDetailActivity.class);
                    intent.putExtra("image", books.get(holder.getAdapterPosition()).getImage());
                    intent.putExtra("image2", books.get(holder.getAdapterPosition()).getImage2());
                    intent.putExtra("image3", books.get(holder.getAdapterPosition()).getImage3());
                    intent.putExtra("image4", books.get(holder.getAdapterPosition()).getImage4());
                    intent.putExtra("author", books.get(holder.getAdapterPosition()).getAuthor());
                    intent.putExtra("publishedYear", books.get(holder.getAdapterPosition()).getPublished_year());
                    intent.putExtra("description", books.get(holder.getAdapterPosition()).getDescription());
                    intent.putExtra("price", books.get(holder.getAdapterPosition()).getPrice());
                    intent.putExtra("name", books.get(holder.getAdapterPosition()).getName());
                    intent.putExtra("id", books.get(holder.getAdapterPosition()).getId());
                    intent.putExtra("category", books.get(holder.getAdapterPosition()).getCategory());

                    startActivity(intent);


                }
            });


        }

        class BookViewHolder extends RecyclerView.ViewHolder {

            CardView cardBook;
            ImageView imageBook;
            TextView textName, textAuthor, textPrice, textDescription;

            BookViewHolder(View view) {
                super(view);

                cardBook = (CardView) view.findViewById(R.id.book_card_view);
                imageBook = (ImageView) view.findViewById(R.id.image_book);
                textName = (TextView) view.findViewById(R.id.text_book_name);
                textAuthor = (TextView) view.findViewById(R.id.text_book_author);
                textPrice = (TextView) view.findViewById(R.id.text_book_price);
                textDescription = (TextView) view.findViewById(R.id.text_description);

            }
        }
    }
}