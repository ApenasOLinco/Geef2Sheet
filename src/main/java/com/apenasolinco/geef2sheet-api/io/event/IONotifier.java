package io.event;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class IONotifier {
	
	public enum EventType {
		FILE_ADDED, FILE_REMOVED, FILES_PROCESSED;
	}
	
	public class IONotifierEvent {
		private EventType eventType;
		
		private File[] files;

		public IONotifierEvent(EventType type, File[] files) {
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
	

    private final HashMap<EventType, ArrayList<IOObserver>> observers = new HashMap<>();

    public IONotifier() {
        Arrays.stream(EventType.values()).forEach(event -> observers.put(event, new ArrayList<>()));
    }

    public void subscribe(IOObserver observer, EventType eventType) {
        observers.get(eventType).add(observer);
    }

    public void unsubscribe(IOObserver observer, EventType eventType) {
        observers.get(eventType).remove(observer);
    }

    public void notifyObservers(EventType eventType, File... files) {
        observers.get(eventType).forEach(observer -> observer.update(new IONotifierEvent(eventType, files)));
    }
}






























