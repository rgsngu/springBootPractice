package com.example.demorg.service;

import com.example.demorg.entity.Journal;
import com.example.demorg.entity.User;
import com.example.demorg.repository.JournalRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserService userService;

//    private static final Logger logger = LoggerFactory.getLogger(JournalService.class);

    @Transactional
    public void saveEntry(Journal journal, String userName) throws Exception {
        try {
            Optional<User> byUserName = userService.findByUserName(userName);
            if(byUserName.isPresent()){
                User user=byUserName.get();
                journal.setDate(LocalDate.now());
                Journal journal1 = journalRepository.save(journal);
                user.getJournalList().add(journal1);
                userService.saveUser(user);
            }
        }catch (Exception e){
            log.error("Exception while saving the journal");
            throw new Exception(e);
        }
    }

    public void saveEntry(Journal journal){
        journalRepository.save(journal);
    }

    public List<Journal> getAll(){
        return journalRepository.findAll();
    }

    public Optional<Journal> findById(ObjectId id){
        return journalRepository.findById(id);
    }

    @Transactional
    public boolean deleteById(ObjectId myid, String userName) {
        boolean checkRemove = false;
        try {
            Optional<User> userOptional = userService.findByUserName(userName);
            if(userOptional.isPresent()){
                User user = userOptional.get();
                checkRemove = user.getJournalList().removeIf(x -> x.getId().equals(myid));
                if (checkRemove) {
                    userService.saveUser(user);
                    journalRepository.deleteById(myid);
                }
            }
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error while deleting the entry");
        }
        return checkRemove;
    }

    public List<Journal> findByUserName(String userName){
        return null;
    }
}
