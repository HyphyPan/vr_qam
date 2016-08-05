package com.nd.restful.controller;

import com.alibaba.fastjson.JSONArray;
import com.nd.restful.services.FileOperateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Hyphy on 2016/8/4.
 */

@RestController
public class FileOperateController {
    @Autowired
    FileOperateService service;

    @RequestMapping(value = "/{serviceName}/dentry", method = RequestMethod.POST)
    public void upload(@PathVariable String serviceName, HttpServletRequest request) throws Exception {
        if (serviceName.equals("service")) {
            service.upload(request);
        }
    }
}
