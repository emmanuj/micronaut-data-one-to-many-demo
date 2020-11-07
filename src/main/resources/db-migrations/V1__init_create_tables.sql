
-- ;;

create table if not exists category_answers
(
    id        uuid primary key not null,
    category  text             not null,
    answer    text,
    reviewed  boolean default false
);

-- ;;

create table if not exists answer_votes
(
    id                 uuid primary key not null,
    category_answer_id uuid references category_answers (id) on delete cascade,
    vote               text             not null
);
