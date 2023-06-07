package com.cesarFrancisco.votePage.configs;

import com.cesarFrancisco.votePage.api.resources.UserResource;
import com.cesarFrancisco.votePage.domain.entities.User;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import com.cesarFrancisco.votePage.domain.entities.VoteItem;
import com.cesarFrancisco.votePage.domain.repositories.VoteRepository;
import com.cesarFrancisco.votePage.domain.services.UserService;
import com.cesarFrancisco.votePage.domain.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("test")
public class TestConfig implements CommandLineRunner {

    @Autowired
    private UserResource userResource;
    @Autowired
    private UserService userService;

    @Autowired
    private VoteRepository voteService;

    @Autowired
    private VoteRepository voteRepository;

    @Override
    public void run(String... args) throws Exception {

        User user = new User(null, "Cesar", "cesarfsjunior@hotmail.com", "201103Ju", null, null);
        User user2 = new User(null, "Andre", "andre@hotmail.com", "12345", null, null);
        User user3 = new User(null, "Maria", "mari@hotmail.com", "54321", null, null);
        userService.insert(user);
        userService.insert(user2);
        userService.insert(user3);

        Vote vot1 = new Vote(null, "Votação1", user);
        Vote vot2 = new Vote(null, "Votação2", user2);

        voteService.save(vot1);
        voteService.save(vot2);


        VoteItem item1 = new VoteItem(null, "item1", vot1);
        VoteItem item2 = new VoteItem(null, "item2", vot1);

        item1.addUser(user);
        item2.addUser(user);
        vot1.addItem(item1);
        vot1.addItem(item2);

        voteRepository.save(vot1);
    }
}
