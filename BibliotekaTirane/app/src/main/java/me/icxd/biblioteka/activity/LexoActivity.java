package me.icxd.biblioteka.activity;


import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;


import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;



import me.icxd.biblioteka.R;

public class LexoActivity extends BaseActivity {
    private String liber1 = "liber1.pdf";
    private String liber2 = "liber.pdf";
    private String liber = new String();
    private String titull;
    private PDFView pdfView;
    private Integer pageNumber = 0;

    private String pdfFileName;
    private SharedPreferences sharedPreferences;
    private int savedValue;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_lexo);
        pdfView = (PDFView) findViewById(R.id.pdfView);
        pdfView.setBackgroundColor(Color.LTGRAY);

        /*pdfView.fromAsset("kamy.pdf")
                .enableSwipe(false)
                .enableDoubletap(true)
                .enableAnnotationRendering(true)
                .enableAntialiasing(true)
                .swipeHorizontal(false)
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        SaveInt("faqe", page);
                        pdfView.jumpTo(page);
                    }
                })
                .password(null)
                .load();

        pdfView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        pdfView.setDrawingCacheEnabled(true);
        pdfView.fitToWidth();
        pdfView.useBestQuality(true);
*/

        pdfView.useBestQuality(true);
        pdfView.setLayerType(View.LAYER_TYPE_HARDWARE,null);
        pdfView.setDrawingCacheEnabled(true);
        pdfView.fromAsset("kamy.pdf")
                .enableSwipe(false)
                .swipeVertical(false)
                .enableDoubletap(true)
                .showMinimap(true)
                .defaultPage(LoadInt())
                .onPageChange(new OnPageChangeListener() {
                    @Override
                    public void onPageChanged(int page, int pageCount) {
                        SaveInt("faqe", page);

                    }
                })
                .enableAnnotationRendering(true)
                .password(null)
                .load();



    }
    public void SaveInt(String key, int value){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }
    public int LoadInt(){
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return sharedPreferences.getInt("faqe",1);
    }
}
