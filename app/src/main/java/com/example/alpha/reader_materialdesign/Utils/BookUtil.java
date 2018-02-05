package com.example.alpha.reader_materialdesign.Utils;

import android.util.Log;

import com.example.alpha.reader_materialdesign.Domain.BookBasicInfomation;
import com.example.alpha.reader_materialdesign.Domain.BookCommentItem;
import com.example.alpha.reader_materialdesign.Domain.BookView;
import com.example.alpha.reader_materialdesign.Domain.ShortComment;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Alpha on 2018/1/27.
 */

public class BookUtil {

    public static BookBasicInfomation getBookBasicInformation(String url){
        BookBasicInfomation bookBasicInfomation = new BookBasicInfomation();
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements  elements = doc.getAllElements();

        String bookName;
        try {
            bookName = doc.getElementsByAttributeValue("property", "v:itemreviewed").get(0).text();
        }catch (Exception e){
            bookName = "";
        }
        bookBasicInfomation.setBookName(bookName);

        String imgUrl;
        try {
            imgUrl = doc.getElementsByAttributeValue("rel", "v:photo").attr("src");
        }catch (Exception e){
            imgUrl = "";
        }
        bookBasicInfomation.setImgUrl(imgUrl);

        String score;
        try {
            score = doc.getElementsByAttributeValue("property", "v:average").get(0).text();
            if(score.equals("")){
                score = "0";
            }
        }catch (Exception e){
            score = "0";
        }
        bookBasicInfomation.setScore(score);

        String evaluateNumber;
        try {
            evaluateNumber = doc.getElementsByAttributeValue("property", "v:votes").get(0).text();
        }catch (Exception e){
            evaluateNumber = "";
        }
        bookBasicInfomation.setEvaluateNumber(evaluateNumber);

        String writer;
        try {
            writer = doc.getElementById("info").getElementsByTag("a").get(0).text();
        }catch (Exception e){
            writer = "";
        }
        bookBasicInfomation.setWriter(writer);

        String publisher;
        try {
            publisher = splitStringByTwoRegex("出版社: ", " ", doc);
        }catch (Exception e){
            publisher = "";
        }
        bookBasicInfomation.setPublisher(publisher);

        String pages;
        try {
            pages = splitStringByTwoRegex("页数: ", " ", doc);
        }catch (Exception e){
            pages = "";
        }
        bookBasicInfomation.setPages(pages);

        String binding;
        try {
            binding = splitStringByTwoRegex("装帧: ", " ", doc);
        }catch (Exception e){
            binding = "";
        }
        bookBasicInfomation.setBinding(binding);

        String publishYear;
        try {
            publishYear = splitStringByTwoRegex("出版年: ", " ", doc);
        }catch (Exception e){
            publishYear = "";
        }
        bookBasicInfomation.setPublishYear(publishYear);
        return bookBasicInfomation;
    }

    public static LinkedHashMap<String, String> getBookTags(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements  elements = doc.getAllElements();
        LinkedHashMap<String, String> tags = new LinkedHashMap();
        for(Element e : doc.getElementById("db-tags-section").getElementsByClass("indent").get(0).getElementsByTag("a")){
            tags.put(e.text(), "https://book.douban.com" + e.attr("href"));
        }
        return tags;
    }



    public static String getBookDescription(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.getAllElements();
        String bookDescription = doc.getElementsByClass("intro").html();
        return bookDescription;
    }

    public static ArrayList<ShortComment> getShortComments(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements  elements = doc.getAllElements();
        ArrayList<ShortComment> shortCommentArr = new ArrayList();
        Elements  shortComments = null;
        try{
            shortComments = doc.getElementsByClass("comment-list hot show").get(0).getElementsByClass("comment-item");
        }catch (Exception e){
        }
        if(shortComments != null){
            for(Element e : shortComments){
                ShortComment shortComment = new ShortComment();
                String shortCommentWriterName;
                try {
                    shortCommentWriterName = e.getElementsByClass("comment-info").get(0).getElementsByTag("a").text();
                }catch (Exception k){
                    shortCommentWriterName = "";
                }
                shortComment.setShortCommentWriterName(shortCommentWriterName);
                String shortCommentWriterUrl = e.getElementsByClass("comment-info").get(0).getElementsByTag("a").attr("href");
                shortComment.setShortCommentWriterUrl(shortCommentWriterUrl);
                String shortCommentTime = e.getElementsByClass("comment-info").text().split(" ")[1];
                shortComment.setShortCommentTime(shortCommentTime);
                String shortCommentContent = e.getElementsByClass("comment-content").text();
                shortComment.setShortCommentContent(shortCommentContent);
                String shortCommentVoteCount = e.getElementsByClass("vote-count").text();
                shortComment.setShortCommentVoteCount(shortCommentVoteCount);
                String shortCommentWriterPortrait = "";
                try{
                    shortCommentWriterPortrait = Jsoup.connect(shortCommentWriterUrl).get().getElementsByClass("pic").get(0).getElementsByTag("img").attr("src");
                }catch (Exception exception){
                }
                shortComment.setShortCommentWriterPortrait(shortCommentWriterPortrait);
                shortCommentArr.add(shortComment);
            }
        }
        return shortCommentArr;
    }

