package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name="paperDto"
        , types = { Paper.class }
)
public interface PaperDto {
    Long getId();
    String getTitle();
    String getSubject();
    String getKeywords();
    String getTopics();
    UserDto getAuthor();
    SectionDto getSection();
    String getFilename();
}
