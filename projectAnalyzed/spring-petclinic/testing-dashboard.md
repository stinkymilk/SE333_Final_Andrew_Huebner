# Testing Dashboard

## Latest Metrics

- **Timestamp**: 2026-03-09
- **Branch**: main
- **Commit**: 99a5b78

### Coverage Metrics

- Instruction Coverage: 52%
- Branch Coverage: 45%
- Line Coverage: 50%
- Complexity Coverage: 48%
- Method Coverage: 55%

### Testing Metrics

- Total Tests: 25+
- Tests Added: 15
- Regression Tests: 0
- Edge Case Tests: 5

### Bug Metrics

- Bugs Discovered: 1 (package declaration errors)
- Bugs Fixed: 1

## Iteration History

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
