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
 * Test class for {@link Vet}
 *
 * @author Andrew Huebner
 */
class VetTest {

	@Test
	void shouldCreateVet() {
		Vet vet = new Vet();
		assertThat(vet).isNotNull();
	}

	@Test
	void shouldHaveZeroSpecialtiesInitially() {
		Vet vet = new Vet();
		assertThat(vet.getNrOfSpecialties()).isEqualTo(0);
	}

	@Test
	void shouldAddSpecialty() {
		Vet vet = new Vet();
		Specialty specialty = new Specialty();
		specialty.setName("Surgery");
		vet.addSpecialty(specialty);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(1);
		assertThat(vet.getSpecialties()).contains(specialty);
	}

	@Test
	void shouldAddMultipleSpecialties() {
		Vet vet = new Vet();
		Specialty specialty1 = new Specialty();
		specialty1.setName("Surgery");
		Specialty specialty2 = new Specialty();
		specialty2.setName("Radiology");
		vet.addSpecialty(specialty1);
		vet.addSpecialty(specialty2);
		assertThat(vet.getNrOfSpecialties()).isEqualTo(2);
		assertThat(vet.getSpecialties()).contains(specialty1, specialty2);
	}

	@Test
	void shouldSetFirstAndLastName() {
		Vet vet = new Vet();
		vet.setFirstName("James");
		vet.setLastName("Carter");
		assertThat(vet.getFirstName()).isEqualTo("James");
		assertThat(vet.getLastName()).isEqualTo("Carter");
	}

}