package com.plego.wagerocity.android;

import com.plego.wagerocity.SystemServiceModule;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class DaggerComponentInitializer {

    public static BaseComponent initBase(WagerocityApplication application) {
        return DaggerBaseComponent.builder().uiModule(new UiModule(application))
                .systemServiceModule(new SystemServiceModule(application)).build();
    }

}
