package com.nd.restful.services;

import com.nd.sdp.cs.demo.Demo2_0_4;
import com.nd.sdp.cs.sdk.Dentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Hyphy on 2016/7/29.
 */

@Service
public class CsService {

    @Autowired
    static String dentryId = "73120074-8a70-4576-944f-6898945828de";

    static String serviceId = "";

    @Autowired
    static String serviceName = "VrResource";

    public Object FileList() {
        List<Dentry> list = null;
        try {
            Dentry dentry = new Dentry();
            String session = Demo2_0_4.getSession(serviceName, serviceId, "251361");
            list.add(dentry);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
