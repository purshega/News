// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.car;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddDetailCarFragment_ViewBinding implements Unbinder {
  private AddDetailCarFragment target;

  private View view2131362000;

  private View view2131362246;

  private View view2131362247;

  private View view2131361852;

  private View view2131361946;

  private View view2131361971;

  private View view2131361943;

  private View view2131361969;

  private View view2131361853;

  private View view2131361862;

  private View view2131361860;

  private View view2131361869;

  private View view2131361866;

  @UiThread
  public AddDetailCarFragment_ViewBinding(final AddDetailCarFragment target, View source) {
    this.target = target;

    View view;
    target.mLlAll = Utils.findRequiredViewAsType(source, R.id.ll_all, "field 'mLlAll'", LinearLayout.class);
    target.mLlHeader = Utils.findRequiredViewAsType(source, R.id.ll_header, "field 'mLlHeader'", LinearLayout.class);
    target.mTvNumbOrder = Utils.findRequiredViewAsType(source, R.id.tv_numb_order, "field 'mTvNumbOrder'", TextView.class);
    target.mTvStatusOrder = Utils.findRequiredViewAsType(source, R.id.tv_status_order, "field 'mTvStatusOrder'", TextView.class);
    target.mTvDateOrder = Utils.findRequiredViewAsType(source, R.id.tv_date_order, "field 'mTvDateOrder'", TextView.class);
    view = Utils.findRequiredView(source, R.id.ibtn_car_now, "field 'mIbtnCarNow' and method 'onMapClicked'");
    target.mIbtnCarNow = Utils.castView(view, R.id.ibtn_car_now, "field 'mIbtnCarNow'", ImageButton.class);
    view2131362000 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onMapClicked();
      }
    });
    target.mSpinnerСity = Utils.findRequiredViewAsType(source, R.id.spinner_city, "field 'mSpinnerСity'", Spinner.class);
    target.mEtPhone = Utils.findRequiredViewAsType(source, R.id.et_phone, "field 'mEtPhone'", EditText.class);
    target.mSpinnerDirection = Utils.findRequiredViewAsType(source, R.id.spinner_direction, "field 'mSpinnerDirection'", Spinner.class);
    view = Utils.findRequiredView(source, R.id.switch_me, "field 'mSwitchMe' and method 'onCheckedFor'");
    target.mSwitchMe = Utils.castView(view, R.id.switch_me, "field 'mSwitchMe'", Switch.class);
    view2131362246 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedFor(p0, p1);
      }
    });
    view = Utils.findRequiredView(source, R.id.switch_other, "field 'mSwitchOther' and method 'onCheckedFor'");
    target.mSwitchOther = Utils.castView(view, R.id.switch_other, "field 'mSwitchOther'", Switch.class);
    view2131362247 = view;
    ((CompoundButton) view).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
      @Override
      public void onCheckedChanged(CompoundButton p0, boolean p1) {
        target.onCheckedFor(p0, p1);
      }
    });
    target.mLlNumbPassenger = Utils.findRequiredViewAsType(source, R.id.ll_numb_passenger, "field 'mLlNumbPassenger'", LinearLayout.class);
    target.mEtNumbPassenger = Utils.findRequiredViewAsType(source, R.id.et_numb_passenger, "field 'mEtNumbPassenger'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_add_passenger, "field 'mBtnAddPassenger' and method 'onAddPassengerClick'");
    target.mBtnAddPassenger = Utils.castView(view, R.id.btn_add_passenger, "field 'mBtnAddPassenger'", Button.class);
    view2131361852 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddPassengerClick(Utils.castParam(p0, "doClick", 0, "onAddPassengerClick", 0, Button.class));
      }
    });
    target.mRvPassenger = Utils.findRequiredViewAsType(source, R.id.rv_passenger, "field 'mRvPassenger'", RecyclerView.class);
    target.mEtPointStart = Utils.findRequiredViewAsType(source, R.id.et_point_start, "field 'mEtPointStart'", EditText.class);
    view = Utils.findRequiredView(source, R.id.et_date_start, "field 'mEtDateStart' and method 'onDateClick'");
    target.mEtDateStart = Utils.castView(view, R.id.et_date_start, "field 'mEtDateStart'", EditText.class);
    view2131361946 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_time_start, "field 'mEtTimeStart' and method 'onDateClick'");
    target.mEtTimeStart = Utils.castView(view, R.id.et_time_start, "field 'mEtTimeStart'", EditText.class);
    view2131361971 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    target.mEtPointEnd = Utils.findRequiredViewAsType(source, R.id.et_point_end, "field 'mEtPointEnd'", EditText.class);
    view = Utils.findRequiredView(source, R.id.et_date_end, "field 'mEtDateEnd' and method 'onDateClick'");
    target.mEtDateEnd = Utils.castView(view, R.id.et_date_end, "field 'mEtDateEnd'", EditText.class);
    view2131361943 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_time_end, "field 'mEtTimeEnd' and method 'onDateClick'");
    target.mEtTimeEnd = Utils.castView(view, R.id.et_time_end, "field 'mEtTimeEnd'", EditText.class);
    view2131361969 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_add_point, "field 'mBtnAddPoint' and method 'onAddPointClick'");
    target.mBtnAddPoint = Utils.castView(view, R.id.btn_add_point, "field 'mBtnAddPoint'", Button.class);
    view2131361853 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onAddPointClick(Utils.castParam(p0, "doClick", 0, "onAddPointClick", 0, Button.class));
      }
    });
    target.mRvPoint = Utils.findRequiredViewAsType(source, R.id.rv_point, "field 'mRvPoint'", RecyclerView.class);
    target.mEtComment = Utils.findRequiredViewAsType(source, R.id.et_comment, "field 'mEtComment'", EditText.class);
    target.mLlDriver = Utils.findRequiredViewAsType(source, R.id.ll_driver, "field 'mLlDriver'", LinearLayout.class);
    target.mTvDriver = Utils.findRequiredViewAsType(source, R.id.tv_driver, "field 'mTvDriver'", TextView.class);
    target.mTvCar = Utils.findRequiredViewAsType(source, R.id.tv_car, "field 'mTvCar'", TextView.class);
    target.mTvComment = Utils.findRequiredViewAsType(source, R.id.tv_comment, "field 'mTvComment'", TextView.class);
    target.mLlRating = Utils.findRequiredViewAsType(source, R.id.ll_rating, "field 'mLlRating'", LinearLayout.class);
    target.mSpinnerRating = Utils.findRequiredViewAsType(source, R.id.spinner_rating_car, "field 'mSpinnerRating'", Spinner.class);
    target.mEtCommentRating = Utils.findRequiredViewAsType(source, R.id.et_comment_rating, "field 'mEtCommentRating'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_draft, "field 'mBtnDraft' and method 'onButtonClick'");
    target.mBtnDraft = Utils.castView(view, R.id.btn_draft, "field 'mBtnDraft'", Button.class);
    view2131361862 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_confirm, "field 'mBtnConfirm' and method 'onButtonClick'");
    target.mBtnConfirm = Utils.castView(view, R.id.btn_confirm, "field 'mBtnConfirm'", Button.class);
    view2131361860 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_revoke, "field 'mBtnCancel' and method 'onButtonClick'");
    target.mBtnCancel = Utils.castView(view, R.id.btn_revoke, "field 'mBtnCancel'", Button.class);
    view2131361869 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_rating, "field 'mBtnRating' and method 'onButtonClick'");
    target.mBtnRating = Utils.castView(view, R.id.btn_rating, "field 'mBtnRating'", Button.class);
    view2131361866 = view;
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
    AddDetailCarFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mLlAll = null;
    target.mLlHeader = null;
    target.mTvNumbOrder = null;
    target.mTvStatusOrder = null;
    target.mTvDateOrder = null;
    target.mIbtnCarNow = null;
    target.mSpinnerСity = null;
    target.mEtPhone = null;
    target.mSpinnerDirection = null;
    target.mSwitchMe = null;
    target.mSwitchOther = null;
    target.mLlNumbPassenger = null;
    target.mEtNumbPassenger = null;
    target.mBtnAddPassenger = null;
    target.mRvPassenger = null;
    target.mEtPointStart = null;
    target.mEtDateStart = null;
    target.mEtTimeStart = null;
    target.mEtPointEnd = null;
    target.mEtDateEnd = null;
    target.mEtTimeEnd = null;
    target.mBtnAddPoint = null;
    target.mRvPoint = null;
    target.mEtComment = null;
    target.mLlDriver = null;
    target.mTvDriver = null;
    target.mTvCar = null;
    target.mTvComment = null;
    target.mLlRating = null;
    target.mSpinnerRating = null;
    target.mEtCommentRating = null;
    target.mBtnDraft = null;
    target.mBtnConfirm = null;
    target.mBtnCancel = null;
    target.mBtnRating = null;

    view2131362000.setOnClickListener(null);
    view2131362000 = null;
    ((CompoundButton) view2131362246).setOnCheckedChangeListener(null);
    view2131362246 = null;
    ((CompoundButton) view2131362247).setOnCheckedChangeListener(null);
    view2131362247 = null;
    view2131361852.setOnClickListener(null);
    view2131361852 = null;
    view2131361946.setOnClickListener(null);
    view2131361946 = null;
    view2131361971.setOnClickListener(null);
    view2131361971 = null;
    view2131361943.setOnClickListener(null);
    view2131361943 = null;
    view2131361969.setOnClickListener(null);
    view2131361969 = null;
    view2131361853.setOnClickListener(null);
    view2131361853 = null;
    view2131361862.setOnClickListener(null);
    view2131361862 = null;
    view2131361860.setOnClickListener(null);
    view2131361860 = null;
    view2131361869.setOnClickListener(null);
    view2131361869 = null;
    view2131361866.setOnClickListener(null);
    view2131361866 = null;
  }
}
