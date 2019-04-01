// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.reference;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ReferenceParamAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ReferenceParamAdapter.ViewHolder target;

  @UiThread
  public ReferenceParamAdapter$ViewHolder_ViewBinding(ReferenceParamAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvParamName = Utils.findRequiredViewAsType(source, R.id.tv_param_name, "field 'tvParamName'", TextView.class);
    target.tvParamValue = Utils.findRequiredViewAsType(source, R.id.tv_param_value, "field 'tvParamValue'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferenceParamAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvParamName = null;
    target.tvParamValue = null;
  }
}
