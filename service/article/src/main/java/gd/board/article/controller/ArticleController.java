package gd.board.article.controller;

import gd.board.article.service.ArticleService;
import gd.board.article.service.request.ArticleCreateRequest;
import gd.board.article.service.request.ArticleUpdateRequest;
import gd.board.article.service.response.ArticlePageResponse;
import gd.board.article.service.response.ArticleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class ArticleController {

    private final ArticleService articleService;

    @PostMapping("/v1/articles")
    public ArticleResponse save(@RequestBody ArticleCreateRequest request){
        return articleService.create(request);
    }

    @GetMapping("/v1/articles")
    public ArticlePageResponse readAll(
            @RequestParam("boardId") Long boardId,
            @RequestParam("page") Long page,
            @RequestParam("pageSize") Long pageSize
    ){
        return articleService.readAll(boardId, page, pageSize);
    }

    @PutMapping("/v1/articles/{articleId}")
    public ArticleResponse update(@PathVariable Long articleId,
                                  @RequestBody ArticleUpdateRequest request){
        return articleService.update(articleId, request);
    }

    @GetMapping("/v1/articles/{articleId}")
    public ArticleResponse read(@PathVariable Long articleId){
        return articleService.read(articleId);
    }

    @DeleteMapping("/v1/articles/{articleId}")
    public void delete(@PathVariable Long articleId){
        articleService.delete(articleId);
    }
}
