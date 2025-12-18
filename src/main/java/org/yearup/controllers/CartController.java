package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.User;

import java.security.Principal;

@RestController
@RequestMapping("/cart")
@CrossOrigin
@PreAuthorize("isAuthenticated()")
public class CartController
{
    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;

    public CartController(ShoppingCartDao shoppingCartDao, UserDao userDao)
    {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
    }

    private int getUserId(Principal principal)
    {
        if (principal == null)
        {
            throw new RuntimeException("Principal is null");
        }

        User user = userDao.getByUserName(principal.getName());
        return user.getId();
    }

    @GetMapping
    public ShoppingCart getCart(Principal principal)
    {
        int userId = getUserId(principal);
        return shoppingCartDao.getByUserId(userId);
    }

    @PostMapping("/products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProduct(Principal principal, @PathVariable int productId)
    {
        int userId = getUserId(principal);
        shoppingCartDao.addProductToCart(userId, productId);
    }

    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void clearCart(Principal principal)
    {
        int userId = getUserId(principal);
        shoppingCartDao.clearCart(userId);
    }
}
