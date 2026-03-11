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

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link PetType}
 *
 * @author Andrew Huebner
 */
class PetTypeTest {

	@Test
	void shouldCreatePetType() {
		PetType petType = new PetType();
		assertThat(petType).isNotNull();
	}

	@Test
	void shouldSetName() {
		PetType petType = new PetType();
		petType.setName("Dog");
		assertThat(petType.getName()).isEqualTo("Dog");
	}

	@Test
	void shouldUpdateName() {
		PetType petType = new PetType();
		petType.setName("Cat");
		assertThat(petType.getName()).isEqualTo("Cat");
		petType.setName("Bird");
		assertThat(petType.getName()).isEqualTo("Bird");
	}

	@Test
	void shouldSetNameToEmpty() {
		PetType petType = new PetType();
		petType.setName("Hamster");
		petType.setName("");
		assertThat(petType.getName()).isEmpty();
	}

	@Test
	void shouldHaveToString() {
		PetType petType = new PetType();
		petType.setName("Rabbit");
		assertThat(petType.toString()).isNotNull().contains("Rabbit");
	}

}