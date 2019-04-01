// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.hr_vacation;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VacationLimitFragment_ViewBinding implements Unbinder {
  private VacationLimitFragment target;

  private View view2131361977;

  @UiThread
  public VacationLimitFragment_ViewBinding(final VacationLimitFragment target, View source) {
    this.target = target;

    View view;
    target.mSwipeRefresh = Utils.findRequiredViewAsType(source, R.id.swipeRefresh, "field 'mSwipeRefresh'", SwipeRefreshLayout.class);
    target.mRecyclerView = Utils.findRequiredViewAsType(source, R.id.main_recycler, "field 'mRecyclerView'", RecyclerView.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.main_progress, "field 'mProgressBar'", ProgressBar.class);
    target.mTvEmpty = Utils.findRequiredViewAsType(source, R.id.tv_empty_list, "field 'mTvEmpty'", TextView.class);
    view = Utils.findRequiredView(source, R.id.fab_hr_leave_create, "field 'mFabCreate'");
    target.mFabCreate = Utils.castView(view, R.id.fab_hr_leave_create, "field 'mFabCreate'", FloatingActionButton.class);
    view2131361977 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    VacationLimitFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSwipeRefresh = null;
    target.mRecyclerView = null;
    target.mProgressBar = null;
    target.mTvEmpty = null;
    target.mFabCreate = null;

    view2131361977.setOnClickListener(null);
    view2131361977 = null;
  }
}
