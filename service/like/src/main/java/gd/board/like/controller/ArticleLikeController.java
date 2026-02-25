package gd.board.like.controller;

import gd.board.like.service.ArticleLikeService;
import gd.board.like.service.response.ArticleLikeResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleLikeController {

    private final ArticleLikeService articleLikeService;

    @GetMapping("/v1/article-likes/articles/{articleId}/users/{userId}")
    public ArticleLikeResponse read(@PathVariable("articleId") Long articleId,
                                    @PathVariable("userId") Long userId){
        return articleLikeService.read(articleId, userId);
    }

    @GetMapping("/v1/article-likes/articles/{articleId}/count")
    public Long readCount(@PathVariable("articleId") Long articleId){
        return articleLikeService.count(articleId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-1")
    public void likePessimisticV1(@PathVariable("articleId") Long articleId,
                     @PathVariable("userId") Long userId){
        articleLikeService.likePessimisticLockV1(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-1")
    public void unlikePessimisticV1(@PathVariable("articleId") Long articleId,
                       @PathVariable("userId") Long userId){
        articleLikeService.unlikePessimisticLockV1(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-2")
    public void likePessimisticV2(@PathVariable("articleId") Long articleId,
                     @PathVariable("userId") Long userId){
        articleLikeService.likePessimisticLockV2(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock-2")
    public void unlikePessimisticV2(@PathVariable("articleId") Long articleId,
                       @PathVariable("userId") Long userId){
        articleLikeService.unlikePessimisticLockV2(articleId, userId);
    }

    @PostMapping("/v1/article-likes/articles/{articleId}/users/{userId}/optimistic-lock")
    public void likeOptimisticV1(@PathVariable("articleId") Long articleId,
                     @PathVariable("userId") Long userId){
        articleLikeService.likeOptimisticLock(articleId, userId);
    }

    @DeleteMapping("/v1/article-likes/articles/{articleId}/users/{userId}/pessimistic-lock")
    public void unlikeOptimisticV1(@PathVariable("articleId") Long articleId,
                       @PathVariable("userId") Long userId){
        articleLikeService.unlikeOptimisticLock(articleId, userId);
    }



}
