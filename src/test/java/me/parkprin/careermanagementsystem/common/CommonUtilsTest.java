package me.parkprin.careermanagementsystem.common;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CommonUtilsTest {

    @Autowired
    CommonUtils commonUtils;

    @Test
    public void 글자타입의_숫자형_변수인지_확인하는_기능테스트(){
        assertThat(commonUtils.isNumberic("A")).isFalse();
        assertThat(commonUtils.isNumberic("22")).isTrue();
    }
}
