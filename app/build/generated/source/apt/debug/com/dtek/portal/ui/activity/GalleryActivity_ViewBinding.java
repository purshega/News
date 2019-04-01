// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryActivity_ViewBinding implements Unbinder {
  private GalleryActivity target;

  @UiThread
  public GalleryActivity_ViewBinding(GalleryActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public GalleryActivity_ViewBinding(GalleryActivity target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.container, "field 'viewPager'", ViewPager.class);
    target.mainContent = Utils.findRequiredViewAsType(source, R.id.main_content, "field 'mainContent'", CoordinatorLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
    target.mainContent = null;
  }
}
