package com.mytoy.bookstore.dto;


import com.mytoy.bookstore.model.Basket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BasketDto {

    private Long id;
    private Long bookId;
    private String title;
    private int price;
    private int disPrice;
    private int disRate;
    private int quantity;
    private int shippingFee;

    private String thumbnailType;


    /* 장바구니Dto 생성 메소드 */
    public static BasketDto createBasketDto(Basket basket){
        BasketDto basketDto = BasketDto.builder()
                .id(basket.getId())
                .bookId(basket.getBook().getId())
                .title(basket.getBook().getTitle())
                .price(basket.getBook().getPrice())
                .disRate(basket.getBook().getDisRate())
                .disPrice(basket.getBook().getDisPrice())
                .shippingFee(basket.getBook().getShippingFee())
                .quantity(basket.getQuantity())
                .thumbnailType(basket.getBook().getThumbnailType())
                .build();
        return basketDto;
    }

}
