package org.project.ClientPack;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableObject {
    private PropertyChangeSupport support;

    public ObservableObject() {
        this.support = new PropertyChangeSupport(this);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void firePropertyChange(String propertyName, Object newValue) {
        support.firePropertyChange(propertyName, null, newValue);
    }
}
