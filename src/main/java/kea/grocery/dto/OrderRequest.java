package kea.grocery.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OrderRequest {
    private int productId;
    private int quantity;


}

