package com.plego.wagerocity.android;

import android.app.Application;

import com.plego.wagerocity.utils.UiUtils;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
@Module
@Singleton
public class UiModule {

    private Application appContext;

    public UiModule(Application appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    public Application provideAppContext() {
        return appContext;
    }

    @Provides
    @Singleton
    public UiUtils provideUtils(Application application) {
        return new UiUtils(application);
    }
}
