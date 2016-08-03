package com.nd.restful.controller;

import com.alibaba.fastjson.JSONArray;
import com.nd.restful.services.CsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hyphy on 2016/7/29.
 */

@RestController
public class CsController {
    @Autowired
    CsService service;

    @RequestMapping("/file")
    public JSONArray getFileList() throws Exception {
        return service.getFileList();
    }
}
