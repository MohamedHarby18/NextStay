package com.nextstay.nextstaymain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.nextstay.nextstaymain.repository.userrepo;

@Service
public class userservice {
    @Autowired
    private userrepo userrepo;
    
}
