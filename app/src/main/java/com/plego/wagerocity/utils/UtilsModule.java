package com.plego.wagerocity.utils;

import dagger.Module;
import dagger.Provides;
import roboguice.inject.ContextSingleton;

/**
 * Created by Hassan Jawed on 11/3/2015.
 */
@Module
@ContextSingleton
public class UtilsModule {

	@Provides @ContextSingleton public DateUtils provideDateUtils () {
		return new DateUtils();
	}
}
