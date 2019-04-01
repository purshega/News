// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.youtube.adapter;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class YouTubeAdapter$VideoViewHolder_ViewBinding implements Unbinder {
  private YouTubeAdapter.VideoViewHolder target;

  @UiThread
  public YouTubeAdapter$VideoViewHolder_ViewBinding(YouTubeAdapter.VideoViewHolder target,
      View source) {
    this.target = target;

    target.videoTitle = Utils.findRequiredViewAsType(source, R.id.video_title, "field 'videoTitle'", TextView.class);
    target.videoImegeView = Utils.findRequiredViewAsType(source, R.id.iv_video, "field 'videoImegeView'", ImageView.class);
    target.videoImegePlay = Utils.findRequiredViewAsType(source, R.id.iv_play, "field 'videoImegePlay'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    YouTubeAdapter.VideoViewHolder target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.videoTitle = null;
    target.videoImegeView = null;
    target.videoImegePlay = null;
  }
}
