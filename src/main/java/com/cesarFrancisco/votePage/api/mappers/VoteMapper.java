package com.cesarFrancisco.votePage.api.mappers;

import com.cesarFrancisco.votePage.api.dto.VoteDto;
import com.cesarFrancisco.votePage.api.insertDto.VoteInsertDto;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class VoteMapper {

    private final ModelMapper mapper;

    public VoteMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    public Vote voteRequestToVote(VoteInsertDto request) {
        return mapper.map(request, Vote.class);
    }

    public VoteDto toVoteDto(Vote vote) {
        return mapper.map(vote, VoteDto.class);
    }
}
