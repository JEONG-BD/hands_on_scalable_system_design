package gd.board.comment.controller;

import gd.board.comment.service.CommentService;
import gd.board.comment.service.request.CommentCreateRequest;
import gd.board.comment.service.response.CommentPageResponse;
import gd.board.comment.service.response.CommentResponse;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping("/v1/comments/{commentId}")
    public CommentResponse read(@PathVariable("commentId") Long commentId){
        return commentService.read(commentId);
    }

    @GetMapping("/v1/comments")
    public CommentPageResponse readAll(@RequestParam("articleId") Long articleId,
                                       @RequestParam("page") Long page,
                                       @RequestParam("pageSize") Long pageSize){
        return commentService.readAll(articleId, page, pageSize);
    }


    @GetMapping("/v1/comments/infinite-scroll")
    public List<CommentResponse> readAllInfiniteScroll(@RequestParam("articleId") Long articleId,
                                                       @RequestParam(value = "lastParentCommentId", required = false) Long lastParentCommentId,
                                                       @RequestParam(value = "lastCommentId", required = false) Long lastCommentId,
                                                       @RequestParam("pageSize") Long pageSize){
        return commentService.readAll(articleId, lastParentCommentId, lastCommentId, pageSize);
    }

    @PostMapping("/v1/comments")
    public CommentResponse create(@RequestBody CommentCreateRequest request){
        return commentService.create(request);
    }

    @DeleteMapping("/v1/comments/{commentId}")
    public void delete(@PathVariable("commentId") Long commentId ){
        commentService.delete(commentId);
    }

}
