// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.hr_vacation;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
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

public class CreateVacationFragment_ViewBinding implements Unbinder {
  private CreateVacationFragment target;

  private View view2131362222;

  private View view2131362230;

  private View view2131361946;

  private View view2131361943;

  private View view2131361872;

  @UiThread
  public CreateVacationFragment_ViewBinding(final CreateVacationFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.spinner_location, "field 'mSpinnerLocation' and method 'spinnerItemSelected'");
    target.mSpinnerLocation = Utils.castView(view, R.id.spinner_location, "field 'mSpinnerLocation'", Spinner.class);
    view2131362222 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.spinnerItemSelected(Utils.castParam(p0, "onItemSelected", 0, "spinnerItemSelected", 0, Spinner.class), p2);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
    view = Utils.findRequiredView(source, R.id.spinner_vacation, "field 'mSpinnerVacation' and method 'spinnerItemSelected'");
    target.mSpinnerVacation = Utils.castView(view, R.id.spinner_vacation, "field 'mSpinnerVacation'", Spinner.class);
    view2131362230 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.spinnerItemSelected(Utils.castParam(p0, "onItemSelected", 0, "spinnerItemSelected", 0, Spinner.class), p2);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
    target.mTvBalanceDay = Utils.findRequiredViewAsType(source, R.id.tv_balance_day, "field 'mTvBalanceDay'", TextView.class);
    target.mLlPrepaidDay = Utils.findRequiredViewAsType(source, R.id.ll_prepaid_day, "field 'mLlPrepaidDay'", LinearLayout.class);
    target.mTvPrepaidDay = Utils.findRequiredViewAsType(source, R.id.tv_prepaid_day, "field 'mTvPrepaidDay'", TextView.class);
    view = Utils.findRequiredView(source, R.id.et_date_start, "field 'mEtDateStart' and method 'onDateClick'");
    target.mEtDateStart = Utils.castView(view, R.id.et_date_start, "field 'mEtDateStart'", EditText.class);
    view2131361946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_date_end, "field 'mEtDateEnd' and method 'onDateClick'");
    target.mEtDateEnd = Utils.castView(view, R.id.et_date_end, "field 'mEtDateEnd'", EditText.class);
    view2131361943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    target.mTvTotalDays = Utils.findRequiredViewAsType(source, R.id.tv_total_days, "field 'mTvTotalDays'", TextView.class);
    target.mCbSkipChief = Utils.findRequiredViewAsType(source, R.id.cb_skip_chief, "field 'mCbSkipChief'", CheckBox.class);
    target.mEtDescription = Utils.findRequiredViewAsType(source, R.id.et_description, "field 'mEtDescription'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_send, "method 'onButtonClick'");
    view2131361872 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    CreateVacationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mSpinnerLocation = null;
    target.mSpinnerVacation = null;
    target.mTvBalanceDay = null;
    target.mLlPrepaidDay = null;
    target.mTvPrepaidDay = null;
    target.mEtDateStart = null;
    target.mEtDateEnd = null;
    target.mTvTotalDays = null;
    target.mCbSkipChief = null;
    target.mEtDescription = null;

    ((AdapterView<?>) view2131362222).setOnItemSelectedListener(null);
    view2131362222 = null;
    ((AdapterView<?>) view2131362230).setOnItemSelectedListener(null);
    view2131362230 = null;
    view2131361946.setOnClickListener(null);
    view2131361946 = null;
    view2131361943.setOnClickListener(null);
    view2131361943 = null;
    view2131361872.setOnClickListener(null);
    view2131361872 = null;
  }
}
