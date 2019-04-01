// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.adapter.businees_trips;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ApproversListAdapter$ItemViewHolder_ViewBinding implements Unbinder {
  private ApproversListAdapter.ItemViewHolder target;

  @UiThread
  public ApproversListAdapter$ItemViewHolder_ViewBinding(ApproversListAdapter.ItemViewHolder target,
      View source) {
    this.target = target;

    target.tvItemName_1 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_1, "field 'tvItemName_1'", TextView.class);
    target.tvItemName_2 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_2, "field 'tvItemName_2'", TextView.class);
    target.tvItemName_3 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_3, "field 'tvItemName_3'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ApproversListAdapter.ItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvItemName_1 = null;
    target.tvItemName_2 = null;
    target.tvItemName_3 = null;
  }
}
