package me.icxd.biblioteka.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikepenz.google_material_typeface_library.GoogleMaterial;
import com.mikepenz.iconics.IconicsDrawable;

import org.litepal.crud.DataSupport;

import cn.pedant.SweetAlert.SweetAlertDialog;
import me.icxd.biblioteka.R;
import me.icxd.biblioteka.activity.BookInfoActivity;
import me.icxd.biblioteka.adapter.BookGridAdapter;
import me.icxd.biblioteka.model.bean.Book;


public class BookGridFragment extends Fragment implements AdapterView.OnItemClickListener {

    public static final int TYPE_ALL = 1;
    public static final int TYPE_FAVORITE = 2;
    public static final int TAG1_AL = 1;
    public static final int TAG1_WD = 2;
    public static final int TAG1_DEF = 0;
    public static final String TAG2_DEF = "Default";
    public static final String TAG2_DR = "Drame";
    public static final String TAG2_FL = "Filozofik";
    public static final String TAG2_ANT = "Antike";
    public static final String TAG2_POE = "Poezi";
    public static final String TAG2_HIS = "Historik";


    //public static final int TYPE_

    private static final String ARG_TYPE = "type";
    private static final String ARG_TAG1 = "tag1";
    private static final String ARG_TAG2 = "tag2";
    private int type = TYPE_ALL;

    private int tag1 = TAG1_DEF;
    private String tag2 = TAG2_DEF;
    
    private GridView gridView;
    private BookGridAdapter bookGridAdapter;
    private int gridPosition = -1;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (this.isVisible()) {
            if (isVisibleToUser) {
                fetchData();
                bookGridAdapter.notifyDataSetChanged();
            }
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    public static BookGridFragment newInstance(int type, int tag1, String tag2) {
        BookGridFragment fragment = new BookGridFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TYPE, type);
        args.putInt(ARG_TAG1, tag1);
        args.putString(ARG_TAG2, tag2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("Skerdi", "Po krijohet BookGridFragment");
        if (getArguments() != null) {
            Log.i("Skerdi", "Argumentat  jo null");
            type = getArguments().getInt(ARG_TYPE);
            tag1 = getArguments().getInt(ARG_TAG1);
            tag2 = getArguments().getString(ARG_TAG2);
            Log.i("Skerdi", "Argumentat jane : "+ type + " tag" + tag1+"tag2 "+tag2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("Skerdi", "OnCreateView BGF");
        View view = inflater.inflate(R.layout.fragment_book_grid, container, false);
        gridView = (GridView) view.findViewById(R.id.gridView);
        gridView.setOnItemClickListener(this);
        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                gridPosition = position;
                Log.i("HB", "onItemLongClick:gridPosition: " + gridPosition);
                return false;
            }
        });
        registerForContextMenu(gridView);
        View emptyView = view.findViewById(R.id.empty);
        ImageView ivIcon = (ImageView) emptyView.findViewById(R.id.iv_icon);
        TextView tvText = (TextView) emptyView.findViewById(R.id.tv_text);
        if (type == TYPE_FAVORITE) {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_favorite).colorRes(R.color.grid_empty_icon).sizeDp(40));
            tvText.setText("Nuk ka libra te preferuar");
        } else {
            ivIcon.setImageDrawable(new IconicsDrawable(getContext()).icon(GoogleMaterial.Icon.gmd_import_contacts).colorRes(R.color.grid_empty_icon).sizeDp(48));
            tvText.setText("Nuk ka asnje liber per te shfaqur");
        }
        gridView.setEmptyView(emptyView);
        Log.i("Skerdi", "U krijuar BookGridFragment");
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        bookGridAdapter = new BookGridAdapter(getContext());
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
        gridView.setAdapter(bookGridAdapter);
        Log.i("Skerdi", "OnViewCrated + bookGridAdapter");
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent = new Intent(getActivity(), BookInfoActivity.class);
        intent.putExtra("id", (int) bookGridAdapter.getItemId(position));
        startActivity(intent);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "Fshi librin e selektuar");
        menu.add(1, 2, 1, "Fshi te gjithe librat");
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Log.i("HB", "onContextItemSelected:adapter.getCount(): " + bookGridAdapter.getCount());
        Log.i("HB", "onContextItemSelected:gridPosition: " + gridPosition);
        if (item.getItemId() == 1 && gridPosition != -1) {

            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Jeni te sigurte qe deshironi t'a fshini librin")
                    .setContentText("Ky veprim nuk mund te kthehet mbrapsht")
                    .setConfirmText("Po")
                    .setCancelText("Jo")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DataSupport.delete(Book.class, bookGridAdapter.getItemId(gridPosition));
                                    fetchData();
                                    bookGridAdapter.notifyDataSetChanged();
                                }
                            }, 800);

                            sDialog
                                    .setTitleText("U fshi me sukses")
                                    .setContentText("Libri i fshi me sukses")
                                    .setConfirmText("OK")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else if (item.getItemId() == 2) {

            new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Jeni te sigurt qe deshironi te fshini te gjithe librat?")
                    .setContentText("Ky veprim nuk mund te kthehet mbrapsht")
                    .setConfirmText("Po")
                    .setCancelText("Jo")
                    .showCancelButton(true)
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    DataSupport.deleteAll(Book.class);
                                    fetchData();
                                    bookGridAdapter.notifyDataSetChanged();
                                }
                            }, 1000);
                            sDialog
                                    .setTitleText("Fshi librat!")
                                    .setContentText("Te gjithe librat u fshine me sukses")
                                    .setConfirmText("determine")
                                    .showCancelButton(false)
                                    .setConfirmClickListener(null)
                                    .changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        }
                    })
                    .show();
        } else {
            return super.onContextItemSelected(item);
        }
        return true;
    }

    public void fetchData() {
        Log.i("HB", type + "GridFragment.fetchData");
        Log.i("Skerdi", "Tag 1: "+ tag1);
        if(tag2.equals(TAG2_DEF)){
            if (type == TYPE_FAVORITE ) {
                if(tag1==TAG1_DEF) {
                    bookGridAdapter.setData(DataSupport.where("favourite = ?", "1").order("id desc").find(Book.class));
                }
                else if(tag1==TAG1_AL){
                    bookGridAdapter.setData(DataSupport.where("tag1 = ?", "1").order("id desc").find(Book.class));
                }
                else if(tag1==TAG1_WD){
                    bookGridAdapter.setData(DataSupport.where( "tag1 = ?", "2").order("id desc").find(Book.class));
                }
            } else if(type==TYPE_ALL){
                if(tag1==TAG1_DEF) {
                    bookGridAdapter.setData(DataSupport.order("id desc").find(Book.class));
                }
                else if(tag1==TAG1_AL){
                    bookGridAdapter.setData(DataSupport.where("tag1 = ?", "1").order("id desc").find(Book.class));
                }
                else if(tag1==TAG1_WD){
                    bookGridAdapter.setData(DataSupport.where("tag1 = ?", "2").order("id desc").find(Book.class));
                }
            }
        }
        else{
            bookGridAdapter.setData(DataSupport.where("tag2 = ?",tag2).order("id desc").find(Book.class));
        }
    }


    @Override
    public void onResume() {
        super.onResume();
        Log.i("HB", type + "GridFragment.onResume");
        fetchData();
        bookGridAdapter.notifyDataSetChanged();
    }
}
