package com.example.trail.Utility.EnumPack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  KeyConstants {
    BMOB_SIXPLUS("e023da5238d5d577fc4f2533b74472c0"),BMOB_GSH("b096ac50b630d5a7db1c69abb7a34caa");
    private String key;
    public static String BIGML_USERNAME="2quarius";
    public static String BIGML_API_KEY="29936a85c0635fcfb2618775a7bba504bbcc820e";
    /**
     *  此为Bmob的APP_ID
     */
    public static String BMOB_APPID = "46c730e7e33eabeb3ec790b3fb0a02d7";//--外网

    /**
     * 此为腾讯官方提供给开发者用于测试的APP_ID，个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
     */
    public static final String QQ_APP_ID ="101769768";

    /**
     *  微信平台的APPID,请自行前往微信开放平台注册申请应用
     */
    public static final String WEIXIN_APP_ID ="wxd49c53f6dd22fc32";
    //wxd49c53f6dd22fc32
    /**
     * 微信平台的AppSecret
     */
    public static final String WEIXIN_APP_SECRET ="3cf6719db0ff7b18c9c6632b12ecb69f";
    //3cf6719db0ff7b18c9c6632b12ecb69f
    /**
     *  微信平台的grant type，固定值：authorization_code
     */
    public static final String WEIXIN_GRANT_TYPE ="authorization_code";
}
