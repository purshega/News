// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.content.Context;
import android.content.res.Resources;
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

public class StatusWorkFragment_ViewBinding implements Unbinder {
  private StatusWorkFragment target;

  private View view2131361858;

  private View view2131361864;

  private View view2131361941;

  private View view2131361968;

  @UiThread
  public StatusWorkFragment_ViewBinding(final StatusWorkFragment target, View source) {
    this.target = target;

    View view;
    target.mRvStatus = Utils.findRequiredViewAsType(source, R.id.rv_status, "field 'mRvStatus'", RecyclerView.class);
    target.mSpinnerCause = Utils.findRequiredViewAsType(source, R.id.spinner_cause, "field 'mSpinnerCause'", Spinner.class);
    target.mLlCause = Utils.findRequiredViewAsType(source, R.id.ll_cause, "field 'mLlCause'", LinearLayout.class);
    target.mEtNote = Utils.findRequiredViewAsType(source, R.id.et_note, "field 'mEtNote'", EditText.class);
    target.mLlNote = Utils.findRequiredViewAsType(source, R.id.ll_note, "field 'mLlNote'", LinearLayout.class);
    target.llDateTime = Utils.findRequiredViewAsType(source, R.id.ll_date_time, "field 'llDateTime'", LinearLayout.class);
    view = Utils.findRequiredView(source, R.id.btn_cancel, "field 'mBtnCancel' and method 'onButtonClicked'");
    target.mBtnCancel = Utils.castView(view, R.id.btn_cancel, "field 'mBtnCancel'", Button.class);
    view2131361858 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClicked(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btn_ok, "field 'mBtnOk' and method 'onButtonClicked'");
    target.mBtnOk = Utils.castView(view, R.id.btn_ok, "field 'mBtnOk'", Button.class);
    view2131361864 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClicked(p0);
      }
    });
    target.tvReason = Utils.findRequiredViewAsType(source, R.id.tv_reason, "field 'tvReason'", TextView.class);
    view = Utils.findRequiredView(source, R.id.et_date, "field 'etDate' and method 'onDateTimeClick'");
    target.etDate = Utils.castView(view, R.id.et_date, "field 'etDate'", EditText.class);
    view2131361941 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.et_time, "field 'etTime' and method 'onDateTimeClick'");
    target.etTime = Utils.castView(view, R.id.et_time, "field 'etTime'", EditText.class);
    view2131361968 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onDateTimeClick(p0);
      }
    });

    Context context = source.getContext();
    Resources res = context.getResources();
    target.takeQueue = res.getString(R.string.text_status_take_queue);
    target.takeWork = res.getString(R.string.text_status_take_work);
    target.rejectIncorrectly = res.getString(R.string.text_status_reject_incorrectly);
    target.rejectReturn = res.getString(R.string.text_status_reject_return);
    target.reject = res.getString(R.string.text_status_reject);
    target.wait = res.getString(R.string.text_status_wait);
    target.done = res.getString(R.string.text_status_done);
    target.inWork = res.getString(R.string.text_status_in_work);
  }

  @Override
  @CallSuper
  public void unbind() {
    StatusWorkFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mRvStatus = null;
    target.mSpinnerCause = null;
    target.mLlCause = null;
    target.mEtNote = null;
    target.mLlNote = null;
    target.llDateTime = null;
    target.mBtnCancel = null;
    target.mBtnOk = null;
    target.tvReason = null;
    target.etDate = null;
    target.etTime = null;

    view2131361858.setOnClickListener(null);
    view2131361858 = null;
    view2131361864.setOnClickListener(null);
    view2131361864 = null;
    view2131361941.setOnClickListener(null);
    view2131361941 = null;
    view2131361968.setOnClickListener(null);
    view2131361968 = null;
  }
}
