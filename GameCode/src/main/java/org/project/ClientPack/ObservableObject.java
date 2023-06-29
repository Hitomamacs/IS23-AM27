package org.project.ClientPack;

import com.google.gson.annotations.Expose;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class ObservableObject {
    public PropertyChangeSupport getSupport() {
        return support;
    }

    public void setSupport(PropertyChangeSupport support) {
        this.support = support;
    }



    private transient PropertyChangeSupport support;

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
