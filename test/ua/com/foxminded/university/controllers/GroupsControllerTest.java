package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.university.Application;
import ua.com.foxminded.university.H2JpaConfig;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = {Application.class, H2JpaConfig.class})
@ActiveProfiles("test")
class GroupsControllerTest {

    @Autowired
    private WebApplicationContext context;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    void testShowAllGroups() throws Exception {
        String groupsControllerRequestMapping = "/groups";
        String view = "groups/all-groups";
        String firstGroupName = "GS-10-1";
        String secondGroupName = "ERB-11-2";

        this.mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view))
                .andExpect(content().string(allOf(
                        containsString(firstGroupName),
                        containsString(secondGroupName))));
    }

    @Test
    void deleteGroup() throws Exception {
        String groupsControllerRequestMapping = "/groups/1";
        String view = "redirect:/groups";

        mockMvc.perform(delete(groupsControllerRequestMapping))
                .andExpect(view().name(view))
                .andExpect(status().is3xxRedirection());
    }

    @Test
    void createGroup() throws Exception {
        String groupsControllerRequestMapping = "/groups/new";
        String view = "groups/new";

        mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));

        groupsControllerRequestMapping = "/groups";
        view = "groups/all-groups";

        mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().is2xxSuccessful())
                .andExpect(view().name(view));
    }

    @Test
    void editGroup() throws Exception {
        String groupsControllerRequestMapping = "/groups/2/edit";
        String view = "groups/edit";

        mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }
}