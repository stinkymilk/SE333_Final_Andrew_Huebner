
import mcp


@mcp.tool()
def parse_jacoco_coverage(project_root: str) -> dict:
   import xml.etree.ElementTree as ET
from pathlib import Path

@mcp.tool()
def parse_jacoco_coverage(project_root: str) -> dict:
    """
    Parses JaCoCo XML coverage report and returns coverage metrics.
    """

    report_path = Path(project_root) / "target/site/jacoco/jacoco.xml"

    if not report_path.exists():
        return {"error": "JaCoCo report not found"}

    tree = ET.parse(report_path)
    root = tree.getroot()

    counters = {}

    for counter in root.findall("counter"):
        covered = int(counter.attrib["covered"])
        missed = int(counter.attrib["missed"])
        total = covered + missed

        if total == 0:
            percent = 0
        else:
            percent = round((covered / total) * 100, 2)

        counters[counter.attrib["type"]] = {
            "covered": covered,
            "missed": missed,
            "coverage_percent": percent
        }

    return counters

