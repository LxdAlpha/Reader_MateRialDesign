package com.example.alpha.reader_materialdesign.Domain;

/**
 * Created by Alpha on 2018/1/30.
 */

public class BookBasicInfomation {
    String bookName;
    String imgUrl;
    String score;
    String evaluateNumber;
    String writer;
    String publisher;
    String pages;
    String binding;
    String publishYear;

    public BookBasicInfomation() {
        bookName = "";
        imgUrl = "";
        score = "0";
        evaluateNumber = "";
        writer = "";
        publisher = "";
        pages = "";
        binding = "";
        publishYear = "";
    }

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
}
