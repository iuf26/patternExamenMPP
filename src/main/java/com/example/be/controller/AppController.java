package com.example.be.controller;

import com.example.be.repository.ParentRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AppController {


    @Autowired
    private ParentRepo repo;

    @GetMapping("/hi")
    public void sayHi(){
        repo.add(2);
    }
}
