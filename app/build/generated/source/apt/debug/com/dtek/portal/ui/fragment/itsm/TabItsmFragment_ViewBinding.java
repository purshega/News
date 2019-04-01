// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.itsm;

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

public class TabItsmFragment_ViewBinding implements Unbinder {
  private TabItsmFragment target;

  private View view2131361979;

  @UiThread
  public TabItsmFragment_ViewBinding(final TabItsmFragment target, View source) {
    this.target = target;

    View view;
    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.pager_it_service, "field 'mViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_it_service_layout, "field 'tabLayout'", TabLayout.class);
    view = source.findViewById(R.id.fab_it_service_add);
    if (view != null) {
      view2131361979 = view;
      view.setOnClickListener(new DebouncingOnClickListener() {
        @Override
        public void doClick(View p0) {
          target.onClickFab();
        }
      });
    }
  }

  @Override
  @CallSuper
  public void unbind() {
    TabItsmFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.tabLayout = null;

    if (view2131361979 != null) {
      view2131361979.setOnClickListener(null);
      view2131361979 = null;
    }
  }
}
