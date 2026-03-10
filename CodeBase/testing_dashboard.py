import mcp

@mcp.tool()
def write_testing_dashboard(repository_path: str, metrics: dict) -> str:
    import json
from datetime import datetime
from pathlib import Path

@mcp.tool()
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