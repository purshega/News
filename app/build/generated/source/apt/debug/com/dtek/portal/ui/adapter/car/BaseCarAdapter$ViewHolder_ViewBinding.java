// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class BaseCarAdapter$ViewHolder_ViewBinding implements Unbinder {
  private BaseCarAdapter.ViewHolder target;

  @UiThread
  public BaseCarAdapter$ViewHolder_ViewBinding(BaseCarAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
    target.tvRoute = Utils.findRequiredViewAsType(source, R.id.tv_route, "field 'tvRoute'", TextView.class);
    target.tvState = Utils.findRequiredViewAsType(source, R.id.tv_state, "field 'tvState'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    BaseCarAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.tvRoute = null;
    target.tvState = null;
  }
}
