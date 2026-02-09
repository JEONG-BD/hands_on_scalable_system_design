package gd.board.article.api;

import gd.board.article.service.response.ArticlePageResponse;
import gd.board.article.service.response.ArticleResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

class ArticleApiTest {
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
    public void readAllTest() throws Exception{
        //given
        ArticlePageResponse response = client.get()
                .uri("/v1/articles?boardId=1&pageSize=30&page=1")
                .retrieve()
                .body(ArticlePageResponse.class);
        //when
        System.out.println("response.getArticleCount = " + response.getArticleCount());
        for (ArticleResponse article : response.getArticles()) {
            System.out.println("article =" + article.getId());

        }
        //then
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

    @Test
    public void readAllInfiniteScrollTest() throws Exception{
        //given
        List<ArticleResponse> articles1 = client.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });

        for (ArticleResponse article : articles1) {
            System.out.println(article.getId());
        }

        Long lastArticleId = articles1.getLast().getId();

        List<ArticleResponse> articles2 = client.get()
                .uri("/v1/articles/infinite-scroll?boardId=1&pageSize=5&lastArticleId=%s".formatted(lastArticleId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArticleResponse>>() {
                });
        System.out.println("---------");


        for (ArticleResponse article : articles2) {
            System.out.println(article.getId());
        }
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