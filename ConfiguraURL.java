package utils;

public class ConfiguraURL implements UrlInterface{
    public String getURL(String urlConfig){
        String url = "";

        if(urlConfig.equals(MEUDESCONTODEV)){
            url = "http://dev-admin.meudesconto.gpa.digital.s3-website-us-east-1.amazonaws.com/#/login";
        }
        if(urlConfig.equals(MEUDESCONTOHLG)){
            url = "http://hlg-admin.meudesconto.gpa.digital.s3-website-us-east-1.amazonaws.com/#/login";
        }
        return url;
    }
}