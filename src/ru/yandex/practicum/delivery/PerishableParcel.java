package ru.yandex.practicum.delivery;

public class PerishableParcel extends Parcel {
    private int timeToLive;

    public PerishableParcel(String description, int weight, String deliveryAddress, int sendDay, int timeToLive) {
        super(description, weight, deliveryAddress, sendDay, ParcelType.PERISHABLE);
        this.timeToLive = timeToLive;
    }

    public boolean isExpired(int currentDay) {
        return (sendDay + timeToLive) < currentDay;
    }
}
