package com.mytoy.bookstore.service;

import com.mytoy.bookstore.form.UserForm;
import com.mytoy.bookstore.model.*;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.OrderRepository;
import com.mytoy.bookstore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class OrderServiceTest {

    @Autowired
    OrderService orderService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    BookRepository bookRepository;
    @Autowired
    UserService userService;

    // 파라미터 밖으로 빼기 Ctrl + Alt + P
    private Book createItem(String title, int price, int stockQuantity) {
        Book book = new Book();
        book.setTitle(title);
        book.setPrice(price);
        book.setQuantity(stockQuantity);
        bookRepository.save(book);
        return book;
    }

    private User createUser() {
        UserForm userForm = new UserForm();
        userForm.setUid("memberA");
        userForm.setAbode("시흥시 호반 베르디움 더 프라임 배미골길23 1507동 1203호");
        userForm.setPassword("1111");
        User savedUser = userService.save(userForm);
        return savedUser;
    }

    @Test
    @Rollback(false)
    void 상품주문() throws Exception {
        //Given
        User user = createUser();
        Book book = createItem("JPA", 10000, 10);

        int orderCount = 2;
        //When
        Long orderId = orderService.order(user.getId(), book.getId(), orderCount);

        //Then
        Order getOrder = orderRepository.findById(orderId).orElse(null);

        System.out.println(user.getRoles().get(0).getId());
//        System.out.println(user.getRoles().get(0).getUid());
        Assertions.assertEquals(OrderStatus.ORDER, getOrder.getStatus(), "상품 주문시 상태는 ORDER");
        Assertions.assertEquals(getOrder.getOrderBooks().size(), 1, "주문한 상품 종류 수가 정확해야 한다.");
        Assertions.assertEquals(getOrder.getTotalPrice(), 10000 * 2, "주문 가격은 가격 * 수량이다.");
        Assertions.assertEquals(book.getQuantity(), 8, "주문 수량만큼 재고가 줄어야 한다.");

    }

/*    @Test
    void 주문취소() throws Exception {
        //Given
        Member member = createMember();
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 2;
        Long orderId = orderService.order(member.getId(), book.getId(), orderCount);

        //When
        orderService.cancelOrder(orderId);

        //Then
        Order order = orderRepository.findOne(orderId);
        Assertions.assertEquals(order.getStatus(), OrderStatus.CANCEL, "주문 취소시 상태는 CANCEL 이다.");
        Assertions.assertEquals(book.getStockQuantity(), 10, "주문 취소된 상품은 재고가 다시 증가해야 한다.");
    }

    @Test
    void 상품주문_재고수량초과() throws Exception {
        //Given
        Member member = createMember();
        Item book = createItem("JPA", 10000, 10);
        int orderCount = 11;

        //When
        try {
            orderService.order(member.getId(), book.getId(), orderCount);
        }catch (NotEnoughStockException e){
            return;
        }

        //Then
        AssertionErrors.fail("재고 수량 부족 예외가 발생해야 한다. 여기까지 오면 안된다.");
    }*/
}