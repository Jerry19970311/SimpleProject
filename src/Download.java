/**
 * Created by XZL on 2017/3/9.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
public class Download {
    public void downloadPage(String url,String filePath) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        InputStream is = null;
        try {
            System.out.println("-------------------------------------------\n"+url+"\n-------------------------------------");
            CloseableHttpResponse response;
            if(url.startsWith("http"))
                response = httpClient.execute(httpGet);
            else{
                return;
            }
            is = null;
            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                HttpEntity entity = response.getEntity();
                is = entity.getContent();
            }
        } catch (ClientProtocolException e) {
            // TODO Auto-generated catch block
            return;
        } catch (IOException e) {
            // TODO Auto-generated catch block
            //e.printStackTrace();
        }

        filePath=filePath+getFileName(url);
        saveFile(filePath, is);
    }
    private void saveFile(String filePath,InputStream is) {
        Scanner sc = null;
        try {
            sc = new Scanner(is);
        }catch (NullPointerException e){
            return;
        }
        Writer os=null;
        try {
            os=new PrintWriter(filePath);
            while(sc.hasNext()){
                try {
                    String s=sc.nextLine()+"\n";
                    os.write(s);
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (FileNotFoundException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }finally{
            if(sc!=null){
                sc.close();
            }
            if(os!=null){
                try {
                    os.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                try {
                    os.close();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
    }
    public static String getFileName(String url){
        //保证每次处理的都是链接，避免因为处理非链接的内容造成爬虫效率低下。
        if(url.startsWith("http")) {
            url = url.substring(7);
        }
        String fileName=url.replaceAll("[\\?:`|<>\"/]","_")+".html";
        return fileName;
    }
}
