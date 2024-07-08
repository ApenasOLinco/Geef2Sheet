package io.event;

import java.io.File;

import io.event.IONotifier.IONotifierEvent;

@FunctionalInterface
public interface IOObserver {
    void update(IONotifierEvent event);
}
