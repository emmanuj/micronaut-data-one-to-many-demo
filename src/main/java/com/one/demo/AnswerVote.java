package com.one.demo;
import io.micronaut.data.annotation.*;
import lombok.Data;

import java.util.UUID;

@Data
@MappedEntity("answer_votes")
public class AnswerVote {
    @Id
    @AutoPopulated
    UUID id;

    @Relation(value = Relation.Kind.MANY_TO_ONE, cascade = Relation.Cascade.ALL)
    @MappedProperty(value = "category_answer_id")
    CategoryAnswer categoryAnswer;

    String vote;
}
