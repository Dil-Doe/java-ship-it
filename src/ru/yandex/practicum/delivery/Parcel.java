package ru.yandex.practicum.delivery;

public abstract class Parcel {
    protected final String description;
    protected final int weight;
    protected final String deliveryAddress;
    protected final int sendDay;
    protected final ParcelType type;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay, ParcelType type) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
        this.type = type;
    }

    public void packageItem() {
        System.out.printf("Посылка <<%s>> упакована!%n", description);
    }

    public void deliver() {
        System.out.printf("Посылка <<%s>> доставлена по адресу %s!%n", description, deliveryAddress);
    }

    public int calculateDeliveryCost() {
        return weight * type.getBaseCost();
    }

    // нужны только для тестов:

    public int getWeight() {
        return weight;
    }

    public String getDescription() {
        return description;
    }
}
