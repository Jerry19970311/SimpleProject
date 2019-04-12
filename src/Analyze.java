import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by XZL on 2017/3/16.
 */

public class Analyze {
    public ArrayList analyze(String filePath){
        ArrayList urls =new ArrayList();
        File input =new File(filePath);
        Document doc;
        try {
            input.createNewFile();

            doc = Jsoup.parse(input,"utf-8","http://www.bistu.edu.cn");

            Elements contents =doc.getElementsByTag("body");
            for(Element cont:contents){
                Elements links =cont.getElementsByTag("a");
                for(Element link:links){
                    String linkhref =link.attr("href");
                    urls.add(linkhref);
                    System.out.println(linkhref);
                }
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return urls;
    }
}
