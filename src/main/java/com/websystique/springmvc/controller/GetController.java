package com.websystique.springmvc.controller;

/**
 * Created with IntelliJ IDEA.
 * User: linzh
 * Date: 16-7-1
 * Time: 下午5:39
 * To change this template use File | Settings | File Templates.
 */

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.websystique.springmvc.domain.Message;

@RestController
public class GetController {
    @RequestMapping(value = "/rest/{player}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Message message(@PathVariable String player) {

        // REST Endpoint.
        Message msg = new Message(player, "GET /" + player);

        return msg;
    }
}
