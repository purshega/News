// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.gallery.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class GalleryAdapter$ViewHolder_ViewBinding implements Unbinder {
  private GalleryAdapter.ViewHolder target;

  @UiThread
  public GalleryAdapter$ViewHolder_ViewBinding(GalleryAdapter.ViewHolder target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tv_title, "field 'tvTitle'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    GalleryAdapter.ViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
  }
}
