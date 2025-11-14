package com.teamlostandfound;

import java.time.LocalDate;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Item {
    private final IntegerProperty id;
    private final StringProperty name;
    private final StringProperty category;
    private final StringProperty location;
    private final ObjectProperty<LocalDate> date;
    private final StringProperty description;
    private final StringProperty status;
    private final StringProperty contactName;
    private final StringProperty contactPhone;

    public Item() {
        this.id = new SimpleIntegerProperty();
        this.name = new SimpleStringProperty();
        this.category = new SimpleStringProperty();
        this.location = new SimpleStringProperty();
        this.date = new SimpleObjectProperty<>();
        this.description = new SimpleStringProperty();
        this.status = new SimpleStringProperty();
        this.contactName = new SimpleStringProperty();
        this.contactPhone = new SimpleStringProperty();
    }

    public Item(int id, String name, String category, String location, LocalDate date, String description, String status) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.location = new SimpleStringProperty(location);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
        this.contactName = new SimpleStringProperty();
        this.contactPhone = new SimpleStringProperty();
    }

    public Item(int id, String name, String category, String location, LocalDate date, String description, String status, String contactName, String contactPhone) {
        this.id = new SimpleIntegerProperty(id);
        this.name = new SimpleStringProperty(name);
        this.category = new SimpleStringProperty(category);
        this.location = new SimpleStringProperty(location);
        this.date = new SimpleObjectProperty<>(date);
        this.description = new SimpleStringProperty(description);
        this.status = new SimpleStringProperty(status);
        this.contactName = new SimpleStringProperty(contactName != null ? contactName : "");
        this.contactPhone = new SimpleStringProperty(contactPhone != null ? contactPhone : "");
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public IntegerProperty idProperty() {
        return id;
    }

    public String getName() {
        return name.get();
    }

    public void setName(String name) {
        this.name.set(name);
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getCategory() {
        return category.get();
    }

    public void setCategory(String category) {
        this.category.set(category);
    }

    public StringProperty categoryProperty() {
        return category;
    }

    public String getLocation() {
        return location.get();
    }

    public void setLocation(String location) {
        this.location.set(location);
    }

    public StringProperty locationProperty() {
        return location;
    }

    public LocalDate getDate() {
        return date.get();
    }

    public void setDate(LocalDate date) {
        this.date.set(date);
    }

    public ObjectProperty<LocalDate> dateProperty() {
        return date;
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String description) {
        this.description.set(description);
    }

    public StringProperty descriptionProperty() {
        return description;
    }

    public String getStatus() {
        return status.get();
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public StringProperty statusProperty() {
        return status;
    }

    public String getContactName() {
        return contactName.get();
    }

    public void setContactName(String contactName) {
        this.contactName.set(contactName);
    }

    public StringProperty contactNameProperty() {
        return contactName;
    }

    public String getContactPhone() {
        return contactPhone.get();
    }

    public void setContactPhone(String contactPhone) {
        this.contactPhone.set(contactPhone);
    }

    public StringProperty contactPhoneProperty() {
        return contactPhone;
    }
}

