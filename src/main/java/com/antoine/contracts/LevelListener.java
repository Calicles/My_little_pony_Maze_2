package com.antoine.contracts;

import com.antoine.events.LevelChangeEvent;

public interface LevelListener {

	void update(LevelChangeEvent lve);
}
