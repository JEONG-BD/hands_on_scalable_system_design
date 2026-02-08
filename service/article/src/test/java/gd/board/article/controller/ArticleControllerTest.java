package gd.board.article.controller;

import gd.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

class ArticleControllerTest {
    RestClient client = RestClient.create("http://localhost:9000");

    @Test
    public void createTest(){
        //given
        ArticleCreateRequest articleCreateRequest = new ArticleCreateRequest("hi", "content", 1L, 1L);
        //when
        ArticleResponse response = create(articleCreateRequest);
        //then
        System.out.println(response);
    }

    @Test
    public void readTest() throws Exception{
        //given
        ArticleResponse response = read(278839208181137408L);
        System.out.println(response);
        //when
        //then
    }

    @Test
    public void updateTest() throws Exception{
        //given
        update(278839208181137408L);
        ArticleResponse response = read(278839208181137408L);
        System.out.println(response);
        //when
        //then
    }

    @Test
    public void deleteTest() throws Exception{
        //given
        client.delete()
                .uri("/v1/articles/{articleId}", 278839208181137408L)
                .retrieve();
        //when
        //then
    }

    ArticleResponse read(Long articleId){
        return client.get()
                .uri("/v1/articles/{articleId}", articleId)
                .retrieve()
                .body(ArticleResponse.class);

    }

    ArticleResponse create(ArticleCreateRequest request){
        return client.post()
                .uri("/v1/articles")
                .body(request)
                .retrieve()
                .body(ArticleResponse.class);
    }

    void update(Long articleId){
        client.put()
                .uri("/v1/articles/{articleId}", articleId)
                .body(new ArticleUpdateRequest("hi 2", "my"))
                .retrieve();

    }

    @AllArgsConstructor
    @Getter
    static class ArticleCreateRequest {
        private String content;
        private String title;
        private Long writerId;
        private Long boardId;
    }

    @Getter
    @AllArgsConstructor
    static class ArticleUpdateRequest{
        private String title;
        private String content;
    }



}