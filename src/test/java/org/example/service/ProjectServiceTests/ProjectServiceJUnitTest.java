package org.example.service.ProjectServiceTests;


import javassist.NotFoundException;
import org.example.entity.ProjectEntity;
import org.example.service.ProjectService;
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
        assertThat(savedProject.getName()).isEqualTo(CREATION_NAME);

        projectService.delete(savedProject.getId());

        ProjectEntity projectAfterDeletion = projectService.findByProjectName(CREATION_NAME);

    }
}
