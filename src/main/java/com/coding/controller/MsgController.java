package com.coding.controller;

import com.coding.handler.WebSocketServer;
import com.coding.service.MsgSendService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yunji
 */
@Slf4j
@Api(tags = "消息接口")
@RequiredArgsConstructor
@RestController
public class MsgController {

    private final MsgSendService msgSendService;
    private final WebSocketServer webSocketServer;


    @ApiOperation("发送消息")
    @GetMapping("send")
    public String send(String msg) {
        Map<String,Object> map=new HashMap<>();
        map.put("msg",msg);
        map.put("date", LocalDateTime.now().toString());
        int size = msgSendService.sendMsg(map);
        webSocketServer.sendToAll(map);
        return "发送成功" + size;
    }


}
