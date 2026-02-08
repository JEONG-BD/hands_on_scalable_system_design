
-- 1 ARTICLE 조회 1 실행 시간 확인
select * from article               -- 게시글 테이블
 where board_id = 1                 -- 게시판별
 order by created_at desc           -- 최신순
 limit 30 offset 90;                --N번 페이지에서 M개

-- INDEX 생성
create index idx_board_id_article_id on article(board_id asc, article_id desc);

-- ARTICLE 조회 2 실행 시간 확인
select * from article               -- 게시글 테이블
 where board_id = 1                 -- 게시판별
 order by article_id desc           -- 정렬 변경
 limit 30 offset 90;                --N번 페이지에서 M개

-- ARTICLE 조회 3 실행 시간 확인
select * from article               -- 게시글 테이블
where board_id = 1                 -- 게시판별
order by article_id desc           -- 정렬 변경
    limit 30 offset 1499970;                --N번 페이지에서 M개

select board_id, article_id from article
where board_id = 1
order by article_id desc
    limit 30 offset 1499970;

select * from (
      select article_id from article
      where board_id = 1
      order by article_id desc
      limit 30 offset 1499970
) t left join article on t.article_id = article.article_id;

-- 페이지 번호 조회
select count(*)
  from (select article_id from article
         where board_id = {board_id}
         limit {limit}) t;