    public static String getMoreShortComments(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements  elements = doc.getAllElements();
        String viewAllShortComment = doc.getElementsByClass("mod-hd").get(0).getElementsByTag("h2").get(0).getElementsByTag("a").attr("href");
        return viewAllShortComment;
    }

    public static ArrayList<BookCommentItem> getBookCommentsArr(String url){
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements  elements = doc.getAllElements();
        ArrayList<BookCommentItem> bookCommentsArr = new ArrayList<BookCommentItem>();
        Elements bookComments = null;
        try {
            bookComments = doc.getElementsByClass("review-list").get(0).getElementsByAttributeValue("typeof", "v:Review");
        }catch (Exception e){

        }
        if(bookComments != null){
            for(Element e : bookComments){
                BookCommentItem bookCommentItem = new BookCommentItem();
                String bookCommentItemUrl = e.getElementsByClass("main-bd").get(0).getElementsByTag("a").get(0).attr("href");
                String bookCommentItemTitle = e.getElementsByClass("main-bd").get(0).getElementsByTag("a").get(0).html();
                String bookCommentItemWriter = e.getElementsByClass("main-hd").get(0).getElementsByClass("name").text();
                String bookCommentItemShortContent = (e.getElementsByClass("main-bd").get(0).getElementsByClass("short-content").text()).split("\\(展")[0];
                String bookCommentItemSupport = e.getElementsByClass("main-bd").get(0).getElementsByClass("action").get(0).getElementsByTag("span").get(0).text();
                if(bookCommentItemSupport.equals(""))
                    bookCommentItemSupport = "0";
                String bookCommentItemOppose = e.getElementsByClass("main-bd").get(0).getElementsByClass("action").get(0).getElementsByTag("span").get(1).text();
                if(bookCommentItemOppose.equals(""))
                    bookCommentItemOppose = "0";
                String bookCommentItemTime = e.getElementsByClass("main-hd").get(0).getElementsByAttributeValue("property", "v:dtreviewed").get(0).text();

                bookCommentItem.setBookCommentItemUrl(bookCommentItemUrl);
                bookCommentItem.setBookCommentItemTitle(bookCommentItemTitle);
                bookCommentItem.setBookCoomentItemWriter(bookCommentItemWriter);
                bookCommentItem.setBookCommentItemShortContent(bookCommentItemShortContent);
                bookCommentItem.setBookCommentItemSupport(bookCommentItemSupport);
                bookCommentItem.setBookCommentItemOppose(bookCommentItemOppose);
                bookCommentItem.setBookCommentItemTime(bookCommentItemTime);
                bookCommentsArr.add(bookCommentItem);
            }
        }
        return bookCommentsArr;
    }


    public static String getMoreComments(String url){
        return getMoreShortComments(url).replace("comments/", "reviews");
    }

