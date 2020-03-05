package com.hce.ducati;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ZKOnCreationWatcher implements Watcher {
	@Override
	public void process(WatchedEvent event) {
		log.info(event.getPath()+"==="+event.getType().name()+"==="+event.getState().name()+"==="+event.getState().getIntValue()+"==="+event.getState().ordinal()+"==="+event.toString());
	}
}