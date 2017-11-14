package me.icxd.biblioteka.fragment;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.pspdfkit.configuration.activity.PdfActivityConfiguration;
import com.pspdfkit.configuration.activity.UserInterfaceViewMode;
import com.pspdfkit.configuration.page.PageFitMode;
import com.pspdfkit.configuration.page.PageLayoutMode;
import com.pspdfkit.ui.PdfActivity;
import com.pspdfkit.ui.PdfActivityIntentBuilder;

import org.litepal.crud.DataSupport;

import jp.wasabeef.glide.transformations.BlurTransformation;
import me.icxd.biblioteka.R;
import me.icxd.biblioteka.activity.LexoActivity;
import me.icxd.biblioteka.activity.LexoPSPDF;
import me.icxd.biblioteka.model.bean.Book;


public class BookCoverFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_BOOK_ID = "book_id";
    private static final String ARG_BOOK = "book";
    private Button lexoLiber;
    private Book book;
    private Context context;
    public static BookCoverFragment newInstance(int bookId) {
        BookCoverFragment fragment = new BookCoverFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_BOOK_ID, bookId);
        fragment.setArguments(args);
        return fragment;
    }

    public static BookCoverFragment newInstance(Book book) {
        BookCoverFragment fragment = new BookCoverFragment();
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
        View view = inflater.inflate(R.layout.fragment_book_cover, container, false);
        context=getActivity().getBaseContext();
        ImageView ivBookCover = (ImageView) view.findViewById(R.id.book_cover);
        ImageView ivBookCoverBg = (ImageView) view.findViewById(R.id.book_cover_bg);

        TextView tvRate = (TextView) view.findViewById(R.id.tv_cover_rate);
        RatingBar rbRate = (RatingBar) view.findViewById(R.id.rb_cover_rate);

        View viewRate = view.findViewById(R.id.book_rate);

        lexoLiber = (Button) view.findViewById(R.id.button3);
        lexoLiber.setOnClickListener(this);
        Glide.with(ivBookCover.getContext())
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_cover_icon))
                .error(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_book).colorRes(R.color.boo_cover_icon))
                .into(ivBookCover);

        Glide.with(ivBookCoverBg.getContext())
                .load(book.getImage())
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .bitmapTransform(new BlurTransformation(ivBookCoverBg.getContext(), 25, 3))
                .into(ivBookCoverBg);

        tvRate.setText(book.getAverage());
        rbRate.setRating((Float.parseFloat(book.getAverage())/2));
        Animation cover_an = AnimationUtils.loadAnimation(getContext(), R.anim.book_cover_anim);
        ivBookCover.startAnimation(cover_an);
        Animation rate_an = AnimationUtils.loadAnimation(getContext(), R.anim.book_cover_rate_anim);
        viewRate.startAnimation(rate_an);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button3:
                /*
                Log.i("LEXO","SUKSES");
                Intent intent = new Intent(getContext(), LexoActivity.class);
                intent.putExtra("Titulli", book.getTitle());
                sta
                rtActivity(intent);

                */


                /*
                final PdfActivityConfiguration config = new PdfActivityConfiguration.Builder(context).setUserInterfaceViewMode(UserInterfaceViewMode.USER_INTERFACE_VIEW_MODE_AUTOMATIC_BORDER_PAGES).build();
                final Uri assetFile = Uri.parse("file:///android_asset/kamy.pdf");
                final Intent intent = PdfActivityIntentBuilder.fromUri(context, assetFile).build();
                context.startActivity(intent);
                break;
                */

                final PdfActivityConfiguration config = new PdfActivityConfiguration.Builder(context).layout(R.layout.skerdi_pspdf).fitMode(PageFitMode.FIT_TO_WIDTH).disableOutline().showGapBetweenPages(false).layoutMode(PageLayoutMode.SINGLE).build();
                final Uri assetFile = Uri.parse("file:///android_asset/shembull.pdf");
                final Intent intent = PdfActivityIntentBuilder.fromUri(context, assetFile )
                        .configuration(config)
                        .activityClass(LexoPSPDF.class)
                        .build();
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

// Start the MyCustomActivity like any other activity.
                context.startActivity(intent);
        }

    }

}
