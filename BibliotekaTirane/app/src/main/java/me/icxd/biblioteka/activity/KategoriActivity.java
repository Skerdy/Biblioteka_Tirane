package me.icxd.biblioteka.activity;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.icxd.biblioteka.R;
import me.icxd.biblioteka.fragment.BookGridFragment;
import me.icxd.biblioteka.model.bean.Book;
import me.icxd.biblioteka.view.ViewPagerIndicator;

public class KategoriActivity extends AppCompatActivity {
    private Context context;
    private ViewPager viewPager;
  //  private HorizontalScrollMenuView kategori;
    private FragmentPagerAdapter pagerAdapter;
    private List<Book> librat;
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("Libraria", "Librat e mi");
    private boolean klikuar = false;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private Integer tag1;
    private String tag2;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        context = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final FloatingActionMenu fabMenu = (FloatingActionMenu) findViewById(R.id.fabmenu);
        fabMenu.setClosedOnTouchOutside(true);
        final FloatingActionButton fabBtnScanner = (FloatingActionButton) findViewById(R.id.fab_scanner);
        fabBtnScanner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                Intent intent = new Intent(KategoriActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fabBtnAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        tag1=getIntent().getIntExtra("tag1",0);
        tag2=getIntent().getStringExtra("tag2");
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);
        // Fragment
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL, tag1,tag2));
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE, tag1,tag2));
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(fragments!=null)
        {
            fragments=null;
        }
        if(pagerAdapter!=null)
            pagerAdapter=null;
        if (viewPager!=null)
            viewPager=null;
    }
}
