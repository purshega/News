// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;

  private View view2131362205;

  private View view2131362101;

  private View view2131362103;

  private View view2131362111;

  private View view2131362102;

  private View view2131362109;

  private View view2131362100;

  private View view2131362110;

  private View view2131362106;

  private View view2131362113;

  private View view2131362119;

  private View view2131362105;

  private View view2131362107;

  private View view2131362104;

  private View view2131362088;

  private View view2131362108;

  @UiThread
  public MainActivity_ViewBinding(MainActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public MainActivity_ViewBinding(final MainActivity target, View source) {
    this.target = target;

    View view;
    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mToolbarTitle = Utils.findRequiredViewAsType(source, R.id.toolbar_title, "field 'mToolbarTitle'", TextView.class);
    target.mDrawer = Utils.findRequiredViewAsType(source, R.id.drawer_layout, "field 'mDrawer'", DrawerLayout.class);
    target.mNavigationView = Utils.findRequiredViewAsType(source, R.id.nav_view, "field 'mNavigationView'", NavigationView.class);
    view = Utils.findRequiredView(source, R.id.service_item, "field 'service_item' and method 'onClick'");
    target.service_item = Utils.castView(view, R.id.service_item, "field 'service_item'", LinearLayout.class);
    view2131362205 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.news_group_layout = Utils.findRequiredViewAsType(source, R.id.news_group_layout, "field 'news_group_layout'", LinearLayout.class);
    target.service_group_layout = Utils.findRequiredViewAsType(source, R.id.service_group_layout, "field 'service_group_layout'", LinearLayout.class);
    target.media_group_layout = Utils.findRequiredViewAsType(source, R.id.media_group_layout, "field 'media_group_layout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.nav_car, "field 'nav_car' and method 'onClick'");
    target.nav_car = Utils.castView(view, R.id.nav_car, "field 'nav_car'", LinearLayout.class);
    view2131362101 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_it_service, "field 'nav_it_service' and method 'onClick'");
    target.nav_it_service = Utils.castView(view, R.id.nav_it_service, "field 'nav_it_service'", LinearLayout.class);
    view2131362103 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_room_service, "field 'nav_room_service' and method 'onClick'");
    target.nav_room_service = Utils.castView(view, R.id.nav_room_service, "field 'nav_room_service'", LinearLayout.class);
    view2131362111 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_hr_vacation, "field 'nav_hr_vacation' and method 'onClick'");
    target.nav_hr_vacation = Utils.castView(view, R.id.nav_hr_vacation, "field 'nav_hr_vacation'", LinearLayout.class);
    view2131362102 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_qr, "field 'nav_qr' and method 'onClick'");
    target.nav_qr = Utils.castView(view, R.id.nav_qr, "field 'nav_qr'", LinearLayout.class);
    view2131362109 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_business_trip, "field 'nav_business_trip' and method 'onClick'");
    target.nav_business_trip = Utils.castView(view, R.id.nav_business_trip, "field 'nav_business_trip'", LinearLayout.class);
    view2131362100 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_references, "field 'nav_references' and method 'onClick'");
    target.nav_references = Utils.castView(view, R.id.nav_references, "field 'nav_references'", LinearLayout.class);
    view2131362110 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_newspaper, "field 'nav_newspaper' and method 'onClick'");
    target.nav_newspaper = Utils.castView(view, R.id.nav_newspaper, "field 'nav_newspaper'", LinearLayout.class);
    view2131362106 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_video, "field 'nav_video' and method 'onClick'");
    target.nav_video = Utils.castView(view, R.id.nav_video, "field 'nav_video'", LinearLayout.class);
    view2131362113 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.service_separator_layout = Utils.findRequiredViewAsType(source, R.id.service_separator_layout, "field 'service_separator_layout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.news_item, "method 'onClick'");
    view2131362119 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_news_dtek, "method 'onClick'");
    view2131362105 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_not_miss, "method 'onClick'");
    view2131362107 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_news_company, "method 'onClick'");
    view2131362104 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.media_item, "method 'onClick'");
    view2131362088 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.nav_photo, "method 'onClick'");
    view2131362108 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    MainActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mToolbarTitle = null;
    target.mDrawer = null;
    target.mNavigationView = null;
    target.service_item = null;
    target.news_group_layout = null;
    target.service_group_layout = null;
    target.media_group_layout = null;
    target.nav_car = null;
    target.nav_it_service = null;
    target.nav_room_service = null;
    target.nav_hr_vacation = null;
    target.nav_qr = null;
    target.nav_business_trip = null;
    target.nav_references = null;
    target.nav_newspaper = null;
    target.nav_video = null;
    target.service_separator_layout = null;

    view2131362205.setOnClickListener(null);
    view2131362205 = null;
    view2131362101.setOnClickListener(null);
    view2131362101 = null;
    view2131362103.setOnClickListener(null);
    view2131362103 = null;
    view2131362111.setOnClickListener(null);
    view2131362111 = null;
    view2131362102.setOnClickListener(null);
    view2131362102 = null;
    view2131362109.setOnClickListener(null);
    view2131362109 = null;
    view2131362100.setOnClickListener(null);
    view2131362100 = null;
    view2131362110.setOnClickListener(null);
    view2131362110 = null;
    view2131362106.setOnClickListener(null);
    view2131362106 = null;
    view2131362113.setOnClickListener(null);
    view2131362113 = null;
    view2131362119.setOnClickListener(null);
    view2131362119 = null;
    view2131362105.setOnClickListener(null);
    view2131362105 = null;
    view2131362107.setOnClickListener(null);
    view2131362107 = null;
    view2131362104.setOnClickListener(null);
    view2131362104 = null;
    view2131362088.setOnClickListener(null);
    view2131362088 = null;
    view2131362108.setOnClickListener(null);
    view2131362108 = null;
  }
}
