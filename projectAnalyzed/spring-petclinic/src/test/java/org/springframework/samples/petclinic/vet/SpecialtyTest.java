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
package org.springframework.samples.petclinic.vet;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Test class for {@link Specialty}
 *
 * @author Andrew Huebner
 */
class SpecialtyTest {

	@Test
	void shouldCreateSpecialty() {
		Specialty specialty = new Specialty();
		assertThat(specialty).isNotNull();
	}

	@Test
	void shouldSetName() {
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");
		assertThat(specialty.getName()).isEqualTo("Surgery");
	}

	@Test
	void shouldUpdateName() {
		Specialty specialty = new Specialty();
		specialty.setName("Dentistry");
		assertThat(specialty.getName()).isEqualTo("Dentistry");
		specialty.setName("Radiology");
		assertThat(specialty.getName()).isEqualTo("Radiology");
	}

	@Test
	void shouldSetNameToEmpty() {
		Specialty specialty = new Specialty();
		specialty.setName("Internal Medicine");
		specialty.setName("");
		assertThat(specialty.getName()).isEmpty();
	}

	@Test
	void shouldSetSpecialtyId() {
		Specialty specialty = new Specialty();
		specialty.setId(5);
		assertThat(specialty.getId()).isEqualTo(5);
	}

}
