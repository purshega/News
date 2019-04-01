// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.newspaper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ArchiveNewsPaperFragment_ViewBinding implements Unbinder {
  private ArchiveNewsPaperFragment target;

  @UiThread
  public ArchiveNewsPaperFragment_ViewBinding(ArchiveNewsPaperFragment target, View source) {
    this.target = target;

    target.newspaperRV = Utils.findRequiredViewAsType(source, R.id.newspaper_recycler_view, "field 'newspaperRV'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ArchiveNewsPaperFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.newspaperRV = null;
  }
}
