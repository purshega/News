// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.newspaper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import com.github.chrisbanes.photoview.PhotoView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class NewsPaperPageFragment_ViewBinding implements Unbinder {
  private NewsPaperPageFragment target;

  @UiThread
  public NewsPaperPageFragment_ViewBinding(NewsPaperPageFragment target, View source) {
    this.target = target;

    target.photoView = Utils.findRequiredViewAsType(source, R.id.pdf_page, "field 'photoView'", PhotoView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    NewsPaperPageFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.photoView = null;
  }
}
