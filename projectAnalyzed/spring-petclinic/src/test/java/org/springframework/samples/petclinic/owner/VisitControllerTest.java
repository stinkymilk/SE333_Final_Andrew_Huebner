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

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

/**
 * Test class for {@link VisitController}
 *
 * @author Andrew Huebner
 */
@WebMvcTest(VisitController.class)
class VisitControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private OwnerRepository ownerRepository;

	@Test
	void testInitNewVisitForm() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);
		pet.setId(1);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/pets/1/visits/new"))
			.andExpect(status().isOk())
			.andExpect(model().attributeExists("pet"))
			.andExpect(model().attributeExists("owner"))
			.andExpect(model().attributeExists("visit"))
			.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

	@Test
	void testProcessNewVisitFormSuccess() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);
		pet.setId(1);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc
			.perform(post("/owners/1/pets/1/visits/new").param("date", "2023-01-01").param("description", "Test visit"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"))
			.andExpect(flash().attribute("message", "Your visit has been booked"));
	}

	@Test
	void testProcessNewVisitFormHasErrors() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);
		pet.setId(1);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(post("/owners/1/pets/1/visits/new").param("date", "").param("description", ""))
			.andExpect(status().isOk())
			.andExpect(model().attributeHasErrors("visit"))
			.andExpect(view().name("pets/createOrUpdateVisitForm"));
	}

}