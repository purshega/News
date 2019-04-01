// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.hr_vacation;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TabVacationFragment_ViewBinding implements Unbinder {
  private TabVacationFragment target;

  @UiThread
  public TabVacationFragment_ViewBinding(TabVacationFragment target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.pager_hr_leave, "field 'mViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_layout_hr_leave, "field 'tabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TabVacationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.tabLayout = null;
  }
}
