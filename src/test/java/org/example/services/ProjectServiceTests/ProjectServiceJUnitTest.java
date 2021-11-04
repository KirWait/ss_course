package org.example.services.ProjectServiceTests;


import javassist.NotFoundException;
import org.example.entities.ProjectEntity;
import org.example.entities.UserEntity;
import org.example.entities.enums.Status;
import org.example.services.ProjectService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProjectServiceJUnitTest {

    private static final Long CUSTOMER_ID = 1L;

    @Autowired
    private ProjectService projectService;

    private static final String CREATION_NAME = "REGISTER";

    @Test(expected = NotFoundException.class)
    public void saveAndDeleteShouldSaveAndDeleteProject() throws NotFoundException {

        projectService.save(new ProjectEntity(CREATION_NAME,CUSTOMER_ID));

        ProjectEntity savedProject = projectService.findByProjectName(CREATION_NAME);
        assertThat(savedProject).isNotNull();
        assertThat(savedProject.getProjectName()).isEqualTo(CREATION_NAME);

        projectService.delete(savedProject.getProjectId());

        ProjectEntity projectAfterDeletion = projectService.findByProjectName(CREATION_NAME);

    }
}
