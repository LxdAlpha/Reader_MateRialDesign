package com.example.alpha.reader_materialdesign.Domain;

import java.util.ArrayList;
import java.util.LinkedHashMap;

/**
 * Created by Alpha on 2018/1/26.
 */
public class BookView {
    String bookName;
    String imgUrl;
    String score;
    String evaluateNumber;
    String writer;
    String publisher;
    String pages;
    String binding;
    String publishYear;
    LinkedHashMap<String, String> tags;
    String bookDescription;
    ArrayList<ShortComment> shortComments;
    String viewAllShortComment;
    ArrayList<BookCommentItem> bookCommentItems;
    String viewAllBookCommentUrl;

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getEvaluateNumber() {
        return evaluateNumber;
    }

    public void setEvaluateNumber(String evaluateNumber) {
        this.evaluateNumber = evaluateNumber;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getPages() {
        return pages;
    }

    public void setPages(String pages) {
        this.pages = pages;
    }

    public String getBinding() {
        return binding;
    }

    public void setBinding(String binding) {
        this.binding = binding;
    }

    public String getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(String publishYear) {
        this.publishYear = publishYear;
    }

    public LinkedHashMap<String, String> getTags() {
        return tags;
    }

    public void setTags(LinkedHashMap<String, String> tags) {
        this.tags = tags;
    }

    public String getBookDescription() {
        return bookDescription;
    }

    public void setBookDescription(String bookDescription) {
        this.bookDescription = bookDescription;
    }



    public String getViewAllShortComment() {
        return viewAllShortComment;
    }

    public void setViewAllShortComment(String viewAllShortComment) {
        this.viewAllShortComment = viewAllShortComment;
    }


    public String getViewAllBookCommentUrl() {
        return viewAllBookCommentUrl;
    }

    public void setViewAllBookCommentUrl(String viewAllBookCommentUrl) {
        this.viewAllBookCommentUrl = viewAllBookCommentUrl;
    }

    public ArrayList<ShortComment> getShortComments() {
        return shortComments;
    }

    public void setShortComments(ArrayList<ShortComment> shortComments) {
        this.shortComments = shortComments;
    }

    public ArrayList<BookCommentItem> getBookCommentItems() {
        return bookCommentItems;
    }

    public void setBookCommentItems(ArrayList<BookCommentItem> bookCommentItems) {
        this.bookCommentItems = bookCommentItems;
    }
}
