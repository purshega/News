// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.news;

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

public class TabNewsFragment_ViewBinding implements Unbinder {
  private TabNewsFragment target;

  @UiThread
  public TabNewsFragment_ViewBinding(TabNewsFragment target, View source) {
    this.target = target;

    target.mViewPager = Utils.findRequiredViewAsType(source, R.id.pager_news, "field 'mViewPager'", ViewPager.class);
    target.tabLayout = Utils.findRequiredViewAsType(source, R.id.tab_news_layout, "field 'tabLayout'", TabLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TabNewsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mViewPager = null;
    target.tabLayout = null;
  }
}
