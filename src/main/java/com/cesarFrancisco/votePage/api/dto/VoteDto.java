package com.cesarFrancisco.votePage.api.dto;

import java.util.ArrayList;
import java.util.List;


public class VoteDto {
    private Long id;
    private String title;
    private UserDto creator;
    private List<VoteItemDto> items = new ArrayList<>();

    public VoteDto() {
    }

    public VoteDto(Long id, String title, UserDto creator, List<VoteItemDto> items) {
        this.id = id;
        this.title = title;
        this.creator = creator;
        this.items = items;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserDto getCreator() {
        return creator;
    }

    public void setCreator(UserDto creator) {
        this.creator = creator;
    }

    public List<VoteItemDto> getItems() {
        return items;
    }

    public void setItems(List<VoteItemDto> items) {
        this.items = items;
    }
}
