package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.OrderDto;
import com.mytoy.bookstore.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {

    OrderDto toOrderDto(Order order);


}
