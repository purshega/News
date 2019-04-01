// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.it;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseItsmAdapter$ViewHolder_ViewBinding implements Unbinder {
  private BaseItsmAdapter.ViewHolder target;

  @UiThread
  public BaseItsmAdapter$ViewHolder_ViewBinding(BaseItsmAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.tvSubject = Utils.findRequiredViewAsType(source, R.id.tv_subject, "field 'tvSubject'", TextView.class);
    target.tvState = Utils.findRequiredViewAsType(source, R.id.tv_state, "field 'tvState'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseItsmAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumber = null;
    target.tvSubject = null;
    target.tvState = null;
  }
}
