package gd.board.comment.api;

import gd.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;

public class CommentApiTest {

    RestClient client = RestClient.create("http://localhost:9001");

    @Test
    public void create() throws Exception{
        //given
        CommentResponse response1 = createComment(new CommentCreateRequest(1L, "my comment1", null, 1L));
        CommentResponse response2 = createComment(new CommentCreateRequest(1L, "my comment2", response1.getCommentId(), 1L));
        CommentResponse response3 = createComment(new CommentCreateRequest(1L, "my comment3", response1.getCommentId(), 1L));

        System.out.println("commentID= %s :".formatted(response1.getCommentId()));
        System.out.println("commentID= %s :".formatted(response2.getCommentId()));
        System.out.println("commentID= %s :".formatted(response3.getCommentId()));
        //when
        //then
    }

    @Test
    public void read() throws Exception{
        //given
        CommentResponse response = client.get()
                .uri("/v1/comments/{commentId}", 283145669742727168L)
                .retrieve()
                .body(CommentResponse.class);
        //when
        System.out.println("response = " + response);

        //then
    }
    
    @Test
    public void delete(){
        //given
        client.delete()
                .uri("/v1/comments/{commentedId}", 283145669742727168L)
                .retrieve();
        

        //when
        
        //then
    }

    private CommentResponse createComment(CommentCreateRequest request) {
        return client.post()
                .uri("/v1/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Getter
    @AllArgsConstructor
    public static class CommentCreateRequest {

        private Long articleId;
        private String content;
        private Long parentCommentId;
        private Long writerId;
    }

}
