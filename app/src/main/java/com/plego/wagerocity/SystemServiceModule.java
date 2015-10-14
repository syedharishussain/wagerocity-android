package com.plego.wagerocity;

import android.app.Application;
import android.content.Context;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
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
	@Singleton public Context providesApplicationContext () {
		return appContext;
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

	@Provides @Singleton public ImageLoader provideImageLoader (Context context) {
		ImageLoader imageLoader = ImageLoader.getInstance();
		if (imageLoader.isInited()) return imageLoader;
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder( context ).build();
		imageLoader.init( config );
		return imageLoader;
	}
}
