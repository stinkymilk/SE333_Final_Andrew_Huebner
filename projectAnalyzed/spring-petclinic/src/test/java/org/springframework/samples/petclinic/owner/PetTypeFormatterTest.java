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
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

/**
 * Test class for {@link PetTypeFormatter}
 *
 * @author AI Testing Agent
 */
@ExtendWith(MockitoExtension.class)
class PetTypeFormatterTest {

	@Mock
	private PetTypeRepository petTypeRepository;

	private PetTypeFormatter formatter;

	private PetType dogType;

	private PetType catType;

	private List<PetType> petTypes;

	@BeforeEach
	void setup() {
		// Arrange
		formatter = new PetTypeFormatter(petTypeRepository);

		dogType = new PetType();
		dogType.setId(1);
		dogType.setName("dog");

		catType = new PetType();
		catType.setId(2);
		catType.setName("cat");

		petTypes = Arrays.asList(dogType, catType);
	}

	@Test
	void testPrintWithValidPetType() {
		// Act
		String result = formatter.print(dogType, Locale.ENGLISH);

		// Assert
		assertThat(result).isEqualTo("dog");
	}

	@Test
	void testPrintWithNullName() {
		// Arrange
		PetType petTypeWithNullName = new PetType();
		petTypeWithNullName.setId(3);
		// name is null by default

		// Act
		String result = formatter.print(petTypeWithNullName, Locale.ENGLISH);

		// Assert
		assertThat(result).isEqualTo("<null>");
	}

	@Test
	void testParseWithValidType() throws ParseException {
		// Arrange
		when(petTypeRepository.findPetTypes()).thenReturn(petTypes);

		// Act
		PetType result = formatter.parse("dog", Locale.ENGLISH);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("dog");
		assertThat(result.getId()).isEqualTo(1);
	}

	@Test
	void testParseWithAnotherValidType() throws ParseException {
		// Arrange
		when(petTypeRepository.findPetTypes()).thenReturn(petTypes);

		// Act
		PetType result = formatter.parse("cat", Locale.ENGLISH);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo("cat");
		assertThat(result.getId()).isEqualTo(2);
	}

	@Test
	void testParseWithInvalidType() {
		// Arrange
		when(petTypeRepository.findPetTypes()).thenReturn(petTypes);

		// Act & Assert
		assertThatThrownBy(() -> formatter.parse("bird", Locale.ENGLISH)).isInstanceOf(ParseException.class)
			.hasMessageContaining("type not found: bird");
	}

	@Test
	void testParseWithEmptyPetTypesList() {
		// Arrange
		when(petTypeRepository.findPetTypes()).thenReturn(new ArrayList<>());

		// Act & Assert
		assertThatThrownBy(() -> formatter.parse("dog", Locale.ENGLISH)).isInstanceOf(ParseException.class)
			.hasMessageContaining("type not found: dog");
	}

	@Test
	void testParseWithNullText() {
		// Arrange
		when(petTypeRepository.findPetTypes()).thenReturn(petTypes);

		// Act & Assert
		assertThatThrownBy(() -> formatter.parse(null, Locale.ENGLISH)).isInstanceOf(ParseException.class);
	}

}
