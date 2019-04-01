// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.reference;

import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import com.dtek.portal.ui.fragment.itsm.DetailItsmFragment_ViewBinding;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailFragment_ViewBinding extends DetailItsmFragment_ViewBinding {
  private DetailFragment target;

  @UiThread
  public DetailFragment_ViewBinding(DetailFragment target, View source) {
    super(target, source);

    this.target = target;

    target.layoutOrganization = Utils.findRequiredViewAsType(source, R.id.layout_organization, "field 'layoutOrganization'", LinearLayout.class);
    target.tvNameOrganization = Utils.findRequiredViewAsType(source, R.id.tv_name_organization, "field 'tvNameOrganization'", TextView.class);
    target.layoutPersonnelNumber = Utils.findRequiredViewAsType(source, R.id.layout_personnel_number, "field 'layoutPersonnelNumber'", LinearLayout.class);
    target.tvPersonnelNumber = Utils.findRequiredViewAsType(source, R.id.tv_personnel_number, "field 'tvPersonnelNumber'", TextView.class);
    target.layoutPhoneNumber = Utils.findRequiredViewAsType(source, R.id.layout_phone_number, "field 'layoutPhoneNumber'", LinearLayout.class);
    target.tvPhoneNumber = Utils.findRequiredViewAsType(source, R.id.tv_phone_number, "field 'tvPhoneNumber'", TextView.class);
    target.layoutParams = Utils.findRequiredViewAsType(source, R.id.layout_params, "field 'layoutParams'", LinearLayout.class);
    target.rvReferenceParams = Utils.findRequiredViewAsType(source, R.id.rv_reference_params, "field 'rvReferenceParams'", RecyclerView.class);
    target.layoutReferenceType = Utils.findRequiredViewAsType(source, R.id.layout_reference_type, "field 'layoutReferenceType'", LinearLayout.class);
    target.tvReferenceType = Utils.findRequiredViewAsType(source, R.id.tv_reference_type, "field 'tvReferenceType'", TextView.class);
    target.layoutSubject = Utils.findRequiredViewAsType(source, R.id.layout_subject, "field 'layoutSubject'", LinearLayout.class);
  }

  @Override
  public void unbind() {
    DetailFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.layoutOrganization = null;
    target.tvNameOrganization = null;
    target.layoutPersonnelNumber = null;
    target.tvPersonnelNumber = null;
    target.layoutPhoneNumber = null;
    target.tvPhoneNumber = null;
    target.layoutParams = null;
    target.rvReferenceParams = null;
    target.layoutReferenceType = null;
    target.tvReferenceType = null;
    target.layoutSubject = null;

    super.unbind();
  }
}
