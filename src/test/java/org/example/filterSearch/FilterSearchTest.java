package org.example.filterSearch;


import javassist.NotFoundException;
import org.example.repository.TaskRepository;
import org.example.service.DateFormatter;
import org.example.service.TaskService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Calendar;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterSearchTest {

    @Autowired
    TaskService taskService;

//    @MockBean
//    TaskRepository taskRepository;

    @Before
    public void setUp(){


    }

    @Test
    public void filterShouldFilterTaskList() {

     //   System.out.println(DateFormatter.formatterWithTime.format(Calendar.getInstance().getTime()));
//        List<TaskEntity> result = taskRepository.findAll();
//        System.out.println("Before filtration : ");
//        result.forEach(System.out::println);
//
//        result = taskService.searchByFilter(new TaskRequestDto());
//
//        System.out.println("After filtration : ");
//        result.forEach(System.out::println);

    }

}
