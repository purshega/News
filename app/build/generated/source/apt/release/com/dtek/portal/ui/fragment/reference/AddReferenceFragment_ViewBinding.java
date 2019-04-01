// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.reference;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import com.dtek.portal.ui.fragment.itsm.CreateItsmFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddReferenceFragment_ViewBinding extends CreateItsmFragment_ViewBinding {
  private AddReferenceFragment target;

  private View view2131362020;

  private View view2131361860;

  @UiThread
  public AddReferenceFragment_ViewBinding(final AddReferenceFragment target, View source) {
    super(target, source);

    this.target = target;

    View view;
    target.llSearchPerson = Utils.findRequiredViewAsType(source, R.id.search_person_layout, "field 'llSearchPerson'", LinearLayout.class);
    target.pbPerson = Utils.findRequiredViewAsType(source, R.id.pb_person, "field 'pbPerson'", ProgressBar.class);
    target.llReferenceAttribute = Utils.findRequiredViewAsType(source, R.id.reference_attribute_layout, "field 'llReferenceAttribute'", LinearLayout.class);
    target.sReferenceType = Utils.findRequiredViewAsType(source, R.id.spinner_reference_type, "field 'sReferenceType'", Spinner.class);
    target.rvReferenceDates = Utils.findRequiredViewAsType(source, R.id.rv_reference_dates, "field 'rvReferenceDates'", RecyclerView.class);
    target.rvReferenceAttribute = Utils.findRequiredViewAsType(source, R.id.rv_reference_attribute, "field 'rvReferenceAttribute'", RecyclerView.class);
    target.llUrgency = Utils.findRequiredViewAsType(source, R.id.urgency_layout, "field 'llUrgency'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.iv_clear_person, "method 'onClick'");
    view2131362020 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "method 'onClick'");
    view2131361860 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
  }

  @Override
  public void unbind() {
    AddReferenceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.llSearchPerson = null;
    target.pbPerson = null;
    target.llReferenceAttribute = null;
    target.sReferenceType = null;
    target.rvReferenceDates = null;
    target.rvReferenceAttribute = null;
    target.llUrgency = null;

    view2131362020.setOnClickListener(null);
    view2131362020 = null;
    view2131361860.setOnClickListener(null);
    view2131361860 = null;

    super.unbind();
  }
}
