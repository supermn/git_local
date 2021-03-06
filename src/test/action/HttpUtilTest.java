package test.action;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;


public class HttpUtilTest {
    
    /**
     * 向指定 URL 发送POST方法的请求
     * 
     * @param url
     *            发送请求的 URL
     * @param param
     *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
     * @return 所代表远程资源的响应结果
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            System.out.println("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }    
    
    public static void main(String[] args) {
    	
    	String url = "";
    	String param = "";
    	//添加总店账户
    	url = "http://192.168.100.118:8080/nbcloud/appBossUser/addBossUser.do";
    	param = "AGENTNAME=18712121212&PHONE=19988776651&PASSWORD=E10ADC3949BA59ABBE56E057F20F883E&EMAIL=19988776651@qq.com&PROVINCE=&CITY=&ADDRESS=&REMARK=";
        
    	//网吧会员注册
    	//url = "http://192.168.100.103:8080/nbcloud/appMbUser/reg.do";
    	//param = "PHONE=18711111113&PASSWORD=123456&CONFIRM_PASSWORD=123456&USER_FROM=1&INVITE=18711111111";
    	
    	//网吧会员认证
    	//url = "http://192.168.100.103:8080/nbcloud/appMbUser/attesta.do";
    	//param = "TOKEN=d7a4f994eae641f79011ec659af7400e&NAME=孙晴&ID_CARD=350212199008298209&IMG=/imge/imge.jpg&CITY=00301&ADDRESS=庐阳区";
    	
    	//网吧会员登陆
    	//url = "http://47.98.45.178:8090/nbcloud/appMbUser/login.do";
    	//param = "PHONE=18711111111&PASSWORD=123456";
    	
    	//网吧会员充值
    	//url = "http://192.168.100.103:8080/nbcloud/appMbRecharge/rechargePre.do";
    	//param = "TOKEN=d3f9a5a5992a4bd2bb2e179ffd395137&AMOUNT=0.01&PAY_WAY=1";
    	
    	//网吧会员购买商品C扫B
    	//url = "http://192.168.100.167:8080/nbcloud/appMbOrder/addOrder.do";
    	//param = "TOKEN=2450351bfe734240b29c51d0991c3604&BRANCH_NM=2018011709313753501&ORDER_NUMBER=123&PAY_AMOUNT=100&NET_AMOUNT=150&ORDER_TYPE=1&REMARK=100网费";
    	
    	//网吧会员购买商品B扫C
    	//url = "http://192.168.100.167:8080/nbcloud/appMbOrder/addOrderB.do";
    	//param = "ID_CARD=350212199008298209&BRANCH_NM=2018011709313753501&PAY_CODE=665131409622568901&ORDER_NUMBER=1240&PAY_AMOUNT=60&NET_AMOUNT=80&ORDER_TYPE=1&REMARK=80网费";
    	
    	//网吧会员购买商品列表
    	//url = "http://47.98.45.178:8090/nbcloud/appMbOrder/list.do";
    	//param = "TOKEN=7372713712f64c91b2e8b5e5fd71bfe8&BRANCH_NM=2018011709313753501&KEYWORDS=&STATUS_LIKE=&LAST_START=&LAST_END=&SHOW_COUNT=2&CURRENT_PAGE=1";
    	
    	//网吧会员撤销消费
    	//url = "http://192.168.100.103:8080/nbcloud/appMbOrder/revokeOrder.do";
    	//param = "TOKEN=64a7e8b0847f465f839c9e82878ec70c&ID=0e4f882531ab4a9e8aebad9770e1248c";
    	
    	//网吧会员查询付款码
    	//url = "http://192.168.100.167:8080/nbcloud/appMbPayCode/queryPayCode.do";
    	//param = "TOKEN=2450351bfe734240b29c51d0991c3604";
    	
    	//门店查询订单详情
    	//url = "http://192.168.100.167:8080/nbcloud/appMbOrder/queryOrder.do";
    	//param = "BRANCH_NM=2018011709313753501&ORDER_NUMBER=1232";
    	
    	//门店撤销消费
    	//url = "http://47.98.45.178:8090/nbcloud/appMbOrder/revokeOrderByBranch.do";
    	//param = "BRANCH_NM=2018011709313753501&ORDER_NUMBER=1233";
    	
    	//网吧客户端会员充值
    	//url = "http://47.98.45.178:8090/nbcloud/appPcClient/rechargePre.do";
    	//param = "ID_CARD=350212199008298209&BRANCH_NM=2018011709313753501&AMOUNT=0.01&PAY_WAY=1";
    	
    	String s=HttpUtilTest.sendPost(url, param);
        System.out.println(s);
        
    }
}