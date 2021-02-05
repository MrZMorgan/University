package ua.com.foxminded.university.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ua.com.foxminded.university.config.TestConfig;
import ua.com.foxminded.university.models.Group;
import ua.com.foxminded.university.services.GroupsService;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
class GroupsControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private GroupsService groupsService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    void testShowAllGroups() throws Exception {
        String groupsControllerRequestMapping = "/groups";
        String view = "groups/all-groups";

        this.mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(view().name(view));
    }

    @Test
    void deleteGroup() throws Exception {
        String groupsControllerRequestMapping = "/groups/1";
        String view = "redirect:/groups";

        mockMvc.perform(delete(groupsControllerRequestMapping))
                .andExpect(view().name(view))
                .andExpect(status().is3xxRedirection())
        ;
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

        String expectedModelAttributeName = "group";
        Group expectedGroup = groupsService.readOneRecordFromTable(2);

        mockMvc.perform(get(groupsControllerRequestMapping))
                .andExpect(status().isOk())
                .andExpect(model().attribute(expectedModelAttributeName, expectedGroup))
                .andExpect(view().name(view));
    }
}