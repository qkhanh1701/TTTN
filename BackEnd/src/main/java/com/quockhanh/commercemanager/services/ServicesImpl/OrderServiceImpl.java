package com.quockhanh.commercemanager.services.ServicesImpl;

import com.quockhanh.commercemanager.converter.OrderConverter;
import com.quockhanh.commercemanager.message.request.OrderDetailsRequest;
import com.quockhanh.commercemanager.model.DTO.AddCartDTO;
import com.quockhanh.commercemanager.model.DTO.OrderDTO;
import com.quockhanh.commercemanager.model.DTO.OrderDetailsDTO;
import com.quockhanh.commercemanager.model.Order;
import com.quockhanh.commercemanager.model.User;
import com.quockhanh.commercemanager.repository.OrderRepository;
import com.quockhanh.commercemanager.services.*;
import com.quockhanh.commercemanager.services.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderConverter orderConverter;

    @Autowired
    private OrderDetailsService orderDetailsService;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private EmailService emailService;

    private static final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> list = orderRepository.findAllByOrderByIdDesc();
        List<OrderDTO> dtoList = new ArrayList<>();
        list.stream().forEach(s -> {
            dtoList.add(orderConverter.toDTO(s));
        });
        return dtoList;
    }

    @Override
    public List<OrderDTO> getTop10Orders() {
        List<OrderDTO> dtoList = new ArrayList<>();
        orderRepository.findTop10ByOrderByIdDesc().stream().forEach(s -> {
            OrderDTO dto = new OrderDTO();
            dto = orderConverter.toDTO(s);
            dtoList.add(dto);
        });
        System.out.println(dtoList);
        return dtoList;
    }

    @Override
    public OrderDTO addOrder(OrderDTO orderDTO) throws Exception {
        try {
            Order obj = new Order();
            obj.setOrder_date(orderDTO.getOrder_date());
            obj.setAmount(orderDTO.getAmount());
            obj.setAddress(orderDTO.getAddress());
            obj.setPhone_number(orderDTO.getPhone_number());
            obj.setReceiver(orderDTO.getReceiver());
            obj.setStatus(orderDTO.getStatus());
            Optional<User> temp = userService.findById(orderDTO.getUser_id());
            AtomicReference<User> pro = new AtomicReference<>(new User());
            temp.ifPresent(s -> {
                pro.set(s);
            });
            obj.setUser(pro.get());
            orderRepository.save(obj);
            List<AddCartDTO> list = cartService.getCartByUserId(orderDTO.getUser_id());
            list.stream().forEach(
                    (s) -> {
                        OrderDetailsRequest orderDetailsRequest = new OrderDetailsRequest();
                        orderDetailsRequest.setOrderId(obj.getId());
                        orderDetailsRequest.setProductId(s.getProduct().getId());
                        orderDetailsRequest.setQuantity(s.getQuantity());
                        orderDetailsRequest.setAmount(s.getPrice());
                        orderDetailsRequest.setDiscount(s.getProduct().getPromotion());
                        try {
                            OrderDetailsDTO orderDetailsDTO = orderDetailsService.addOrderDetailByOrderId(orderDetailsRequest);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
            );
            System.out.println("aa");
            cartService.removeCartByUserId(orderDTO.getUser_id());
            return orderConverter.toDTO(obj);
        }catch(Exception e) {
            e.printStackTrace();
            logger.error(""+e.getMessage());
            throw new Exception(e.getMessage());
        }
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        Order order = orderRepository.getById(id);
        return order;
    }

    @Override
    public List<OrderDTO> getListOrderByUserId(Long user_Id) throws Exception {
        Optional<User> temp = userService.findById(user_Id);
        AtomicReference<User> user = new AtomicReference<>(new User());
        temp.ifPresent(s -> {
            user.set(s);
        });

        List<Order> list = orderRepository.findOrdersByUserOrderByIdDesc(user.get());
        List<OrderDTO> listDTO = new ArrayList<>();
        list.stream().forEach(
                (s) -> {
                    listDTO.add(orderConverter.toDTO(s));
                }
        );
        return listDTO;
    }

    @Override
    public boolean sendEmail(String subject, String message, String to) throws Exception {
        boolean foo = false; // Set the false, default variable "foo", we will allow it after sending code process email
        try {
            System.out.println("Sending email...");
            SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
            simpleMailMessage.setFrom("tranminhtuannhj@gmail.com");
            simpleMailMessage.setTo(to);
            simpleMailMessage.setSubject(subject);
            simpleMailMessage.setText(message);
            System.out.println("error...");
            emailService.sendEmail(simpleMailMessage);
            System.out.println("Sending email successlly...");
            foo = true; // Set the "foo" variable to true after successfully sending emails
        }catch(Exception e){
            System.out.println("Sending mail error..." + e);
        }
        return foo; // and return foo variable
    }


}
