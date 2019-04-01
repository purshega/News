// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SplashActivity_ViewBinding implements Unbinder {
  private SplashActivity target;

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SplashActivity_ViewBinding(SplashActivity target, View source) {
    this.target = target;

    target.imageView1 = Utils.findRequiredViewAsType(source, R.id.imageView1, "field 'imageView1'", ImageView.class);
    target.imageView2 = Utils.findRequiredViewAsType(source, R.id.imageView2, "field 'imageView2'", ImageView.class);
    target.imageView3 = Utils.findRequiredViewAsType(source, R.id.imageView3, "field 'imageView3'", ImageView.class);
    target.imageView4 = Utils.findRequiredViewAsType(source, R.id.imageView4, "field 'imageView4'", ImageView.class);
    target.imageView5 = Utils.findRequiredViewAsType(source, R.id.imageView5, "field 'imageView5'", ImageView.class);
    target.imageView6 = Utils.findRequiredViewAsType(source, R.id.imageView6, "field 'imageView6'", ImageView.class);
    target.imageView7 = Utils.findRequiredViewAsType(source, R.id.imageView7, "field 'imageView7'", ImageView.class);
    target.mTvVersion = Utils.findRequiredViewAsType(source, R.id.tv_version, "field 'mTvVersion'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SplashActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView1 = null;
    target.imageView2 = null;
    target.imageView3 = null;
    target.imageView4 = null;
    target.imageView5 = null;
    target.imageView6 = null;
    target.imageView7 = null;
    target.mTvVersion = null;
  }
}
