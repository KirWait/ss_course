package org.example.services.TaskServiceTests;

import org.example.entities.ProjectEntity;
import org.example.entities.TaskEntity;
import org.example.entities.TaskVersionEntity;
import org.example.services.TaskService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

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
//        assertThat(savedProject.getProjectName()).isEqualTo(CREATION_NAME);
//
//        taskService.delete(savedProject.getProjectId());
//
//        TaskEntity projectAfterDeletion = taskService.findByProjectName(CREATION_NAME);
//
//    }
    @Test
    public void test(){
        List<TaskEntity> list = List.of(new TaskEntity(99999L, "NAME", "DESC"));
        System.out.println(list);
    }
}