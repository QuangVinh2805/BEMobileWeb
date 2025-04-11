package com.example.mobile_store.controller;

import com.example.mobile_store.models.Order;
import com.example.mobile_store.models.OrderDetail;
import com.example.mobile_store.repository.OrderRepository;
import com.example.mobile_store.request.OrderRequest;
import com.example.mobile_store.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    OrderService orderService;

    public static Logger logger = LoggerFactory.getLogger(OrderController.class);
    @Autowired
    private OrderRepository orderRepository;

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<List<Order>> listAllOrder(@RequestParam(required = false) String startDate,
                                                    @RequestParam(required = false) String endDate) {
        return orderService.listAllOrder(startDate, endDate);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrder(@PathVariable("id") long id) {
        return orderService.deleteOrder(id);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<List<OrderDetail>> getOrders(@PathVariable("id") long id) {
        return orderService.getOrders(id);
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<HttpStatus> deleteAllOrder() {
        try {
            orderRepository.deleteAll();
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<List<OrderDetail>> updateOrder(@RequestBody OrderRequest request) {
        Date updatedAt = request.getUpdatedAt();
        System.out.println("Ngày cập nhật: " + updatedAt); // Đã parse thành Date
        return orderService.updateOrder(request);
    }

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestBody OrderRequest orderRequest) {
        return orderService.createOrder(orderRequest);
    }
}
