package com.rozsa.aopexample.resources;


import com.rozsa.aopexample.services.CatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/cat")
public class CatsResource {

    private final CatsService catsService;

    @GetMapping("/all/facts")
    public List<String> getCatsFacts(@RequestParam String filter) {
        return catsService.getFacts(filter);
    }
}
