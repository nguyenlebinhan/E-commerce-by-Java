package com.example.demo.Repository;

import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface CartItemRepo extends JpaRepository<CartItem,Long> {

    @Query("Select ci.product from CartItem ci WHERE ci.product.id=?1")
    Product findProductById(Long productID);

    @Query("Select c from CartItem c WHERE c.cart.id=?1 AND c.product.id=?2")
    CartItem findCartItemByProductIdAndCartID(Long cartID,Long productID);


    @Modifying
    @Query("DELETE FROM CartItem c WHERE c.product.id = ?1 AND c.cart.id = ?2")
    void deleteCartItemByProductIdAndCartId(Long productId, Long cartId);

}
