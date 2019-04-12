package fr.kybox.kypizzapp.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.api.payments.*;
import fr.kybox.kypizzapp.config.property.PaypalProperties;
import fr.kybox.kypizzapp.exception.NotFoundException;
import fr.kybox.kypizzapp.model.Product;
import fr.kybox.kypizzapp.model.auth.Address;
import fr.kybox.kypizzapp.model.auth.RegisteredUser;
import fr.kybox.kypizzapp.model.order.Order;
import fr.kybox.kypizzapp.model.order.OrderProduct;
import fr.kybox.kypizzapp.model.payment.paypal.PaypalPayUrl;
import fr.kybox.kypizzapp.model.payment.paypal.PaypalToken;
import fr.kybox.kypizzapp.service.OrderService;
import fr.kybox.kypizzapp.service.PaypalService;
import fr.kybox.kypizzapp.service.ProductService;
import fr.kybox.kypizzapp.utils.MathUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import static com.paypal.base.Constants.HTTP_CONFIG_DEFAULT_HTTP_METHOD;
import static fr.kybox.kypizzapp.utils.constant.ValueObject.SPACE;
import static org.springframework.http.HttpHeaders.*;

@Service
public class PaypalServiceImpl implements PaypalService {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    private final PaypalProperties paypalProperties;
    private final ProductService productService;
    private final OrderService orderService;

    public PaypalServiceImpl(ProductService productService, OrderService orderService, PaypalProperties paypalProperties) {
        this.productService = productService;
        this.orderService = orderService;
        this.paypalProperties = paypalProperties;
    }

    @Override
    public PaypalPayUrl paymentProcess(String orderId, RedirectUrls redirectUrls) throws IOException {

        Order order = orderService.findById(orderId);



        PaypalToken paypalToken = generatePaypalToken();
        List<Item> items = generatePaypalItemList(order);
        ShippingAddress shippingAddress = generateShippingAddress(order);
        ItemList itemList = generateItemList(items, order, shippingAddress);

        return null;
    }


    private List<Item> generatePaypalItemList(Order order) {

        List<OrderProduct> productList = order.getProductList();

        if (productList == null || productList.isEmpty())
            throw new NotFoundException("There are no product in this order.");

        List<Item> itemList = new ArrayList<>();

        for (OrderProduct orderProduct : productList) {

            Product product = productService.findProductById(orderProduct.getId());

            Item item = new Item();
            item.setCurrency("USD");
            item.setName(product.getName());
            item.setDescription(product.getDescription());
            item.setCategory("PHYSICAL");
            item.setQuantity(String.valueOf(orderProduct.getQuantity()));
            item.setPrice(String.valueOf(product.getPrice()));
            item.setTax(String
                    .valueOf(MathUtils.getTaxAmountRounded(product.getPrice(),
                            orderProduct.getQuantity(), product.getTax())));
            itemList.add(item);
        }

        return itemList;
    }

    private PaypalToken generatePaypalToken() throws IOException {

        URL url = new URL(paypalProperties.getSandboxUrl());
        URLConnection urlConnection = url.openConnection();
        HttpURLConnection httpURLConnection = null;

        if(urlConnection instanceof HttpURLConnection)
            httpURLConnection = (HttpURLConnection) urlConnection;
        else throw new IOException("Http url is not correct.");

        String credentials = paypalProperties.getClientId() + ":" + paypalProperties.getSecret();
        String basicAuth = "Basic " + DatatypeConverter.printBase64Binary(credentials.getBytes());

        httpURLConnection.setDoOutput(true);
        httpURLConnection.setRequestMethod(HTTP_CONFIG_DEFAULT_HTTP_METHOD);
        httpURLConnection.setRequestProperty(ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        httpURLConnection.setRequestProperty(AUTHORIZATION, basicAuth);
        httpURLConnection.setRequestProperty(CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE);

        DataOutputStream dos = new DataOutputStream(httpURLConnection.getOutputStream());
        dos.writeBytes("grant_type=client_credentials");
        dos.flush();
        dos.close();

        BufferedReader br = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        String data;
        StringBuilder urlContent = new StringBuilder();

        while ((data = br.readLine()) != null) urlContent.append(data);

        return new ObjectMapper().readValue(urlContent.toString(), PaypalToken.class);
    }

    private ShippingAddress generateShippingAddress(Order order) {

        Address address = order.getAddress();
        if(address == null)
            throw new NotFoundException("The reference address of the order was not found.");

        ShippingAddress shippingAddress = new ShippingAddress();
        RegisteredUser user = order.getCustomer();

        shippingAddress.setRecipientName(address.getFirstName() + SPACE + address.getLastName());
        shippingAddress.setLine1(address.getStreetNumber() + SPACE + address.getStreetName());
        if(address.getAdditionalInfo() != null) shippingAddress.setLine2(address.getAdditionalInfo());

        shippingAddress.setPostalCode(address.getZipCode());
        shippingAddress.setCity(address.getCity());
        shippingAddress.setCountryCode("US");

        return shippingAddress;
    }

    private ItemList generateItemList(List<Item> items, Order order, ShippingAddress shippingAddress) {

        ItemList itemList = new ItemList();
        itemList.setItems(items);
        itemList.setShippingAddress(shippingAddress);
        itemList.setShippingPhoneNumber(order.getCustomer().getPhoneNumber());
        itemList.setShippingMethod(order.getDeliveryMethod().getDescription());

        return itemList;
    }

    private String initPayment(Order order, ItemList itemList, RedirectUrls redirectUrls) {

        //Details details = getPaypalDetails(itemList, order.)
        return null;
    }

    private Details getPaypalDetails(ItemList itemList, double deliveryAmount) {

        Details details = new Details();
        details.setSubtotal(String.valueOf(getSubTotalAmount(itemList)));
        details.setTax(String.valueOf(getTaxAmount(itemList)));
        details.setShipping(String.valueOf(deliveryAmount));

        return details;
    }

    private double getSubTotalAmount(ItemList itemList) {

        double subTotal = 0;

        for(Item item : itemList.getItems())
            subTotal += Double.parseDouble(item.getPrice()) * Double.parseDouble(item.getQuantity());

        return subTotal;
    }

    private double getTaxAmount(ItemList itemList) {

        double taxAmount = 0;

        for(Item item : itemList.getItems())
            taxAmount += Double.parseDouble(item.getTax());

        return taxAmount;
    }
}
