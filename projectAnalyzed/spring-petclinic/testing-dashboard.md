# Testing Dashboard

## Latest Metrics

- **Timestamp**: 2026-03-10
- **Branch**: main
- **Commit**: c41e205

### Coverage Metrics (JaCoCo)

- Instruction Coverage: 76.64% (863/1126)
- Branch Coverage: 71.59% (63/88)
- Method Coverage: 85.19% (92/108)
- Line Coverage: 80.41% (238/296)
- Uncovered Lines: 58
- Uncovered Branches: 25

### Testing Metrics

- Total Tests: 112
- Tests Added Since Previous Dashboard Snapshot: 43
- Regression Tests Added: 3
- Edge Case Tests Added: 20+

### Bug Metrics

- Bugs Discovered: 0 production defects
- Bugs Fixed: 0 production fixes
- Test/Dependency Issues Fixed: Multiple (formatting, assertion alignment, and test setup issues)

### Target Classes Improved in This Iteration

- `OwnerControllerTest`
- `VisitControllerTest`
- `OwnerTest`
- `PetTest`
- `VisitTest`
- `PetTypeTest`
- `VetTest`
- `SpecialtyTest` (new)
- `BaseEntityTest`
- `NamedEntityTest`
- `PersonTest`

## Iteration History

### Iteration 2026-03-10 (Current)

- Coverage Before: 73.0% instruction, 68.2% branch, 80.56% method
- Coverage After: 76.64% instruction, 71.59% branch, 85.19% method
- Improvement: +3.64% instruction, +3.39% branch, +4.63% method
- Tests Added: +43 (net vs previous dashboard snapshot)
- Message: Expanded model and controller unit tests with JUnit 5 and stabilized test execution.

### Iteration 2026-03-09

- Coverage Before: 50.09% instruction, 27.27% branch
- Coverage After: 73.0% instruction, 68.2% branch
- Improvement: +22.91% instruction, +40.93% branch
- Tests Added: 28
- Target Classes: `PetController`, `PetTypeFormatter`, `PetValidator`
- Message: Added comprehensive tests for three previously uncovered classes.

### Iteration 1

- Timestamp: 2026-03-09
- Coverage Before: 12.62%
- Coverage After: 15%
- Tests Added: 2
- Message: Added `VetControllerTest`.

### Iteration 2

- Timestamp: 2026-03-09
- Coverage Before: 15%
- Coverage After: 18%
- Tests Added: 2
- Message: Added `OwnerControllerTest`.

### Iteration 3

- Timestamp: 2026-03-09
- Coverage Before: 18%
- Coverage After: 52%
- Tests Added: 15
- Message: Added comprehensive model and controller tests.

### Iteration 4

- Timestamp: 2026-03-09
- Coverage Before: 52%
- Coverage After: 52%
- Tests Added: 0
- Message: Fixed dependency errors in test files (package declarations, missing `@MockBean`).

## Audit Log

### AI CHANGE LOG

- Timestamp: 2026-03-10
- Branch: main
- Commit: c41e205
- Coverage Before: 73.0% instruction
- Coverage After: 76.64% instruction
- Files Added:
  - `src/test/java/org/springframework/samples/petclinic/vet/SpecialtyTest.java`
- Files Modified:
  - `src/test/java/org/springframework/samples/petclinic/owner/OwnerControllerTest.java`
  - `src/test/java/org/springframework/samples/petclinic/owner/VisitControllerTest.java`
  - `src/test/java/org/springframework/samples/petclinic/owner/OwnerTest.java`
  - `src/test/java/org/springframework/samples/petclinic/owner/PetTest.java`
  - `src/test/java/org/springframework/samples/petclinic/owner/VisitTest.java`
  - `src/test/java/org/springframework/samples/petclinic/owner/PetTypeTest.java`
  - `src/test/java/org/springframework/samples/petclinic/vet/VetTest.java`
  - `src/test/java/org/springframework/samples/petclinic/model/BaseEntityTest.java`
  - `src/test/java/org/springframework/samples/petclinic/model/NamedEntityTest.java`
  - `src/test/java/org/springframework/samples/petclinic/model/PersonTest.java`
- Purpose: Improve coverage with JUnit 5-focused unit tests and maintain a stable passing suite.

### AI CHANGE LOG

- Timestamp: 2026-03-09
- Branch: main
- Commit: 99a5b78
- Coverage Before: 52%
- Coverage After: 52%
- Files Modified:
  - `VetControllerTest.java` (changed to `@WebMvcTest`)
  - `OwnerControllerTest.java` (changed to `@WebMvcTest`, added `@MockBean` for `PetTypeRepository`)
  - `VisitTest.java` (fixed package declaration)
- Purpose: Fix dependency errors in test compilation and execution.
