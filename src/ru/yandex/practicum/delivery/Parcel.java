package ru.yandex.practicum.delivery;

public abstract class Parcel {
    private String description;
    private int weight;
    private String deliveryAddress;
    private int sendDay;
    private ParcelType type;

    public Parcel(String description, int weight, String deliveryAddress, int sendDay, ParcelType type) {
        this.description = description;
        this.weight = weight;
        this.deliveryAddress = deliveryAddress;
        this.sendDay = sendDay;
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public int getSendDay() {
        return sendDay;
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

    public int getWeight() {
        return weight;
    }
}
