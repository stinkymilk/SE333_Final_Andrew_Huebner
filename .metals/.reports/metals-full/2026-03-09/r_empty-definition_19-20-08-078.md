error id: file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTest.java:org/springframework/boot/test/mock/mockito/MockBean#
file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTest.java
empty definition using pc, found symbol in pc: org/springframework/boot/test/mock/mockito/MockBean#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 1164
uri: file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTest.java
text:
```scala
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.@@MockBean;
import org.springframework.test.web.servlet.MockMvc;

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

}
```


#### Short summary: 

empty definition using pc, found symbol in pc: org/springframework/boot/test/mock/mockito/MockBean#