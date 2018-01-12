package com.example.alpha.reader_materialdesign.Utils;

import com.example.alpha.reader_materialdesign.Domain.MainCommunity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Alpha on 2018/1/9.
 */

public class CommunityUtil {
    public static ArrayList<MainCommunity> getMainCommunityItem() throws IOException {
        int[] start = {0, 20, 40};
        Document doc = null;
        ArrayList<MainCommunity> result = new ArrayList<MainCommunity>();



        for(int i = 0; i < 3; i++){
            doc = Jsoup.connect("https://book.douban.com/review/best/?start=" + start[i]).get();
            Elements contents = doc.getElementsByAttribute("data-cid");
            for(Element e : contents){
                MainCommunity item = new MainCommunity();

                Elements writer = e.getElementsByClass("name");
                String writerString = writer.text();
                item.setWriter(writerString);

                Elements recommend = e.getElementsByClass("main-title-rating");
                String recommendString = recommend.attr("title");
                item.setRecommend(recommendString);

                Elements time = e.getElementsByClass("main-meta");
                String timeString = time.text();
                item.setTime(timeString);

                Elements title = e.getElementsByClass("main-bd").select("h2");
                String titleString = title.text();
                item.setTitle(titleString);

                Elements shortContent = e.getElementsByClass("short-content");
                String shortContentString = shortContent.text().replace("(展开)", "");
                item.setShortContent(shortContentString);

                Elements url = e.getElementsByClass("main-bd").select("h2 a");
                String urlString = url.attr("href");
                item.setUrl(urlString);

                result.add(item);
            }
        }
        return result;
    }
}
