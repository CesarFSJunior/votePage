package com.cesarFrancisco.votePage.domain.services;

import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import com.cesarFrancisco.votePage.domain.entities.VoteItem;
import com.cesarFrancisco.votePage.domain.repositories.VoteRepository;
import com.cesarFrancisco.votePage.exceptions.ObjectNotFoundException;
import com.cesarFrancisco.votePage.exceptions.VoteException;
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

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        User user = userService.findByEmail(email);

        vote.setCreator(user);

        return voteRepository.save(vote);
    }

    public Vote addVote(Long id, String item) {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Vote> optVote = voteRepository.findById(id);

        if (optVote.isEmpty()) {
            throw new ObjectNotFoundException("Vote not found");
        }

        Vote vote = optVote.get();
        User user = userService.findByEmail(email);


        for (VoteItem voteItem : vote.getItems()) {
            for (User votingUser : voteItem.getUsers()) {
                if (votingUser.equals(user)) {
                    throw new VoteException("User already voted");
                }
            }
            if (voteItem.getName().equals(item)) {
                voteItem.addVote();
                voteItem.addUser(user);
            }
        }

        return voteRepository.save(vote);
    }

    public void delete(Long id) {
        voteRepository.deleteById(id);
    }

}
