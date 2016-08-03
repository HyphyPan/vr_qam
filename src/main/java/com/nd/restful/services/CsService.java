package com.nd.restful.services;

import com.alibaba.fastjson.JSONArray;
import com.nd.gaea.client.http.WafSecurityHttpClient;
import com.nd.gaea.client.support.WafUcServerConfig;
import com.nd.sdp.cs.common.CsConfig;
import com.nd.sdp.cs.sdk.Dentry;
import org.apache.http.client.methods.HttpPatch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Hyphy on 2016/7/29.
 */

@Service
public class CsService {
    String dentryId = "ca2e8f92-9a5f-4111-b92d-8725b5b55fd5";
    String serviceId = "22a72450-691d-4fe9-8b39-0724c76990a9";
    String serviceName = "dev_content_aaaaaa";
    String path = "VrResource";
    String userId = "251361";
    Dentry dentry = null;

    public CsService() throws Exception {
        dentry = new Dentry();
        dentry.setPath("/" + serviceName + "/VrResource/");
        dentry.setServiceId(serviceId);
    }

    public JSONArray getFileList() throws Exception {
        JSONArray jsonArray = new JSONArray();
        String path = null;
        String session = getSession();
        List<Dentry> list = Dentry.list(serviceName, path, serviceId, "createAt gt 0", "createAt desc", 10, session);
        jsonArray = JSONArray.parseArray(list.toString());
        return jsonArray;
    }

    private String session = null;
    private long expireAt = 0;

    private String getSession() {
        if (session == null) {
            Map sessionMap = requestSession();
            session = (String) sessionMap.get("session");
            expireAt = (Long) sessionMap.get("expire_at");
            return session;
        } else {
            if (expireAt < System.currentTimeMillis()) {
                Map sessionMap = requestSession();
                session = (String) sessionMap.get("session");
                expireAt = (Long) sessionMap.get("expire_at");
                return session;
            }
        }
        return session;
    }

    @SuppressWarnings({"deprecation", "rawtypes"})
    private Map requestSession() {
        WafUcServerConfig wafUcServerConfig = new WafUcServerConfig();
//      wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter-pressure.web.sdp.101.com/");
//		wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.dev.web.nd/");
//		wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.qa.web.sdp.101.com/");
        wafUcServerConfig.setUC_API_DOMAIN("http://101uccenter.beta.web.sdp.101.com/");//
        wafUcServerConfig.setUC_API_VERSION("v0.93");
        wafUcServerConfig.setWafUcServerConfig();

        HttpPatch httpPath = null;

        WafSecurityHttpClient httpClient = new WafSecurityHttpClient();
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("path", "/" + serviceName); //必须以 "/"+服务名称作为起始路径(例如：申请的服务名称为:example,path的开头为"/example");
        param.put("uid", userId); //用户uid
        param.put("role", "admin"); //取值仅限字符串"user"、"admin"(user：只能管理授权的路径下自己的目录项,admin：可以管理授权的路径下全部的目录项)。
        param.put("service_id", serviceId);
        param.put("expires", 5 * 60);//  session过期时间，单位秒
        String url = CsConfig.getHostUrl() + "/sessions";
        try {
            Map sessionMap = httpClient.post(url, param, Map.class);
            System.out.println("session=" + sessionMap);
            return sessionMap;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
