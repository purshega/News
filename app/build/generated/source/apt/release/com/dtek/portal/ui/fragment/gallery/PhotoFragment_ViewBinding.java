// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.gallery;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import com.github.chrisbanes.photoview.PhotoView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoFragment_ViewBinding implements Unbinder {
  private PhotoFragment target;

  private View view2131362026;

  @UiThread
  public PhotoFragment_ViewBinding(final PhotoFragment target, View source) {
    this.target = target;

    View view;
    target.mImage = Utils.findRequiredViewAsType(source, R.id.pv_image, "field 'mImage'", PhotoView.class);
    target.mConstraintLayout = Utils.findRequiredViewAsType(source, R.id.constraintLayout, "field 'mConstraintLayout'", ConstraintLayout.class);
    target.mProgress = Utils.findRequiredViewAsType(source, R.id.progress, "field 'mProgress'", ProgressBar.class);
    view = Utils.findRequiredView(source, R.id.iv_download, "field 'mIvDownload' and method 'onViewClicked'");
    target.mIvDownload = Utils.castView(view, R.id.iv_download, "field 'mIvDownload'", ImageView.class);
    view2131362026 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onViewClicked();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mImage = null;
    target.mConstraintLayout = null;
    target.mProgress = null;
    target.mIvDownload = null;

    view2131362026.setOnClickListener(null);
    view2131362026 = null;
  }
}
