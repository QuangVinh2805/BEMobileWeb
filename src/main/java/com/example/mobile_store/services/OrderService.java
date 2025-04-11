package com.example.mobile_store.services;


import com.example.mobile_store.models.*;
import com.example.mobile_store.repository.*;
import com.example.mobile_store.request.OrderRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class OrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    CartRepository cartRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductDetailRepository productDetailRepository;

    @Autowired
    private JavaMailSender emailSender;

    public ResponseEntity<List<Order>> listAllOrder(String startDateQuery, String endDateQuery) {
        List<Order> listOrder;

        if (startDateQuery != null && endDateQuery != null) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date createdAt = formatter.parse(startDateQuery);
                Date endAt = formatter.parse(endDateQuery);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(createdAt);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                Date startDate = calendar.getTime();


                Calendar calendar2 = Calendar.getInstance();
                calendar2.setTime(endAt);
                calendar2.set(Calendar.HOUR_OF_DAY, 23);
                calendar2.set(Calendar.MINUTE, 59);
                calendar2.set(Calendar.SECOND, 59);
                Date endDate = calendar2.getTime();

                if (endDate.after(startDate)) {
                    listOrder = orderRepository.findByDate(startDate, endDate);
                } else {
                    listOrder = orderRepository.findAll();
                }
            } catch (Exception e) {
                listOrder = orderRepository.findAll();
            }
        } else {
            listOrder = orderRepository.findAll();
        }
        if (listOrder.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Order>>(listOrder, HttpStatus.OK);
    }

    public void save(Order order) {
        orderRepository.save(order);
    }

    public ResponseEntity<List<OrderDetail>> updateOrder(OrderRequest request) {
        // Kiểm tra request null
        if (request == null) {
            String message = "Request không được null";
            System.out.println(message);
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Long orderId = request.getOrderId();
        Long userId = request.getUserId();
        Long status = request.getStatus();
        String address = request.getAddress();

        // Kiểm tra các giá trị đầu vào
        if (orderId == null) {
            System.out.println("orderId không được null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userId == null) {
            System.out.println("userId không được null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (status == null) {
            System.out.println("status không được null");
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        // Kiểm tra người dùng và đơn hàng
        User user = userRepository.findByUserId(userId);
        if (user == null) {
            System.out.println("User không tồn tại");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        Order order = orderRepository.findOrderById(orderId);
        if (order == null) {
            System.out.println("Order không tồn tại");
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }

        // Cập nhật trạng thái và thời gian cập nhật
        order.setStatus(status);
        order.setUpdatedAt(new Date());

        // Cập nhật địa chỉ nếu có
        if (address != null && !address.isEmpty()) {
            order.setAddress(address);
        }

        orderRepository.save(order);

        // Trả về danh sách chi tiết đơn hàng
        List<OrderDetail> orderDetails = orderDetailRepository.findOrderDetailByOrder(order);
        return new ResponseEntity<>(orderDetails, HttpStatus.OK);
    }


    public ResponseEntity<?> createOrder(OrderRequest request) {
        Long userId = request.getUserId();
        String address = request.getAddress();

        if (userId == null) {
            String message = "userId null";
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }

        User user = userRepository.findByUserId(userId);
        if (user == null) {
            String message = "User null";
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }

        List<Cart> carts = cartRepository.findByUserId(userId);
        if (carts == null || carts.isEmpty()) {
            String message = "Bạn chưa chọn sản phẩm để thanh toán";
            System.out.println(message);
            return new ResponseEntity<>(message, HttpStatus.FORBIDDEN);
        }

        StringBuilder emailMessage = new StringBuilder();
        emailMessage.append("Chúc mừng bạn đã đặt hàng thành công\n");

        List<OrderDetail> orderDetails = new ArrayList<>();
        long totalPrice = 0L;

        // Tạo mới đơn hàng trước để có order_id
        Order order = Order.builder()
                .user(user)
                .status(0L)
                .address(address)
                .createdAt(new Date())
                .build();
        orderRepository.save(order);

        for (Cart cart : carts) {
            Product product = cart.getProduct();
            ProductPrice productPrice = cart.getProductPrice(); // lấy product_price từ cart
            long quantity = cart.getQuantity();
            long itemTotal = productPrice.getPrice() * quantity;

            emailMessage.append("- ")
                    .append(quantity).append(" ")
                    .append(product.getProductName()).append(": ")
                    .append(itemTotal).append(" VND\n");

            OrderDetail orderDetail = OrderDetail.builder()
                    .product(product)
                    .productPrice(productPrice)
                    .order(order)
                    .quantity(quantity)
                    .totalPrice(itemTotal)
                    .createdAt(new Date())
                    .build();

            orderDetails.add(orderDetail);
            totalPrice += itemTotal;
        }

        orderDetailRepository.saveAll(orderDetails);

        // Cập nhật lại tổng tiền vào Order
        order.setTotalPrice((long) totalPrice);
        orderRepository.save(order);
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("vinhdaumoi2805.com");
        message.setTo(user.getEmail());
        message.setSubject("Thông báo đặt hàng thành công");
        message.setText(emailMessage.toString());

        new Thread(() -> emailSender.send(message)).start();

        // Xoá giỏ hàng sau khi tạo đơn hàng
        cartRepository.deleteAll(carts);


//        // Gửi email nếu cần (đang chỉ log ra console)
//        System.out.println(emailMessage);

        return new ResponseEntity<>("Đặt hàng thành công!", HttpStatus.OK);
    }



    public ResponseEntity<List<OrderDetail>> getOrders(Long userId) {
        List<OrderDetail> orderDetails;
        orderDetails = orderDetailRepository.getOrderDetailsByUserId(userId);
        if (orderDetails.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderDetails);
    }


    public ResponseEntity<String> deleteOrder(Long id) {
        Long orderId = orderRepository.findIdByOrderId(id);
        if (orderId == null) {
            String message = "order id not found";
            System.out.println(message + orderId);
            return new ResponseEntity(message, HttpStatus.FORBIDDEN);

        }
        orderRepository.deleteById(orderId);
        return new ResponseEntity(HttpStatus.OK);
    }

}
