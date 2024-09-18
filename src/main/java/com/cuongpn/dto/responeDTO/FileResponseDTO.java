package com.cuongpn.dto.responeDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class FileResponseDTO {
    private String fileName;

    private String fileUrl;
}
