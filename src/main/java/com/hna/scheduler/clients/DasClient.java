package com.hna.scheduler.clients;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("das")
public interface DasClient {

    @RequestMapping(method = RequestMethod.POST, value = "/datas/insert")
    ResponseEntity insert(@RequestBody CreateDataRequest request);
}
