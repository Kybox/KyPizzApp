package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.config.property.RestaurantProperties;
import fr.kybox.kypizzapp.exception.ProductNotFoundException;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.model.cart.Cart;
import fr.kybox.kypizzapp.model.cart.ProductFromCart;
import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.model.order.OrderStatus;
import fr.kybox.kypizzapp.repository.OrderRepository;
import fr.kybox.kypizzapp.repository.ProductRepository;
import fr.kybox.kypizzapp.service.AuthService;
import fr.kybox.kypizzapp.service.OrderService;
import fr.kybox.kypizzapp.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RestaurantProperties restaurantProperties;

    @Autowired
    public OrderServiceImpl(AuthService authService,
                            RestaurantProperties restaurantProperties,
                            ProductRepository productRepository,
                            OrderRepository orderRepository) {

        this.authService = authService;
        this.restaurantProperties = restaurantProperties;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
    }

    @Override
    public ResponseEntity<Order> createOrder(HttpServletRequest request) {

        /*
          Error filters on cart
         */
        Cookie cookie = CookieUtils.getCookieCart(request);
        if (cookie == null) return ResponseEntity.badRequest().build();

        Cart cart = CookieUtils.getCartFromCookie(cookie);
        if(cart == null) return ResponseEntity.badRequest().build();

        if(cart.getProductList().isEmpty()) return ResponseEntity.badRequest().build();

        /*
          Error filter on jwt
         */
        RegisteredUser customer = authService.getAuthenticatedUser(request);
        if(customer == null) return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();

        return ResponseEntity.ok(orderRepository.save(createNewOrder(customer, cart)));
    }

    private Order createNewOrder(RegisteredUser customer, Cart cart) throws ProductNotFoundException {

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.SAVED);
        order.setRestaurant(restaurantProperties.getName());
        order.setPaid(false);
        order.setToDeliver(false);
        order.setCreationDate(LocalDateTime.now());

        for(ProductFromCart productFromCart : cart.getProductList()){

            Optional<Product> optProduct = productRepository.findById(productFromCart.getId());
            if(!optProduct.isPresent())
                throw new ProductNotFoundException("Cannot found the product " + productFromCart.getName());

            order.getProductList().put(productFromCart.getId(), productFromCart.getQuantity());
        }

        return order;
    }
}
