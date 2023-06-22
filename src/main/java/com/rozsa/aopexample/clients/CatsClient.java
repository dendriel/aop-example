package com.rozsa.aopexample.clients;

import com.rozsa.aopexample.clients.response.CatFactsResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@FeignClient("cats")
public interface CatsClient {


    // https://cat-fact.herokuapp.com/facts/
    @GetMapping("/facts")
    public List<CatFactsResponse> getCatsFacts();
}
