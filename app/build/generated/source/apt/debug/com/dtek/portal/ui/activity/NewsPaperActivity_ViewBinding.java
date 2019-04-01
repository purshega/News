// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsPaperActivity_ViewBinding implements Unbinder {
  private NewsPaperActivity target;

  @UiThread
  public NewsPaperActivity_ViewBinding(NewsPaperActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public NewsPaperActivity_ViewBinding(NewsPaperActivity target, View source) {
    this.target = target;

    target.viewPager = Utils.findRequiredViewAsType(source, R.id.container, "field 'viewPager'", ViewPager.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsPaperActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.viewPager = null;
  }
}
