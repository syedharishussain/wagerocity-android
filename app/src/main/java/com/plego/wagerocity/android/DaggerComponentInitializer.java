package com.plego.wagerocity.android;

import com.plego.wagerocity.android.services.ApiModule;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
public class DaggerComponentInitializer {

    public static BaseComponent initBase(WagerocityApplication application) {
        return DaggerBaseComponent.builder().uiModule( new UiModule( application ) ).apiModule( new ApiModule() )
                .build();
    }
}
