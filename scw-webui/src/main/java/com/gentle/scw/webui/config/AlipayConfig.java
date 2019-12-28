package com.gentle.scw.webui.config;

import java.io.FileWriter;
import java.io.IOException;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 *修改日期：2017-04-05
 *说明：
 *以下代码只是为了方便商户测试而提供的样例代码，商户可以根据自己网站的需要，按照技术文档编写,并非一定要使用该代码。
 *该代码仅供学习和研究支付宝接口使用，只是提供一个参考。
 */

public class AlipayConfig {
	
//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
	public static String app_id = "2016101200671261";
	
	// 商户私钥，您的PKCS8格式RSA2私钥
    public static String merchant_private_key = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCHjDgK/wnhGXYPMjJvBcJ829YpSSNKM4zKk1iMTMHXFZfngYw9aJlf75yGZsBF+D2NSJSq0ns5qXkOEI6sxbOu4xK5xvPr1pBSbS2NytjTyj+38URXx/eE/vfMnYLB1qptI80c++HP7ueNlC65QwCGnKXy2qCeWzGRaGMuy7zODYCDMzYrHY2PDG8S9DlDEIEGV13dPS6xBMVXBpxgeEiNkYznDyY8s7ivXj5PuOkgHU7VEQeHBko/N/tjUtZJautl19xQfzXN4qbIWxMcSTsWwKhyHNgpBpO6bWJFS81fA2agBPBEylPuexNxb8MKdxjSVkHecNYI0AyemeQQli0nAgMBAAECggEAXMtvqoeC+el2W1zgDgWBaf0OFmGNmUTFRAGvgw0hPt/3iHO10GyFY5okLBa31q7lZI2lyXQjDAyrZSeMpVcLddTagvhjELdpQiSgJQwTeJpjGMhBI18wYooylo/WbmFJd0IlL4x4wOMq/k/N1OK7v2wKZ8GoaADCZGt0SxsLfHvZ1d99dNkSpY03oAvM31TDBh/hujkmQ+YNNTtKlACqPUQ22m2fpQx8Q91c5EuIAoLiZ2treHQlkvOu2hxDcKun+xwNhPXOzrY/HaHycShR/xQ/pjwQlFNpGKZMy8ynWbn0UjgcR0K9+xVQW0c2GJxd7v24xVRU8WcyCkzObZ43UQKBgQDizjrDRoSyNXh15lM8lon3yRi09gfBa7upHUQ07+Wql2V3mf1c6Q9kI0/UBoWMGh/wSdI2wIwP8seSgvOX/HKIUAnDNnxwUy0ikcdIZvmusA/ShJOaF6cDeaGualou0B1jzS51cfmv5Or5O7+nLtEc2Z2p1oA7miV++HRRVd05WwKBgQCY/tTNYuNVNmhilsXdVK4NkKAVMz2A6mxgsFj4zoex0YbFkyhkiMNOQdkG0q/l7hahNa5Wa0y3rF7e9IHnR9n+auzZYkv3vsNEw7Y+Vllg+K4Uzc03uH+iQrGJ3u3+ypjsoHDmn2Bad3woZw7C8zMZVnKkIgVWPLRqj4vxo3kZJQKBgEm035/qYOFRQy59hXthKMEf1ymn8ulGy5uv6SSS+b4wqUbvAkmZa+kNGLo8zFW1f7+lHe2xMVNVgMn6SJOR9N6btDB+mn4eacKcQXkkDexZRysQ7q7bFOmqM4LtCRXBiGuQmJKOUah56mrIogYAzvBjQDube9ziwWC7+YEdOGGVAoGAfl5FgJtEMBbvYzcrmSOfW77wKnKXQ0rdV4NxCZj1BY+NpNVmkJtRzeqfin4tIVplQKRpKiIYTMFH3xnPzitPyE+i7+671pavDLYmahjCXFEq4C4YagSvD2PM5pbGtyO56gCfIC3V1QNJ/skYrTdZJn171UvwYoljb3y/V0fx9y0CgYBXNCAAqv6m04RTUcxFmY2zzqTXkYsSQJX5sWOlpH8yZb2CKyVt6UPxNfMoB1vV5bVaSzbruKoCHkXL3HV6+p9jka1yhl1nzdInxReCqBSerCm2PJVBTAJk8glor3q0iOyQ81icfQr+aofAPR1buEue4J0RwT/J68ASbxZ/22YS4Q==";
	
	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
    public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgSM9FnVIkSvVZnBhlZ/6ECAv32QVfr0UDeeYrFO8qLLmwzywt4y4DlGYDJIbbt6RQOx0aCATv1tbejY/eO51YGek5JTp3Xj2JtGK0CpiGs96dghN37FaoMYXkDakpG5vT0SDEjnxhOj5UFWRfIyMkKx2bmIi4sBMOyZ5xbbK67Yb654LUlnSkMlKAdlCs0upfPrtGuJ6n9hlxVkxP/jyGUWxWv0b+4P+l9cVSbfyY6Epu5E4yGu7LsV0OBISyFm60psIuGPoFia2LX+Q9w/6RzKBGT+DKeWu0e2vVxlb9rutM3Z8WXUn51R1LxCWYK5f0HhPHxTZ4Tj2mR8AWjr3pQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.97.209.0/order/notify_url";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String return_url = "http://47.97.209.0/order/return_url";

	// 签名方式
	public static String sign_type = "RSA2";
	
	// 字符编码格式
	public static String charset = "utf-8";
	
	// 支付宝网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";
	
	// 日志
	public static String log_path = "G:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

    /** 
     * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
     * @param sWord 要写入日志里的文本内容
     */
    public static void logResult(String sWord) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
            writer.write(sWord);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                try {
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}