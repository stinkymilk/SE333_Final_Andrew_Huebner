error id: file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/VisitControllerTest.java
file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/VisitControllerTest.java
### com.thoughtworks.qdox.parser.ParseException: syntax error @[185,3]

error in qdox parser
file content:
```java
offset: 5903
uri: file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/VisitControllerTest.java
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

import static org.mockito.Mockito.verify;
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
import static org.mockito.ArgumentMatchers.any;

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

	@Test
	void testInitNewVisitFormOwnerNotFound() throws Exception {
		when(ownerRepository.findById(999)).thenReturn(Optional.empty());

		mockMvc.perform(get("/owners/999/pets/1/visits/new"))
			.andExpect(status().isInternalServerError());
	}

	@Test
	void testInitNewVisitFormPetNotFound() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		// No pets in the owner

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/pets/999/visits/new"))
			.andExpect(status().isInternalServerError());
	}

	@Test
	void testProcessNewVisitFormSuccessWithValidDate() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);
		pet.setId(1);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
		when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

		mockMvc.perform(post("/owners/1/pets/1/visits/new")
			.param("date", LocalDate.now().toString())
			.param("description", "Vaccination"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"))
			.andExpect(flash().attribute("message", "Your visit has been booked"));

		verify(ownerRepository).save(any(Owner.class));
	}

	@Test
	void testProcessNewVisitFormSuccessMultiplePets() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet1 = new Pet();
		pet1.setName("Pet 1");
		pet1.setId(1);
		owner.addPet(pet1);

		Pet pet2 = new Pet();
		pet2.setName("Pet 2");
		pet2.setId(2);
		owner.addPet(pet2);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));
		when(ownerRepository.save(any(Owner.class))).thenReturn(owner);

		mockMvc.perform(post("/owners/1/pets/2/visits/new")
			.param("date", LocalDate.now().toString())
			.param("description", "Checkup"))
			.andExpect(status().is3xxRedirection())
			.andExpect(redirectedUrl("/owners/1"));

		verify(ownerRepository).save(any(Owner.class));
	}

	@Test
	void testInitNewVisitFormWithoutDescription() throws Exception {
		Owner owner = new Owner();
		owner.setId(1);
		Pet pet = new Pet();
		pet.setName("Test Pet");
		owner.addPet(pet);
		pet.setId(1);

		when(ownerRepository.findById(1)).thenReturn(Optional.of(owner));

		mockMvc.perform(get("/owners/1/pets/1/visits/new"))
			.andExpect(status().isOk())
			.andExpect(view().name("pets/createOrUpdateVisitForm"))
			.andExpect(model().attributeExists("visit"));
	}@@
```

```



#### Error stacktrace:

```
com.thoughtworks.qdox.parser.impl.Parser.yyerror(Parser.java:2025)
	com.thoughtworks.qdox.parser.impl.Parser.yyparse(Parser.java:2147)
	com.thoughtworks.qdox.parser.impl.Parser.parse(Parser.java:2006)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:232)
	com.thoughtworks.qdox.library.SourceLibrary.parse(SourceLibrary.java:190)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:94)
	com.thoughtworks.qdox.library.SourceLibrary.addSource(SourceLibrary.java:89)
	com.thoughtworks.qdox.library.SortedClassLibraryBuilder.addSource(SortedClassLibraryBuilder.java:162)
	com.thoughtworks.qdox.JavaProjectBuilder.addSource(JavaProjectBuilder.java:174)
	scala.meta.internal.mtags.JavaMtags.indexRoot(JavaMtags.scala:49)
	scala.meta.internal.metals.SemanticdbDefinition$.foreachWithReturnMtags(SemanticdbDefinition.scala:99)
	scala.meta.internal.metals.Indexer.indexSourceFile(Indexer.scala:560)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3(Indexer.scala:691)
	scala.meta.internal.metals.Indexer.$anonfun$reindexWorkspaceSources$3$adapted(Indexer.scala:688)
	scala.collection.IterableOnceOps.foreach(IterableOnce.scala:630)
	scala.collection.IterableOnceOps.foreach$(IterableOnce.scala:628)
	scala.collection.AbstractIterator.foreach(Iterator.scala:1313)
	scala.meta.internal.metals.Indexer.reindexWorkspaceSources(Indexer.scala:688)
	scala.meta.internal.metals.MetalsLspService.$anonfun$onChange$2(MetalsLspService.scala:940)
	scala.runtime.java8.JFunction0$mcV$sp.apply(JFunction0$mcV$sp.scala:18)
	scala.concurrent.Future$.$anonfun$apply$1(Future.scala:691)
	scala.concurrent.impl.Promise$Transformation.run(Promise.scala:500)
	java.base/java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1144)
	java.base/java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:642)
	java.base/java.lang.Thread.run(Thread.java:1583)
```
#### Short summary: 

QDox parse error in file:///C:/Users/Andy/Desktop/fina_project_se333_AndrewHuebner/projectAnalyzed/spring-petclinic/src/test/java/org/springframework/samples/petclinic/owner/VisitControllerTest.java