// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.newspaper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabNewsPaperFragment_ViewBinding implements Unbinder {
  private TabNewsPaperFragment target;

  private View view2131361857;

  @UiThread
  public TabNewsPaperFragment_ViewBinding(final TabNewsPaperFragment target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.pager_newspaper, "field 'mViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_newspaper_layout, "field 'tabLayout'", TabLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_archive, "field 'btn_archive' and method 'onViewClicked'");
    target.btn_archive = Utils.castView(view, R.id.btn_archive, "field 'btn_archive'", Button.class);
    view2131361857 = view;
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
    TabNewsPaperFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.tabLayout = null;
    target.btn_archive = null;

    view2131361857.setOnClickListener(null);
    view2131361857 = null;
  }
}
