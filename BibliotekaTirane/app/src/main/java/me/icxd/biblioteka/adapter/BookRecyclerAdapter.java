package me.icxd.biblioteka.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import me.icxd.biblioteka.R;
import me.icxd.biblioteka.activity.BookInfoActivity;

import me.icxd.biblioteka.model.bean.Book;


public class BookRecyclerAdapter extends RecyclerView.Adapter<BookRecyclerViewHolder> {

    List<Book> list;
    LayoutInflater inflater;
    Context context;

    public BookRecyclerAdapter(Context context) {
        this.context = context;
        list = new ArrayList<>();
        inflater = LayoutInflater.from(context);
    }

    public void add(Book bookData) {
        list.add(bookData);
        notifyItemInserted(list.size() - 1);
    }

    public void setData(List<Book> booksData) {
        list.clear();
        list.addAll(booksData);
    }

    public void clear() {
        list.clear();
    }

    @Override
    public BookRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new BookRecyclerViewHolder(inflater.inflate(R.layout.activity_search_book_item, parent, false));
    }

    @Override
    public void onBindViewHolder(BookRecyclerViewHolder holder, final int position) {

        Glide.with(holder.bookImage.getContext())
                .load(list.get(position).getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(holder.bookImage.getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_item_icon).paddingDp(10))
                .into(holder.bookImage);


        holder.bookName.setText(list.get(position).getTitle());
        holder.bookPoints.setText(list.get(position).getAverage());
        holder.bookAuthor.setText(list.get(position).getAuthor());
        holder.bookPublisher.setText(list.get(position).getPublisher());
        holder.bookPubdate.setText(list.get(position).getPubdate());
        holder.bookPrice.setText(list.get(position).getPrice());


        if (list.get(position).getTranslator().isEmpty()) {
            holder.bookDivider.setVisibility(View.GONE);
            holder.bookTranslator.setText("");
        } else {
            holder.bookTranslator.setText(list.get(position).getTranslator() + "Perkthyes");
        }


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<Book> books = DataSupport.where("isbn13 = ?", list.get(position).getIsbn13()).find(Book.class);

                if (books.size() > 0) {
                    Intent intent = new Intent(context, BookInfoActivity.class);
                    intent.putExtra("id", books.get(0).getId());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}

class BookRecyclerViewHolder extends RecyclerView.ViewHolder {

    ImageView bookImage;
    TextView bookName;
    TextView bookPoints;
    TextView bookAuthor;
    TextView bookTranslator;
    TextView bookPublisher;
    TextView bookPubdate;
    TextView bookPrice;
    TextView bookDivider;
    CardView cardView;

    public BookRecyclerViewHolder(View itemView) {
        super(itemView);
        bookImage = (ImageView) itemView.findViewById(R.id.book_item_image);
        bookName = (TextView) itemView.findViewById(R.id.book_item_title);
        bookPoints = (TextView) itemView.findViewById(R.id.book_item_points);
        bookAuthor = (TextView) itemView.findViewById(R.id.book_item_author);
        bookTranslator = (TextView) itemView.findViewById(R.id.book_item_translator);
        bookPublisher = (TextView) itemView.findViewById(R.id.book_item_publisher);
        bookPubdate = (TextView) itemView.findViewById(R.id.book_item_pubdate);
        bookPrice = (TextView) itemView.findViewById(R.id.book_item_price);
        bookDivider = (TextView) itemView.findViewById(R.id.book_item_divider);
        cardView = (CardView) itemView.findViewById(R.id.book_item);
    }
}