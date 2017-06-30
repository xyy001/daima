// Generated code from Butter Knife. Do not modify!
package me.chunyu.spike.wcl_permission_demo;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class MainActivity$$ViewBinder<T extends me.chunyu.spike.wcl_permission_demo.MainActivity> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492969, "field 'mTToolbar'");
    target.mTToolbar = finder.castView(view, 2131492969, "field 'mTToolbar'");
  }

  @Override public void unbind(T target) {
    target.mTToolbar = null;
  }
}
