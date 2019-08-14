package com.example.trail.Utility.EnumPack;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum  KeyConstants {
    BMOB_SIXPLUS("e023da5238d5d577fc4f2533b74472c0"),BMOB_GSH("b096ac50b630d5a7db1c69abb7a34caa");
    private String key;
    /**
     *  此为Bmob的APP_ID
     */
    public static String BMOB_APPID = "46c730e7e33eabeb3ec790b3fb0a02d7";//--外网

    /**
     * 此为腾讯官方提供给开发者用于测试的APP_ID，个人开发者需要去QQ互联官网为自己的应用申请对应的AppId
     */
    public static final String QQ_APP_ID ="222222";

    /**
     *  微信平台的APPID,请自行前往微信开放平台注册申请应用
     */
    public static final String WEIXIN_APP_ID ="wx4f49df1c2cfc15eb";

    /**
     * 微信平台的AppSecret
     */
    public static final String WEIXIN_APP_SECRET ="03eec97e8be49cd84f67bbe12469f19e";

    /**
     *  微信平台的grant type，固定值：authorization_code
     */
    public static final String WEIXIN_GRANT_TYPE ="authorization_code";
}
