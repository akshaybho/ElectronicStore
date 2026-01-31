package com.example.elctronic.store.ElectronicStore.dtos;


import com.example.elctronic.store.ElectronicStore.entities.OrderItem;
import com.example.elctronic.store.ElectronicStore.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class OrderDto {

    private String orderId;

    private String orderStatus = "PENDING";

    private String paymentStatus = "NOTPAID";

    private int orderAmount;

    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date orderedDate = new Date();

    private Date deliveredDate;

    private List<OrderItemDto> orderItems = new ArrayList<>();
}
