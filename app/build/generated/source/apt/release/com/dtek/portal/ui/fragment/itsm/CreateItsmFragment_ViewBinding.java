// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.itsm;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.CharSequence;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CreateItsmFragment_ViewBinding implements Unbinder {
  private CreateItsmFragment target;

  private View view2131361830;

  private TextWatcher view2131361830TextWatcher;

  private View view2131362019;

  private View view2131361860;

  @UiThread
  public CreateItsmFragment_ViewBinding(final CreateItsmFragment target, View source) {
    this.target = target;

    View view;
    target.actvPerson = Utils.findRequiredViewAsType(source, R.id.actv_person, "field 'actvPerson'", AutoCompleteTextView.class);
    view = Utils.findRequiredView(source, R.id.actv_address, "field 'mActvAddress' and method 'onTextChanged'");
    target.mActvAddress = Utils.castView(view, R.id.actv_address, "field 'mActvAddress'", AutoCompleteTextView.class);
    view2131361830 = view;
    view2131361830TextWatcher = new TextWatcher() {
      @Override
      public void onTextChanged(CharSequence p0, int p1, int p2, int p3) {
        target.onTextChanged(p0);
      }

      @Override
      public void beforeTextChanged(CharSequence p0, int p1, int p2, int p3) {
      }

      @Override
      public void afterTextChanged(Editable p0) {
      }
    };
    ((TextView) view).addTextChangedListener(view2131361830TextWatcher);
    view = Utils.findRequiredView(source, R.id.iv_clear_address, "field 'mIvClearAddress' and method 'onButtonClick'");
    target.mIvClearAddress = Utils.castView(view, R.id.iv_clear_address, "field 'mIvClearAddress'", ImageView.class);
    view2131362019 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onButtonClick(p0);
      }
    });
    target.mPbAddress = Utils.findRequiredViewAsType(source, R.id.pb_address, "field 'mPbAddress'", ProgressBar.class);
    target.mEtRoom = Utils.findRequiredViewAsType(source, R.id.et_room, "field 'mEtRoom'", EditText.class);
    target.mSpinnerUrgency = Utils.findRequiredViewAsType(source, R.id.spinner_urgency, "field 'mSpinnerUrgency'", Spinner.class);
    target.mEtSubject = Utils.findRequiredViewAsType(source, R.id.et_subject, "field 'mEtSubject'", EditText.class);
    target.mtSubject = Utils.findRequiredViewAsType(source, R.id.t_subject, "field 'mtSubject'", TextView.class);
    target.mEtDescription = Utils.findRequiredViewAsType(source, R.id.et_description, "field 'mEtDescription'", EditText.class);
    view = Utils.findRequiredView(source, R.id.btn_confirm, "field 'mBtnConfirm' and method 'onButtonClick'");
    target.mBtnConfirm = Utils.castView(view, R.id.btn_confirm, "field 'mBtnConfirm'", Button.class);
    view2131361860 = view;
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
    CreateItsmFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.actvPerson = null;
    target.mActvAddress = null;
    target.mIvClearAddress = null;
    target.mPbAddress = null;
    target.mEtRoom = null;
    target.mSpinnerUrgency = null;
    target.mEtSubject = null;
    target.mtSubject = null;
    target.mEtDescription = null;
    target.mBtnConfirm = null;

    ((TextView) view2131361830).removeTextChangedListener(view2131361830TextWatcher);
    view2131361830TextWatcher = null;
    view2131361830 = null;
    view2131362019.setOnClickListener(null);
    view2131362019 = null;
    view2131361860.setOnClickListener(null);
    view2131361860 = null;
  }
}
