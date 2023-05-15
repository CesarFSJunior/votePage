package com.cesarFrancisco.votePage.api.dto;

import java.util.ArrayList;
import java.util.List;

public class VoteItemDto {
    private Long idVoteItem;
    private String name;
    private List<UserDto> users = new ArrayList<>();

    public VoteItemDto() {
    }

    public VoteItemDto(Long idVoteItem, String name, List<UserDto> users) {
        this.idVoteItem = idVoteItem;
        this.name = name;
        this.users = users;
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

    public List<UserDto> getUsers() {
        return users;
    }

    public void setUsers(List<UserDto> users) {
        this.users = users;
    }
}
