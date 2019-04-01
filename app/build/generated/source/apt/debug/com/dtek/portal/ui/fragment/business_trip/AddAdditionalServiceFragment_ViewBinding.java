// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.business_trip;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddAdditionalServiceFragment_ViewBinding implements Unbinder {
  private AddAdditionalServiceFragment target;

  private View view2131361861;

  private View view2131361870;

  @UiThread
  public AddAdditionalServiceFragment_ViewBinding(final AddAdditionalServiceFragment target,
      View source) {
    this.target = target;

    View view;
    target.spinnerServices = Utils.findRequiredViewAsType(source, R.id.spinner_services, "field 'spinnerServices'", Spinner.class);
    target.etComment = Utils.findRequiredViewAsType(source, R.id.et_comments, "field 'etComment'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_delete, "field 'btnDelete' and method 'onClick'");
    target.btnDelete = Utils.castView(view, R.id.btn_delete, "field 'btnDelete'", Button.class);
    view2131361861 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save, "method 'onClick'");
    view2131361870 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddAdditionalServiceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.spinnerServices = null;
    target.etComment = null;
    target.btnDelete = null;

    view2131361861.setOnClickListener(null);
    view2131361861 = null;
    view2131361870.setOnClickListener(null);
    view2131361870 = null;
  }
}
