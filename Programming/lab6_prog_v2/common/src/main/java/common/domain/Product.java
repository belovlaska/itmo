package common.domain;



import common.utility.Validateble;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Product class
 *
 * @author belovlaska
 * @version 1.0
 */
public class Product implements Validateble, Comparable<Product>, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private Integer id; // Поле не может быть null, Значение поля должно быть больше 0, Значение этого поля должно быть уникальным, Значение этого поля должно генерироваться автоматически
    private String name; // Поле не может быть null, Строка не может быть пустой
    private Coordinates coordinates; // Поле не может быть null
    private java.time.LocalDateTime creationDate; // Поле не может быть null, Значение этого поля должно генерироваться автоматически
    private Long price; // Поле может быть null, Значение поля должно быть больше 0
    private UnitOfMeasure unitOfMeasure; // Поле не может быть null
    private Person owner; // Поле может быть null

    public Product(int id, String name, Coordinates coordinates, LocalDateTime creationDate, Long price, UnitOfMeasure unitOfMeasure, Person owner) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        this.creationDate = creationDate;
        this.price = price;
        this.unitOfMeasure = unitOfMeasure;
        this.owner = owner;
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
        if(owner == null) {
            return null;
        }
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
        return "Product id " + id +
                "\nName: " + name  +
                "\nCoordinates: " + coordinates +
                "\nDate of creation: " + creationDate +
                "\nPrice: " + price +
                "\nUnit of measure: " + unitOfMeasure +
                "\nOwner:" + owner;
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
     * Update product
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
        //compare products by coordinates
        if(coordinates.getX() > other.getCoordinates().getX()){
            return 1;
        }
        else if(coordinates.getX() < other.getCoordinates().getX()){
            return -1;
        }
        else if(coordinates.getX() == other.getCoordinates().getX()){
            return coordinates.getY().compareTo(other.getCoordinates().getY());
        }
        return 0;
    }

    public Product copy(int id) {
        return new Product(id, this.name, this.coordinates, this.creationDate,
                this.price, this.unitOfMeasure, this.owner
        );
    }
}
