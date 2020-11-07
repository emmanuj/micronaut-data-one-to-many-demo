package com.one.demo;

import edu.umd.cs.findbugs.annotations.NonNull;
import io.micronaut.data.annotation.Join;
import io.micronaut.data.jdbc.annotation.JdbcRepository;
import io.micronaut.data.model.query.builder.sql.Dialect;
import io.micronaut.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

@JdbcRepository(dialect = Dialect.POSTGRES)
public abstract class CategoryAnswerRepository implements CrudRepository<CategoryAnswer, UUID> {
    @NonNull
    @Join("votes")
    public abstract List<CategoryAnswer> findAll();

    @Join(value="votes", alias="v_", type = Join.Type.LEFT_FETCH)
    public abstract List<CategoryAnswer> findAllOrderById();
}
