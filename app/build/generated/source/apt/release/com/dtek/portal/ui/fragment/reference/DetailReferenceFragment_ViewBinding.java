// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.reference;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailReferenceFragment_ViewBinding implements Unbinder {
  private DetailReferenceFragment target;

  private View view2131361869;

  private View view2131361865;

  private View view2131361868;

  @UiThread
  public DetailReferenceFragment_ViewBinding(final DetailReferenceFragment target, View source) {
    this.target = target;

    View view;
    target.tvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'tvNumber'", TextView.class);
    target.tvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'tvStatus'", TextView.class);
    target.tvInitiator = Utils.findRequiredViewAsType(source, R.id.tv_initiator, "field 'tvInitiator'", TextView.class);
    target.tvDateCreate = Utils.findRequiredViewAsType(source, R.id.tv_date_create, "field 'tvDateCreate'", TextView.class);
    target.tvDateEnd = Utils.findRequiredViewAsType(source, R.id.tv_date_end, "field 'tvDateEnd'", TextView.class);
    target.tvDatePlan = Utils.findRequiredViewAsType(source, R.id.tv_date_plan, "field 'tvDatePlan'", TextView.class);
    target.tvNameAffects = Utils.findRequiredViewAsType(source, R.id.tv_name_affects, "field 'tvNameAffects'", TextView.class);
    target.tvNameOrganization = Utils.findRequiredViewAsType(source, R.id.tv_name_organization, "field 'tvNameOrganization'", TextView.class);
    target.tvPersonnelNumber = Utils.findRequiredViewAsType(source, R.id.tv_personnel_number, "field 'tvPersonnelNumber'", TextView.class);
    target.tvPhoneNumber = Utils.findRequiredViewAsType(source, R.id.tv_phone_number, "field 'tvPhoneNumber'", TextView.class);
    target.tvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'tvAddress'", TextView.class);
    target.tvUrgency = Utils.findRequiredViewAsType(source, R.id.tv_urgency, "field 'tvUrgency'", TextView.class);
    target.tvSubject = Utils.findRequiredViewAsType(source, R.id.tv_subject, "field 'tvSubject'", TextView.class);
    target.tvDescription = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'tvDescription'", TextView.class);
    target.layoutSolution = Utils.findRequiredViewAsType(source, R.id.layout_solution, "field 'layoutSolution'", LinearLayout.class);
    target.tvSolution = Utils.findRequiredViewAsType(source, R.id.tv_solution, "field 'tvSolution'", TextView.class);
    target.rvReferenceParams = Utils.findRequiredViewAsType(source, R.id.rv_reference_params, "field 'rvReferenceParams'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.btn_revoke, "field 'mBtnRevoke' and method 'onButtonClick'");
    target.mBtnRevoke = Utils.castView(view, R.id.btn_revoke, "field 'mBtnRevoke'", Button.class);
    view2131361869 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_rate, "field 'mBtnRate' and method 'onButtonClick'");
    target.mBtnRate = Utils.castView(view, R.id.btn_rate, "field 'mBtnRate'", Button.class);
    view2131361865 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_return, "field 'mBtnReturn' and method 'onButtonClick'");
    target.mBtnReturn = Utils.castView(view, R.id.btn_return, "field 'mBtnReturn'", Button.class);
    view2131361868 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    target.mLlRating = Utils.findRequiredViewAsType(source, R.id.ll_rating, "field 'mLlRating'", LinearLayout.class);
    target.mLlDateEnd = Utils.findRequiredViewAsType(source, R.id.ll_date_end, "field 'mLlDateEnd'", LinearLayout.class);
    target.mLlDatePlan = Utils.findRequiredViewAsType(source, R.id.ll_date_plan, "field 'mLlDatePlan'", LinearLayout.class);
    target.mSpinnerRating = Utils.findRequiredViewAsType(source, R.id.spinner_rating, "field 'mSpinnerRating'", Spinner.class);
    target.mEtCommentRating = Utils.findRequiredViewAsType(source, R.id.et_comment_rating, "field 'mEtCommentRating'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailReferenceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvNumber = null;
    target.tvStatus = null;
    target.tvInitiator = null;
    target.tvDateCreate = null;
    target.tvDateEnd = null;
    target.tvDatePlan = null;
    target.tvNameAffects = null;
    target.tvNameOrganization = null;
    target.tvPersonnelNumber = null;
    target.tvPhoneNumber = null;
    target.tvAddress = null;
    target.tvUrgency = null;
    target.tvSubject = null;
    target.tvDescription = null;
    target.layoutSolution = null;
    target.tvSolution = null;
    target.rvReferenceParams = null;
    target.mBtnRevoke = null;
    target.mBtnRate = null;
    target.mBtnReturn = null;
    target.mLlRating = null;
    target.mLlDateEnd = null;
    target.mLlDatePlan = null;
    target.mSpinnerRating = null;
    target.mEtCommentRating = null;

    view2131361869.setOnClickListener(null);
    view2131361869 = null;
    view2131361865.setOnClickListener(null);
    view2131361865 = null;
    view2131361868.setOnClickListener(null);
    view2131361868 = null;
  }
}
