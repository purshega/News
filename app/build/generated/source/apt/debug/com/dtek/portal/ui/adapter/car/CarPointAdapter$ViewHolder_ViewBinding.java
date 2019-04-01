// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CarPointAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CarPointAdapter.ViewHolder target;

  private View view2131362036;

  @UiThread
  public CarPointAdapter$ViewHolder_ViewBinding(final CarPointAdapter.ViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_remove_point, "field 'ivRemovePoint' and method 'onRemoveClick'");
    target.ivRemovePoint = Utils.castView(view, R.id.iv_remove_point, "field 'ivRemovePoint'", ImageView.class);
    view2131362036 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRemoveClick(p0);
      }
    });
    target.tvNumbPoint = Utils.findRequiredViewAsType(source, R.id.tv_numb_point, "field 'tvNumbPoint'", TextView.class);
    target.etAddressPoint = Utils.findRequiredViewAsType(source, R.id.et_address_point, "field 'etAddressPoint'", EditText.class);
    target.etDatePoint = Utils.findRequiredViewAsType(source, R.id.et_date_point, "field 'etDatePoint'", EditText.class);
    target.etTimePoint = Utils.findRequiredViewAsType(source, R.id.et_time_point, "field 'etTimePoint'", EditText.class);
    target.rbWaitPoint = Utils.findRequiredViewAsType(source, R.id.rb_wait_point, "field 'rbWaitPoint'", RadioButton.class);
    target.rlWait = Utils.findRequiredViewAsType(source, R.id.rl_wait, "field 'rlWait'", RelativeLayout.class);
    target.spinnerWaitMinutes = Utils.findRequiredViewAsType(source, R.id.spinner_wait_minute, "field 'spinnerWaitMinutes'", Spinner.class);
    target.rbTakeAwayPoint = Utils.findRequiredViewAsType(source, R.id.rb_take_away_point, "field 'rbTakeAwayPoint'", RadioButton.class);
    target.llTakeAway = Utils.findRequiredViewAsType(source, R.id.ll_take_away, "field 'llTakeAway'", LinearLayout.class);
    target.etDateTakeAwayPoint = Utils.findRequiredViewAsType(source, R.id.et_date_take_away_point, "field 'etDateTakeAwayPoint'", EditText.class);
    target.etTimeTakeAwayPoint = Utils.findRequiredViewAsType(source, R.id.et_time_take_away_point, "field 'etTimeTakeAwayPoint'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CarPointAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivRemovePoint = null;
    target.tvNumbPoint = null;
    target.etAddressPoint = null;
    target.etDatePoint = null;
    target.etTimePoint = null;
    target.rbWaitPoint = null;
    target.rlWait = null;
    target.spinnerWaitMinutes = null;
    target.rbTakeAwayPoint = null;
    target.llTakeAway = null;
    target.etDateTakeAwayPoint = null;
    target.etTimeTakeAwayPoint = null;

    view2131362036.setOnClickListener(null);
    view2131362036 = null;
  }
}
