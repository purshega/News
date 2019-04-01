// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.business_trip;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.AdapterView;
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

public class AddTicketFragment_ViewBinding implements Unbinder {
  private AddTicketFragment target;

  private View view2131361948;

  private View view2131361950;

  private View view2131361884;

  private View view2131361949;

  private View view2131361951;

  private View view2131362227;

  private View view2131361861;

  private View view2131361870;

  @UiThread
  public AddTicketFragment_ViewBinding(final AddTicketFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.et_departure_date, "field 'etDepartureDate' and method 'onDateTimeClick'");
    target.etDepartureDate = Utils.castView(view, R.id.et_departure_date, "field 'etDepartureDate'", EditText.class);
    view2131361948 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_departure_time, "field 'etDepartureTime' and method 'onDateTimeClick'");
    target.etDepartureTime = Utils.castView(view, R.id.et_departure_time, "field 'etDepartureTime'", EditText.class);
    view2131361950 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    target.returnTripCbLayout = Utils.findRequiredViewAsType(source, R.id.return_trip_cb_layout, "field 'returnTripCbLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.cb_return_trip, "field 'cbReturnTrip' and method 'onClick'");
    target.cbReturnTrip = Utils.castView(view, R.id.cb_return_trip, "field 'cbReturnTrip'", CheckBox.class);
    view2131361884 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.returnTripLayout = Utils.findRequiredViewAsType(source, R.id.return_trip_layout, "field 'returnTripLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.et_departure_date_of_return_trip, "field 'etDepartureDateOfReturnTrip' and method 'onDateTimeClick'");
    target.etDepartureDateOfReturnTrip = Utils.castView(view, R.id.et_departure_date_of_return_trip, "field 'etDepartureDateOfReturnTrip'", EditText.class);
    view2131361949 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_departure_time_of_return_trip, "field 'etDepartureTimeOfReturnTrip' and method 'onDateTimeClick'");
    target.etDepartureTimeOfReturnTrip = Utils.castView(view, R.id.et_departure_time_of_return_trip, "field 'etDepartureTimeOfReturnTrip'", EditText.class);
    view2131361951 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.spinner_ticket_type, "field 'sTicketType' and method 'spinnerItemSelected'");
    target.sTicketType = Utils.castView(view, R.id.spinner_ticket_type, "field 'sTicketType'", Spinner.class);
    view2131362227 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.spinnerItemSelected(Utils.castParam(p0, "onItemSelected", 0, "spinnerItemSelected", 0, Spinner.class), p2);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
    target.acTvDestinationTo = Utils.findRequiredViewAsType(source, R.id.ac_tv_destination_to, "field 'acTvDestinationTo'", AutoCompleteTextView.class);
    target.pdDestinationToSearch = Utils.findRequiredViewAsType(source, R.id.pb_destination_to_search, "field 'pdDestinationToSearch'", ProgressBar.class);
    target.ivDestinationToList = Utils.findRequiredViewAsType(source, R.id.iv_destination_to_list, "field 'ivDestinationToList'", ImageView.class);
    target.acTvDestinationFrom = Utils.findRequiredViewAsType(source, R.id.ac_tv_destination_from, "field 'acTvDestinationFrom'", AutoCompleteTextView.class);
    target.pdDestinationFromSearch = Utils.findRequiredViewAsType(source, R.id.pb_destination_from_search, "field 'pdDestinationFromSearch'", ProgressBar.class);
    target.ivDestinationFromList = Utils.findRequiredViewAsType(source, R.id.iv_destination_from_list, "field 'ivDestinationFromList'", ImageView.class);
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
    AddTicketFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etDepartureDate = null;
    target.etDepartureTime = null;
    target.returnTripCbLayout = null;
    target.cbReturnTrip = null;
    target.returnTripLayout = null;
    target.etDepartureDateOfReturnTrip = null;
    target.etDepartureTimeOfReturnTrip = null;
    target.sTicketType = null;
    target.acTvDestinationTo = null;
    target.pdDestinationToSearch = null;
    target.ivDestinationToList = null;
    target.acTvDestinationFrom = null;
    target.pdDestinationFromSearch = null;
    target.ivDestinationFromList = null;
    target.etComment = null;
    target.btnDelete = null;

    view2131361948.setOnClickListener(null);
    view2131361948 = null;
    view2131361950.setOnClickListener(null);
    view2131361950 = null;
    view2131361884.setOnClickListener(null);
    view2131361884 = null;
    view2131361949.setOnClickListener(null);
    view2131361949 = null;
    view2131361951.setOnClickListener(null);
    view2131361951 = null;
    ((AdapterView<?>) view2131362227).setOnItemSelectedListener(null);
    view2131362227 = null;
    view2131361861.setOnClickListener(null);
    view2131361861 = null;
    view2131361870.setOnClickListener(null);
    view2131361870 = null;
  }
}
