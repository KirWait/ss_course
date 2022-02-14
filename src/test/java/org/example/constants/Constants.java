package org.example.constants;

import lombok.experimental.FieldDefaults;
import org.example.entity.ProjectEntity;
import org.example.entity.ReleaseEntity;
import org.example.entity.TaskEntity;
import org.example.entity.UserEntity;
import org.example.enumeration.Active;
import org.example.enumeration.Roles;
import org.example.enumeration.Status;
import org.example.enumeration.Type;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import static org.example.service.MyDateFormat.formatterWithTime;
import static org.example.service.MyDateFormat.formatterWithoutTime;

@FieldDefaults(makeFinal = true)
public class Constants {
    //USER DETAILS
    public static Long USER_ID = 1L;
    public static String USER_USERNAME = "USER_USERNAME";
    public static String USER_PASSWORD = "PASSWORD";
    public static Roles USER_ROLES = Roles.ROLE_ADMIN;
    public static Active USER_ACTIVE = Active.ACTIVE;
    //CUSTOMER DETAILS
    public static Long CUSTOMER_ID = 2L;
    public static String CUSTOMER_USERNAME = "CUSTOMER_USERNAME";
    public static String CUSTOMER_PASSWORD = "PASSWORD";
    public static Roles CUSTOMER_ROLES = Roles.ROLE_USER;
    public static Active CUSTOMER_ACTIVE = Active.ACTIVE;
    //PROJECT DETAILS
    public static Long PROJECT_ID = 1L;
    public static String PROJECT_NAME = "PROJECT_NAME";
    public static Status PROJECT_STATUS = Status.BACKLOG;
    public static boolean PROJECT_PAID = true;
    public static Long PROJECT_PRICE = 100L;
    //RELEASE DETAILS
    public static Long RELEASE_ID = 1L;
    public static Date RELEASE_CREATION_TIME = new GregorianCalendar().getTime();
    public static Date RELEASE_END_TIME;

    static {
        try {
            RELEASE_END_TIME = formatterWithoutTime.parse("2022-12-31");
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static String RELEASE_VERSION = "1.0.0";
    //TASK DETAILS
    public static Long TASK_ID = 1L;
    public static Status TASK_STATUS = Status.BACKLOG;
    public static String TASK_NAME = "TASK_NAME";
    public static String TASK_DESCRIPTION = "DESCRIPTION";
    public static Type TASK_TYPE = Type.BUG;
    public static Date TASK_CREATION_TIME = new GregorianCalendar().getTime();
    //ENTITIES
    public static UserEntity USER = UserEntity.builder()
            .username(USER_USERNAME)
            .password(USER_PASSWORD)
            .active(USER_ACTIVE)
            .roles(USER_ROLES)
            .build();

    public static UserEntity CUSTOMER = UserEntity.builder()
            .username(CUSTOMER_USERNAME)
            .password(CUSTOMER_PASSWORD)
            .active(CUSTOMER_ACTIVE)
            .roles(CUSTOMER_ROLES)
            .build();

    public static ProjectEntity PROJECT = ProjectEntity.builder()
            .status(PROJECT_STATUS)
            .customer(CUSTOMER)
            .name(PROJECT_NAME)
            .price(PROJECT_PRICE)
            .isPaid(PROJECT_PAID)
            .build();

    public static ReleaseEntity RELEASE = ReleaseEntity.builder()
            .version(RELEASE_VERSION)
            .creationTime(RELEASE_CREATION_TIME)
            .endTime(RELEASE_END_TIME)
            .project(PROJECT)
            .build();

    public static TaskEntity TASK = TaskEntity.builder()
            .name(TASK_NAME)
            .author(USER)
            .creationTime(TASK_CREATION_TIME)
            .description(TASK_DESCRIPTION)
            .project(PROJECT)
            .release(RELEASE)
            .responsible(USER)
            .type(TASK_TYPE)
            .status(TASK_STATUS)
            .build();

}
