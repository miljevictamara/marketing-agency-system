package com.bsep.marketingacency.service;

import com.bsep.marketingacency.dto.UserDto;
import com.bsep.marketingacency.model.RejectionNote;
import com.bsep.marketingacency.model.Role;
import com.bsep.marketingacency.model.User;
import com.bsep.marketingacency.repository.RejectionNoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class RejectionNoteService {
    @Autowired
    private RejectionNoteRepository rejectionNoteRepository;

    public List<RejectionNote> findAll() {
        return rejectionNoteRepository.findAll();
    }

    public Boolean isUserRejectionExpired(String email) {
        List<RejectionNote> rejections = rejectionNoteRepository.findAll();

        Date currentTime = new Date();

        for (RejectionNote rejection : rejections) {
            if (rejection.getEmail().equals(email)) {
              if (currentTime.after(new Date(rejection.getRejectionDate().getTime() + TimeUnit.MINUTES.toMillis(60)))) {
                    System.out.println("The user " + email + " has been rejected, and the rejection period has expired.");
                    return true;
                }
                else {
                    System.out.println("The user " + email + " has been rejected, but the rejection period has not expired yet.");
                    return false;
                }
            }
        }
        System.out.println("No rejection found for the user " + email + ".");
        return true;
    }

    public RejectionNote save(RejectionNote note) {
        RejectionNote rejectionNote = new RejectionNote();
        rejectionNote.setRejectionDate(note.getRejectionDate());
        rejectionNote.setReason(note.getReason());
        rejectionNote.setEmail(note.getEmail());

        return this.rejectionNoteRepository.save(rejectionNote);
    }
}
