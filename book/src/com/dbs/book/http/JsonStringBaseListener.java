package com.dbs.book.http;

import com.duowan.mobile.netroid.Listener;

public abstract class JsonStringBaseListener extends Listener<String> {

	@Override
	public void onSuccess(String response) {
		parse(response);
	}

	public  abstract void parse(String response);
}
