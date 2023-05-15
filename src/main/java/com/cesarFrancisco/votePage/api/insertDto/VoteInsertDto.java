package com.cesarFrancisco.votePage.api.insertDto;

import com.cesarFrancisco.votePage.domain.entities.VoteItem;

import java.util.ArrayList;
import java.util.List;

public class VoteInsertDto {
    private String title;
    private List<VoteItem> items = new ArrayList<>();

    public VoteInsertDto() {
    }

    public VoteInsertDto(String title, List<VoteItem> items) {
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<VoteItem> getItems() {
        return items;
    }

    public void setItems(List<VoteItem> items) {
        this.items = items;
    }
}

