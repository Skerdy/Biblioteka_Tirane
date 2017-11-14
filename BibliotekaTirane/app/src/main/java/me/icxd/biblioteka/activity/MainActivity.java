package me.icxd.biblioteka.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

//import com.darwindeveloper.horizontalscrollmenulibrary.custom_views.HorizontalScrollMenuView;
import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.mikepenz.iconics.context.IconicsContextWrapper;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import me.icxd.biblioteka.R;
import me.icxd.biblioteka.fragment.BookGridFragment;
import me.icxd.biblioteka.model.bean.Book;
import me.icxd.biblioteka.model.data.DataManager;
import me.icxd.biblioteka.view.ViewPagerIndicator;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private Context context;
    private ViewPager viewPager;
    private FragmentPagerAdapter pagerAdapter;
    private List<Book> librat;
    private ViewPagerIndicator viewPagerIndicator;
    private List<String> titles = Arrays.asList("Libraria", "Librat e mi");
    private boolean klikuar = false;
    private List<Fragment> fragments = new ArrayList<Fragment>();

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(IconicsContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
                startActivity(intent);
            }
        });
        FloatingActionButton fabBtnAdd = (FloatingActionButton) findViewById(R.id.fab_add);

        fabBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fabMenu.close(true);
                new mbushLibrademo().execute(DataManager.mbushLibra());
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        viewPager = (ViewPager) findViewById(R.id.view_pager);
     //   kategori = (HorizontalScrollMenuView) findViewById(R.id.kategori);
        viewPagerIndicator = (ViewPagerIndicator) findViewById(R.id.indicator);
        viewPagerIndicator.setTabItemTitles(titles);

        // Fragment
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL, BookGridFragment.TAG1_DEF, BookGridFragment.TAG2_DEF));
        fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE, BookGridFragment.TAG1_DEF, BookGridFragment.TAG2_DEF));
        // PagerAdapter
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
      //  initKategori();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.nav_scanner) {
            Intent intent = new Intent(MainActivity.this, ScannerActivity.class);
            startActivity(intent);
        } else if (id == R.id.shqip) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_AL);
            intent.putExtra("tag2", BookGridFragment.TAG2_DEF);
            startActivity(intent);
        }
        else if (id == R.id.huaj) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_WD);
            intent.putExtra("tag2", BookGridFragment.TAG2_DEF);
            startActivity(intent);
        }
        else if (id == R.id.drame) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_DEF);
            intent.putExtra("tag2", BookGridFragment.TAG2_DR);
            startActivity(intent);
        }
        else if (id == R.id.poezi) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_DEF);
            intent.putExtra("tag2", BookGridFragment.TAG2_POE);
            startActivity(intent);
        }
        else if (id == R.id.Filozofik) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_DEF);
            intent.putExtra("tag2", BookGridFragment.TAG2_FL);
            startActivity(intent);
        }

        else if (id == R.id.antike) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_DEF);
            intent.putExtra("tag2", BookGridFragment.TAG2_ANT);
            startActivity(intent);
        }
        else if (id == R.id.nav_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.historik) {
            Intent intent = new Intent(MainActivity.this, KategoriActivity.class);
            intent.putExtra("tag1", BookGridFragment.TAG1_DEF);
            intent.putExtra("tag2", BookGridFragment.TAG2_HIS);
            startActivity(intent);
        }
        else if (id == R.id.nav_profili) {
            Intent intent = new Intent(MainActivity.this, ProfilActivity.class);
            startActivity(intent);
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void saveBook(Book book) {

        Boolean isAdded = false;
        List<Book> books = DataSupport.findAll(Book.class);

            for (int i = 0; i < books.size(); i++) {
                Book book_db = books.get(i);
                if ((book_db.getAuthor() + book_db.getTitle()).equals(book.getAuthor() + book.getTitle())) {
                    isAdded = true;
                    break;
                } else {
                    isAdded = false;
                }
            }
            if (!isAdded) {
                book.save();
            }

        /*if (isAdded) {
            Toast.makeText(context, "Librat u shtuan!", Toast.LENGTH_SHORT).show();
        } else {
            if (book.save()) {
                Toast.makeText(context, "U ruajt me sukses!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, BookInfoActivity.class);
                intent.putExtra("id", book.getId());
                startActivity(intent);
            } else {
                Toast.makeText(context, "Ruajtja e librit deshtoi", Toast.LENGTH_SHORT).show();
            }
        }
        */
    }
/*
    private void initKategori(){
        klikuar=false;
        kategori.addItem("Letersi Shqiptare",R.drawable.albania);
        kategori.addItem("Letersi E Huaj",R.drawable.bota);
        kategori.setOnHSMenuClickListener(new HorizontalScrollMenuView.OnHSMenuClickListener() {
            @Override
            public void onHSMClick(com.darwindeveloper.horizontalscrollmenulibrary.extras.MenuItem menuItem, int position) {
             if(!klikuar) {
                 klikuar=true;
                 kategori.editItem(0, "Roman", R.drawable.roman, false, 0);
                 kategori.editItem(1, "Drame", R.drawable.roman, false, 0);
                 kategori.addItem("Fantashkence", R.drawable.roman);
                 kategori.addItem("Thriller", R.drawable.roman);
                 kategori.addItem("Filozofi", R.drawable.roman);
                 kategori.addItem("Mbrapsht", R.drawable.backbtn);

                 if (position == 0) {
                     Handler handler = new Handler();
                     handler.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                             startLoadingAnim();
                             Log.d("Skerdi", "Fshirja e fragmenteve");
                             fragments.remove(0);
                             fragments.remove(0);
                             //fragments.
                             fragments= new ArrayList<Fragment>();
                             fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL, BookGridFragment.TAG1_AL));
                             fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE, BookGridFragment.TAG1_AL));

                             Log.d("Skerdi", "Shtohen fragmentet");
                             viewPager.setAdapter(pagerAdapter);
                             pagerAdapter.notifyDataSetChanged();
                             Log.d("Skerdi", "Shtohet pagerAdapter");
                             viewPagerIndicator.setViewPager(viewPager, 0);
                             stopLoadingAnim();
                         }
                     }, 800);

                 }
                 else if (position==1){
                     Handler handler1 = new Handler();
                     handler1.postDelayed(new Runnable() {
                         @Override
                         public void run() {
                     startLoadingAnim();
                     Log.d("Skerdi", "Fshirja e fragmenteve");
                     fragments.remove(0);
                     fragments.remove(0);
                             fragments= new ArrayList<Fragment>();
                     fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_ALL, BookGridFragment.TAG1_WD));
                     fragments.add(BookGridFragment.newInstance(BookGridFragment.TYPE_FAVORITE, BookGridFragment.TAG1_WD));
                     Log.d("Skerdi", "Shtohen fragmentet");
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
                     Log.d("Skerdi", "Shtohet pagerAdapter");
                     viewPagerIndicator.setViewPager(viewPager, 0);
                     stopLoadingAnim();
                         }
                     }, 800);
                 }
             }
             else{
                 switch (position){
                     case 0:
                         stopLoadingAnim();
                         break;
                     case 1:
                         startLoadingAnim();
                         break;
                     case 2:
                         stopLoadingAnim();
                         break;
                     case 3:
                         startLoadingAnim();
                         break;
                     case 4:
                         startLoadingAnim();
                     case 5:
                         kategori.removeAllViews();
                         kategori = (HorizontalScrollMenuView) findViewById(R.id.kategori);
                         initKategori();
                         break;
                 }
             }
            }
        });
    }
    */

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

    private void startLoadingAnim() {
     Log.i("Start","Animation");
        viewPager.setVisibility(View.GONE);
        findViewById(R.id.loadViewm).setVisibility(View.VISIBLE);
    }

    private void stopLoadingAnim() {
        Log.i("Stop","Animation");
        viewPager.setVisibility(View.VISIBLE);
        findViewById(R.id.loadViewm).setVisibility(View.GONE);
    }

    private class mbushLibrademo extends AsyncTask <List<Book>, Void, Void>{

        @Override
        protected Void doInBackground(List<Book>... params) {
            for(int i=0;i<params[0].size();i++) {
            saveBook(params[0].get(i));
        }
            return null;
        }
    }

    private class shfaqLibra extends AsyncTask<Integer,Integer,Integer>{

        @Override
        protected Integer doInBackground(Integer... params) {
            return null;
        }
    }
}
