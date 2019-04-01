// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.gallery;

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

public class PhotoGridFragment_ViewBinding implements Unbinder {
  private PhotoGridFragment target;

  @UiThread
  public PhotoGridFragment_ViewBinding(PhotoGridFragment target, View source) {
    this.target = target;

    target.mTvEmptyList = Utils.findRequiredViewAsType(source, R.id.tv_empty_list, "field 'mTvEmptyList'", TextView.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.main_recycler, "field 'mRecyclerView'", RecyclerView.class);
    target.mSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'mSwipeRefresh'", SwipeRefreshLayout.class);
    target.mProgress = Utils.findRequiredViewAsType(source, R.id.main_progress, "field 'mProgress'", ProgressBar.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoGridFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvEmptyList = null;
    target.mRecyclerView = null;
    target.mSwipeRefresh = null;
    target.mProgress = null;
  }
}
