// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CarPassengerAdapter$ViewHolder_ViewBinding implements Unbinder {
  private CarPassengerAdapter.ViewHolder target;

  private View view2131362035;

  @UiThread
  public CarPassengerAdapter$ViewHolder_ViewBinding(final CarPassengerAdapter.ViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.iv_remove, "field 'ivRemove' and method 'onRemoveClick'");
    target.ivRemove = Utils.castView(view, R.id.iv_remove, "field 'ivRemove'", ImageView.class);
    view2131362035 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onRemoveClick(p0);
      }
    });
    target.tvNumbPassenger = Utils.findRequiredViewAsType(source, R.id.tv_numb_passenger, "field 'tvNumbPassenger'", TextView.class);
    target.spinnerTypePassenger = Utils.findRequiredViewAsType(source, R.id.spinner_type_passenger, "field 'spinnerTypePassenger'", Spinner.class);
    target.actvFioPassenger = Utils.findRequiredViewAsType(source, R.id.actv_fio_passenger, "field 'actvFioPassenger'", AutoCompleteTextView.class);
    target.cbSmsPassenger = Utils.findRequiredViewAsType(source, R.id.cb_sms_passenger, "field 'cbSmsPassenger'", CheckBox.class);
    target.etPhonePassenger = Utils.findRequiredViewAsType(source, R.id.et_phone_passenger, "field 'etPhonePassenger'", EditText.class);
    target.etCommentPassenger = Utils.findRequiredViewAsType(source, R.id.et_comment_passenger, "field 'etCommentPassenger'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CarPassengerAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivRemove = null;
    target.tvNumbPassenger = null;
    target.spinnerTypePassenger = null;
    target.actvFioPassenger = null;
    target.cbSmsPassenger = null;
    target.etPhonePassenger = null;
    target.etCommentPassenger = null;

    view2131362035.setOnClickListener(null);
    view2131362035 = null;
  }
}
