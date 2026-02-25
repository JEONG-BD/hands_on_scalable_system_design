package gd.board.like.api;

import gd.board.like.service.response.ArticleLikeResponse;
import org.junit.jupiter.api.Test;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestClient;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LikeApiTest {
    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    public void likeAndUnLikeTest(){
        //given
        Long articleId = 9999L;
        like(articleId, 1L, "pessimistic-lock-1");
        like(articleId, 2L, "pessimistic-lock-1");
        like(articleId, 3L, "pessimistic-lock-1");

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

    void like(Long articleId, Long userId, String lockType){
        restClient.post()
                .uri("/v1/article-likes/articles/{articleId}/users/{userId}/"+ lockType, articleId, userId)
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


    @Test
    public void likePerformanceTest() throws InterruptedException {

        ExecutorService executorService = Executors.newFixedThreadPool(100);
        likePerformanceTest(executorService, 1111L, "pessimistic-lock-1");
        likePerformanceTest(executorService, 1111L, "pessimistic-lock-2");
        likePerformanceTest(executorService, 1111L, "optimistic-lock");

    }

    void likePerformanceTest(ExecutorService executorService, Long articleId, String lockType) throws InterruptedException {

        CountDownLatch countDownLatch = new CountDownLatch(3000);
        System.out.println(lockType + "start");

        like(articleId, 1L, lockType);


        long startTime = System.nanoTime();
        long start = startTime;
        for (int i = 0; i < 3000; i++) {
            long userId = i + 2;
            executorService.submit(() -> {
                like(articleId, userId, lockType);
                countDownLatch.countDown();
            });
        }

        countDownLatch.await();

        long endTime = System.nanoTime();
        System.out.println("lockType = " + lockType + " time  = " + (endTime-startTime) / 1000000 + "ms" );
        System.out.println("lockType" + lockType + "end" );
    }





}
