// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.itsm.it.twoline;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailTwoLineFragment_ViewBinding implements Unbinder {
  private DetailTwoLineFragment target;

  private View view2131361978;

  @UiThread
  public DetailTwoLineFragment_ViewBinding(final DetailTwoLineFragment target, View source) {
    this.target = target;

    View view;
    target.mTvNumber = Utils.findRequiredViewAsType(source, R.id.tv_number, "field 'mTvNumber'", TextView.class);
    target.mTvDateStart = Utils.findRequiredViewAsType(source, R.id.tv_date_start, "field 'mTvDateStart'", TextView.class);
    target.mTvDatePlan = Utils.findRequiredViewAsType(source, R.id.tv_date_plan, "field 'mTvDatePlan'", TextView.class);
    target.mTvStatus = Utils.findRequiredViewAsType(source, R.id.tv_status, "field 'mTvStatus'", TextView.class);
    target.mTvInitiator = Utils.findRequiredViewAsType(source, R.id.tv_initiator, "field 'mTvInitiator'", TextView.class);
    target.mTvNameAffects = Utils.findRequiredViewAsType(source, R.id.tv_name_affects, "field 'mTvNameAffects'", TextView.class);
    target.mTvCity = Utils.findRequiredViewAsType(source, R.id.tv_city, "field 'mTvCity'", TextView.class);
    target.mTvAddress = Utils.findRequiredViewAsType(source, R.id.tv_address, "field 'mTvAddress'", TextView.class);
    target.mTvRoom = Utils.findRequiredViewAsType(source, R.id.tv_room, "field 'mTvRoom'", TextView.class);
    target.mTvPhone = Utils.findRequiredViewAsType(source, R.id.tv_phone, "field 'mTvPhone'", TextView.class);
    target.mTvEmail = Utils.findRequiredViewAsType(source, R.id.tv_email, "field 'mTvEmail'", TextView.class);
    target.mTvPriority = Utils.findRequiredViewAsType(source, R.id.tv_priority, "field 'mTvPriority'", TextView.class);
    target.mTvService = Utils.findRequiredViewAsType(source, R.id.tv_service, "field 'mTvService'", TextView.class);
    target.mTvTurn = Utils.findRequiredViewAsType(source, R.id.tv_turn, "field 'mTvTurn'", TextView.class);
    target.mTvSubject = Utils.findRequiredViewAsType(source, R.id.tv_subject, "field 'mTvSubject'", TextView.class);
    target.mTvDescription = Utils.findRequiredViewAsType(source, R.id.tv_description, "field 'mTvDescription'", TextView.class);
    view = Utils.findRequiredView(source, R.id.fab_it_change, "field 'mFabItChange' and method 'onFabClick'");
    target.mFabItChange = Utils.castView(view, R.id.fab_it_change, "field 'mFabItChange'", FloatingActionButton.class);
    view2131361978 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onFabClick();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailTwoLineFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.mTvNumber = null;
    target.mTvDateStart = null;
    target.mTvDatePlan = null;
    target.mTvStatus = null;
    target.mTvInitiator = null;
    target.mTvNameAffects = null;
    target.mTvCity = null;
    target.mTvAddress = null;
    target.mTvRoom = null;
    target.mTvPhone = null;
    target.mTvEmail = null;
    target.mTvPriority = null;
    target.mTvService = null;
    target.mTvTurn = null;
    target.mTvSubject = null;
    target.mTvDescription = null;
    target.mFabItChange = null;

    view2131361978.setOnClickListener(null);
    view2131361978 = null;
  }
}
