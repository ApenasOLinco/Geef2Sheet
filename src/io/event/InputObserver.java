package io.event;

import java.io.File;

import io.event.InputNotifier.InputNotifierEvent;

@FunctionalInterface
public interface InputObserver {
    void update(InputNotifierEvent event);
}
