package com.project.shopapp.entity;

import jakarta.persistence.*;
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
@Table(name = "order_details")
public class OderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;

    @ManyToOne
    Order order;

    @ManyToOne
    Product product;

    @Column(nullable = false)
    float price;

    @Column(nullable = false)
    int quantity;

    @Column(nullable = false)
    long totalMoney;

    String color;
}
