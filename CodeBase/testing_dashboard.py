import json
import sys
from datetime import datetime
from pathlib import Path


def write_testing_dashboard(repository_path: str, metrics: dict) -> str:
    """
    Updates the testing metrics dashboard.
    """

    dashboard_path = Path(repository_path) / ".github" / "testing-metrics.json"
    dashboard_path.parent.mkdir(parents=True, exist_ok=True)

    entry = {
        "timestamp": datetime.utcnow().isoformat(),
        **metrics
    }

    if dashboard_path.exists():
        data = json.loads(dashboard_path.read_text())
    else:
        data = []

    data.append(entry)

    dashboard_path.write_text(json.dumps(data, indent=2))

    return f"Dashboard updated at {dashboard_path}"


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python testing_dashboard.py <repository_path> [metrics_json]")
        print("\nExample:")
        print('  python testing_dashboard.py projectAnalyzed/spring-petclinic \'{"instruction_coverage": 73.0, "branch_coverage": 68.18}\'')
        sys.exit(1)
    
    repo_path = sys.argv[1]
    
    # Parse metrics from command line or use defaults
    if len(sys.argv) > 2:
        try:
            metrics = json.loads(sys.argv[2])
        except json.JSONDecodeError as e:
            print(f"Error parsing metrics JSON: {e}")
            sys.exit(1)
    else:
        # Default metrics for testing
        metrics = {
            "instruction_coverage": 0.0,
            "branch_coverage": 0.0,
            "tests_added": 0,
            "message": "Test entry"
        }
    
    result = write_testing_dashboard(repo_path, metrics)
    print(result)