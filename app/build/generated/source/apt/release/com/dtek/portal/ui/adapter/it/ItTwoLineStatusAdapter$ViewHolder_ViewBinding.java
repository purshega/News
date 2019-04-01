// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.it;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.RadioButton;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ItTwoLineStatusAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ItTwoLineStatusAdapter.ViewHolder target;

  private View view2131361892;

  @UiThread
  public ItTwoLineStatusAdapter$ViewHolder_ViewBinding(final ItTwoLineStatusAdapter.ViewHolder target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.choice_select, "field 'radioButton' and method 'onItemClick'");
    target.radioButton = Utils.castView(view, R.id.choice_select, "field 'radioButton'", RadioButton.class);
    view2131361892 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    ItTwoLineStatusAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.radioButton = null;

    view2131361892.setOnClickListener(null);
    view2131361892 = null;
  }
}
