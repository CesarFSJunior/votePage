package com.cesarFrancisco.votePage.api.resources;

import com.cesarFrancisco.votePage.api.dto.VoteDto;
import com.cesarFrancisco.votePage.api.mappers.VoteMapper;
import com.cesarFrancisco.votePage.api.insertDto.VoteInsertDto;
import com.cesarFrancisco.votePage.domain.entities.Vote;
import com.cesarFrancisco.votePage.domain.entities.VoteItem;
import com.cesarFrancisco.votePage.domain.services.VoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RequestMapping(value = "/votes")
@Controller
public class VoteResource {

    @Autowired
    VoteService voteService;

    @Autowired
    VoteMapper mapper;

    @GetMapping
    public ResponseEntity<List<VoteDto>> findAll() {
        List<Vote> votes = voteService.findAll();

        List<VoteDto> votesDto = votes.stream().map(vote -> mapper.toVoteDto(vote)).toList();

        return ResponseEntity.ok().body(votesDto);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<VoteDto> findById(@PathVariable Long id) {
        Vote vote = voteService.findById(id);

        VoteDto voteDto = mapper.toVoteDto(vote);

        return ResponseEntity.ok().body(voteDto);
    }

    @PostMapping
    public ResponseEntity<VoteDto> insert(@RequestBody VoteInsertDto voteInsertDto) {
        Vote vote = mapper.voteRequestToVote(voteInsertDto);

        vote = voteService.insert(vote);

        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{id}").build(vote.getId());

        VoteDto voteDto = mapper.toVoteDto(vote);

        return ResponseEntity.created(uri).body(voteDto);
    }

    @PutMapping(value = "/{id}/{item}")
    public ResponseEntity<Vote> addVote(@PathVariable Long id, @PathVariable String item) {
        Vote vote = voteService.addVote(id, item);

        return ResponseEntity.ok().body(vote);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        voteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
