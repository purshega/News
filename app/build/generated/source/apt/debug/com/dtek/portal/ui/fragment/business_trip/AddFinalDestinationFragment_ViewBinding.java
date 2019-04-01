// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.business_trip;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddFinalDestinationFragment_ViewBinding implements Unbinder {
  private AddFinalDestinationFragment target;

  private View view2131361946;

  private View view2131361943;

  private View view2131361883;

  private View view2131361879;

  private View view2131361940;

  private View view2131361939;

  private View view2131361861;

  private View view2131361870;

  @UiThread
  public AddFinalDestinationFragment_ViewBinding(final AddFinalDestinationFragment target,
      View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.et_date_start, "field 'etDateStart' and method 'onDateClick'");
    target.etDateStart = Utils.castView(view, R.id.et_date_start, "field 'etDateStart'", EditText.class);
    view2131361946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_date_end, "field 'etDateEnd' and method 'onDateClick'");
    target.etDateEnd = Utils.castView(view, R.id.et_date_end, "field 'etDateEnd'", EditText.class);
    view2131361943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cb_name_is_absent, "field 'cbNameIsAbsent' and method 'onClick'");
    target.cbNameIsAbsent = Utils.castView(view, R.id.cb_name_is_absent, "field 'cbNameIsAbsent'", CheckBox.class);
    view2131361883 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.absentCompanyNameLayout = Utils.findRequiredViewAsType(source, R.id.absent_company_name_layout, "field 'absentCompanyNameLayout'", LinearLayout.class);
    target.etAbsentCompanyName = Utils.findRequiredViewAsType(source, R.id.et_absent_company_name, "field 'etAbsentCompanyName'", EditText.class);
    target.cbCompensationLayout = Utils.findRequiredViewAsType(source, R.id.cb_compensation_layout, "field 'cbCompensationLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.cb_compensation, "field 'cbCompensation' and method 'onClick'");
    target.cbCompensation = Utils.castView(view, R.id.cb_compensation, "field 'cbCompensation'", CheckBox.class);
    view2131361879 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.compensationLayout = Utils.findRequiredViewAsType(source, R.id.compensation_layout, "field 'compensationLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.et_compensation_start_date, "field 'etCompensationStartDate' and method 'onDateClick'");
    target.etCompensationStartDate = Utils.castView(view, R.id.et_compensation_start_date, "field 'etCompensationStartDate'", EditText.class);
    view2131361940 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_compensation_end_date, "field 'etCompensationEndDate' and method 'onDateClick'");
    target.etCompensationEndDate = Utils.castView(view, R.id.et_compensation_end_date, "field 'etCompensationEndDate'", EditText.class);
    view2131361939 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    target.acTvFinalDestination = Utils.findRequiredViewAsType(source, R.id.ac_tv_final_destination, "field 'acTvFinalDestination'", AutoCompleteTextView.class);
    target.ivDestinationsList = Utils.findRequiredViewAsType(source, R.id.iv_destinations_list, "field 'ivDestinationsList'", ImageView.class);
    target.pbDestinationsSearch = Utils.findRequiredViewAsType(source, R.id.pb_destinations_search, "field 'pbDestinationsSearch'", ProgressBar.class);
    target.acTvOrganizations = Utils.findRequiredViewAsType(source, R.id.ac_tv_organizations, "field 'acTvOrganizations'", AutoCompleteTextView.class);
    target.ivOrganizationsList = Utils.findRequiredViewAsType(source, R.id.iv_organizations_list, "field 'ivOrganizationsList'", ImageView.class);
    target.pbOrganizationsSearch = Utils.findRequiredViewAsType(source, R.id.pb_organizations_search, "field 'pbOrganizationsSearch'", ProgressBar.class);
    target.sBusinessTripGoal = Utils.findRequiredViewAsType(source, R.id.business_trip_goal, "field 'sBusinessTripGoal'", Spinner.class);
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
    AddFinalDestinationFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etDateStart = null;
    target.etDateEnd = null;
    target.cbNameIsAbsent = null;
    target.absentCompanyNameLayout = null;
    target.etAbsentCompanyName = null;
    target.cbCompensationLayout = null;
    target.cbCompensation = null;
    target.compensationLayout = null;
    target.etCompensationStartDate = null;
    target.etCompensationEndDate = null;
    target.acTvFinalDestination = null;
    target.ivDestinationsList = null;
    target.pbDestinationsSearch = null;
    target.acTvOrganizations = null;
    target.ivOrganizationsList = null;
    target.pbOrganizationsSearch = null;
    target.sBusinessTripGoal = null;
    target.btnDelete = null;

    view2131361946.setOnClickListener(null);
    view2131361946 = null;
    view2131361943.setOnClickListener(null);
    view2131361943 = null;
    view2131361883.setOnClickListener(null);
    view2131361883 = null;
    view2131361879.setOnClickListener(null);
    view2131361879 = null;
    view2131361940.setOnClickListener(null);
    view2131361940 = null;
    view2131361939.setOnClickListener(null);
    view2131361939 = null;
    view2131361861.setOnClickListener(null);
    view2131361861 = null;
    view2131361870.setOnClickListener(null);
    view2131361870 = null;
  }
}
