package com.dtek.portal.ui.fragment.newspaper;

import android.annotation.SuppressLint;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.pdf.PdfRenderer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.dtek.portal.R;
import com.dtek.portal.ui.activity.NewsPaperActivity;
import com.github.chrisbanes.photoview.OnMatrixChangedListener;
import com.github.chrisbanes.photoview.OnScaleChangedListener;
import com.github.chrisbanes.photoview.OnSingleFlingListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.github.chrisbanes.photoview.PhotoViewAttacher;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static com.dtek.portal.ui.activity.NewsPaperActivity.currentPage;
import static com.dtek.portal.ui.activity.NewsPaperActivity.pdfRenderer;


public class NewsPaperPageFragment extends Fragment {
    private static final String ARG_PAGE = "this_page";

    @BindView(R.id.pdf_page)
    PhotoView photoView;

    Unbinder unbinder;

    public NewsPaperPageFragment() {
    }


    public static NewsPaperPageFragment newInstance(int page) {
        NewsPaperPageFragment fragment = new NewsPaperPageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_newspaper_page, container, false);
        unbinder = ButterKnife.bind(this, v);
        if (getArguments() != null) {
            int current_page = getArguments().getInt(ARG_PAGE);
                showPage(current_page);
        }
        return v;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void showPage(int index) {
        if (pdfRenderer.getPageCount() <= index) {
            return;
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage.close();
        }
        // Use `openPage` to open a specific page in PDF_NEWSPAPER.
        currentPage = pdfRenderer.openPage(index);
        // Important: the destination bitmap must be ARGB (not RGB).

        int orientation = this.getResources().getConfiguration().orientation;
        int width_size = 64;
        int heigth_size = 40;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            width_size = 40;
            heigth_size = 64;
        }
        float currentZoomLevel = 1;
        int newWidth = (int) (getResources().getDisplayMetrics().widthPixels * currentPage.getWidth() / 10 * currentZoomLevel / width_size);
        int newHeight = (int) (getResources().getDisplayMetrics().heightPixels * currentPage.getHeight() /10* currentZoomLevel / heigth_size);

        Bitmap bitmap = Bitmap.createBitmap(newWidth, newHeight, Bitmap.Config.ARGB_8888);
//        Matrix matrix = new Matrix();
//
//        float dpiAdjustedZoomLevel = currentZoomLevel * DisplayMetrics.DENSITY_MEDIUM / getResources().getDisplayMetrics().densityDpi;
//        matrix.setScale(dpiAdjustedZoomLevel, dpiAdjustedZoomLevel);

        currentPage.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
        // We are ready to show the Bitmap to user.
        photoView.setImageBitmap(bitmap);
        photoView.setMaximumScale(10);
//        photoView.setOnScaleChangeListener((scaleFactor, focusX, focusY) -> {
//            Log.d("PARAMS"," SCALE = " + photoView.getScale() + "   Focux X  = " + focusX + "   Focus Y = " + focusY); });
//        photoView.setOnMatrixChangeListener(rect -> {
//            Log.d("Y","Y ======= " + photoView.getDisplayRect().toString());
//        });
    }



    @Override
    public void onDestroyView() {
        super.onDestroyView();
        photoView = null;
        unbinder.unbind();
    }
}