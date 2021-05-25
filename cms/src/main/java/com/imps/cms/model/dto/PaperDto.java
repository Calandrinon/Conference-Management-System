package com.imps.cms.model.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaperDto implements Serializable {
    private Long id;
    private String title;
    private String subject;
    private String keywords;
    private String topics;
    private Long authorId;
    private String fileName;
    private Long sectionId;
}
