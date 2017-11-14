package me.icxd.biblioteka.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

import me.icxd.biblioteka.adapter.BookInfoAdapter;
import me.icxd.biblioteka.R;
import me.icxd.biblioteka.model.bean.Book;
import me.icxd.biblioteka.model.bean.TagItem;


public class BookInfoItemFragment extends Fragment {
    
    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";

    private Book book;
    private List<TagItem> data;

    public static BookInfoItemFragment newInstance(int bookId) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookInfoItemFragment newInstance(Book book) {
        BookInfoItemFragment fragment = new BookInfoItemFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_BOOK, book);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if (getArguments().containsKey(ARG_BOOK_ID)) {
                book = DataSupport.find(Book.class, getArguments().getInt(ARG_BOOK_ID));
            } else if (getArguments().containsKey(ARG_BOOK)) {
                book = (Book) getArguments().getSerializable(ARG_BOOK);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        ListView lv = (ListView) inflater.inflate(R.layout.fragment_book_info_item_list, container, false);
        data = new ArrayList<>();
        data.add(new TagItem("Autori", book.getAuthor()));
        data.add(new TagItem("ShtypShkronja", book.getPublisher()));
        if (!book.getOrigin_title().isEmpty()) data.add(new TagItem("Titulli", book.getOrigin_title()));
        if (!book.getTranslator().isEmpty()) data.add(new TagItem("Perkthyesi", book.getTranslator()));
        data.add(new TagItem("Viti i publikimit", book.getPubdate()));
        data.add(new TagItem("Numri i faqeve", book.getPages()));
        data.add(new TagItem("Cmimi", book.getPrice()));
        if (!book.getBinding().isEmpty()) data.add(new TagItem("Sasia", book.getBinding()));
        data.add(new TagItem("ISBN", book.getIsbn13()));
        BookInfoAdapter lvBaseAdapter = new BookInfoAdapter(getContext(), data);
        lv.setAdapter(lvBaseAdapter);
        return lv;
    }

}
