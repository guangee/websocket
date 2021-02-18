package com.coding.mapper;

import com.coding.PushApp;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@ActiveProfiles("dev")
@Component
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {PushApp.class})
public class PersonRepositoryTest {


    @Test
    public void demo(){

    }

}
