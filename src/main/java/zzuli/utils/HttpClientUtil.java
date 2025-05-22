package zzuli.utils;


import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import zzuli.common.constant.MessageConstant;

import java.io.IOException;
import java.net.URI;
import java.util.Map;

/**
 * Http工具类
 */
@Slf4j
public class HttpClientUtil {

    static final  int TIMEOUT_MSEC = 5 * 1000;

    /**
     * 发送GET方式请求
     * @param url
     * @param paramMap
     * @return
     */
    public static String doGet(String url,Map<String,String> paramMap,Map<String,String> Header) {
        // 创建Httpclient对象
        CloseableHttpClient httpClient = HttpClients.createDefault();

        String result = "";
        CloseableHttpResponse response = null;

        try{
            URIBuilder builder = new URIBuilder(url);
            if(paramMap != null){
                for (String key : paramMap.keySet()) {
                    builder.addParameter(key,paramMap.get(key));
                }
            }
            URI uri = builder.build();

            //创建GET请求
            HttpGet httpGet = new HttpGet(uri.toString());

            //设置cookie

            //设置请求头
            if(Header != null){
                for (String key : Header.keySet()) {
                    httpGet.setHeader(key,Header.get(key));
                }
            }
//            log.info("请求地址：{}",uri.toString());
            //发送请求
            response = httpClient.execute(httpGet);

//            log.info("请求状态码：{}",response.getStatusLine().getStatusCode());
            //判断响应状态
            if(response.getStatusLine().getStatusCode() == 200){
                result = EntityUtils.toString(response.getEntity(),"UTF-8");
            }else {
                log.info("请求失败");
            }
        }catch (Exception e){
            log.error("发送GET请求失败",e);
        }finally {
            try {
                response.close();
                httpClient.close();
            } catch (IOException e) {
                log.error("关闭资源失败",e);
            }
        }

        return result;
    }
}
