package com.mytoy.bookstore.mapper;

import com.mytoy.bookstore.dto.OrderDto;
import com.mytoy.bookstore.dto.OrderDto.OrderDtoBuilder;
import com.mytoy.bookstore.model.Order;
import com.mytoy.bookstore.model.OrderBook;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-11-18T19:42:21+0900",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 11.0.11 (Oracle Corporation)"
)
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderDto toOrderDto(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderDtoBuilder orderDto = OrderDto.builder();

        orderDto.id( order.getId() );
        orderDto.user( order.getUser() );
        List<OrderBook> list = order.getOrderBooks();
        if ( list != null ) {
            orderDto.orderBooks( new ArrayList<OrderBook>( list ) );
        }
        orderDto.delivery( order.getDelivery() );
        orderDto.orderDate( order.getOrderDate() );
        orderDto.status( order.getStatus() );

        return orderDto.build();
    }
}
