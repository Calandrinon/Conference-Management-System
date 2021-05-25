package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaperDto implements Serializable {
    private Long id;
    private String title;
    private String subject;
    private String keywords;
    private String topics;
    private Long authorId;
    private String fileName;
    private MultipartFile data;
    private Long sectionId;
    private Long conferenceId;
}
