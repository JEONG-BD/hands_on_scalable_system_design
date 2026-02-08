package gd.board.article.service;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PageCalculatorTest {

    @Test
    public void calculatePageLimitTest(){
        //given
        calculatePageLimitTest(1L, 30L, 10L, 301L);
        //when

        //then
    }

    void calculatePageLimitTest(Long page, Long pageSize, Long movablePageCount, Long expected){
        Long result = PageCalculator.calculatePageLimit(page, pageSize, movablePageCount);
        Assertions.assertThat(result).isEqualTo(expected);

    }

}