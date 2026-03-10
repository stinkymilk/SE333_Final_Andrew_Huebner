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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

/**
 * Unit test suite for PetController to improve code coverage. Directly tests controller
 * methods without MockMvc complexity.
 */
class PetControllerTest {

	private PetController petController;

	private OwnerRepository ownerRepository;

	private PetTypeRepository petTypeRepository;

	private Owner owner;

	private PetType petType;

	@BeforeEach
	void setUp() {
		ownerRepository = mock(OwnerRepository.class);
		petTypeRepository = mock(PetTypeRepository.class);
		petController = new PetController(ownerRepository, petTypeRepository);

		owner = new Owner();
		owner.setId(1);
		owner.setFirstName("John");
		owner.setLastName("Doe");

		petType = new PetType();
		petType.setId(1);
		petType.setName("dog");

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
		when(petTypeRepository.findPetTypes()).thenReturn(List.of(petType));
	}

	@Test
	void testPopulatePetTypes() {
		// Act
		Collection<PetType> types = petController.populatePetTypes();

		// Assert
		assertThat(types).hasSize(1);
		assertThat(types).contains(petType);
	}

	@Test
	void testFindOwner() {
		// Act
		Owner foundOwner = petController.findOwner(1);

		// Assert
		assertThat(foundOwner).isNotNull();
		assertThat(foundOwner.getId()).isEqualTo(1);
		assertThat(foundOwner.getFirstName()).isEqualTo("John");
	}

	@Test
	void testFindPetWhenPetIdIsNull() {
		// Act
		Pet pet = petController.findPet(1, null);

		// Assert
		assertThat(pet).isNotNull();
		assertThat(pet.isNew()).isTrue();
	}

	@Test
	void testInitCreationForm() {
		// Arrange
		ModelMap model = new ModelMap();

		// Act
		String viewName = petController.initCreationForm(owner, model);

		// Assert
		assertThat(viewName).isEqualTo("pets/createOrUpdatePetForm");
		assertThat(owner.getPets()).hasSize(1);
	}

	@Test
	void testProcessCreationFormSuccess() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Fluffy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(petType);

		BindingResult result = new BindException(pet, "pet");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

		// Act
		String viewName = petController.processCreationForm(owner, pet, result, redirectAttributes);

		// Assert
		assertThat(viewName).isEqualTo("redirect:/owners/{ownerId}");
		assertThat(result.hasErrors()).isFalse();
	}

	@Test
	void testProcessCreationFormDuplicateName() {
		// Arrange
		Pet existingPet = new Pet();
		existingPet.setId(100); // Set ID so it's not considered "new"
		existingPet.setName("Fluffy");
		existingPet.setBirthDate(LocalDate.of(2020, 1, 1));
		existingPet.setType(petType);
		// Directly add to collection since addPet only works for new pets
		owner.getPets().add(existingPet);

		Pet newPet = new Pet();
		newPet.setName("Fluffy");
		newPet.setBirthDate(LocalDate.of(2021, 1, 1));
		newPet.setType(petType);

		BindingResult result = new BindException(newPet, "pet");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		// Act
		String viewName = petController.processCreationForm(owner, newPet, result, redirectAttributes);

		// Assert
		assertThat(viewName).isEqualTo("pets/createOrUpdatePetForm");
		assertThat(result.hasFieldErrors("name")).isTrue();
	}

	@Test
	void testProcessCreationFormFutureBirthDate() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Fluffy");
		pet.setBirthDate(LocalDate.now().plusDays(1));
		pet.setType(petType);

		BindingResult result = new BindException(pet, "pet");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		// Act
		String viewName = petController.processCreationForm(owner, pet, result, redirectAttributes);

		// Assert
		assertThat(viewName).isEqualTo("pets/createOrUpdatePetForm");
		assertThat(result.hasFieldErrors("birthDate")).isTrue();
	}

	@Test
	void testInitUpdateForm() {
		// Act
		String viewName = petController.initUpdateForm();

		// Assert
		assertThat(viewName).isEqualTo("pets/createOrUpdatePetForm");
	}

	@Test
	void testProcessUpdateFormSuccess() {
		// Arrange
		Pet existingPet = new Pet();
		existingPet.setId(1);
		existingPet.setName("Fluffy");
		existingPet.setBirthDate(LocalDate.of(2020, 1, 1));
		existingPet.setType(petType);
		owner.addPet(existingPet);

		Pet updatedPet = new Pet();
		updatedPet.setId(1);
		updatedPet.setName("Fluffy Updated");
		updatedPet.setBirthDate(LocalDate.of(2020, 1, 1));
		updatedPet.setType(petType);

		BindingResult result = new BindException(updatedPet, "pet");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

		// Act
		String viewName = petController.processUpdateForm(owner, updatedPet, result, redirectAttributes);

		// Assert
		assertThat(viewName).isEqualTo("redirect:/owners/{ownerId}");
		assertThat(result.hasErrors()).isFalse();
	}

	@Test
	void testProcessUpdateFormDuplicateName() {
		// Arrange
		Pet pet1 = new Pet();
		pet1.setId(1);
		pet1.setName("Fluffy");
		pet1.setBirthDate(LocalDate.of(2020, 1, 1));
		pet1.setType(petType);
		// Directly add to collection since addPet only works for new pets
		owner.getPets().add(pet1);

		Pet pet2 = new Pet();
		pet2.setId(2);
		pet2.setName("Buddy");
		pet2.setBirthDate(LocalDate.of(2021, 1, 1));
		pet2.setType(petType);
		owner.getPets().add(pet2);

		Pet updatedPet2 = new Pet();
		updatedPet2.setId(2);
		updatedPet2.setName("Fluffy"); // Trying to use pet1's name
		updatedPet2.setBirthDate(LocalDate.of(2021, 1, 1));
		updatedPet2.setType(petType);

		BindingResult result = new BindException(updatedPet2, "pet");
		RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

		// Act
		String viewName = petController.processUpdateForm(owner, updatedPet2, result, redirectAttributes);

		// Assert
		assertThat(viewName).isEqualTo("pets/createOrUpdatePetForm");
		assertThat(result.hasFieldErrors("name")).isTrue();
	}

}
