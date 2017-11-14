package me.icxd.biblioteka.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.RectF;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.pspdfkit.document.PdfBox;
import com.pspdfkit.document.PdfDocument;
import com.pspdfkit.ui.PdfActivity;

import me.icxd.biblioteka.R;

/**
 * Created by W2020 Android on 10/23/2017.
 */

public class LexoPSPDF extends PdfActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.d("PDFActivity", " on Create");

    }

    @Override
    protected void onStart() {
        Log.d("PDFActivity", " on Start");

        super.onStart();
        getPdfFragment().setBackgroundColor(Color.rgb(240,239,245));


        // int text = getPdfFragment().getDocument().getPageCount();
        // Log.d("PDFText",  "" +text);
    }

    @Override
    protected void onApplyThemeResource(Resources.Theme theme, int resid, boolean first) {

        super.onApplyThemeResource(theme, resid, first);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Log.d("PDFActivity", " on ApplyThemeResource");
    }

    @Override
    protected void onChildTitleChanged(Activity childActivity, CharSequence title) {
        super.onChildTitleChanged(childActivity, title);
        Log.d("PDFActivity", " on ChildTitleChanged");
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

        Log.d("skerdi", " "+ getWindow().findViewById(com.pspdfkit.R.id.pspdf__activity_fragment_container).getHeight());
        Log.d("PDFActivity", " on AttachedtoWindow");
       /* RectF rectF = getPdfFragment().getDocument().getPageBox(1,PdfBox.CROP_BOX);

        getPdfFragment().zoomTo(rectF, 1, 100 );
        */
    }

    @Override
    public void onAttachFragment(Fragment fragment) {
        super.onAttachFragment(fragment);
        Log.d("PDFActivity", " on AttachFragment");
    }

    @Override
    public void onPageChanged(@NonNull PdfDocument document, int pageIndex) {
        super.onPageChanged(document, pageIndex);
        Log.d("PDFActivity", " on Page Changed");
    }


}
