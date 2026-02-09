package gd.board.article.repository;

import gd.board.article.entity.Article;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@Slf4j
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void findAllTest () throws Exception{
        //given
        List<Article> articles = articleRepository.findAll(1L, 1499970L, 30L);
        log.info("article.size = {} ",  articles.size());
        for (Article article : articles) {
            log.info("article ={}", article);
        }
        //when

        //then
    }
    
    @Test
    public void findInfiniteScrollTest() throws Exception{
        //given
        List<Article> articles = articleRepository.findInfiniteScroll(1L, 30L);
        //when
        for (Article article : articles) {
            log.info("articleId = {}", article.getArticleId());
        }

        Long lastArticleId = articles.getLast().getArticleId();

        log.info("article lastId {}" , lastArticleId);
        List<Article> articlesAfter = articleRepository.findInfiniteScroll(1L, 30L, lastArticleId);

        Long articleId = articlesAfter.getFirst().getArticleId();
        log.info("article firstId {}" , articleId);
        //then
    }

    @Test
    public void countTest() throws Exception{
        //given
        Long count = articleRepository.count(1L, 10000L);
        //when
        log.info("count = {}" , count);

        //then
    }


}