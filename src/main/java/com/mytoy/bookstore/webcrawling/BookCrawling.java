package com.mytoy.bookstore.webcrawling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookCrawling {

    public static void main(String[] args) throws IOException {

        /* 교보문고 메인 */
        String url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?mallGb=KOR&linkClass=D&range=1&kind=0&orderClick=DAb";
        Document doc = Jsoup.connect(url).get();
        Elements e1 = doc.getElementsByAttributeValue("class", "list_type01");
        int bookLength = e1.get(0).select("div .button").size();

        for(int i=0; i < bookLength; i++){

            // 제목
            Elements titleElements = e1.get(0).select("div .title");
            // 부제목
            Elements subtitleElements = e1.get(0).select("div .subtitle");
            // 작가 (작가 + 출판사 + 발행일)
            Elements authorElements = e1.get(0).select("div .author");
            // 가격 (할인가 + 할인률) -> 원가 따로 로직만들어서 구해야됨.
            Elements priceElements = e1.get(0).select("div .price");
            // 이미지 구하면됨 jsoup 이미지 크롤링 찾아보기.

            System.out.println(titleElements.get(i).text());
            System.out.println(subtitleElements.get(i).text());
            System.out.println(authorElements.get(i).text());
            System.out.println(priceElements.get(i).text());
            System.out.println("================================");

        }

//        // 제목
//        Elements titleElements = e1.get(0).select("div .title");
//        for(Element e : titleElements){
//            System.out.println(e.text());
//        }
//
//        // 부제목
//        Elements subtitleElements = e1.get(0).select("div .subtitle");
//        for(Element e : subtitleElements){
//            System.out.println(e.text());
//        }
//
//        // 저자
//        Elements authorElements = e1.get(0).select("div .author");
//        for(Element e : authorElements){
//            System.out.println(e.text());
//        }

    }
}
