package io.event;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class InputNotifier {
	
	public enum EventType {
		FILE_ADDED, FILE_REMOVED, JLIST_FOCUS_CHANGED;
	}
	
	public class InputNotifierEvent {
		private EventType eventType;
		
		private File[] files;

		public InputNotifierEvent(EventType type, File[] files) {
			eventType = type;
			this.files = files;
			
		}
		
		/**
		 * Returns the type of this event
		 * @return the type of this event
		 */
		public EventType getEventType() {
			return eventType;
		}
		
		/**
		 * Returns the modified files related to this {@code EventType}
		 * 
		 * @return the modified files related to this {@code EventType}
		 */
		public File[] getFiles() {
			return files;
		}
	}
	

    private final HashMap<EventType, ArrayList<InputObserver>> observers = new HashMap<>();

    public InputNotifier() {
        Arrays.stream(EventType.values()).forEach(event -> observers.put(event, new ArrayList<>()));
    }

    public void subscribe(InputObserver observer, EventType eventType) {
        observers.get(eventType).add(observer);
    }

    public void unsubscribe(InputObserver observer, EventType eventType) {
        observers.get(eventType).remove(observer);
    }

    public void notifyObservers(EventType eventType, File... files) {
        observers.get(eventType).forEach(observer -> observer.update(new InputNotifierEvent(eventType, files)));
    }
}






























