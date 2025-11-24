package ru.yandex.practicum;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.delivery.*;

import static org.junit.jupiter.api.Assertions.*;

class DeliveryCostTest {

    @Test
    void testCalculateDeliveryCost() {
        StandardParcel standard = new StandardParcel("Книга", 5, "Москва", 1);
        assertEquals(10, standard.calculateDeliveryCost(), "Стандартная посылка должна стоить weight * 2");
        FragileParcel fragile = new FragileParcel("Стеклянная ваза", 3, "Санкт-Петербург", 1);
        assertEquals(12, fragile.calculateDeliveryCost(), "Хрупкая посылка должна стоить weight * 4");
        PerishableParcel perishable = new PerishableParcel("Молоко", 2, "Казань", 1, 5);
        assertEquals(6, perishable.calculateDeliveryCost(), "Скоропортящаяся посылка должна стоить weight * 3");
    }

    @Test
    void testIsExpired() {
        PerishableParcel freshParcel = new PerishableParcel("Яблоки", 3, "Москва", 1, 5);
        assertFalse(freshParcel.isExpired(5), "Посылка не должна быть просрочена на 5-й день");
        assertTrue(freshParcel.isExpired(7), "Посылка должна быть просрочена на 7-й день");
        assertFalse(freshParcel.isExpired(6), "Посылка не просрочена в день окончания срока");
    }

    @Test
    void testAddParcelToBox() {
        ParcelBox<StandardParcel> box = new ParcelBox<>(10); // максимальный вес 10

        StandardParcel p1 = new StandardParcel("Книга", 5, "Москва", 1);
        StandardParcel p2 = new StandardParcel("Журнал", 5, "Москва", 1);
        StandardParcel p3 = new StandardParcel("Газета", 2, "Москва", 1);

        box.addParcel(p1);
        box.addParcel(p2);

        assertEquals(2, box.getAllParcels().size(), "Должны быть добавлены две посылки");
        assertTrue(box.getAllParcels().contains(p1));
        assertTrue(box.getAllParcels().contains(p2));

        box.addParcel(p3);
        assertEquals(2, box.getAllParcels().size(), "Третья посылка не должна быть добавлена из-за превышения веса");
        assertFalse(box.getAllParcels().contains(p3));
    }
}