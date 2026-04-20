package com.nextstay.nextstaymain.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import com.nextstay.nextstaymain.service.userservice;

@RestController
public class usercontroller {
    @Autowired
    private userservice userservice;
}
