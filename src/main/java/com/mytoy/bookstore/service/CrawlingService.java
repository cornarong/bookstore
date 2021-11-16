package com.mytoy.bookstore.service;

import com.mytoy.bookstore.model.Book;
import com.mytoy.bookstore.model.BookType;
import com.mytoy.bookstore.model.User;
import com.mytoy.bookstore.repository.BookRepository;
import com.mytoy.bookstore.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CrawlingService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    @Transactional
    public void crawling(String uid, String type) throws IOException {
        User user = userRepository.findByUid(uid);
        String url = "";
        BookType bookType;
        /* 교보문고 국내도서 */
        if(type.equals("korea")){
            url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?mallGb=KOR&linkClass=D&range=1&kind=0&orderClick=DAb";
            bookType = BookType.DOMESTIC;
        /* 교보문고 국외도서 */
        }else if(type.equals("foreign")){
            url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?mallGb=KOR&linkClass=E&range=1&kind=0&orderClick=DAb";
            bookType = BookType.INTERNATIONAL;
        }else{
            return;
        }
        Document doc = Jsoup.connect(url).get();
        Elements e1 = doc.getElementsByAttributeValue("class", "list_type01");

        Elements titleElements = e1.get(0).select("div.title");
        Elements subtitleElements = e1.get(0).select("div.subtitle");
        Elements authorElements = e1.get(0).select("div.author");
        Elements priceElements = e1.get(0).select("div.price");
        Elements imgElements = e1.get(0).select("strong.rank").next();
        Elements detailUrlElements = e1.get(0).select("span.check").prev();

        for(int i=0; i < titleElements.size(); i++){
            // * 제목
            String title = titleElements.get(i).text();
            // * 부제목
            String subtitle = subtitleElements.get(i).text();
            // * 작가, 출판사, 발행일
            String[] info3 = authorElements.get(i).text().split("\\|");
            String author = info3[0].replace("더보기 ","").trim();
            String publisher = info3[1].trim();
            LocalDate publishedDate = LocalDate.parse(info3[2].replace("년","")
                    .replace("월","").replace("일","")
                    .trim().replaceAll(" ","-"));
            // * 할인률, 할인가, 원가
            Pattern pt = Pattern.compile("(\\w.*?)(원.*\\[)(\\w.*?)(%)");
            Matcher mc = pt.matcher(priceElements.get(i).text());
            mc.find();
            int disRate = Integer.parseInt(mc.group(3).trim());
            int disPrice = Integer.parseInt(mc.group(1).replace(",","").trim());
            int price = (int)((double)disPrice * (100 / (double)(100 - disRate)));

            // * 이미지 url
            String imageUrl = imgElements.get(i).attr("src");

            // * 상세 페이지 링크 (링크로 들어가서 나머지 정보를 긁어온다.)
            String detailUrl = detailUrlElements.get(i).attr("href");
            Document detailDoc = Jsoup.connect(detailUrl).get();

            // * 책소개, 상세이미지, 목차, 책속으로, 출판사 서평
            Elements select = detailDoc.select("div.box_detail_content");
            String html = select.toString();
            String content = "";
            if(html.contains("<!-- *** s:책소개 *** -->")){
                content += html.substring(html.indexOf("<!-- *** s:책소개 *** -->")
                        , html.indexOf("<!-- *** //e:책소개 *** -->"));
            }
            if(html.contains("<!-- *** s:상세이미지 *** -->")){
                content += html.substring(html.indexOf("<!-- *** s:상세이미지 *** -->")
                , html.indexOf("<!-- *** //e:상세이미지 *** -->"));
            }
            if(html.contains("<!-- *** s:Contents - 목차/책 속으로/출판사 서평 *** -->")){
                content += html.substring(html.indexOf("<!-- *** s:Contents - 목차/책 속으로/출판사 서평 *** -->")
                , html.indexOf("<!-- *** //e:Contents - 목차/책 속으로/출판사 서평 *** -->"));
            }

            // * 크롤링으로 구한 데이터
            // title, subtitle, author, publisher, publishedDate, price, disRate, disPrice, imageUrl, content

            // * 수동처리 데이터
            // type, quantity, shippingFee

            Book book = Book.createBook(bookType, title, subtitle, author, publisher, publishedDate, price, disRate, disPrice, imageUrl, content, user);
            if(bookRepository.findByTitle(title) == null){
                bookRepository.save(book);
            }
        }
    }
}
