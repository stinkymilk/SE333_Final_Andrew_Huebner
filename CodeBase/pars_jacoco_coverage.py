
import xml.etree.ElementTree as ET
from pathlib import Path
import sys


def parse_jacoco_coverage(jacoco_xml_path: str) -> dict:
    """
    Parses JaCoCo XML coverage report and returns coverage metrics.
    """

    report_path = Path(jacoco_xml_path)

    if not report_path.exists():
        return {"error": f"JaCoCo report not found at {report_path}"}

    tree = ET.parse(report_path)
    root = tree.getroot()

    counters = {}

    for counter in root.findall("counter"):
        covered = int(counter.attrib["covered"])
        missed = int(counter.attrib["missed"])
        total = covered + missed

        if total == 0:
            percent = 0.0
        else:
            percent = round((covered / total) * 100, 2)

        counters[counter.attrib["type"]] = {
            "covered": covered,
            "missed": missed,
            "total": total,
            "coverage_percent": percent
        }

    return counters


if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("Usage: python pars_jacoco_coverage.py <path_to_jacoco.xml>")
        sys.exit(1)
    
    results = parse_jacoco_coverage(sys.argv[1])
    
    if "error" in results:
        print(f"Error: {results['error']}")
        sys.exit(1)
    
    print("\n=== JaCoCo Coverage Report ===\n")
    for metric, data in results.items():
        print(f"{metric}:")
        print(f"  Covered: {data['covered']}")
        print(f"  Missed: {data['missed']}")
        print(f"  Total: {data['total']}")
        print(f"  Coverage: {data['coverage_percent']}%")
        print()

