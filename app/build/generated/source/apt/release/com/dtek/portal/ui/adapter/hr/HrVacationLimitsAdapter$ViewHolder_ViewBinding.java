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

public class HrVacationLimitsAdapter$ViewHolder_ViewBinding implements Unbinder {
  private HrVacationLimitsAdapter.ViewHolder target;

  @UiThread
  public HrVacationLimitsAdapter$ViewHolder_ViewBinding(HrVacationLimitsAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvType = Utils.findRequiredViewAsType(source, R.id.tv_name_type, "field 'tvType'", TextView.class);
    target.tvBalance = Utils.findRequiredViewAsType(source, R.id.tv_balance_day, "field 'tvBalance'", TextView.class);
    target.tvPrepayment = Utils.findRequiredViewAsType(source, R.id.tv_prepayment_days, "field 'tvPrepayment'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HrVacationLimitsAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvType = null;
    target.tvBalance = null;
    target.tvPrepayment = null;
  }
}
