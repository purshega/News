// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.mToolbar = Utils.findRequiredViewAsType(source, R.id.toolbar, "field 'mToolbar'", Toolbar.class);
    target.mEtLogin = Utils.findRequiredViewAsType(source, R.id.et_login, "field 'mEtLogin'", EditText.class);
    target.mEtSms = Utils.findRequiredViewAsType(source, R.id.et_sms, "field 'mEtSms'", EditText.class);
    target.mBtnSms = Utils.findRequiredViewAsType(source, R.id.btn_sms, "field 'mBtnSms'", Button.class);
    target.mBtnEnter = Utils.findRequiredViewAsType(source, R.id.btn_enter, "field 'mBtnEnter'", Button.class);
    target.mSmsForm = Utils.findRequiredViewAsType(source, R.id.sms_login_form, "field 'mSmsForm'", LinearLayout.class);
    target.mProgressBar = Utils.findRequiredViewAsType(source, R.id.progress, "field 'mProgressBar'", ProgressBar.class);
    target.mTvHelp = Utils.findRequiredViewAsType(source, R.id.tv_help_sms, "field 'mTvHelp'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mToolbar = null;
    target.mEtLogin = null;
    target.mEtSms = null;
    target.mBtnSms = null;
    target.mBtnEnter = null;
    target.mSmsForm = null;
    target.mProgressBar = null;
    target.mTvHelp = null;
  }
}
