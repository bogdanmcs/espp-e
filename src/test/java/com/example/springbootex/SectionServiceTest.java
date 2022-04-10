package com.example.springbootex;

import com.example.springbootex.entity.Section;
import com.example.springbootex.exception.NotFoundException;
import com.example.springbootex.repository.SectionRepository;
import com.example.springbootex.service.SectionService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsNot.not;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class SectionServiceTest {

    @Mock
    private SectionRepository sectionRepository;

    @InjectMocks
    private SectionService sectionService;

    @Test
    @DisplayName("Given there are no available sections, when retrieving a section by id, then a not found exception is thrown")
    void givenThereAreNoAvailableSections_whenRetrievingASectionById_thenANotFoundExceptionIsThrown() {
        assertThrows(NotFoundException.class, () -> sectionService.get(13));
    }

    @Test
    @DisplayName("Given there are available sections, when retrieving the sections, then the correct sections are retrieved")
    void givenThereAreAvailableSections_whenRetrievingTheSections_thenTheCorrectSectionsAreRetrieved() {
        final List<Section> sections = Arrays.asList(
                new Section(1, "S1"),
                new Section(6, "S2")
        );

        when(sectionRepository.findAll()).thenReturn(sections);

        final List<Section> sectionsResulted = sectionRepository.findAll();

        assertNotNull(sectionsResulted, "The resulted sections are null");
        assertEquals(sectionsResulted.size(), sectionsResulted.size(), "Not all sections were retrieved");
        assertTrue(sectionsResulted.iterator().hasNext(), "There is just a single section");
        sectionsResulted.forEach(section -> {
            assertThat(section.getId(), not(0));
            assertThat("The name must not be null or empty", StringUtils.hasLength(section.getName()));
        });
    }
}
