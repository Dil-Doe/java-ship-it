package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.FragileParcel;
import ru.yandex.practicum.delivery.ParcelBox;
import ru.yandex.practicum.delivery.PerishableParcel;
import ru.yandex.practicum.delivery.StandardParcel;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void testCalculateDeliveryCost() {
        StandardParcel standard = new StandardParcel("Книга", 5, "Москва", 1);
        assertEquals(10, standard.calculateDeliveryCost());

        FragileParcel fragile = new FragileParcel("Ваза", 3, "Санкт-Петербург", 1);
        assertEquals(12, fragile.calculateDeliveryCost());

        PerishableParcel perishable = new PerishableParcel("Молоко", 2, "Казань", 1, 5);
        assertEquals(6, perishable.calculateDeliveryCost());
    }

    @Test
    void testCalculateDeliveryCost_ZeroWeight() {
        StandardParcel parcel = new StandardParcel("Пустая коробка", 0, "Клин", 1);
        assertEquals(0, parcel.calculateDeliveryCost());
    }

    @Test
    void testIsExpired_NotExpired() {
        PerishableParcel parcel = new PerishableParcel("Молоко", 1,
                "Калининград", 1, 5);
        assertFalse(parcel.isExpired(5));
    }

    @Test
    void testIsExpired_Expired() {
        PerishableParcel parcel = new PerishableParcel("Мороженое", 1,
                "Тверь", 2, 3);
        assertTrue(parcel.isExpired(6));
    }

    @Test
    void testIsExpired_ExpiresToday() {
        PerishableParcel parcel = new PerishableParcel("Йогурт", 1,
                "Мытищи", 1, 4);
        assertFalse(parcel.isExpired(5));
    }

    @Test
    void testAddParcel_WithinWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel parcel = new StandardParcel("Документы", 3, "Офис", 1);

        boolean added = box.addParcel(parcel);

        assertTrue(added);
        assertEquals(1, box.getAllParcels().size());
        assertEquals(3, box.getAllParcels().getFirst().getWeight());
    }

    @Test
    void testAddParcel_ExactWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(5);
        StandardParcel parcel = new StandardParcel("Книга", 5, "Дом", 1);

        assertTrue(box.addParcel(parcel));
        assertEquals(1, box.getAllParcels().size());
    }

    @Test
    void testAddParcel_OverWeight() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(7);
        StandardParcel parcel = new StandardParcel("Коробка", 8, "Склад", 1);

        assertFalse(box.addParcel(parcel));
        assertTrue(box.getAllParcels().isEmpty());
    }

    @Test
    void testAddParcel_AddTwoParcelsWithinLimit() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10);
        StandardParcel p1 = new StandardParcel("P1", 4, "A", 1);
        StandardParcel p2 = new StandardParcel("P2", 3, "B", 1);

        assertTrue(box.addParcel(p1));
        assertTrue(box.addParcel(p2));

        assertEquals(2, box.getAllParcels().size());
        assertEquals(7, box.getAllParcels().stream().mapToInt(StandardParcel::getWeight).sum());
    }

    @Test
    void testAddParcel_SecondParcelTooHeavy_FirstRemains() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(6);
        StandardParcel p1 = new StandardParcel("P1", 4, "A", 1);
        StandardParcel p2 = new StandardParcel("P2", 3, "B", 1);

        assertTrue(box.addParcel(p1));
        assertFalse(box.addParcel(p2));

        assertEquals(1, box.getAllParcels().size());
        assertEquals("P1", box.getAllParcels().getFirst().getDescription());
    }

    @Test
    void testFragileParcelTracking() {
        FragileParcel fragile = new FragileParcel("Стеклянная ваза", 3, "СПб", 1);

        // Просто вызываем — если ошибок нет, тест пройден
        fragile.reportStatus("Склад");
        fragile.reportStatus("Курьер");
    }
}