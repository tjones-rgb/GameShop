package org.yearup.data.mysql;


import org.springframework.stereotype.Component;
import org.yearup.data.ShoppingCartDao;
import org.yearup.models.Product;
import org.yearup.models.ShoppingCart;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class MySqlShoppingCartDao extends MySqlDaoBase implements ShoppingCartDao {
    public MySqlShoppingCartDao(DataSource dataSource) {
        super(dataSource);
    }


    @Override
    public ShoppingCart getByUserId(int userId) {
        String sql = "SELECT p.*, sc.Quantity FROM shopping_cart sc" +
                "JOIN products p ON sc.product_id " +
                "WHERE sc..user_id = ?";

        ShoppingCart cart = new ShoppingCart();

        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(sql)){
            statement.setInt(1, userId);

            ResultSet row = statement.executeQuery();

            while (row.next()) {
                Product product = MySqlProductDao.mapRow(row);
                item.setProduct(product);
                item.setQuantity(quantity);
                cart.add(item);
            }
        } catch (SQLException e) {

        }
        return cart;
    }

}
