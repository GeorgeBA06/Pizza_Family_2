package com.example.pizza.entity;

import com.example.pizza.responseDto.CategoryDto;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.ToString;

import java.util.*;
import java.util.stream.Collectors;

//@Entity
//@Data
@Getter
public enum Category {

    PIZZA("Пицца", "Классические и авторские пиццы", 1, true),
    DRINKS("Напитки", "Холодные и горячие напитки", 2, true),
    APPETIZERS("Закуски", "Горячие и холодные закуски", 3, true),
    DESSERTS("Десерты", "Сладкие десерты и выпечка", 4, true),
    COMBO("Комбо", "Специальные наборы", 5, true);

    private final String displayName;
    private final String description;
    private final int displayOrder;
    private final boolean available;

    Category(String displayName, String description, int displayOrder, boolean available){
        this.displayName=displayName;
        this.description=description;
        this.displayOrder=displayOrder;
        this.available=available;
    }

    public List<Category> getAvailableCategories(){
        return Arrays.stream(values())
                .filter(Category :: isAvailable)
                .sorted(Comparator.comparing(Category :: getDisplayOrder))
                .collect(Collectors.toList());
    }

    public static Optional<Category> fromDisplayName(String displayName){
        return Arrays.stream(values())
                .filter(category -> category.getDisplayName().equalsIgnoreCase(displayName))
                .findFirst();
    }

    public CategoryDto toDto(){
        return new CategoryDto(
                this.name(),
                this.displayName,
                this.description,
                this.displayOrder,
                this.available
        );
    }




//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY )
//    private Long Id;
//
//    @Column(name = "category_name", nullable = false, unique = true)
//    private String name;
//
//    @OneToMany(mappedBy = "category", fetch = FetchType.LAZY)
//    @ToString.Exclude
//    private List<Item> itemList = new ArrayList<>();
}
