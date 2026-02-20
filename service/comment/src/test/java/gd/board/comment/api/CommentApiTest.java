package gd.board.comment.api;

import gd.board.comment.service.response.CommentPageResponse;
import gd.board.comment.service.response.CommentResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;

import java.util.List;

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
    
    @Test
    public void readAll(){
        //given
        CommentPageResponse response = client.get()
                .uri("/v1/comments?articleId=1&page=1&pageSize=10")
                .retrieve()
                .body(CommentPageResponse.class);
        //when
        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            if(comment.getCommentId().equals(comment.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.println(comment.getCommentId());
        }
        //then
    }
    @Test
    public void readAllInfiniteAll(){
        //given
        List<CommentResponse> response1 = client.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        //when
        for (CommentResponse commentResponse : response1) {
            if(commentResponse.getCommentId().equals(commentResponse.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.println(commentResponse.getCommentId());
        }
        Long parentCommentId = response1.getLast().getParentCommentId();
        Long commentId = response1.getLast().getCommentId();

        List<CommentResponse> response2 = client.get()
                .uri("/v1/comments/infinite-scroll?articleId=1&lastParentCommentId=%s&lastCommentId=%s&pageSize=5"
                        .formatted(parentCommentId, commentId))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("SecondPage");
        //when
        for (CommentResponse commentResponse : response2) {
            if(commentResponse.getCommentId().equals(commentResponse.getParentCommentId())){
                System.out.print("\t");
            }
            System.out.println(commentResponse.getCommentId());
        }

        //then
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
