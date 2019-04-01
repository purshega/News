// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.hr;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class HrVacationSubordinateAdapter$ViewHolder_ViewBinding implements Unbinder {
  private HrVacationSubordinateAdapter.ViewHolder target;

  @UiThread
  public HrVacationSubordinateAdapter$ViewHolder_ViewBinding(HrVacationSubordinateAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvSubordinate = Utils.findRequiredViewAsType(source, R.id.tv_subordinate, "field 'tvSubordinate'", TextView.class);
    target.tvType = Utils.findRequiredViewAsType(source, R.id.tv_type, "field 'tvType'", TextView.class);
    target.tvPeriod = Utils.findRequiredViewAsType(source, R.id.tv_period, "field 'tvPeriod'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HrVacationSubordinateAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvSubordinate = null;
    target.tvType = null;
    target.tvPeriod = null;
  }
}
