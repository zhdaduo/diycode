package com.example.bill.delta.ui.site;

import com.example.bill.delta.internal.di.scopes.PerFragment;
import dagger.Subcomponent;

@PerFragment
@Subcomponent(modules = SiteBaseModule.class)
public interface SiteBaseComponent {

  void inject(SitesFragment sitesFragment);
}
