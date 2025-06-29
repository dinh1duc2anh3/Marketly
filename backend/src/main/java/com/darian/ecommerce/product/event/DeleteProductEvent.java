package com.darian.ecommerce.product.event;

import com.darian.ecommerce.auth.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class DeleteProductEvent extends ApplicationEvent {
    private final Integer userId;
    private final Long productId;
    private final UserRole role;

    public DeleteProductEvent(Object source, Integer userId, Long productId, UserRole role) {
        super(source);
        this.userId = userId;
        this.productId = productId;
        this.role = role;
    }
}