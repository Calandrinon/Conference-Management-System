package com.imps.cms.model;


import org.springframework.data.rest.core.config.Projection;

@Projection(
        name="paperDto"
        , types = { Paper.class }
)
public interface PaperInterface {
    Long getId();
    String getTitle();
    String getSubject();
    String getKeywords();
    String getTopics();
    UserDtoInterface getAuthor();
    SectionInterface getSection();
    String getFilename();
}
