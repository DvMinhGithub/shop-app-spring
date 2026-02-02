package com.project.shopapp.dto.request;

import java.util.List;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CartMergeRequest {
    @NotEmpty(message = "Cart items are required")
    @Valid
    List<CartItemMergeRequest> items;
}
