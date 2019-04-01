// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.reference;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReferenceDateAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ReferenceDateAdapter.ViewHolder target;

  @UiThread
  public ReferenceDateAdapter$ViewHolder_ViewBinding(ReferenceDateAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvDateName = Utils.findRequiredViewAsType(source, R.id.tv_date_name, "field 'tvDateName'", TextView.class);
    target.etDate = Utils.findRequiredViewAsType(source, R.id.et_date, "field 'etDate'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferenceDateAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvDateName = null;
    target.etDate = null;
  }
}
