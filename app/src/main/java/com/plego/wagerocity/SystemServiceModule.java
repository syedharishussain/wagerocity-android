package com.plego.wagerocity;

import android.app.Application;
import com.plego.wagerocity.android.WagerocityPref;
import com.plego.wagerocity.android.controller.events.EventFactory;
import com.squareup.otto.Bus;
import dagger.Module;
import dagger.Provides;

import javax.inject.Singleton;

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
    public Bus providesBus () {
        return new Bus();
    }

    @Provides
    @Singleton
    public EventFactory providesEventFactory (Bus bus) {
        return new EventFactory( bus );
    }

    @Provides
    @Singleton
    public WagerocityPref providesPref(Application application) {
        return new WagerocityPref(application);
    }
}
