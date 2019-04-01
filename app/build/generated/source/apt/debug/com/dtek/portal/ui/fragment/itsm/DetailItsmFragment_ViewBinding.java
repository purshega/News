// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.itsm;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailItsmFragment_ViewBinding implements Unbinder {
  private DetailItsmFragment target;

  private View view2131361869;

  private View view2131361865;

  private View view2131361868;

  private View view2131362223;

  private View view2131361937;

  private TextWatcher view2131361937TextWatcher;

  @UiThread
  public DetailItsmFragment_ViewBinding(final DetailItsmFragment target, View source) {
    this.target = target;

    View view;
    target.mTvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'mTvNumber'", TextView.class);
    target.mTvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'mTvStatus'", TextView.class);
    target.mTvInitiator = Utils.findRequiredViewAsType(source, R.id.tv_initiator, "field 'mTvInitiator'", TextView.class);
    target.mTvDateStart = Utils.findRequiredViewAsType(source, R.id.tv_date_start, "field 'mTvDateStart'", TextView.class);
    target.mLlDatePlan = Utils.findRequiredViewAsType(source, R.id.ll_date_plan, "field 'mLlDatePlan'", LinearLayout.class);
    target.mTvDatePlan = Utils.findRequiredViewAsType(source, R.id.tv_date_plan, "field 'mTvDatePlan'", TextView.class);
    target.mLlDateEnd = Utils.findRequiredViewAsType(source, R.id.ll_date_end, "field 'mLlDateEnd'", LinearLayout.class);
    target.mTvDateEnd = Utils.findRequiredViewAsType(source, R.id.tv_date_end, "field 'mTvDateEnd'", TextView.class);
    target.mTvAffects = Utils.findRequiredViewAsType(source, R.id.tv_name_affects, "field 'mTvAffects'", TextView.class);
    target.mTvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'mTvAddress'", TextView.class);
    target.mTvUrgency = Utils.findRequiredViewAsType(source, R.id.tv_urgency, "field 'mTvUrgency'", TextView.class);
    target.mTvSubject = Utils.findRequiredViewAsType(source, R.id.tv_subject, "field 'mTvSubject'", TextView.class);
    target.mTvDescription = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'mTvDescription'", TextView.class);
    target.mTvSolution = Utils.findRequiredViewAsType(source, R.id.tv_solution, "field 'mTvSolution'", TextView.class);
    target.mLlRating = Utils.findRequiredViewAsType(source, R.id.ll_rating, "field 'mLlRating'", LinearLayout.class);
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
    view = Utils.findRequiredView(source, R.id.spinner_rating, "field 'mSpinnerRating'");
    target.mSpinnerRating = Utils.castView(view, R.id.spinner_rating, "field 'mSpinnerRating'", Spinner.class);
    view2131362223 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.spinnerItemSelected(p2);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
    view = Utils.findRequiredView(source, R.id.et_comment_rating, "field 'mEtCommentRating'");
    target.mEtCommentRating = Utils.castView(view, R.id.et_comment_rating, "field 'mEtCommentRating'", EditText.class);
    view2131361937 = view;
    view2131361937TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.onTextChanged(p0, p1, p2, p3);
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view2131361937TextWatcher);
    target.layoutSolution = Utils.findRequiredViewAsType(source, R.id.layout_solution, "field 'layoutSolution'", LinearLayout.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailItsmFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvNumber = null;
    target.mTvStatus = null;
    target.mTvInitiator = null;
    target.mTvDateStart = null;
    target.mLlDatePlan = null;
    target.mTvDatePlan = null;
    target.mLlDateEnd = null;
    target.mTvDateEnd = null;
    target.mTvAffects = null;
    target.mTvAddress = null;
    target.mTvUrgency = null;
    target.mTvSubject = null;
    target.mTvDescription = null;
    target.mTvSolution = null;
    target.mLlRating = null;
    target.mBtnRevoke = null;
    target.mBtnRate = null;
    target.mBtnReturn = null;
    target.mSpinnerRating = null;
    target.mEtCommentRating = null;
    target.layoutSolution = null;

    view2131361869.setOnClickListener(null);
    view2131361869 = null;
    view2131361865.setOnClickListener(null);
    view2131361865 = null;
    view2131361868.setOnClickListener(null);
    view2131361868 = null;
    ((AdapterView<?>) view2131362223).setOnItemSelectedListener(null);
    view2131362223 = null;
    ((TextView) view2131361937).removeTextChangedListener(view2131361937TextWatcher);
    view2131361937TextWatcher = null;
    view2131361937 = null;
  }
}
