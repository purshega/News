// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.newspaper.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArchiveNewPaperAdapter$ArchiveViewHolder_ViewBinding implements Unbinder {
  private ArchiveNewPaperAdapter.ArchiveViewHolder target;

  @UiThread
  public ArchiveNewPaperAdapter$ArchiveViewHolder_ViewBinding(ArchiveNewPaperAdapter.ArchiveViewHolder target,
      View source) {
    this.target = target;

    target.title_name = Utils.findRequiredViewAsType(source, R.id.title_name, "field 'title_name'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArchiveNewPaperAdapter.ArchiveViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.title_name = null;
  }
}
