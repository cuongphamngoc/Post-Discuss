package com.cuongpn.dto.responeDTO;

import com.cuongpn.enums.VoteType;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class VoteDTO {
    private Long id;
    private AuthorDTO author;
    private VoteType voteType;
}
