package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.OrderDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.Order;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/orders")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class OrdersController
{
    private OrderDao orderDao;
    private ShoppingCartDao shoppingCartDao;
    private UserDao userDao;

    public OrdersController(OrderDao orderDao,
                            ShoppingCartDao shoppingCartDao,
                            UserDao userDao)
    {
        this.orderDao = orderDao;
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
    }

    private int getUserId(Principal principal)
    {
        if (principal == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        User user = userDao.getByUserName(principal.getName());

        if (user == null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);

        return user.getId();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Order createOrder(Principal principal)
    {
        int userId = getUserId(principal);

        var cart = shoppingCartDao.getByUserId(userId);

        if (cart == null || cart.getItems().isEmpty())
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Cannot create order with empty cart");

        Order order = orderDao.create(userId);
        shoppingCartDao.clearCart(userId);

        return order;
    }
}
