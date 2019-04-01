// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.it;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ItTwoLineTaskAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ItTwoLineTaskAdapter.ViewHolder target;

  private View view2131361878;

  @UiThread
  public ItTwoLineTaskAdapter$ViewHolder_ViewBinding(final ItTwoLineTaskAdapter.ViewHolder target,
      View source) {
    this.target = target;

    View view;
    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvPriority = Utils.findRequiredViewAsType(source, R.id.tv_priority, "field 'tvPriority'", TextView.class);
    target.tvDescription = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tvDescription'", TextView.class);
    view = Utils.findRequiredView(source, R.id.cardView, "field 'cardView' and method 'onItemClick'");
    target.cardView = Utils.castView(view, R.id.cardView, "field 'cardView'", CardView.class);
    view2131361878 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ItTwoLineTaskAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumber = null;
    target.tvAddress = null;
    target.tvPriority = null;
    target.tvDescription = null;
    target.cardView = null;

    view2131361878.setOnClickListener(null);
    view2131361878 = null;
  }
}
