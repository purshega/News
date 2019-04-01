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

public class AdditionalServicesListAdapter$ItemViewHolder_ViewBinding implements Unbinder {
  private AdditionalServicesListAdapter.ItemViewHolder target;

  @UiThread
  public AdditionalServicesListAdapter$ItemViewHolder_ViewBinding(AdditionalServicesListAdapter.ItemViewHolder target,
      View source) {
    this.target = target;

    target.tvItemName_1 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_1, "field 'tvItemName_1'", TextView.class);
    target.tvItemNameValue_1 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_value_1, "field 'tvItemNameValue_1'", TextView.class);
    target.tvItemName_2 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_2, "field 'tvItemName_2'", TextView.class);
    target.tvItemNameValue_2 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_value_2, "field 'tvItemNameValue_2'", TextView.class);
    target.tvItemName_3 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_3, "field 'tvItemName_3'", TextView.class);
    target.tvItemNameValue_3 = Utils.findRequiredViewAsType(source, R.id.tv_item_name_value_3, "field 'tvItemNameValue_3'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AdditionalServicesListAdapter.ItemViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvItemName_1 = null;
    target.tvItemNameValue_1 = null;
    target.tvItemName_2 = null;
    target.tvItemNameValue_2 = null;
    target.tvItemName_3 = null;
    target.tvItemNameValue_3 = null;
  }
}
