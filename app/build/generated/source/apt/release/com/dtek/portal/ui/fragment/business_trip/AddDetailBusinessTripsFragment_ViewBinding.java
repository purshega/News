// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.business_trip;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddDetailBusinessTripsFragment_ViewBinding implements Unbinder {
  private AddDetailBusinessTripsFragment target;

  private View view2131361986;

  private View view2131361984;

  private View view2131362268;

  private View view2131362266;

  private View view2131361998;

  private View view2131361996;

  private View view2131361836;

  private View view2131361834;

  private View view2131361844;

  private View view2131362220;

  private View view2131361946;

  private View view2131361943;

  private View view2131361942;

  private View view2131361870;

  private View view2131361859;

  private View view2131361867;

  private View view2131361856;

  private View view2131361871;

  private View view2131361960;

  @UiThread
  public AddDetailBusinessTripsFragment_ViewBinding(final AddDetailBusinessTripsFragment target,
      View source) {
    this.target = target;

    View view;
    target.acTvEmployee = Utils.findRequiredViewAsType(source, R.id.et_employee, "field 'acTvEmployee'", AutoCompleteTextView.class);
    target.ivEmployeeCancel = Utils.findRequiredViewAsType(source, R.id.iv_employee_cancel, "field 'ivEmployeeCancel'", ImageView.class);
    target.spinnerBusinessTripType = Utils.findRequiredViewAsType(source, R.id.spinner_business_trip_type, "field 'spinnerBusinessTripType'", Spinner.class);
    target.pbEmployeeSearch = Utils.findRequiredViewAsType(source, R.id.pb_employee_search, "field 'pbEmployeeSearch'", ProgressBar.class);
    target.statusInfoLayout = Utils.findRequiredViewAsType(source, R.id.status_info_layout, "field 'statusInfoLayout'", LinearLayout.class);
    target.tvRequestNumber = Utils.findRequiredViewAsType(source, R.id.tv_request_number, "field 'tvRequestNumber'", TextView.class);
    target.tvRequestStatus = Utils.findRequiredViewAsType(source, R.id.tv_request_status, "field 'tvRequestStatus'", TextView.class);
    target.tvCreateDate = Utils.findRequiredViewAsType(source, R.id.tv_create_date, "field 'tvCreateDate'", TextView.class);
    view = Utils.findRequiredView(source, R.id.final_destinations_list_btn, "field 'finalDestinationsListBtn' and method 'onClickItem'");
    target.finalDestinationsListBtn = Utils.castView(view, R.id.final_destinations_list_btn, "field 'finalDestinationsListBtn'", ImageButton.class);
    view2131361986 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.final_destination_add_btn, "field 'ibFinalDestinationAddBtn' and method 'onClickAddItem'");
    target.ibFinalDestinationAddBtn = Utils.castView(view, R.id.final_destination_add_btn, "field 'ibFinalDestinationAddBtn'", ImageButton.class);
    view2131361984 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAddItem(p0);
      }
    });
    target.finalDestinationList = Utils.findRequiredViewAsType(source, R.id.final_destination_list, "field 'finalDestinationList'", LinearLayout.class);
    target.rvFinalDestinations = Utils.findRequiredViewAsType(source, R.id.rv_final_destination, "field 'rvFinalDestinations'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.tickets_list_btn, "field 'tickets_list_btn' and method 'onClickItem'");
    target.tickets_list_btn = Utils.castView(view, R.id.tickets_list_btn, "field 'tickets_list_btn'", ImageButton.class);
    view2131362268 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.ticket_add_btn, "field 'ibTicketAdBtn' and method 'onClickAddItem'");
    target.ibTicketAdBtn = Utils.castView(view, R.id.ticket_add_btn, "field 'ibTicketAdBtn'", ImageButton.class);
    view2131362266 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAddItem(p0);
      }
    });
    target.tickets_list = Utils.findRequiredViewAsType(source, R.id.tickets_list, "field 'tickets_list'", LinearLayout.class);
    target.rvTickets = Utils.findRequiredViewAsType(source, R.id.rv_tickets, "field 'rvTickets'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.hotels_list_btn, "field 'hotels_list_btn' and method 'onClickItem'");
    target.hotels_list_btn = Utils.castView(view, R.id.hotels_list_btn, "field 'hotels_list_btn'", ImageButton.class);
    view2131361998 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.hotel_add_btn, "field 'ibHotelAddBtn' and method 'onClickAddItem'");
    target.ibHotelAddBtn = Utils.castView(view, R.id.hotel_add_btn, "field 'ibHotelAddBtn'", ImageButton.class);
    view2131361996 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAddItem(p0);
      }
    });
    target.hotels_list = Utils.findRequiredViewAsType(source, R.id.hotels_list, "field 'hotels_list'", LinearLayout.class);
    target.rvHotels = Utils.findRequiredViewAsType(source, R.id.rv_hotels, "field 'rvHotels'", RecyclerView.class);
    view = Utils.findRequiredView(source, R.id.additional_services_list_btn, "field 'ib_Additional_Services_List_Btn' and method 'onClickItem'");
    target.ib_Additional_Services_List_Btn = Utils.castView(view, R.id.additional_services_list_btn, "field 'ib_Additional_Services_List_Btn'", ImageButton.class);
    view2131361836 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.additional_service_add_btn, "field 'ibAdditionalServiceAddBtn' and method 'onClickAddItem'");
    target.ibAdditionalServiceAddBtn = Utils.castView(view, R.id.additional_service_add_btn, "field 'ibAdditionalServiceAddBtn'", ImageButton.class);
    view2131361834 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickAddItem(p0);
      }
    });
    target.additional_services_list = Utils.findRequiredViewAsType(source, R.id.additional_services_list, "field 'additional_services_list'", LinearLayout.class);
    target.rvAdditionalServices = Utils.findRequiredViewAsType(source, R.id.rv_additional_services, "field 'rvAdditionalServices'", RecyclerView.class);
    target.approverLayout = Utils.findRequiredViewAsType(source, R.id.approver_layout, "field 'approverLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.approvers_list_btn, "field 'ibApproversListButton' and method 'onClickItem'");
    target.ibApproversListButton = Utils.castView(view, R.id.approvers_list_btn, "field 'ibApproversListButton'", ImageButton.class);
    view2131361844 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onClickItem(p0);
      }
    });
    target.approvers_list = Utils.findRequiredViewAsType(source, R.id.approvers_list, "field 'approvers_list'", LinearLayout.class);
    target.rvApprovers = Utils.findRequiredViewAsType(source, R.id.rv_approvers, "field 'rvApprovers'", RecyclerView.class);
    target.delayDateLayout = Utils.findRequiredViewAsType(source, R.id.delay_date_layout, "field 'delayDateLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.spinner_delay, "field 'spinnerDelay' and method 'spinnerItemSelected'");
    target.spinnerDelay = Utils.castView(view, R.id.spinner_delay, "field 'spinnerDelay'", Spinner.class);
    view2131362220 = view;
    ((AdapterView<?>) view).setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
      @Override
      public void onItemSelected(AdapterView<?> p0, View p1, int p2, long p3) {
        target.spinnerItemSelected(Utils.castParam(p0, "onItemSelected", 0, "spinnerItemSelected", 0, Spinner.class), p2);
      }

      @Override
      public void onNothingSelected(AdapterView<?> p0) {
      }
    });
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
    view = Utils.findRequiredView(source, R.id.et_date_delay, "field 'etDateDelay' and method 'onDateClick'");
    target.etDateDelay = Utils.castView(view, R.id.et_date_delay, "field 'etDateDelay'", EditText.class);
    view2131361942 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    target.tvFinalDestinationsCount = Utils.findRequiredViewAsType(source, R.id.tv_final_destination_count, "field 'tvFinalDestinationsCount'", TextView.class);
    target.tvTicketsCount = Utils.findRequiredViewAsType(source, R.id.tv_tickets_count, "field 'tvTicketsCount'", TextView.class);
    target.tvHotelCount = Utils.findRequiredViewAsType(source, R.id.tv_hotel_count, "field 'tvHotelCount'", TextView.class);
    target.tvAdditionalServicesCount = Utils.findRequiredViewAsType(source, R.id.tv_additional_services_count, "field 'tvAdditionalServicesCount'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btn_save, "field 'btnSave' and method 'onButtonClick'");
    target.btnSave = Utils.castView(view, R.id.btn_save, "field 'btnSave'", Button.class);
    view2131361870 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_change, "field 'btnChange' and method 'onButtonClick'");
    target.btnChange = Utils.castView(view, R.id.btn_change, "field 'btnChange'", Button.class);
    view2131361859 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_recall, "field 'btnRecall' and method 'onButtonClick'");
    target.btnRecall = Utils.castView(view, R.id.btn_recall, "field 'btnRecall'", Button.class);
    view2131361867 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    target.btnDelete = Utils.findRequiredViewAsType(source, R.id.btn_delete, "field 'btnDelete'", Button.class);
    view = Utils.findRequiredView(source, R.id.btn_approving, "field 'btnApproving' and method 'onButtonClick'");
    target.btnApproving = Utils.castView(view, R.id.btn_approving, "field 'btnApproving'", Button.class);
    view2131361856 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_save_changes, "field 'btnSaveChanges' and method 'onButtonClick'");
    target.btnSaveChanges = Utils.castView(view, R.id.btn_save_changes, "field 'btnSaveChanges'", Button.class);
    view2131361871 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    target.buttonsLayout = Utils.findRequiredViewAsType(source, R.id.buttons_layout, "field 'buttonsLayout'", ConstraintLayout.class);
    target.passportDataLayout = Utils.findRequiredViewAsType(source, R.id.passport_data_layout, "field 'passportDataLayout'", LinearLayout.class);
    target.etPassportNumber = Utils.findRequiredViewAsType(source, R.id.et_passport_number, "field 'etPassportNumber'", EditText.class);
    target.passportNumberLayout = Utils.findRequiredViewAsType(source, R.id.passport_number_layout, "field 'passportNumberLayout'", LinearLayout.class);
    target.passportValidityLayout = Utils.findRequiredViewAsType(source, R.id.passport_validity_layout, "field 'passportValidityLayout'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.et_passport_validity, "field 'etPassportValidity' and method 'onDateClick'");
    target.etPassportValidity = Utils.castView(view, R.id.et_passport_validity, "field 'etPassportValidity'", EditText.class);
    view2131361960 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AddDetailBusinessTripsFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.acTvEmployee = null;
    target.ivEmployeeCancel = null;
    target.spinnerBusinessTripType = null;
    target.pbEmployeeSearch = null;
    target.statusInfoLayout = null;
    target.tvRequestNumber = null;
    target.tvRequestStatus = null;
    target.tvCreateDate = null;
    target.finalDestinationsListBtn = null;
    target.ibFinalDestinationAddBtn = null;
    target.finalDestinationList = null;
    target.rvFinalDestinations = null;
    target.tickets_list_btn = null;
    target.ibTicketAdBtn = null;
    target.tickets_list = null;
    target.rvTickets = null;
    target.hotels_list_btn = null;
    target.ibHotelAddBtn = null;
    target.hotels_list = null;
    target.rvHotels = null;
    target.ib_Additional_Services_List_Btn = null;
    target.ibAdditionalServiceAddBtn = null;
    target.additional_services_list = null;
    target.rvAdditionalServices = null;
    target.approverLayout = null;
    target.ibApproversListButton = null;
    target.approvers_list = null;
    target.rvApprovers = null;
    target.delayDateLayout = null;
    target.spinnerDelay = null;
    target.etDateStart = null;
    target.etDateEnd = null;
    target.etDateDelay = null;
    target.tvFinalDestinationsCount = null;
    target.tvTicketsCount = null;
    target.tvHotelCount = null;
    target.tvAdditionalServicesCount = null;
    target.btnSave = null;
    target.btnChange = null;
    target.btnRecall = null;
    target.btnDelete = null;
    target.btnApproving = null;
    target.btnSaveChanges = null;
    target.buttonsLayout = null;
    target.passportDataLayout = null;
    target.etPassportNumber = null;
    target.passportNumberLayout = null;
    target.passportValidityLayout = null;
    target.etPassportValidity = null;

    view2131361986.setOnClickListener(null);
    view2131361986 = null;
    view2131361984.setOnClickListener(null);
    view2131361984 = null;
    view2131362268.setOnClickListener(null);
    view2131362268 = null;
    view2131362266.setOnClickListener(null);
    view2131362266 = null;
    view2131361998.setOnClickListener(null);
    view2131361998 = null;
    view2131361996.setOnClickListener(null);
    view2131361996 = null;
    view2131361836.setOnClickListener(null);
    view2131361836 = null;
    view2131361834.setOnClickListener(null);
    view2131361834 = null;
    view2131361844.setOnClickListener(null);
    view2131361844 = null;
    ((AdapterView<?>) view2131362220).setOnItemSelectedListener(null);
    view2131362220 = null;
    view2131361946.setOnClickListener(null);
    view2131361946 = null;
    view2131361943.setOnClickListener(null);
    view2131361943 = null;
    view2131361942.setOnClickListener(null);
    view2131361942 = null;
    view2131361870.setOnClickListener(null);
    view2131361870 = null;
    view2131361859.setOnClickListener(null);
    view2131361859 = null;
    view2131361867.setOnClickListener(null);
    view2131361867 = null;
    view2131361856.setOnClickListener(null);
    view2131361856 = null;
    view2131361871.setOnClickListener(null);
    view2131361871 = null;
    view2131361960.setOnClickListener(null);
    view2131361960 = null;
  }
}
