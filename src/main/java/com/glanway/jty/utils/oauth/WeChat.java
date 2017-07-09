package com.glanway.jty.utils.oauth;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glanway.jty.utils.HttpUtils;
import com.google.common.collect.Maps;
import org.vacoor.mux.common.json.Jacksons;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by tianxuan
 * date: 2015/11/26
 * describe:
 */
public class WeChat extends Oauth{
    private static final String AUTHORIZE_URL = "https://open.weixin.qq.com/connect/qrconnect";//open.weixin.qq.com/connect/oauth2/authorize
    private static final String ACCESS_TOKEN_URL = "https://api.weixin.qq.com/sns/oauth2/access_token";
    private static final String OPEN_ID_URL = "https://graph.qq.com/oauth2.0/me";
    private static final String USER_INFO_URL = "https://graph.qq.com/user/get_user_info";

    private static final String ACCESS_TOKEN = "access_token";

    private static final String AppId = "wxd6c2a37015cf38be";
    private static final String AppSecret  = "c40f16f8ec879825266304a23fd16fdf";
    private static final String AppCallbackUrl  = "http://gitool.glanway.com/ds/oauth/wx/authorize";//http://djmall.cn/oauth/wx/authorize

    public static WeChat create() {
        return new WeChat(AppId,AppSecret,AppCallbackUrl);
    }

    public WeChat(String appId, String appKey, String redirectUri) {
        super(AUTHORIZE_URL, ACCESS_TOKEN_URL, appId, appKey, redirectUri);
    }

    public String getAuthorizeUrl(String state) {
        Map<String,String> params = Maps.newHashMap();
        params.put("state", state);
        params.put("appid", super.authorizeUrl);
        params.put("scope", "snsapi_userinfo");
        return  super.getAuthorizeUrl(params);
    }

    public String getAuthorizeURL(String state) {
        Map<String,String> params = Maps.newHashMap();
        params.put("state", AppSecret);
        params.put("appid", super.clientId);
        return  super.getAuthorizeURL(params);
    }

    public String getAccessTokenUrl(String authorizationCode) {
        Map<String, String> params = Maps.newHashMap();
        params.put("grant_type", "authorization_code");
        params.put("code", authorizationCode);
        params.put("appid", super.clientId);
        params.put("secret", super.clientSecret);
        return HttpUtils.URLAppend(accessTokenUrl, params, HttpUtils.ENCODING);
    }

    public Map<String,String> getAccessToken(String authorizationCode){
        Map<String,String> rtMap = new HashMap<String,String>();
        String accessTokenURL = getAccessTokenUrl(authorizationCode);
        String result = HttpUtils.URLGet(accessTokenURL,null,null);
        ObjectNode info = Jacksons.deserialize(result, ObjectNode.class);
        int ret1 = info.get("ret").asInt(-1);
        String accessToken = info.get("access_token").asText();
        String openid = info.get("openid").asText();
        rtMap.put("accessToken",accessToken);
        rtMap.put("openid",openid);
        return rtMap;
    }

    public Map<String,String> getUserInfo(String accessToken,String openid){
        Map<String,String> rtMap = Maps.newHashMap();
        Map<String, String> params = Maps.newHashMap();
        params.put(ACCESS_TOKEN, accessToken);
        params.put("lang", "zh_CN");
        params.put("openid", openid);
        String result = HttpUtils.URLGet(USER_INFO_URL, params, HttpUtils.ENCODING);
        ObjectNode info = Jacksons.deserialize(result, ObjectNode.class);
        int ret1 = info.get("ret").asInt(-1);
        String nickname = info.get("nickname").asText();
        String figureurl = info.get("headimgurl").asText();
        String sex = info.get("sex").asText();
        rtMap.put("nickname","男".equals(nickname)?"1":"0");
        rtMap.put("figureurl",figureurl);
        rtMap.put("sex",sex);
        return rtMap;
    }
}
