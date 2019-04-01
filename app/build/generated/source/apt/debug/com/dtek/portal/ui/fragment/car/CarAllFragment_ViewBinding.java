// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CarAllFragment_ViewBinding implements Unbinder {
  private CarAllFragment target;

  @UiThread
  public CarAllFragment_ViewBinding(CarAllFragment target, View source) {
    this.target = target;

    target.mSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'mSwipeRefresh'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.main_recycler, "field 'mRecyclerView'", RecyclerView.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.main_progress, "field 'mProgressBar'", ProgressBar.class);
    target.mTvEmpty = Utils.findRequiredViewAsType(source, R.id.tv_empty_list, "field 'mTvEmpty'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CarAllFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeRefresh = null;
    target.mRecyclerView = null;
    target.mProgressBar = null;
    target.mTvEmpty = null;
  }
}
