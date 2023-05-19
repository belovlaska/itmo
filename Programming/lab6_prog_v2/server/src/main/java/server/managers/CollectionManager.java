package server.managers;

import common.domain.Product;
import server.App;





import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Collection managering class
 * @author belovlaska
 * @version 1.0
 */

public class CollectionManager {
    private HashSet<Product> collection;
    private DumpManager dumpController;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    public CollectionManager(DumpManager dumpController) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpController = dumpController;

        loadCollection();
    }

    /**
     * @return Collection
     */
    public HashSet<Product> getCollection() {
        return collection;
    }

    /**
     * @return Date and time of last initialization
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Date and time of last save
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Save collection to file
     */
    public void save() {
        dumpController.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Load the collection from file
     */
    private void loadCollection() {
        collection = (HashSet<Product>) dumpController.readCollection();
        lastInitTime = LocalDateTime.now();
    }

    @Override
    public String toString() {
        if (collection.isEmpty()) return "Collection is empty!";

        StringBuilder info = new StringBuilder();
        for (var product : collection) {
            info.append(product);
            info.append("\n\n");
        }
        return info.toString();
    }

    /**
     * @return Collection type
     */
    public String getType() {
        return collection.getClass().getName();
    }

    /**
     * @return Collection size
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Add element to collection
     *
     * @param element Element
     */
    public void addToCollection(Product element) {
        collection.add(element);
    }

    /**
     * Delete element from collection
     *
     * @param element element
     */
    public void removeElement(Product element) {
        collection.remove(element);
    }

    /**
     * Get element by it's id
     * @param id
     * @return element
     */
    public Product getById(Integer id) {
        for (Product element : collection) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }

    /**
     * Clear collection
     */
    public void clearCollection() {
        collection.clear();
    }

    /**
     * Checks if the given element is the largest in the collection
     * @param product element
     *
     */
    public boolean greaterThanAll(Product product) {
        for (Product element : collection) {
            if (product.compareTo(element) < 0) return false;
        }
        return true;
    }

    /**
     * Delete all elements that less than given element from the collection
     * @param product
     */
    public void removeLower(Product product){
        collection = collection.stream()
                .filter(element -> product.compareTo(element) < 0)
                .collect(Collectors.toCollection(HashSet::new));
    }

    /**
     * Список всех элементов, чье поле name содержит заданную строку
     * @param name строка
     *
     */
    public List<Product> listContainsName(String name){
        List<Product> list = new ArrayList<>();
        for (Product element: collection){
            if(element.getName().contains(name))
                list.add(element);
        }
        return list;
    }

    /**
     * Список всех элементов, чье поле name начинается на заданную строку
     * @param name
     *
     */
    public List<Product> listStartsWithName(String name){
        List<Product> list = new ArrayList<>();
        for (Product element: collection){
            if(element.getName().startsWith(name))
                list.add(element);
        }
        return list;
    }

    /**
     * Все уникальные имена владельцев
     *
     */
    public Set<String> uniqueOwners(){
        Set<String> uniqs = new LinkedHashSet<>();
        Set<String> dups = new HashSet<>();
        for(Product element: collection){
            if(!uniqs.add(element.getOwner().getName()))
                dups.add(element.getOwner().getName());
        }
        uniqs.removeAll(dups);
        return uniqs;
    }

    /**
     * Проверяет, что все владельцы и продукты валидны
     *
     */
    public boolean validateAll() {
            for (Product element : collection) {
                if(element.getOwner() == null)
                    continue;
                else if (!element.getOwner().validate()) {
                    App.logger.warn("Owner - " + element.getOwner() + " has invalid fields");
                    return false;
                }
            }

            for (Product element : collection) {
                if (!element.validate()) {
                    App.logger.warn("Product with id=" + element.getId() + " has invalid fields");
                    return false;
                }
            }

            App.logger.info("All data are valid.");
            return true;
    }

    public List<Product> sort() {
        return collection.stream().sorted((o1, o2) -> {
            if(o1.getCoordinates().getX() > o2.getCoordinates().getX()){
                return 1;
            }
            else if(o1.getCoordinates().getX() < o2.getCoordinates().getX()){
                return -1;
            }
            else if(o1.getCoordinates().getX() == o2.getCoordinates().getX()){
                return o1.getCoordinates().getY().compareTo(o2.getCoordinates().getY());
            }
            return 0;
        }).collect(Collectors.toList());
    }
}
