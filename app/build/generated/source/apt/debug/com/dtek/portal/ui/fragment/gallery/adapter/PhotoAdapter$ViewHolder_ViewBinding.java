// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.gallery.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class PhotoAdapter$ViewHolder_ViewBinding implements Unbinder {
  private PhotoAdapter.ViewHolder target;

  @UiThread
  public PhotoAdapter$ViewHolder_ViewBinding(PhotoAdapter.ViewHolder target, View source) {
    this.target = target;

    target.ivPhoto = Utils.findRequiredViewAsType(source, R.id.iv_photo, "field 'ivPhoto'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    PhotoAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.ivPhoto = null;
  }
}
