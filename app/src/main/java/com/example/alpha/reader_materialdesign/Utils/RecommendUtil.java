package com.example.alpha.reader_materialdesign.Utils;

import android.util.Log;

import com.example.alpha.reader_materialdesign.Domain.FindBookItem;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/13.
 */

public class RecommendUtil {
    public static ArrayList<FindBookItem> crawlerExecute() throws IOException {
        String[] selectByClass = {"cover-col-4 clearfix", "cover-col-4 pl20 clearfix"};
        Document doc = Jsoup.connect("https://book.douban.com/latest?icn=index-latestbook-all").get();
        ArrayList<FindBookItem> list = new ArrayList<>();
        Element content = null;
        Elements elements = null;
        for (int i = 0; i < 2; i++) {
            content = doc.getElementsByClass(selectByClass[i]).first();
            elements = content.getElementsByTag("li");
            for (Element e : elements) {
                Elements urlAndTitle = e.select("div.detail-frame");
                String urlString = urlAndTitle.select("a").attr("href");
                String titleString = urlAndTitle.select("a").text();
                String ratingString = e.getElementsByClass("font-small").text();
                if (ratingString.equals("")) {
                    ratingString = "待评价";
                }
                String writerString = e.getElementsByClass("color-gray").text();

                String recommendString = null;
                if (i == 0) {
                    recommendString = e.getElementsByClass("detail").text();
                } else {
                    Elements strings = e.getElementsByTag("p");
                    int count = 0;
                    for (Element k : strings) {
                        count++;
                        if (count == 3) {
                            recommendString = k.text();
                        }
                    }
                }


                String imgUrlString = e.select("a.cover").select("img").attr("src");
                //Log.d("lxd", titleString + " " + ratingString);
                FindBookItem item = new FindBookItem();
                item.setUrl(urlString);
                item.setTitle(titleString);
                item.setRating(ratingString);
                item.setWriter(writerString);
                item.setRecommend(recommendString);
                item.setImgUrl(imgUrlString);
                list.add(item);
            }
        }
        return list;
    }

    public static ArrayList<FindBookItem> getConcernedBook() {
        Document doc = null;
        try {
            doc = Jsoup.connect("https://book.douban.com/chart?subcat=I").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ArrayList<FindBookItem> arrayList = new ArrayList<>();
        Elements targets = doc.getElementsByClass("media clearfix");
        for (Element e : targets) {
            FindBookItem item = new FindBookItem();
            item.setUrl(e.getElementsByClass("fleft").attr("href"));
            item.setTitle(e.getElementsByClass("media__body").get(0).getElementsByClass("fleft").get(0).text());
            item.setRating(e.getElementsByClass("font-small color-red fleft").text());
            item.setWriter(e.getElementsByClass("subject-abstract color-gray").text().split("/")[0]);
            String[] temp = e.getElementsByClass("subject-abstract color-gray").text().split("/");
            String recommend = "";
            for (int i = 1; i < temp.length; i++) {
                recommend = recommend + temp[i];
            }
            item.setRecommend(recommend);
            item.setImgUrl(e.getElementsByClass("subject-cover").attr("src"));
            arrayList.add(item);
        }

        Document doc1 = null;
        try {
            doc1 = Jsoup.connect("https://book.douban.com/chart?subcat=F").get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements targets1 = doc1.getElementsByClass("media clearfix");
        for (Element e : targets1) {
            FindBookItem item = new FindBookItem();
            item.setUrl(e.getElementsByClass("fleft").attr("href"));
            item.setTitle(e.getElementsByClass("media__body").get(0).getElementsByClass("fleft").get(0).text());
            item.setRating(e.getElementsByClass("font-small color-red fleft").text());
            item.setWriter(e.getElementsByClass("subject-abstract color-gray").text().split("/")[0]);
            String[] temp = e.getElementsByClass("subject-abstract color-gray").text().split("/");
            String recommend = "";
            for (int i = 1; i < temp.length; i++) {
                recommend = recommend + temp[i];
            }
            item.setRecommend(recommend);
            item.setImgUrl(e.getElementsByClass("subject-cover").attr("src"));
            arrayList.add(item);
        }
        return arrayList;
    }


    public static ArrayList<FindBookItem> getTop250Book() {
        Document doc = null;
        ArrayList<FindBookItem> array = new ArrayList<>();
        for (int i = 0; i <= 225; i = i + 25) {
            try {
                doc = Jsoup.connect("https://book.douban.com/top250?start=" + i).get();
            } catch (IOException e) {
                e.printStackTrace();
            }
            Elements targets = doc.getElementById("content").getElementsByClass("item");
            for (Element e : targets) {
                FindBookItem item = new FindBookItem();
                String url = e.getElementsByTag("a").get(1).attr("href");
                String title = e.getElementsByTag("a").get(1).text();
                String writer = e.getElementsByTag("p").get(0).text();
                String rating = e.getElementsByClass("rating_nums").text();
                String description = e.getElementsByClass("inq").text();
                String imgUrl = e.getElementsByTag("img").attr("src");
                item.setUrl(url);
                item.setTitle(title);
                item.setWriter(writer);
                item.setRating(rating);
                item.setRecommend(description);
                item.setImgUrl(imgUrl);
                array.add(item);
            }
        }
        return array;
    }
}
