--index--
create index idx_article_id_parent_comment_id_comment_id on comment (
     article_id asc,
     parent_comment_id asc,
     comment_id asc
);

-- N번 페이지에서 M개의 댓글 조회
select * from (
      select comment_id
        from comment
       where article_id = {article_id}
       order by parent_comment_id asc, comment_id asc
        limit {limit} offset {offset}
  ) t
  left join comment on t.comment_id = comment.comment_id;

-- 댓글 개수 조회
select count(*) from (
     select comment_id
       from comment
      where article_id = {article_id}
      limit {limit}
  ) t;