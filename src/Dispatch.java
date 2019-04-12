import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

/**
 * Created by XZL on 2017/3/16.
 */
public class Dispatch {
    //种子库。
    private ArrayList<String> seedUrlList=new ArrayList<String>();
    //待处理队列。
    private LinkedList<String> datalaralList=new LinkedList<String>();
    //已下载队列。（用哈希表统计爬过的网页被发现的次数。）
    private Hashtable downloadedUrls=new Hashtable();
    //创建下载器。
    private Download download=new Download();
    //创建分析器。
    private Analyze analize=new Analyze();
    //种子库初始化。
    public void initSeedUrls(){
        seedUrlList.add("http://www.bistu.edu.cn");
    }
    //初始化待处理队列。
    public void seesdatalaral(){
        for(int i=0;i<seedUrlList.size();i++){
            datalaralList.add(seedUrlList.get(i));
        }
    }
    //开始爬虫。进行爬虫直至待处理队列为空。
    public void startClimb(){
        while (!datalaralList.isEmpty()) {
            //取队头分析。
            String newUrl = datalaralList.getFirst();
            //确定爬虫的存储路径。
            String filePath = "E:/CEnglish/";

            /*System.out.println("-------------------------\ndatalaralList的内容有：");
            int length=datalaralList.size();
            for(int i=0;i<length;i++){
                System.out.println(datalaralList.get(i));
            }
            System.out.println("----------------------------------------");*/

            //下载刚刚抓出来的网页。
            download.downloadPage(newUrl, filePath);
            //将此链接放到已下载队列中。
            downloadedUrls.put(newUrl, 1);
            //构建存储地址以及文件名。
            filePath=filePath+Download.getFileName(newUrl);
            //检查待处理队列中的队列是否已访问过，如有，则删除该链接，并在哈希表中把该链接的映射值+1。
            ArrayList<String> tmpUrls = analize.analyze(filePath);
            for (int i = 0; i < tmpUrls.size(); i++) {
                if (!downloadedUrls.containsKey(tmpUrls.get(i))) {
                    if(tmpUrls.get(i).startsWith("http"))
                        datalaralList.add(tmpUrls.get(i));
                }
            }
            //删除。
            datalaralList.removeFirst();
        }
    }
    public static void main(String[] args){
        Dispatch dd=new Dispatch();
        dd.initSeedUrls();
        dd.seesdatalaral();
        dd.startClimb();
    }
}
