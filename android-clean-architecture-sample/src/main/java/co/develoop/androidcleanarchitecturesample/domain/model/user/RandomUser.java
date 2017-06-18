package co.develoop.androidcleanarchitecturesample.domain.model.user;

import org.joda.time.DateTime;

import java.util.Objects;

import co.develoop.androidcleanarchitecture.screen.presenter.recyclerview.RecyclerViewAdapterItem;

public class RandomUser implements Comparable<RandomUser>, RecyclerViewAdapterItem {

    private String email;
    private String phone;
    private String gender;
    private DateTime registeredDate;
    private RandomUserName name;
    private RandomUserPicture picture;
    private RandomUserLocation location;
    private boolean isFavorite;

    private Type mAdapterItemType;

    public RandomUser(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public DateTime getRegisteredDate() {
        return registeredDate;
    }

    public void setRegisteredDate(DateTime registeredDate) {
        this.registeredDate = registeredDate;
    }

    public RandomUserName getName() {
        return name;
    }

    public void setName(RandomUserName name) {
        this.name = name;
    }

    public RandomUserPicture getPicture() {
        return picture;
    }

    public void setPicture(RandomUserPicture picture) {
        this.picture = picture;
    }

    public RandomUserLocation getLocation() {
        return location;
    }

    public void setLocation(RandomUserLocation location) {
        this.location = location;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RandomUser that = (RandomUser) o;
        return Objects.equals(email, that.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }

    @Override
    public int compareTo(RandomUser otherRandomUser) {
        return email.compareToIgnoreCase(otherRandomUser.getEmail());
    }

    @Override
    public void setType(Type type) {
        mAdapterItemType = type;
    }

    @Override
    public Type getType() {
        return mAdapterItemType;
    }
}