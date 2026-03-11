/*
 * Copyright 2012-2025 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springframework.samples.petclinic.owner;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

/**
 * Test class for {@link OwnerController}
 *
 * @author Andrew Huebner
 */
@WebMvcTest(OwnerController.class)
class OwnerControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository ownerRepository;

	@MockBean
	private PetTypeRepository petTypeRepository;

	private OwnerController ownerController;

	private Owner testOwner;

	@BeforeEach
	void setUp() {
		ownerController = new OwnerController(ownerRepository);
		testOwner = new Owner();
		testOwner.setId(1);
		testOwner.setFirstName("John");
		testOwner.setLastName("Doe");
	}

	@Test
	void testInitCreationForm() throws Exception {
		mockMvc.perform(get("/owners/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/createOrUpdateOwnerForm"));
	}

	@Test
	void testShowOwner() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1"))
			.andExpect(status().isOk())
			.andExpect(view().name("owners/ownerDetails"))
			.andExpect(model().attributeExists("owner"));
	}

	@Test
	void testInitFindForm() throws Exception {
		mockMvc.perform(get("/owners/find")).andExpect(status().isOk()).andExpect(view().name("owners/findOwners"));
	}

	@Test
	void testInitCreationFormDirect() {
		// Direct unit test without MockMvc
		String view = ownerController.initCreationForm();
		assertThat(view).isEqualTo("owners/createOrUpdateOwnerForm");
	}

	@Test
	void testInitFindFormDirect() {
		// Direct unit test without MockMvc
		String view = ownerController.initFindForm();
		assertThat(view).isEqualTo("owners/findOwners");
	}

	@Test
	void testProcessCreationFormSuccess() {
		// Direct unit test
		Owner newOwner = new Owner();
		newOwner.setFirstName("Jane");
		newOwner.setLastName("Smith");

		BindingResult result = new BindException(newOwner, "owner");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		when(ownerRepository.save(any(Owner.class))).thenAnswer(invocation -> {
			Owner saved = invocation.getArgument(0);
			saved.setId(10);
			return saved;
		});

		String viewName = ownerController.processCreationForm(newOwner, result, redirectAttributes);

		assertThat(viewName).contains("redirect:/owners/");
		assertThat(result.hasErrors()).isFalse();
	}

	@Test
	void testInitUpdateOwnerForm() {
		String view = ownerController.initUpdateOwnerForm();
		assertThat(view).isEqualTo("owners/createOrUpdateOwnerForm");
	}

	@Test
	void testFindOwnerFound() {
		when(ownerRepository.findById(1)).thenReturn(Optional.of(testOwner));

		Owner found = ownerController.findOwner(1);

		assertThat(found).isNotNull();
		assertThat(found.getId()).isEqualTo(1);
		assertThat(found.getFirstName()).isEqualTo("John");
	}

	@Test
	void testFindOwnerNotFound() {
		when(ownerRepository.findById(999)).thenReturn(Optional.empty());

		assertThatThrownBy(() -> ownerController.findOwner(999)).isInstanceOf(IllegalArgumentException.class)
			.hasMessageContaining("Owner not found");
	}

	@Test
	void testFindOwnerNull() {
		Owner newOwner = ownerController.findOwner(null);
		assertThat(newOwner).isNotNull();
		assertThat(newOwner.isNew()).isTrue();
	}

}