package org.project.ClientPack;

import com.google.gson.annotations.Expose;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * An abstract base class for objects that support property change notification.
 */
public class ObservableObject {

    private transient PropertyChangeSupport support;

    public PropertyChangeSupport getSupport() {
        return support;
    }

    public void setSupport(PropertyChangeSupport support) {
        this.support = support;
    }

    /**
     * Constructor
     */
    public ObservableObject() {
        this.support = new PropertyChangeSupport(this);
    }

    /**
     * Adds a PropertyChangeListener to the internal PropertyChangeSupport object.
     * @param listener The PropertyChangeListener to be added.
     */
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    /**
     * Notifies registered PropertyChangeListeners about a property change event.
     * @param propertyName The name of the property that has changed.
     * @param newValue The new value of the property.
     */
    public void firePropertyChange(String propertyName, Object newValue) {
        support.firePropertyChange(propertyName, null, newValue);
    }
}
