// Generated code from Butter Knife. Do not modify!
package com.dtek.portal.ui.fragment.newspaper;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.dtek.portal.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class MainNewsPaperFragment_ViewBinding implements Unbinder {
  private MainNewsPaperFragment target;

  private View view2131362006;

  @UiThread
  public MainNewsPaperFragment_ViewBinding(final MainNewsPaperFragment target, View source) {
    this.target = target;

    View view;
    view = Utils.findRequiredView(source, R.id.imageView, "field 'imageView' and method 'onItemClick'");
    target.imageView = Utils.castView(view, R.id.imageView, "field 'imageView'", ImageView.class);
    view2131362006 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.onItemClick();
      }
    });
    target.newspaper_null = Utils.findRequiredViewAsType(source, R.id.newspaper_null, "field 'newspaper_null'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    MainNewsPaperFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imageView = null;
    target.newspaper_null = null;

    view2131362006.setOnClickListener(null);
    view2131362006 = null;
  }
}
