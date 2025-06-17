package com.darian.ecommerce.businesslogic.mapper.cartmapper;

import com.darian.ecommerce.cart.dto.CartDTO;
import com.darian.ecommerce.cart.entity.Cart;
import org.springframework.stereotype.Component;


@Component
public class CartMapper {
    private  final CartItemMapper cartItemMapper;

    public CartMapper(CartItemMapper cartItemMapper) {
        this.cartItemMapper = cartItemMapper;
    }

    public CartDTO toDTO(Cart cart) {
        return CartDTO.builder()
                .userId(cart.getUser().getId())
                .items(cartItemMapper.toDTOList(cart.getItems()))
                .build();
    }
}
