package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;

public class ParcelBox<T extends Parcel> {

    private final int maxWeight;
    private final List<T> parcels = new ArrayList<>();
    private int currentWeight = 0;

    public ParcelBox(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public void addParcel(T parcel) {
        int newWeight = currentWeight + parcel.getWeight();

        if (newWeight > maxWeight) {
            System.out.println("Нельзя добавить посылку: превышен максимальный вес коробки.");
            return;
        }

        parcels.add(parcel);
        currentWeight = newWeight;
        System.out.println("Посылка добавлена в коробку.");
    }

    public List<T> getAllParcels() {
        return parcels;
    }
}

