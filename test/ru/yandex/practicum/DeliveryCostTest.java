package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    private StandardParcel standardParcel;
    private FragileParcel fragileParcel;
    private PerishableParcel perishableParcel;

    @BeforeEach
    void setUp() {
        standardParcel = new StandardParcel("Стандартная", 10, "Москва", 1);
        fragileParcel = new FragileParcel("Хрупкая", 5, "Санкт-Петербург", 2);
        perishableParcel = new PerishableParcel("Скоропортящаяся", 3, "Казань", 5, 7);
    }

    @Test
    void testCalculateDeliveryCost() {
        assertEquals(10 * 2, standardParcel.calculateDeliveryCost());
        assertEquals(5 * 4, fragileParcel.calculateDeliveryCost());
        assertEquals(3 * 3, perishableParcel.calculateDeliveryCost());
    }

    @Test
    void testPerishableParcelIsExpired() {
        assertFalse(perishableParcel.isExpired(10));
        assertTrue(perishableParcel.isExpired(13));
    }

    @Test
    void testParcelBoxAddParcel() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(15);
        box.addParcel(standardParcel);
        assertTrue(box.getAllParcels().contains(standardParcel));
        StandardParcel heavyParcel = new StandardParcel("Тяжелая", 10, "Москва", 2);
        box.addParcel(heavyParcel);
        assertFalse(box.getAllParcels().contains(heavyParcel));
    }
}