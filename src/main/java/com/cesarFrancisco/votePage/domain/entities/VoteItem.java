package com.cesarFrancisco.votePage.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_votesItem")
public class VoteItem implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVoteItem;
    private String name;
    @ManyToOne
    @JoinColumn(name = "id_vote")
    @JsonIgnore
    private Vote vote;
    private int votes = 0;

    public VoteItem() {
    }

    public VoteItem(Long idVoteItem, String name, Vote vote) {
        this.idVoteItem = idVoteItem;
        this.name = name;
        this.vote = vote;
    }

    public Long getIdVoteItem() {
        return idVoteItem;
    }

    public void setIdVoteItem(Long idVoteItem) {
        this.idVoteItem = idVoteItem;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Vote getVote() {
        return vote;
    }

    public void setVote(Vote vote) {
        this.vote = vote;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void addUsers(User user) {
        users.add(user);
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public void addVote() {
        votes++;
    }

    @ManyToMany
    @JoinTable(name = "tb_user_vote_item")
    private List<User> users = new ArrayList<>();


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VoteItem voteItem = (VoteItem) o;
        return Objects.equals(idVoteItem, voteItem.idVoteItem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVoteItem);
    }
}
