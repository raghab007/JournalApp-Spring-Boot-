package com.raghab.journalApp.controller;
import com.raghab.journalApp.entity.JournalEntry;
import com.raghab.journalApp.entity.User;
import com.raghab.journalApp.service.JournalEntryService;
import com.raghab.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/journal")

public class JournalEntryController {
    @Autowired
    JournalEntryService journalEntryService;
   @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getJournalEntriesByUserName(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUserName(userName);
        List<JournalEntry> all = user.getJournalEntries();
        if (all!=null && !all.isEmpty()){
            return  new ResponseEntity<>(all,HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public ResponseEntity<?> createJournal(@RequestBody JournalEntry journalEntry){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        try {
            journalEntryService.saveEntry(journalEntry,userName);
            return new ResponseEntity<>(journalEntry, HttpStatus.CREATED);
        }catch (Exception exception){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("id/{id}")
    public ResponseEntity<?> getJournalById(@PathVariable ObjectId id){
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if (journalEntry.isPresent()){
            return new  ResponseEntity<>(journalEntry,HttpStatus.OK);
        }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("id/{id}/{userName}")
    public ResponseEntity<Void> deleteJournalById(@PathVariable ObjectId id,@PathVariable String userName) {
        User user = userService.findByUserName(userName);
        if(user==null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<JournalEntry> journalEntry = journalEntryService.findById(id);
        if (journalEntry.isPresent()) {
            user.getJournalEntries().removeIf(x->x.getId().equals(id));
            userService.saveEntry(user);
            journalEntryService.removeById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

//    @PutMapping("id/{id}")
//    public ResponseEntity<?> updateJournalBYId(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry){
//        JournalEntry old = journalEntryService.findById(id).orElse(null);
//        if(old!=null){
//            old.setTitle(journalEntry.getTitle()!=null && !journalEntry.getTitle().isEmpty() ?journalEntry.getTitle():old.getTitle());
//            old.setContent(journalEntry.getContent()!=null && !journalEntry.getContent().isEmpty() ?journalEntry.getContent():old.getContent());
//            journalEntryService.saveEntry(old);
//            return new ResponseEntity<>(old,HttpStatus.OK);
//        }
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//    }


}
