package com.nd.restful.controller;

import com.nd.restful.services.CsService;
import com.nd.sdp.cs.demo.Demo2_0_4;
import com.nd.sdp.cs.sdk.Dentry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hyphy on 2016/7/29.
 */

@RestController
public class CsController {
    @Autowired
    private CsService csService;

    @RequestMapping("/list")
    public Object FileList(){
        return csService.FileList();
    }
}
