package com.cesarFrancisco.votePage.domain.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_votesItem")
@NoArgsConstructor
@Getter
@Setter
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
    @ManyToMany
    @JoinTable(name = "tb_user_vote_item")
    private List<User> users = new ArrayList<>();

    public VoteItem(Long idVoteItem, String name, Vote vote) {
        this.idVoteItem = idVoteItem;
        this.name = name;
        this.vote = vote;
    }

    public void addUser(User user) {
        users.add(user);
    }

    public void addVote() {
        votes++;
    }

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
