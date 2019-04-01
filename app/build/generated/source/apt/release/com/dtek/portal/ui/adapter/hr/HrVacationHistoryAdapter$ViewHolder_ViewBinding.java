// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.hr;

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

public class HrVacationHistoryAdapter$ViewHolder_ViewBinding implements Unbinder {
  private HrVacationHistoryAdapter.ViewHolder target;

  @UiThread
  public HrVacationHistoryAdapter$ViewHolder_ViewBinding(HrVacationHistoryAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvType = Utils.findRequiredViewAsType(source, R.id.tv_type_leave, "field 'tvType'", TextView.class);
    target.tvPeriod = Utils.findRequiredViewAsType(source, R.id.tv_period, "field 'tvPeriod'", TextView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'tvStatus'", TextView.class);
    target.ivStatus = Utils.findRequiredViewAsType(source, R.id.iv_status, "field 'ivStatus'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    HrVacationHistoryAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvType = null;
    target.tvPeriod = null;
    target.tvStatus = null;
    target.ivStatus = null;
  }
}
