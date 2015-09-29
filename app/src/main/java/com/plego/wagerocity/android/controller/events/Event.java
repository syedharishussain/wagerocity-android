package com.plego.wagerocity.android.controller.events;

/**
 * Created by Hassan Jawed on 9/30/2015.
 */
public class Event<T> {

	private Action action;
	private T      data;

	public Event (Action action) {
		this.action = action;
	}

	public Event (Action action, T data) {
		this.action = action;
		this.data = data;
	}

	public Action getAction () {
		return action;
	}

	public T getData () {
		return data;
	}
}
