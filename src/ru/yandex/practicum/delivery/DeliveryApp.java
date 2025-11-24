package ru.yandex.practicum.delivery;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class DeliveryApp {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<Parcel> allParcels = new ArrayList<>();
    private static List<Trackable> trackableParcels = new ArrayList<>();

    private static ParcelBox<StandardParcel> standardBox = new ParcelBox<>(50);
    private static ParcelBox<FragileParcel> fragileBox = new ParcelBox<>(20);
    private static ParcelBox<PerishableParcel> perishableBox = new ParcelBox<>(15);

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            showMenu();
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addParcel();
                    break;
                case 2:
                    sendParcels();
                    break;
                case 3:
                    calculateCosts();
                    break;
                case 4:
                    trackParcels();
                    break;
                case 5:
                    showBoxContent();
                    break;
                case 0:
                    running = false;
                    break;
                default:
                    System.out.println("Неверный выбор.");
            }
        }
    }

    private static void showMenu() {
        System.out.println("Выберите действие:");
        System.out.println("1 — Добавить посылку");
        System.out.println("2 — Отправить все посылки");
        System.out.println("3 — Посчитать стоимость доставки");
        System.out.println("4 — Отследить местоположение хрупких посылок");
        System.out.println("5 — Показать содержимое коробки");
        System.out.println("0 — Завершить");
    }

    private static void addParcel() {
        System.out.println("Выберите тип посылки: ");
        System.out.println("1 - Стандартная");
        System.out.println("2 - Хрупкая");
        System.out.println("3 - Скоропортящаяся");

        int typeChoice = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите описание: ");
        String description = scanner.nextLine();

        System.out.print("Введите вес (целое число): ");
        int weight = Integer.parseInt(scanner.nextLine());

        System.out.print("Введите адрес доставки: ");
        String deliveryAddress = scanner.nextLine();

        System.out.print("Введите день отправки: ");
        int sendDay = Integer.parseInt(scanner.nextLine());

        switch (typeChoice) {
            case 1 -> {
                StandardParcel standard = new StandardParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(standard);
                standardBox.addParcel(standard);
            }
            case 2 -> {
                FragileParcel fragile = new FragileParcel(description, weight, deliveryAddress, sendDay);
                allParcels.add(fragile);
                fragileBox.addParcel(fragile);
                trackableParcels.add(fragile);
            }
            case 3 -> {
                System.out.print("Введите срок годности (в днях): ");
                int timeToLive = Integer.parseInt(scanner.nextLine());
                PerishableParcel perishable = new PerishableParcel(description, weight, deliveryAddress, sendDay, timeToLive);
                allParcels.add(perishable);
                perishableBox.addParcel(perishable);
            }
            default -> {
                System.out.println("Неверный выбор типа посылки!");
                return;
            }
        }

        System.out.println("Посылка успешно добавлена!");

    }

    private static void sendParcels() {
        for (Parcel parcel : allParcels) {
            parcel.packageItem();
            parcel.deliver();
        }
    }

    private static void calculateCosts() {
        int total = 0;

        for (Parcel parcel : allParcels) {
            total += parcel.calculateDeliveryCost();
        }

        System.out.println("Общая стоимость доставки: " + total);
    }

    private static void trackParcels() {
        if (trackableParcels.isEmpty()) {
            System.out.println("Нет посылок, поддерживающих трекинг.");
            return;
        }

        System.out.print("Введите новое местоположение: ");
        String location = scanner.nextLine();

        for (Trackable trackable : trackableParcels) {
            trackable.reportStatus(location);
        }
    }

    private static void showBoxContent() {
        System.out.println("Выберите коробку:");
        System.out.println("1 — Стандартные посылки");
        System.out.println("2 — Хрупкие посылки");
        System.out.println("3 — Скоропортящиеся посылки");

        int choice = Integer.parseInt(scanner.nextLine());

        List<? extends Parcel> box;

        switch (choice) {
            case 1 -> box = standardBox.getAllParcels();
            case 2 -> box = fragileBox.getAllParcels();
            case 3 -> box = perishableBox.getAllParcels();
            default -> {
                System.out.println("Неверный выбор.");
                return;
            }
        }

        if (box.isEmpty()) {
            System.out.println("Коробка пуста.");
            return;
        }

        System.out.println("Содержимое коробки:");
        for (Parcel p : box) {
            System.out.println(" - " + p.getDescription());
        }
    }

}

