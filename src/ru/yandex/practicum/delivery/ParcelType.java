package ru.yandex.practicum.delivery;

public enum ParcelType {
    STANDARD(2),
    FRAGILE(4),
    PERISHABLE(3);

    private final int baseCost;

    ParcelType(int baseCost) {
        this.baseCost = baseCost;
    }

    public int getBaseCost() {
        return baseCost;
    }
}
