package com.cesarFrancisco.votePage.domain.services;

import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import com.cesarFrancisco.votePage.domain.entities.VoteItem;
import com.cesarFrancisco.votePage.domain.repositories.VoteRepository;
import com.cesarFrancisco.votePage.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class VoteService {

    @Autowired
    VoteRepository voteRepository;

    @Autowired
    UserService userService;

    public List<Vote> findAll() {

        return voteRepository.findAll();
    }

    public Vote findById(Long id) {
        Optional<Vote> vote = voteRepository.findById(id);

        if(vote.isEmpty()) {
            throw new ObjectNotFoundException("Vote not found");
        }


        return vote.get();
    }
    public Vote insert(Vote vote) {

        for (VoteItem item: vote.getItems()) {
            item.setVote(vote);
        }

        // TODO set creator interactive
        vote.setCreator(new User(1L, "Cesar", "cesar@hotmail.com", "201103Ju", null, new Date()));

        return voteRepository.save(vote);
    }

    public Vote addVote(Long id, String item) {

        // TODO set user to voteItem

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vote> optVote = voteRepository.findById(id);

        if (optVote.isEmpty()) {
            throw new ObjectNotFoundException("Vote not found");
        }

        Vote vote = optVote.get();

        for (VoteItem voteItem : vote.getItems()) {
            if (voteItem.getName().equals(item)) {
                voteItem.addVote();
                voteItem.addUser(userService.findByEmail(email));
            }
        }

        return voteRepository.save(vote);
    }

    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

}
