package me.icxd.biblioteka.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mikepenz.iconics.context.IconicsContextWrapper;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.icxd.biblioteka.R;
import me.icxd.biblioteka.fragment.BookCoverFragment;
import me.icxd.biblioteka.fragment.BookInfoItemFragment;
import me.icxd.biblioteka.fragment.BookIntroFragment;
import me.icxd.biblioteka.fragment.BookNoteFragment;
import me.icxd.biblioteka.model.bean.Book;
import me.icxd.biblioteka.view.ViewPagerIndicator;

public class BookInfoActivity extends BaseActivity {

    private Context context;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;

    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("Detajet e librit", "Permbledhje", "Shenimet e mia");
    private List<Fragment> fragments = new ArrayList<>();
    private Book book;

    private int iconFavorite[] = {R.drawable.ic_favorite_border_white_24dp, R.drawable.ic_favorite_white_24dp};

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_info);
        context = this;
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        int bookId = getIntent().getIntExtra("id", -1);

        book = DataSupport.find(Book.class, bookId);
        setTitle(book.getTitle());
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);
        viewPagerIndicator.setVisibleTabCount(3);

        fragments.add(BookInfoItemFragment.newInstance(bookId));

        fragments.add(BookIntroFragment.newInstance(bookId));

        fragments.add(BookNoteFragment.newInstance(bookId));

        pagerAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return fragments.size();
            }

            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }
        };
        viewPager.setAdapter(pagerAdapter);
        viewPagerIndicator.setViewPager(viewPager, 0);
        Fragment bookCoverragment = BookCoverFragment.newInstance(bookId);
        getSupportFragmentManager().beginTransaction().add(R.id.fragment_book_cover, bookCoverragment).commit();
            }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(this, MainActivity.class));
                return true;
            case R.id.action_favorite:
                book.setFavourite(!book.isFavourite());
                book.save();
                invalidateOptionsMenu();
                new SweetAlertDialog(context, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(book.isFavourite() ? "Koleksioni im" : "Kancelo")
                        .setContentText(book.isFavourite() ? "Libri u koleksionua" : "Koleksioni u kancelua")
                        .setConfirmText("OK")
                        .show();
                return true;
            case R.id.action_browser:
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(book.getAlt()));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.book_info_menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem menuItem = menu.findItem(R.id.action_favorite);
        menuItem.setIcon(iconFavorite[book.isFavourite() ? 1 : 0]);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(this, MainActivity.class));
            return true;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
