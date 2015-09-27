package com.plego.wagerocity;

import android.app.Application;

import com.plego.wagerocity.android.WagerocityPref;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Hassan Jawed on 9/27/2015.
 */
@Module
@Singleton
public class SystemServiceModule {

    private Application appContext;

    public SystemServiceModule(Application appContext) {
        this.appContext = appContext;
    }

    @Provides
    @Singleton
    public WagerocityPref providesPref(Application application) {
        return new WagerocityPref(application);
    }
}
