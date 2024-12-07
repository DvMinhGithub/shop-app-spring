package com.project.shopapp.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = lombok.AccessLevel.PRIVATE)
@Entity
public class OderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    long orderId;
    long productId;
    float price;
    int quantity;
    long totalMoney;
    String color;
}
