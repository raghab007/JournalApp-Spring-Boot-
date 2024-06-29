package com.raghab.journalApp.service;
// controller --> service --> repository
import com.raghab.journalApp.entity.JournalEntry;
import com.raghab.journalApp.entity.User;
import com.raghab.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private  JournalEntryRepository journalEntryRepository;

   @Autowired
    private UserService userService;

   @Transactional
   public void saveEntry(JournalEntry journalEntry, String userName){
        try {
            User user = userService.findByUserName(userName);
            journalEntry.setDate(LocalDateTime.now());
            JournalEntry journalEntry1 =  journalEntryRepository.save(journalEntry);
            user.getJournalEntries().add(journalEntry1);
            userService.saveEntry(user);
        }catch (Exception e){
            System.out.println("Error:"+e);
        }

    }

  public List<JournalEntry> getAll(){

        return journalEntryRepository.findAll();
  }

   public Optional<JournalEntry> findById(ObjectId id){
       return  journalEntryRepository.findById( id);
   }


   public void removeById(ObjectId id){
        journalEntryRepository.deleteById(id);
   }

}
//PlatformTransactionManager
//MongoTransactionManager

