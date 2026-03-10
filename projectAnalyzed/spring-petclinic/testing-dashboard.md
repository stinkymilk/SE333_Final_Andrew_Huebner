# Testing Dashboard

## Latest Metrics

- **Timestamp**: 2026-03-09
- **Branch**: main
- **Commit**: (pending)

### Coverage Metrics

- Instruction Coverage: 73.0%
- Branch Coverage: 68.2%
- Line Coverage: ~70%
- Method Coverage: ~75%

### Testing Metrics

- Total Tests: 69
- Tests Added: 28 (PetControllerTest: 10, PetTypeFormatterTest: 7, PetValidatorTest: 11)
- Target Classes Covered: 3 (PetController, PetTypeFormatter, PetValidator)

### Coverage Details for New Tests

- **PetController**: 73.6% (170/231 instructions covered, was 0%)
- **PetTypeFormatter**: 100% (44/44 instructions covered, was 0%)
- **PetValidator**: 100% (41/41 instructions covered, was 0%)

## Iteration History

### Latest Iteration - 2026-03-09

- Coverage Before: 50.09% instruction, 27.27% branch
- Coverage After: 73.0% instruction, 68.2% branch
- Improvement: +22.91% instruction, +40.93% branch
- Tests Added: 28
- Target Classes: PetController, PetTypeFormatter, PetValidator
- Message: Added comprehensive tests for three previously uncovered classes

### Iteration 1

- Timestamp: 2026-03-09
- Coverage Before: 12.62%
- Coverage After: 15%
- Tests Added: 2
- Message: Added VetControllerTest

### Iteration 2

- Timestamp: 2026-03-09
- Coverage Before: 15%
- Coverage After: 18%
- Tests Added: 2
- Message: Added OwnerControllerTest

### Iteration 3

- Timestamp: 2026-03-09
- Coverage Before: 18%
- Coverage After: 52%
- Tests Added: 15
- Message: Added comprehensive model and controller tests

### Iteration 4

- Timestamp: 2026-03-09
- Coverage Before: 52%
- Coverage After: 52%
- Tests Added: 0
- Message: Fixed dependency errors in test files (package declarations, missing @MockBean)

## Audit Log

AI CHANGE LOG

Timestamp: 2026-03-09  
Branch: test-improvement/20260309  
Commit: 30cfab3

Coverage Before: 18%  
Coverage After: 52%

Files Added:

- PetTest.java
- VisitTest.java
- WelcomeControllerTest.java
- CrashControllerTest.java

Files Modified:

- OwnerTest.java

Purpose: Increase coverage to 50%+ with comprehensive tests, resolved dependency errors in test packages.

AI CHANGE LOG

Timestamp: 2026-03-09  
Branch: main  
Commit: 99a5b78

Coverage Before: 52%  
Coverage After: 52%

Files Modified:

- VetControllerTest.java (changed to @WebMvcTest)
- OwnerControllerTest.java (changed to @WebMvcTest, added @MockBean for PetTypeRepository)
- VisitTest.java (fixed package declaration)

Purpose: Fix dependency errors in test compilation and execution.
