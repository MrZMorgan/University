package ua.com.foxminded.university.rest;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import ua.com.foxminded.university.entities.Course;
import ua.com.foxminded.university.entities.Group;
import ua.com.foxminded.university.entities.Student;
import ua.com.foxminded.university.services.CoursesServiceImpl;
import ua.com.foxminded.university.services.interfaces.GroupsService;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GroupsRESTController.class)
@ActiveProfiles("test")
class GroupsRESTControllerTest {

    @MockBean
    private GroupsService groupsServiceMock;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldShowAllGroups() throws Exception {
        List<Group> expectedGroupsList = expectedGroups();
        when(groupsServiceMock.readTable()).thenReturn(expectedGroupsList);
        String URL = "http://localhost:8080/api/groups";

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(expectedGroupsList.get(0).getId())))
                .andExpect(jsonPath("$[0].name", is(expectedGroupsList.get(0).getName())))
                .andExpect(jsonPath("$[1].id", is(expectedGroupsList.get(1).getId())))
                .andExpect(jsonPath("$[1].name", is(expectedGroupsList.get(1).getName())));
    }

    @Test
    void shouldGetGroup() throws Exception {
        int groupId = 1;
        Group expectedGroup = expectedFirstGroup();
        String URL = String.format("http://localhost:8080/api/groups/%d", groupId);

        when(groupsServiceMock.readOneRecordFromTable(groupId)).thenReturn(expectedGroup);

        mockMvc.perform(get(URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(expectedGroup.getId())))
                .andExpect(jsonPath("$.name", is(expectedGroup.getName())));
    }

    @Test
    void shouldDeleteGroup() throws Exception {
        int groupId = 1;
        String URL = String.format("http://localhost:8080/api/groups/%d", groupId);
        String message = String.format("Group with id %d was deleted", groupId);

        mockMvc.perform(delete(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(containsString(message)));
    }

    @Test
    void shouldCreateGroup() throws Exception {
        String URL = "http://localhost:8080/api/groups";

        String inputJson = "{" +
                "\"id\":3," +
                "\"name\":\"Test group\"," +
                "\"students\":[]," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    @Test
    void shouldUpdateGroup() throws Exception {
        String URL = "http://localhost:8080/api/groups";

        String inputJson = "{" +
                "\"id\":1," +
                "\"name\":\"Test group\"," +
                "\"students\":[]," +
                "\"courses\":[]" +
                "}";

        MvcResult mvcResult = mockMvc.perform(put(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(inputJson)).andReturn();

        assertEquals(HttpStatus.OK.value(), mvcResult.getResponse().getStatus());
        assertEquals(inputJson, mvcResult.getResponse().getContentAsString());
    }

    List<Group> expectedGroups() {
        List<Group> groups = new ArrayList<>();
        groups.add(expectedFirstGroup());
        groups.add(expectedSecondGroup());
        return groups;
    }

    Group expectedFirstGroup() {
        return new Group("GS-10-1");
    }

    Group expectedSecondGroup() {
        return new Group("ERB-11-2");
    }
}