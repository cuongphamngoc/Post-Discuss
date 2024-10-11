package com.cuongpn.dto.requestDTO;

import com.cuongpn.enums.VoteType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class VoteRequest {

    private Long id;
    @NotNull
    private VoteType voteType;
}
