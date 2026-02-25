package gd.board.view.service;

import gd.board.view.repository.ArticleViewCountRepository;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArticleViewService {

    private final ArticleViewCountRepository articleViewCountRepository;


    public Long increase(Long articleId, Long userId){
        return articleViewCountRepository.increase(articleId);
    }

    public Long count(Long articleId){
        return articleViewCountRepository.read(articleId);
    }
}
