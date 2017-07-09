package com.glanway.jty.utils.oauth;

import com.glanway.jty.utils.HttpUtils;

import java.util.Map;

/**
 * Created by tianxuan
 * date: 2015/11/26
 * describe:
 */
public class Oauth {
    private static final String SCOPE_PARAM = "scope";

    private static final String RESPONSE_TYPE_PARAM = "response_type";  //授权类型，此值固定为“code”。
    private static final String CLIENT_ID_PARAM = "client_id";          //申请QQ登录成功后，分配给应用的appid。
    private static final String REDIRECT_URI_PARAM = "redirect_uri";     //成功授权后的回调地址，必须是注册appid时填写的主域名下的地址，建议设置为网站首页或网站的用户中心。注意需要将url进行URLEncode。
    private static final String EMPTY = "";

    protected final String authorizeUrl;     //获取Authorization Code 的请求地址。 https://graph.qq.com/oauth2.0/authorize
    protected final String accessTokenUrl;    //获取Access Token 的 url     https://graph.qq.com/oauth2.0/token
    protected final String clientId;             //  appId
    protected final String clientSecret;
    protected final String redirectUri;        //回调地址

    public Oauth(String authorizeUrl, String accessTokenUrl, String clientId, String clientSecret, String redirectUri) {
        this.authorizeUrl = authorizeUrl;
        this.accessTokenUrl = accessTokenUrl;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUri = redirectUri;
    }

    /**
     *   拼接访问QQ登录页面 所需要的参数(获取Authorization Code)
     *	 会到达QQ登录页面
     *  1. 如果用户成功登录并授权，则会跳转到指定的回调地址，并在redirect_uri地址后带上Authorization Code和原始的state值。如：
     * PC网站：http://graph.qq.com/demo/index.jsp?code=9A5F************************06AF&state=test
     * WAP网站：http://open.z.qq.com/demo/index.jsp?code=9A5F************************06AF&state=test
     * 注意：此code会在10分钟内过期。
     * 2. 如果用户在登录授权过程中取消登录流程，对于PC网站，登录页面直接关闭；对于WAP网站，同样跳转回指定的回调地址，并在redirect_uri地址后带上usercancel参数和原始的state值，其中usercancel值为非零，如：
     * http://open.z.qq.com/demo/index.jsp?usercancel=1&state=test
     * state=STATE&code=AUTHORIZATION_CODE
     * @param
     * @return
     */
    public String getAuthorizeUrl(Map params) {
        params.put(RESPONSE_TYPE_PARAM, "code");
        params.put(CLIENT_ID_PARAM, clientId);
        params.put(REDIRECT_URI_PARAM, redirectUri);
        return  HttpUtils.URLAppend(authorizeUrl, params, HttpUtils.EMPTY);
    }


    public String getAuthorizeURL(Map params) {
        params.put(RESPONSE_TYPE_PARAM, "code");
        params.put(SCOPE_PARAM,"snsapi_login");
        params.put(REDIRECT_URI_PARAM, redirectUri);
        return  HttpUtils.URLAppend(authorizeUrl, params, HttpUtils.EMPTY);
    }

}
