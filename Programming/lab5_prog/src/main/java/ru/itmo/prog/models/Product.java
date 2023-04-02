package ru.itmo.prog.models;

import ru.itmo.prog.controllers.CollectionController;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Класс продукта
 *
 * @author belovlaska
 * @version 1.0
 */
public class Product implements Validateble, Comparable<Product>{
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.time.LocalDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; // Поле может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; // Поле не может быть null
    private Person owner; // Поле может быть null
    private static int nextId=1;

    public Product(String name, Coordinates coordinates, LocalDateTime creationDate, Long price, UnitOfMeasure unitOfMeasure, Person owner) {
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
        this.id = nextId++;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public Long getPrice() {
        return price;
    }

    public UnitOfMeasure getUnitOfMeasure() {
        return unitOfMeasure;
    }

    public Person getOwner() {
        return owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(id, product.id) && Objects.equals(name, product.name) && Objects.equals(coordinates, product.coordinates) && Objects.equals(creationDate, product.creationDate) && Objects.equals(price, product.price) && Objects.equals(unitOfMeasure, product.unitOfMeasure) && Objects.equals(owner, product.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, coordinates, creationDate, price, unitOfMeasure, owner);
    }

    @Override
    public String toString() {
        return "Продукт номер " + id +
                "\nНазвание: " + name  +
                "\nКоординаты: " + coordinates +
                "\nДата создания: " + creationDate +
                "\nЦена: " + price +
                "\nЕдиницы измерения: " + unitOfMeasure +
                "\nВладелец:" + owner;
    }

    @Override
    public boolean validate() {
        if(id != null && id > 0
                && name != null && !name.isEmpty()
                && creationDate != null
                && coordinates != null
                && (price == null || price > 0)
                && unitOfMeasure != null) return true;
        return false;
    }

    /**
     * Обновляет продукт
     * @param product
     */
    public void update(Product product){
        this.name=product.name;
        this.coordinates=product.coordinates;
        this.creationDate=product.creationDate;
        this.price=product.price;
        this.unitOfMeasure=product.unitOfMeasure;
        this.owner=product.owner;
    }


    @Override
    public int compareTo(Product other) {
        //compare products by price
        if (price == null && other.getPrice() == null) {
            return 0;
        }
        if (price == null) {
            return -1;
        }
        if (other.getPrice() == null) {
            return 1;
        }
        var delta = price - other.getPrice();
        if (delta > 0) {
            return 1;
        }
        if (delta == 0) {
            return 0;
        }
        return -1;
    }

    public static void updateNextId(CollectionController collectionController) {
        var maxId = collectionController
                .getCollection()
                .stream().filter(Objects::nonNull)
                .map(Product::getId)
                .mapToInt(Integer::intValue).max().orElse(0);
        nextId = maxId + 1;
    }
}
