package com.one.demo;
import io.micronaut.data.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@MappedEntity("category_answers")
public class CategoryAnswer implements Serializable {
    @Id
    @AutoPopulated
    private UUID id;
    private String category;
    private String answer;

    @Relation(value = Relation.Kind.ONE_TO_MANY, mappedBy = "categoryAnswer")
    Set<AnswerVote> votes = new HashSet<>();
}
