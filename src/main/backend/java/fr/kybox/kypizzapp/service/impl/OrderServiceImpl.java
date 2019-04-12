package fr.kybox.kypizzapp.service.impl;

import fr.kybox.kypizzapp.config.property.RestaurantProperties;
import fr.kybox.kypizzapp.exception.BadRequestException;
import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.exception.ProductNotFoundException;
import fr.kybox.kypizzapp.exception.UnauthorizedException;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.model.cart.Cart;
import fr.kybox.kypizzapp.model.cart.ProductFromCart;
import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.model.order.OrderProduct;
import fr.kybox.kypizzapp.model.order.OrderStatus;
import fr.kybox.kypizzapp.repository.OrderRepository;
import fr.kybox.kypizzapp.repository.ProductRepository;
import fr.kybox.kypizzapp.service.AuthService;
import fr.kybox.kypizzapp.service.OrderService;
import fr.kybox.kypizzapp.utils.CookieUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final RestaurantProperties restaurantProperties;
    private Logger log = LoggerFactory.getLogger(this.getClass());

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
    public Order createOrder(HttpServletRequest request) {

        Cookie cookie = CookieUtils.getCookieCart(request);
        if (cookie == null) throw new BadRequestException("There is no cookie in the request");

        Cart cart = CookieUtils.getCartFromCookie(cookie);
        if (cart == null) throw new BadRequestException("There is no cart in cookies");

        if (cart.getProductList().isEmpty())
            throw new BadRequestException("There no items in the order");

        RegisteredUser customer = authService.getAuthenticatedUser(request);
        if (customer == null) throw new UnauthorizedException("The user is not authenticated");

        Order order = null;
        try { order = findLastSavedOrder(request); }
        catch (RuntimeException e) { log.warn(e.getMessage()); }

        if (order != null) orderRepository.delete(order);

        return orderRepository.save(createNewOrder(customer, cart));
    }

    @Override
    public Order updateOrder(Order order) throws BadRequestException {

        if (!orderRepository.findById(order.getId()).isPresent())
            throw new BadRequestException("The order was not found");

        return orderRepository.save(order);
    }

    @Override
    public Order findLastSavedOrder(HttpServletRequest request) throws RuntimeException {

        RegisteredUser user = authService.getAuthenticatedUser(request);
        if (user == null) throw new UnauthorizedException("The user is not authenticated");

        Optional<Order> optOrder = orderRepository.findByCustomerAndStatus(user, OrderStatus.SAVED);

        return optOrder.orElseThrow(() -> new NotFoundException("No order with saved status was found"));
    }

    @Override
    public Order findById(String id) {

        return orderRepository.findById(id).orElseThrow(() -> new NotFoundException("The order was not found."));
    }

    private Order createNewOrder(RegisteredUser customer, Cart cart) throws ProductNotFoundException {

        Order order = new Order();
        order.setCustomer(customer);
        order.setStatus(OrderStatus.SAVED);
        order.setRestaurant(restaurantProperties.getName());
        order.setPaid(false);
        order.setCreationDate(LocalDateTime.now());

        for (ProductFromCart productFromCart : cart.getProductList()) {

            Optional<Product> optProduct = productRepository.findById(productFromCart.getId());
            if (!optProduct.isPresent())
                throw new ProductNotFoundException("Cannot found the product " + productFromCart.getName());

            order.getProductList().add(new OrderProduct(productFromCart.getId(), productFromCart.getQuantity()));
        }

        return order;
    }
}
