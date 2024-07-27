package com.example.demorg.controller;

import com.example.demorg.entity.Journal;
import com.example.demorg.entity.User;
import com.example.demorg.service.JournalService;
import com.example.demorg.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllJournalEntryOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> user = userService.findByUserName(authentication.getName());
        if(user.isPresent()){
            List<Journal> journalList = user.get().getJournalList();
            if (journalList != null && !journalList.isEmpty())
                return new ResponseEntity<>(journalList,HttpStatus.OK);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


}
