package gd.board.like.api;

import gd.board.like.service.response.ArticleLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    public void likeAndUnLikeTest(){
        //given
        Long articleId = 9999L;
        like(articleId, 1L);
        like(articleId, 2L);
        like(articleId, 3L);

        //when
        ArticleLikeResponse read = read(articleId, 1L);
        ArticleLikeResponse read2 = read(articleId, 2L);
        ArticleLikeResponse read3 = read(articleId, 3L);
        //
        unlike(articleId, 1L);
        unlike(articleId, 2L);
        unlike(articleId, 3L);
        //then
    }

    void like(Long articleId, Long userId){
        restClient.post()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve();
    }

    void unlike(Long articleId, Long userId){
        restClient.delete()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve();
    }

    ArticleLikeResponse read(Long articleId, Long userId){
        return restClient.get()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}", articleId, userId)
                .retrieve().body(ArticleLikeResponse.class);

    }




}
