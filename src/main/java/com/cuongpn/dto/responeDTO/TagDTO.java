package com.cuongpn.dto.responeDTO;

import com.cuongpn.enums.TagType;
import lombok.Data;

@Data
public class TagDTO {
    private Long id;
    private String name;
    private TagType tagType;
}
