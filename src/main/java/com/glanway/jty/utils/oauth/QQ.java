package com.glanway.jty.utils.oauth;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glanway.jty.utils.HttpUtils;
import com.google.common.collect.Maps;
import org.vacoor.mux.common.json.Jacksons;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by tianxuan
 * date: 2015/11/26
 * describe:
 */

public class QQ extends Oauth{
    private static final String QQ_AUTHORIZE_URL = "https://graph.qq.com/oauth2.0/authorize";
    private static final String QQ_ACCESS_TOKEN_URL = "https://graph.qq.com/oauth2.0/token";
    private static final String QQ_OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";
    private static final String QQ_USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    private static final String ACCESS_TOKEN = "access_token";

    private static final String appKey = "101294507";
    private static final String appVal  = "7c8ae3d467e4d9bd517ecebeb6b18d71";
    private static final String appUrl  = "http://djmall.cn/oauth/qq/authorize";/*"http://www.zzrelease.com/phonepages/top6/ThirdPartyLoginTest.aspx"*/


    public static QQ create() {
        return new QQ(appKey,appVal,appUrl);
    }

    public QQ(String appId, String appKey, String redirectUri) {
        super(QQ_AUTHORIZE_URL, QQ_ACCESS_TOKEN_URL, appId, appKey, redirectUri);
    }
    public String getAuthorizeUrl(String state) {
        Map<String,String> params = Maps.newHashMap();
        params.put("state", state);
        params.put("client_id", super.authorizeUrl);
        return  super.getAuthorizeUrl(params);
    }
    /**
     *  通过Authorization Code获取Access Token
     * @param authorizationCode
     * @return
     */
    public String getAccessTokenUrl(String authorizationCode) {
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", "authorization_code");   //授权类型，在本步骤中，此值为“authorization_code”。
        params.put("code", authorizationCode);
        params.put("client_id", clientId);            //申请QQ登录成功后，分配给网站的appid。
        params.put("client_secret", clientSecret);    //申请QQ登录成功后，分配给网站的appkey。
        params.put("redirect_uri", redirectUri);     //回调地址
        return HttpUtils.URLAppend(accessTokenUrl, params, HttpUtils.ENCODING);
    }

    public String getAccessToken(String authorizationCode){
        String accessToken = null;
        String accessTokenURL = getAccessTokenUrl(authorizationCode);
        String result = HttpUtils.URLGet(accessTokenURL, null, null);
        Matcher matcher = Pattern.compile("access_token=([a-zA-Z0-9]*)").matcher(result);
        if(matcher.find()){
            accessToken = matcher.group(1);
        }
        return accessToken;
    }
    public String getOpenId(String accessToken){
        String openId = null;
        Map<String, String> params = Maps.newHashMap();
        params.put(ACCESS_TOKEN, accessToken);
        String result = HttpUtils.URLGet(QQ_OPEN_ID_URL,params, HttpUtils.ENCODING);
       /* Matcher matcher = Pattern.compile("openid:([a-zA-Z0-9]*)").matcher(result);
        if (matcher.find()) {
            openId = matcher.group(1);
        }*/
        openId = result.substring(result.indexOf("openid")+9,result.lastIndexOf("}")-1);
        return openId;
    }

    public Map<String,String> getUserInfo(String accessToken,String openid){
        Map<String,String> rtMap = Maps.newHashMap();
        Map<String, String> params = Maps.newHashMap();
        params.put(ACCESS_TOKEN, accessToken);
        params.put("oauth_consumer_key", clientId);
        params.put("openid", openid);
        String result = HttpUtils.URLGet(QQ_USER_INFO_URL, params, HttpUtils.ENCODING);
        ObjectNode info = Jacksons.deserialize(result, ObjectNode.class);
        String nickname = info.get("nickname").asText();
        String figureurl = info.get("figureurl_qq_1").asText();
        String sex = info.get("gender").asText();
        rtMap.put("nickname",nickname);
        rtMap.put("figureurl",figureurl);
        rtMap.put("sex",sex);
        return rtMap;
    }
}
