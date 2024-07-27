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

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/journal")
public class JournalController {


    @Autowired
    private JournalService journalService;

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

    @PostMapping
    public ResponseEntity<?> createEntry(@RequestBody Journal jentry){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            journalService.saveEntry(jentry,authentication.getName());
            return new ResponseEntity<Journal>(jentry,HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<String>("Fail while saving",HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{myId}")
    public ResponseEntity<Journal> getJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> serviceByUserName = userService.findByUserName(authentication.getName());
        if (serviceByUserName.isPresent()) {
            User user = serviceByUserName.get();
            List<Journal> journalList = user.getJournalList().stream().filter(x -> x.getId().equals(myId)).toList();
            if(!journalList.isEmpty()){
                Optional<Journal> journal = journalService.findById(myId);
                if(journal.isPresent()){
                    return new ResponseEntity<>(journal.get(),HttpStatus.OK);
                }
            }
        }
        Optional<Journal> journalServiceById = journalService.findById(myId);
        return journalServiceById.map(journal -> new ResponseEntity<>(journal, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("id/{myId}")
    public ResponseEntity<?> deleteJournalEntryById(@PathVariable ObjectId myId){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        boolean checkRemove = journalService.deleteById(myId,authentication.getName());
        if(checkRemove){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("id/{id}")
    public ResponseEntity<?> updateJournalEntryById(@PathVariable ObjectId id,@RequestBody Journal journalUpdate) throws Exception {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> serviceByUserName = userService.findByUserName(authentication.getName());
        if (serviceByUserName.isPresent()) {
            User user = serviceByUserName.get();
            List<Journal> journalList = user.getJournalList().stream().filter(x -> x.getId().equals(id)).toList();
            if(!journalList.isEmpty()){
                Optional<Journal> oldJournal = journalService.findById(id);
                if(oldJournal.isPresent()){
                    oldJournal.get().setTitle(journalUpdate.getTitle() != null && !journalUpdate.getTitle().isEmpty() ? journalUpdate.getTitle():oldJournal.get().getTitle());
                    oldJournal.get().setContent(journalUpdate.getContent() != null && !journalUpdate.getContent().isEmpty() ? journalUpdate.getContent():oldJournal.get().getContent());
                    journalService.saveEntry(oldJournal.get());
                    return new ResponseEntity<>(oldJournal.get(),HttpStatus.OK);
                }
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
