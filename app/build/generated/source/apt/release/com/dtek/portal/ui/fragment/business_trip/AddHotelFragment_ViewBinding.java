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
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddHotelFragment_ViewBinding implements Unbinder {
  private AddHotelFragment target;

  private View view2131361933;

  private View view2131361881;

  private View view2131361953;

  private View view2131361944;

  private View view2131361882;

  private View view2131361955;

  private View view2131361861;

  private View view2131361870;

  @UiThread
  public AddHotelFragment_ViewBinding(final AddHotelFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.et_arrival_date, "field 'etArrivalDate' and method 'onDateTimeClick'");
    target.etArrivalDate = Utils.castView(view, R.id.et_arrival_date, "field 'etArrivalDate'", EditText.class);
    view2131361933 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cb_early_arrival, "field 'cbEarlyArrival' and method 'onClick'");
    target.cbEarlyArrival = Utils.castView(view, R.id.cb_early_arrival, "field 'cbEarlyArrival'", CheckBox.class);
    view2131361881 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.earlyArrivalLayout = Utils.findRequiredViewAsType(source, R.id.early_arrival_layout, "field 'earlyArrivalLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.et_early_arrival_time, "field 'etEarlyArrivalTime' and method 'onDateTimeClick'");
    target.etEarlyArrivalTime = Utils.castView(view, R.id.et_early_arrival_time, "field 'etEarlyArrivalTime'", EditText.class);
    view2131361953 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_date_of_departure, "field 'etDateOfDeparture' and method 'onDateTimeClick'");
    target.etDateOfDeparture = Utils.castView(view, R.id.et_date_of_departure, "field 'etDateOfDeparture'", EditText.class);
    view2131361944 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.cb_late_departure, "field 'cbLateDeparture' and method 'onClick'");
    target.cbLateDeparture = Utils.castView(view, R.id.cb_late_departure, "field 'cbLateDeparture'", CheckBox.class);
    view2131361882 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClick(p0);
      }
    });
    target.lateDepartureLayout = Utils.findRequiredViewAsType(source, R.id.late_departure_layout, "field 'lateDepartureLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.et_late_departure_time, "field 'etLateDepartureTime' and method 'onDateTimeClick'");
    target.etLateDepartureTime = Utils.castView(view, R.id.et_late_departure_time, "field 'etLateDepartureTime'", EditText.class);
    view2131361955 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    target.acTvDestination = Utils.findRequiredViewAsType(source, R.id.ac_tv_destination, "field 'acTvDestination'", AutoCompleteTextView.class);
    target.ivDestinationsList = Utils.findRequiredViewAsType(source, R.id.iv_destination_list, "field 'ivDestinationsList'", ImageView.class);
    target.pbDestinationsSearch = Utils.findRequiredViewAsType(source, R.id.pb_destination_search, "field 'pbDestinationsSearch'", ProgressBar.class);
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
    AddHotelFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.etArrivalDate = null;
    target.cbEarlyArrival = null;
    target.earlyArrivalLayout = null;
    target.etEarlyArrivalTime = null;
    target.etDateOfDeparture = null;
    target.cbLateDeparture = null;
    target.lateDepartureLayout = null;
    target.etLateDepartureTime = null;
    target.acTvDestination = null;
    target.ivDestinationsList = null;
    target.pbDestinationsSearch = null;
    target.etComment = null;
    target.btnDelete = null;

    view2131361933.setOnClickListener(null);
    view2131361933 = null;
    view2131361881.setOnClickListener(null);
    view2131361881 = null;
    view2131361953.setOnClickListener(null);
    view2131361953 = null;
    view2131361944.setOnClickListener(null);
    view2131361944 = null;
    view2131361882.setOnClickListener(null);
    view2131361882 = null;
    view2131361955.setOnClickListener(null);
    view2131361955 = null;
    view2131361861.setOnClickListener(null);
    view2131361861 = null;
    view2131361870.setOnClickListener(null);
    view2131361870 = null;
  }
}
