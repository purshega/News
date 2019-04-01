// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.youtube;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class YouTubeFragment_ViewBinding implements Unbinder {
  private YouTubeFragment target;

  @UiThread
  public YouTubeFragment_ViewBinding(YouTubeFragment target, View source) {
    this.target = target;

    target.videoRecyclerView = Utils.findRequiredViewAsType(source, R.id.video_recycler_view, "field 'videoRecyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    YouTubeFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoRecyclerView = null;
  }
}
