package com.nd.restful.controller;

import com.alibaba.fastjson.JSONArray;
import com.nd.restful.services.CsService;
import com.nd.sdp.cs.sdk.Dentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Hyphy on 2016/7/29.
 */

@RestController
public class CsController {
    @Autowired
    CsService service;

    @RequestMapping(value = "/{serviceName}/dentrys", method = RequestMethod.GET)
    public Object getFileList(@PathVariable String serviceName) throws Exception {
        if (serviceName.equals("cs")) {
            return service.getFileList();
        } else return null;
    }

    @RequestMapping(value = "/{serviceName}/dentrys", method = RequestMethod.POST)
    public Dentry uploadFile(@PathVariable String serviceName, @RequestParam String fileName) throws Exception {
        if (serviceName.equals("cs")) {
            return service.uploadFile(fileName);
        } else return null;
    }
}
