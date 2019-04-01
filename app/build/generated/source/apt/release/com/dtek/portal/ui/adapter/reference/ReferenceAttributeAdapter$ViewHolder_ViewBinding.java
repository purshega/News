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

public class ReferenceAttributeAdapter$ViewHolder_ViewBinding implements Unbinder {
  private ReferenceAttributeAdapter.ViewHolder target;

  @UiThread
  public ReferenceAttributeAdapter$ViewHolder_ViewBinding(ReferenceAttributeAdapter.ViewHolder target,
      View source) {
    this.target = target;

    target.tvAttributeName = Utils.findRequiredViewAsType(source, R.id.tv_attribute_name, "field 'tvAttributeName'", TextView.class);
    target.etContent = Utils.findRequiredViewAsType(source, R.id.et_attribute_content, "field 'etContent'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ReferenceAttributeAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvAttributeName = null;
    target.etContent = null;
  }
}
