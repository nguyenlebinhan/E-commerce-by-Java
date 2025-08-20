package com.example.demo.services;

import com.example.demo.Entity.Cart;
import com.example.demo.Entity.CartItem;
import com.example.demo.Entity.Product;
import com.example.demo.Repository.CartItemRepo;
import com.example.demo.Repository.CartRepo;
import com.example.demo.Repository.ProductRepo;
import com.example.demo.exceptions.APIException;
import com.example.demo.exceptions.ResourceNotFoundException;
import com.example.demo.payloads.CartDTO;
import com.example.demo.payloads.ProductDTO;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.stream.Collectors;


@Transactional
@Service
public class CartServiceImpl implements CartService{

    @Autowired
    private CartRepo cartRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private CartItemRepo cartItemRepo;

    @Autowired
    private ModelMapper modelMapper;


    @Override
    public CartDTO addProductToCart(Long cartId, Long productId, Integer quantity) {

        Cart cart =cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","cartId",cartId));

        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        CartItem cartItem =cartItemRepo.findCartItemByProductIdAndCartID(cartId,productId);

        if(cartItem== null){
            throw new APIException("Product "+product.getProductName()+" already exists in the cart");
        }

        if(product.getQuantity() == 0 ){
            throw new APIException(product.getProductName()+ " is not available");
        }

        if(product.getQuantity()< quantity){
            throw new APIException("Please, make an order of the "+product.getProductName()+" less than or equal to the quantity "+product.getQuantity()+".");
        }

        CartItem newCartItem = new CartItem();
        newCartItem.setProduct(product);
        newCartItem.setCart(cart);
        newCartItem.setQuantity(quantity);
        newCartItem.setDiscount(product.getDiscount());
        newCartItem.setProductPrice(product.getSpecialPrice());

        cartItemRepo.save(newCartItem);

        product.setQuantity(product.getQuantity()-quantity);

        cart.setTotalPrice(cart.getTotalPrice()+(product.getSpecialPrice()*quantity));

        CartDTO cartDTO =modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).toList();

        cartDTO.setProducts(productDTOs);

        return cartDTO;
    }

    @Override
    public List<CartDTO> getAllCarts() {
        List<Cart> carts = cartRepo.findAll();

        if(carts.size()==0){
            throw new APIException("No cart has been found");
        }

        List<CartDTO> cartDTOs= carts.stream().map(cart->{
            CartDTO cartDTO=modelMapper.map(cart, CartDTO.class);
            List<ProductDTO>productDTOs= cart.getCartItems().stream().map(cartItem->modelMapper.map(cartItem, ProductDTO.class)).toList();

            cartDTO.setProducts(productDTOs);

            return cartDTO;
        }).toList();

        return cartDTOs;

    }

    @Override
    public CartDTO getCart(String emailId, Long cartId) {
        Cart cart = cartRepo.findCartByEmailAndCartID(emailId,cartId);

        if(cart == null){
            throw new ResourceNotFoundException("Cart","cartId",cartId);
        }

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> products = cart.getCartItems().stream()
                .map(p-> modelMapper.map(p.getProduct(),ProductDTO.class)).toList();

        cartDTO.setProducts(products);

        return cartDTO;
    }

    @Override
    public CartDTO updateProductQuantityInCart(Long cartId, Long productId, Integer quantity) {
        Cart cart = cartRepo.findById(cartId)
                .orElseThrow(() -> new ResourceNotFoundException("Cart", "cartId", cartId));

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "productId", productId));

        if (product.getQuantity() == 0) {
            throw new APIException(product.getProductName() + " is not available");
        }

        if (product.getQuantity() < quantity) {
            throw new APIException("Please, make an order of the " + product.getProductName()
                    + " less than or equal to the quantity " + product.getQuantity() + ".");
        }

        CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartID(cartId, productId);

        if (cartItem == null) {
            throw new APIException("Product " + product.getProductName() + " not available in the cart!!!");
        }

        double cartPrice = cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        product.setQuantity(product.getQuantity() + cartItem.getQuantity() - quantity);

        cartItem.setProductPrice(product.getSpecialPrice());
        cartItem.setQuantity(quantity);
        cartItem.setDiscount(product.getDiscount());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice() * quantity));

        cartItem = cartItemRepo.save(cartItem);

        CartDTO cartDTO = modelMapper.map(cart, CartDTO.class);

        List<ProductDTO> productDTOs = cart.getCartItems().stream()
                .map(p -> modelMapper.map(p.getProduct(), ProductDTO.class)).collect(Collectors.toList());

        cartDTO.setProducts(productDTOs);

        return cartDTO;

    }
    @Override
    public void updateProductInCarts(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","cartId",cartId));

        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product","productId",productId));

        CartItem cartItem =cartItemRepo.findCartItemByProductIdAndCartID(cartId,productId);

        if(cartItem == null){
            throw new APIException("Product "+product.getProductName()+" not available in the cart!!!");
        }

        double cartPrice =cart.getTotalPrice() - (cartItem.getProductPrice() * cartItem.getQuantity());

        cartItem.setProductPrice(product.getSpecialPrice());

        cart.setTotalPrice(cartPrice + (cartItem.getProductPrice()*cartItem.getQuantity()));

        cartItem=cartItemRepo.save(cartItem);
    }

    @Override
    public String deleteProductFromCart(Long cartId, Long productId) {
        Cart cart = cartRepo.findById(cartId).orElseThrow(()->new ResourceNotFoundException("Cart","cartId",cartId));

        CartItem cartItem = cartItemRepo.findCartItemByProductIdAndCartID(cartId,productId);

        if(cartItem == null){
            throw new ResourceNotFoundException("Product","productId",productId);
        }

        cart.setTotalPrice(cart.getTotalPrice()-(cartItem.getProductPrice()*cartItem.getQuantity()));

        Product product= cartItem.getProduct();
        product.setQuantity(product.getQuantity()+cartItem.getQuantity());

        cartItemRepo.deleteCartItemByProductIdAndCartId(cartId,productId);

        return "Product "+ cartItem.getProduct().getProductName()+ " removed from the cart !!!";
    }
}
