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

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

/**
 * Test class for {@link PetValidator}
 *
 * @author AI Testing Agent
 */
class PetValidatorTest {

	private PetValidator validator;

	@BeforeEach
	void setup() {
		validator = new PetValidator();
	}

	@Test
	void testSupportsCorrectClass() {
		// Act & Assert
		assertThat(validator.supports(Pet.class)).isTrue();
	}

	@Test
	void testDoesNotSupportOtherClass() {
		// Act & Assert
		assertThat(validator.supports(Owner.class)).isFalse();
	}

	@Test
	void testValidateValidPet() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Fluffy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);
		pet.setId(1); // Existing pet

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	void testValidateNewPetWithAllFieldsValid() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Buddy");
		pet.setBirthDate(LocalDate.of(2021, 5, 15));
		PetType petType = new PetType();
		petType.setName("cat");
		pet.setType(petType);
		// id is null, so it's a new pet

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isFalse();
	}

	@Test
	void testValidatePetWithNullName() {
		// Arrange
		Pet pet = new Pet();
		pet.setName(null);
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
	}

	@Test
	void testValidatePetWithEmptyName() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
	}

	@Test
	void testValidatePetWithWhitespaceOnlyName() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("   ");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("name").getCode()).isEqualTo("required");
	}

	@Test
	void testValidateNewPetWithNullType() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Fluffy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(null);
		// id is null, so it's a new pet

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("type")).isNotNull();
		assertThat(errors.getFieldError("type").getCode()).isEqualTo("required");
	}

	@Test
	void testValidateExistingPetWithNullType() {
		// Arrange
		Pet pet = new Pet();
		pet.setId(1); // Existing pet
		pet.setName("Fluffy");
		pet.setBirthDate(LocalDate.of(2020, 1, 1));
		pet.setType(null);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert: Existing pets can have null type
		assertThat(errors.getFieldError("type")).isNull();
	}

	@Test
	void testValidatePetWithNullBirthDate() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Fluffy");
		pet.setBirthDate(null);
		PetType petType = new PetType();
		petType.setName("dog");
		pet.setType(petType);

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getFieldError("birthDate")).isNotNull();
		assertThat(errors.getFieldError("birthDate").getCode()).isEqualTo("required");
	}

	@Test
	void testValidatePetWithMultipleErrors() {
		// Arrange
		Pet pet = new Pet();
		pet.setName(""); // Invalid
		pet.setBirthDate(null); // Invalid
		pet.setType(null); // Invalid for new pet
		// id is null, so it's a new pet

		Errors errors = new BeanPropertyBindingResult(pet, "pet");

		// Act
		validator.validate(pet, errors);

		// Assert
		assertThat(errors.hasErrors()).isTrue();
		assertThat(errors.getErrorCount()).isEqualTo(3);
		assertThat(errors.getFieldError("name")).isNotNull();
		assertThat(errors.getFieldError("birthDate")).isNotNull();
		assertThat(errors.getFieldError("type")).isNotNull();
	}

}
