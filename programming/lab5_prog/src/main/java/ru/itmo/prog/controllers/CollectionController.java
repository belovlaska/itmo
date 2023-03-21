package ru.itmo.prog.controllers;

import ru.itmo.prog.exceptions.InvalidDataException;
import ru.itmo.prog.models.Product;
import ru.itmo.prog.utils.consoleShell.Console;


import java.time.LocalDateTime;
import java.util.*;

/**
 * Класс для контроля коллекции
 * @author belovlaska
 * @version 1.0
 */

public class CollectionController {
    private HashSet<Product> collection;
    private DumpController dumpController;
    private LocalDateTime lastInitTime;
    private LocalDateTime lastSaveTime;

    public CollectionController(DumpController dumpController) {
        this.lastInitTime = null;
        this.lastSaveTime = null;
        this.dumpController = dumpController;

        loadCollection();
    }

    /**
     * @return Коллекия
     */
    public HashSet<Product> getCollection() {
        return collection;
    }

    /**
     * @return Дата и время последней инициализации
     */
    public LocalDateTime getLastInitTime() {
        return lastInitTime;
    }

    /**
     * @return Дата и время последнего сохранения
     */
    public LocalDateTime getLastSaveTime() {
        return lastSaveTime;
    }

    /**
     * Сохраняет коллекцию в файл
     */
    public void saveCollection() {
        dumpController.writeCollection(collection);
        lastSaveTime = LocalDateTime.now();
    }

    /**
     * Загружает коллекцию из файла.
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
        return info.substring(0, collection.size() - 2);
    }

    /**
     * @return Тип коллекции
     */
    public String getType() {
        return collection.getClass().getName();
    }

    /**
     * @return Размер коллекции
     */
    public int getSize() {
        return collection.size();
    }

    /**
     * Добавляет элемент в коллекцию
     *
     * @param element Элемент для добавления
     */
    public void addToCollection(Product element) {
        collection.add(element);
    }

    /**
     * Удаляет елемент коллекции
     *
     * @param element элемент
     */
    public void removeElement(Product element) {
        collection.remove(element);
    }

    public Product getById(Integer id) {
        for (Product element : collection) {
            if (element.getId() == id)
                return element;
        }
        return null;
    }

    public void clearCollection() {
        collection.clear();
    }

    public boolean greaterThanAll(Product product) {
        for (Product element : collection) {
            if (product.compareTo(element) < 0) return false;
        }
        return true;
    }

    public void removeLower(Product product){
        for (Product element: collection){
            if(product.compareTo(element) > 0){
                collection.remove(element);
            }
        }
    }

    public List<Product> listContainsName(String name){
        List<Product> list = new ArrayList<>();
        for (Product element: collection){
            if(element.getName().contains(name))
                list.add(element);
        }
        return list;
    }

    public List<Product> listStartsWithName(String name){
        List<Product> list = new ArrayList<>();
        for (Product element: collection){
            if(element.getName().startsWith(name))
                list.add(element);
        }
        return list;
    }

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

    public void validateAll(Console console) {
        try {
            for (Product element : collection) {
                if (!element.getOwner().validate()) {
                    console.printError("Владелец - " + element.getOwner() + " имеет невалидные поля");
                    throw new InvalidDataException();
                }
            }

            for (Product element : collection) {
                if (!element.validate()) {
                    console.printError("Продукт с id=" + element.getId() + " имеет невалидные поля.");
                    throw new InvalidDataException();
                }
            }

            console.println("Данные валидны.");

        }catch(InvalidDataException ignored) {}
    }
}
