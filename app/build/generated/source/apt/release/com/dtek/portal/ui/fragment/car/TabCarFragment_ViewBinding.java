// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabCarFragment_ViewBinding implements Unbinder {
  private TabCarFragment target;

  private View view2131361980;

  @UiThread
  public TabCarFragment_ViewBinding(final TabCarFragment target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.view_pager, "field 'mViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout, "field 'tabLayout'", TabLayout.class);
    view = source.findViewById(R.id.fb_btn);
    if (view != null) {
      view2131361980 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClick();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    TabCarFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.tabLayout = null;

    if (view2131361980 != null) {
      view2131361980.setOnClickListener(null);
      view2131361980 = null;
    }
  }
}
