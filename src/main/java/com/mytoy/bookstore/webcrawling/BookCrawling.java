package com.mytoy.bookstore.webcrawling;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BookCrawling {

//    public static void main(String[] args) throws IOException {
//        /* 교보문고 메인(외국도서) */
//        String url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?mallGb=KOR&linkClass=E&range=1&kind=0&orderClick=DAb";
//        Document doc = Jsoup.connect(url).get();
//        Elements e1 = doc.getElementsByAttributeValue("class", "list_type01");
//
//
//        Elements titleElements = e1.get(0).select("div.title");
//        Elements subtitleElements = e1.get(0).select("div.subtitle");
//        Elements authorElements = e1.get(0).select("div.author");
//        Elements priceElements = e1.get(0).select("div.price");
//        Elements imgElements = e1.get(0).select("strong.rank").next();
//        Elements detailUrlElements = e1.get(0).select("span.check").prev();
//
//
//        for (int i = 0; i < 1; i++) {
//            // * 제목
//            String title = titleElements.get(i).text();
//            // * 부제목
//            String subtitle = subtitleElements.get(i).text();
//            // * 작가, 출판사, 발행일
//            String[] info3 = authorElements.get(i).text().split("\\|");
//            String author = info3[0].replace("더보기 ","").trim();
//            String publisher = info3[1].trim();
//            LocalDate publishedDate = LocalDate.parse(info3[2].replace("년","").replace("월","")
//                    .replace("일","").trim().replaceAll(" ","-"));
//            // * 할인률, 할인가, 원가
//            Pattern pt = Pattern.compile("(\\w.*?)(원.*\\[)(\\w.*?)(%)");
//            Matcher mc = pt.matcher(priceElements.get(i).text());
//            mc.find();
//            int disRate = Integer.parseInt(mc.group(3).trim());
//            int disPrice = Integer.parseInt(mc.group(1).replace(",","").trim());
//            int price = (int)((double)disPrice * (100 / (double)(100 - disRate)));
//            // * 이미지 url
//            String imageUrl = imgElements.get(i).attr("src");
//
//            // * 상세 페이지 링크 (링크로 들어가서 나머지 정보를 긁어온다.)
//            System.out.println(detailUrlElements.get(i).attr("href"));
//            String detailUrl = detailUrlElements.get(i).attr("href");
//            Document detailDoc = Jsoup.connect(detailUrl).get();
//
//            // * 책소개, 상세이미지, 목차, 책속으로, 출판사 서평
//            Elements select = detailDoc.select("div.box_detail_content");
//            String html = select.toString();
//            String content = "";
//            content += html.substring(html.indexOf("<!-- *** s:책소개 *** -->"), html.indexOf("<!-- *** //e:책소개 *** -->"));
//            content += html.substring(html.indexOf("<!-- *** s:상세이미지 *** -->"), html.indexOf("<!-- *** //e:상세이미지 *** -->"));
//            content += html.substring(html.indexOf("<!-- *** s:Contents - 목차/책 속으로/출판사 서평 *** -->"), html.indexOf("<!-- *** //e:Contents - 목차/책 속으로/출판사 서평 *** -->"));
//
//
//
//            System.out.println(title);
//            System.out.println(subtitle);
//            System.out.println(author);
//            System.out.println(publisher);
//            System.out.println(publishedDate);
//            System.out.println(disRate);
//            System.out.println(disPrice);
//            System.out.println(price);
//            System.out.println(imageUrl);
//            System.out.println(content);
////            System.out.println("=================================================");
//        }

//
//        /* 교보문고 메인(국내도서) */
//        String url = "http://www.kyobobook.co.kr/bestSellerNew/bestseller.laf?mallGb=KOR&linkClass=D&range=1&kind=0&orderClick=DAb";
//        Document doc = Jsoup.connect(url).get();
//        Elements e1 = doc.getElementsByAttributeValue("class", "list_type01");
//
//        Elements titleElements = e1.get(0).select("div.title");
//        Elements subtitleElements = e1.get(0).select("div.subtitle");
//        Elements authorElements = e1.get(0).select("div.author");
//        Elements priceElements = e1.get(0).select("div.price");
//        Elements imgElements = e1.get(0).select("strong.rank").next();
//        Elements detailUrlElements = e1.get(0).select("span.check").prev();
//
//        for(int i=0; i < titleElements.size(); i++){
//            // * 제목
//            String title = titleElements.get(i).text();
//            // * 부제목
//            String subtitle = subtitleElements.get(i).text();
//            // * 작가, 출판사, 발행일
//            String[] info3 = authorElements.get(i).text().split("\\|");
//            String author = info3[0].replace("더보기 ","").trim();
//            String publisher = info3[1].trim();
//            LocalDate publishedDate = LocalDate.parse(info3[2].replace("년","").replace("월","")
//                    .replace("일","").trim().replaceAll(" ","-"));
//            // * 할인률, 할인가, 원가
//            Pattern pt = Pattern.compile("(\\w.*?)(원.*\\[)(\\w.*?)(%)");
//            Matcher mc = pt.matcher(priceElements.get(i).text());
//            mc.find();
//            int disRate = Integer.parseInt(mc.group(3).trim());
//            int disPrice = Integer.parseInt(mc.group(1).replace(",","").trim());
//            int price = (int)((double)disPrice * (100 / (double)(100 - disRate)));
//
//            // * 이미지 url
//            String imageUrl = imgElements.get(i).attr("src");
//
//            // * 상세 페이지 링크 (링크로 들어가서 나머지 정보를 긁어온다.)
//            System.out.println(detailUrlElements.get(i).attr("href"));
//            String detailUrl = detailUrlElements.get(i).attr("href");
//            Document detailDoc = Jsoup.connect(detailUrl).get();
//
//            // * 책소개, 상세이미지, 목차, 책속으로, 출판사 서평
//            Elements select = detailDoc.select("div.box_detail_content");
//            String html = select.toString();
//            String content = "";
//            content += html.substring(html.indexOf("<!-- *** s:책소개 *** -->"), html.indexOf("<!-- *** //e:책소개 *** -->"));
//            content += html.substring(html.indexOf("<!-- *** s:상세이미지 *** -->"), html.indexOf("<!-- *** //e:상세이미지 *** -->"));
//            content += html.substring(html.indexOf("<!-- *** s:Contents - 목차/책 속으로/출판사 서평 *** -->"), html.indexOf("<!-- *** //e:Contents - 목차/책 속으로/출판사 서평 *** -->"));
//        }
//    }
}
