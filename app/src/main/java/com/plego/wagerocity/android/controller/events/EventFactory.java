package com.plego.wagerocity.android.controller.events;

import com.squareup.otto.Bus;

/**
 * Created by Hassan Jawed on 9/30/2015.
 */
public class EventFactory {

	private Bus bus;

	public EventFactory (Bus bus) {
		this.bus = bus;
	}

	public void register (Object obj) {
		bus.register( obj );
	}

	public void unregister (Object object) {
		bus.unregister( object );
	}

	public void joinPool (int position) {
		OnItemEvent itemEvent = new OnItemEvent( Action.JOIN_POOL, position );
		bus.post( itemEvent );
	}

	public void unJoinPool (int position) {
		OnItemEvent onItemEvent = new OnItemEvent( Action.UN_JOIN_POOL, position );
		bus.post( onItemEvent );
	}
}
