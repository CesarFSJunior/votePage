package com.cesarFrancisco.votePage.domain.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "tb_votes")
@NoArgsConstructor
@Getter
@Setter
public class Vote implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_votes")
    private Long id;
    private String title;
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User creator;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "vote")
    private List<VoteItem> items = new ArrayList<>();

    public Vote(Long id, String title, User creator) {
        this.id = id;
        this.title = title;
        this.creator = creator;
    }

    public void addItem(VoteItem item) {
        items.add(item);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vote vote = (Vote) o;
        return Objects.equals(id, vote.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
