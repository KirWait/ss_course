package org.example.service.TaskServiceTests;

import org.example.service.TaskService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TaskServiceJUnitTest {

    @Autowired
    private TaskService taskService;
//
//    @Test
//    public void saveAndDeleteShouldSaveAndDeleteTask() {
//        taskService.save(new TaskEntity(CREATION_NAME,CUSTOMER_ID));
//
//        TaskEntity savedProject = taskService.findByProjectName(CREATION_NAME);
//        assertThat(savedProject).isNotNull();
//        assertThat(savedProject.getName()).isEqualTo(CREATION_NAME);
//
//        taskService.delete(savedProject.getProjectId());
//
//        TaskEntity projectAfterDeletion = taskService.findByProjectName(CREATION_NAME);
//
//    }
//    @Test
//    public void test(){
//        List<TaskEntity> list = List.of(new TaskEntity(99999L, "NAME", "DESC"), new TaskEntity(99999L, "KIRNAME", "DESC"));
//        list.stream().filter(i -> i.getName().contains("RN")).forEach(System.out::println);
//    }
}