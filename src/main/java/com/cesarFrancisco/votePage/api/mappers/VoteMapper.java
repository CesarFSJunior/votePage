package com.cesarFrancisco.votePage.api.mappers;

import com.cesarFrancisco.votePage.api.dto.VoteDto;
import com.cesarFrancisco.votePage.api.insertDto.VoteInsertDto;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class VoteMapper {

    private final ModelMapper mapper;

    public Vote voteRequestToVote(VoteInsertDto request) {
        return mapper.map(request, Vote.class);
    }

    public VoteDto toVoteDto(Vote vote) {
        return mapper.map(vote, VoteDto.class);
    }
}
