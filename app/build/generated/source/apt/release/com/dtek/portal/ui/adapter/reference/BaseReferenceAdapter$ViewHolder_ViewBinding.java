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

public class BaseReferenceAdapter$ViewHolder_ViewBinding implements Unbinder {
  private BaseReferenceAdapter.ViewHolder target;

  @UiThread
  public BaseReferenceAdapter$ViewHolder_ViewBinding(BaseReferenceAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvNumb = Utils.findRequiredViewAsType(source, R.id.tv_numb_bt, "field 'tvNumb'", TextView.class);
    target.tvCreateDate = Utils.findRequiredViewAsType(source, R.id.tv_create_date_bt, "field 'tvCreateDate'", TextView.class);
    target.tvState = Utils.findRequiredViewAsType(source, R.id.tv_state_bt, "field 'tvState'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseReferenceAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumb = null;
    target.tvCreateDate = null;
    target.tvState = null;
  }
}