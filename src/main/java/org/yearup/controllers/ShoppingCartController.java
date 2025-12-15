package org.yearup.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.yearup.data.ProductDao;
import org.yearup.data.ShoppingCartDao;
import org.yearup.data.UserDao;
import org.yearup.models.ShoppingCart;
import org.yearup.models.ShoppingCartItem;
import org.yearup.models.User;

import java.security.Principal;


@RestController
@RequestMapping
@CrossOrigin
public class ShoppingCartController {

    private final ShoppingCartDao shoppingCartDao;
    private final UserDao userDao;
    private final ProductDao productDao;

    public ShoppingCartController(ShoppingCartDao shoppingCartDao, UserDao userDao, ProductDao productDao) {
        this.shoppingCartDao = shoppingCartDao;
        this.userDao = userDao;
        this.productDao = productDao;
    }


    @GetMapping
    public ShoppingCart getCart(Principal principal) {
        try {

            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();


            return shoppingCartDao.getByUserId(userId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Error.");
        }
    }


    @PostMapping("products/{productId}")
    @ResponseStatus(HttpStatus.CREATED)
    public void addProductToCart(@PathVariable int productId, Principal principal) {
        try {
            String userName = principal.getName();
            User user = userDao.getByUserName(userName);
            int userId = user.getId();

        ///    shoppingCartDao.addProductToCart(userId, productId);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "cart failed to update.");
        }
    }

        @PutMapping("products/{productId}")
        public void updateProductQuantity(@PathVariable int productId, @RequestBody ShoppingCartItem item , Principal principal) {
            try {
                String userName = principal.getName();
                User user = userDao.getByUserName(userName);
                int userId = user.getId();
        //        shoppingCartDao.updateProductQuantity(userId, productId, item.getQuantity());

            } catch (Exception e) {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "update failed");
            }

        }

            @DeleteMapping
            @ResponseStatus(HttpStatus.NO_CONTENT)
            public void clearCart(Principal principal)
            {
                try {
                    String userName = principal.getName();
                    User user = userDao.getByUserName(userName);
                    int userId = user.getId();
        //            shoppingCartDao.clearCart(userId);
                } catch (Exception e) {
                    throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "clear cart failed");
                }
            }
        }