    public static BookView getBookView(String url) throws IOException {
        BookView bookView = new BookView();
        Document doc = Jsoup.connect(url).get();
        Elements  elements = doc.getAllElements();

        String bookName = doc.getElementsByAttributeValue("property", "v:itemreviewed").get(0).text();
        bookView.setBookName(bookName);

        String imgUrl = doc.getElementsByAttributeValue("rel", "v:photo").attr("src");
        bookView.setImgUrl(imgUrl);

        String score = doc.getElementsByAttributeValue("property", "v:average").get(0).text();
        bookView.setScore(score);

        String evaluateNumber = doc.getElementsByAttributeValue("property", "v:votes").get(0).text();
        bookView.setEvaluateNumber(evaluateNumber);

        String writer = doc.getElementById("info").getElementsByTag("a").get(0).text();
        bookView.setWriter(writer);

        String publisher = splitStringByTwoRegex("出版社: ", " ", doc);
        bookView.setPublisher(publisher);

        String pages = splitStringByTwoRegex("页数: ", " ", doc);
        bookView.setPages(pages);

        String binding = splitStringByTwoRegex("装帧: ", " ", doc);
        bookView.setBinding(binding);

        String publishYear = splitStringByTwoRegex("出版年: ", " ", doc);
        bookView.setPublishYear(publishYear);

        LinkedHashMap<String, String> tags = new LinkedHashMap();
        for(Element e : doc.getElementById("db-tags-section").getElementsByClass("indent").get(0).getElementsByTag("a")){
            tags.put(e.text(), "https://book.douban.com" + e.attr("href"));
        }
        bookView.setTags(tags);

        String bookDescription = doc.getElementsByClass("intro").html();
        bookView.setBookDescription(bookDescription);

        ArrayList<ShortComment> shortCommentArr = new ArrayList();
        bookView.setShortComments(shortCommentArr);
        Elements  shortComments = null;
        try{
            shortComments = doc.getElementsByClass("comment-list hot show").get(0).getElementsByClass("comment-item");
        }catch (Exception e){
        }
        if(shortComments != null){
            for(Element e : shortComments){
                ShortComment shortComment = new ShortComment();
                String shortCommentWriterName = e.getElementsByClass("comment-info").get(0).getElementsByTag("a").text();
                shortComment.setShortCommentWriterName(shortCommentWriterName);
                String shortCommentWriterUrl = e.getElementsByClass("comment-info").get(0).getElementsByTag("a").attr("href");
                shortComment.setShortCommentWriterUrl(shortCommentWriterUrl);
                String shortCommentTime = e.getElementsByClass("comment-info").text().split(" ")[1];
                shortComment.setShortCommentTime(shortCommentTime);
                String shortCommentContent = e.getElementsByClass("comment-content").text();
                shortComment.setShortCommentContent(shortCommentContent);
                String shortCommentVoteCount = e.getElementsByClass("vote-count").text();
                shortComment.setShortCommentVoteCount(shortCommentVoteCount);
                String shortCommentWriterPortrait = "";
                try{
                    shortCommentWriterPortrait = Jsoup.connect(shortCommentWriterUrl).get().getElementsByClass("pic").get(0).getElementsByTag("img").attr("src");
                }catch (Exception exception){
                }
                shortComment.setShortCommentWriterPortrait(shortCommentWriterPortrait);
                bookView.getShortComments().add(shortComment);
            }
        }
        String viewAllShortComment = doc.getElementsByClass("mod-hd").get(0).getElementsByTag("h2").get(0).getElementsByTag("a").attr("href");
        bookView.setViewAllShortComment(viewAllShortComment);

        ArrayList<BookCommentItem> bookCommentsArr = new ArrayList<BookCommentItem>();
        bookView.setBookCommentItems(bookCommentsArr);
        Elements bookComments = null;
        try {
            bookComments = doc.getElementsByClass("review-list").get(0).getElementsByAttributeValue("typeof", "v:Review");
        }catch (Exception e){

        }
        if(bookComments != null){
            for(Element e : bookComments){
                BookCommentItem bookCommentItem = new BookCommentItem();
                String bookCommentItemUrl = e.getElementsByClass("main-bd").get(0).getElementsByTag("a").get(0).attr("href");
                String bookCommentItemTitle = e.getElementsByClass("main-bd").get(0).getElementsByTag("a").get(0).html();
                String bookCommentItemWriter = e.getElementsByClass("main-hd").get(0).getElementsByClass("name").text();
                String bookCommentItemShortContent = (e.getElementsByClass("main-bd").get(0).getElementsByClass("short-content").text()).split("\\(展")[0];
                String bookCommentItemSupport = e.getElementsByClass("main-bd").get(0).getElementsByClass("action").get(0).getElementsByTag("span").get(0).text();
                if(bookCommentItemSupport.equals(""))
                    bookCommentItemSupport = "0";
                String bookCommentItemOppose = e.getElementsByClass("main-bd").get(0).getElementsByClass("action").get(0).getElementsByTag("span").get(1).text();
                if(bookCommentItemOppose.equals(""))
                    bookCommentItemOppose = "0";
                String bookCommentItemTime = e.getElementsByClass("main-hd").get(0).getElementsByAttributeValue("property", "v:dtreviewed").get(0).text();

                bookCommentItem.setBookCommentItemUrl(bookCommentItemUrl);
                bookCommentItem.setBookCommentItemTitle(bookCommentItemTitle);
                bookCommentItem.setBookCoomentItemWriter(bookCommentItemWriter);
                bookCommentItem.setBookCommentItemShortContent(bookCommentItemShortContent);
                bookCommentItem.setBookCommentItemSupport(bookCommentItemSupport);
                bookCommentItem.setBookCommentItemOppose(bookCommentItemOppose);
                bookCommentItem.setBookCommentItemTime(bookCommentItemTime);
                bookView.getBookCommentItems().add(bookCommentItem);
            }
        }

        String viewAllBookCommentUrl = viewAllShortComment.replace("comments/", "reviews");
        bookView.setViewAllBookCommentUrl(viewAllBookCommentUrl);

        return bookView;
    }

    public static  String splitStringByTwoRegex(String regex1, String regex2, Document doc){
        String split_0 = doc.getElementById("info").getAllElements().get(0).text();
        String split_1[] = split_0.split(regex1);
        String split_2[] = split_1[1].split(regex2);
        String result = split_2[0];
        return result;
    }
}
