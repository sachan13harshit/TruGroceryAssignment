package com.trugroceryassignment.cart_api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BillResponse {
    private Integer itemTotalPaise;
    private Integer deliveryFeePaise;
    private Integer grandTotalPaise;
}
