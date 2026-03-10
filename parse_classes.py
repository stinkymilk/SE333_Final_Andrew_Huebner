import xml.etree.ElementTree as ET
from pathlib import Path

report_path = Path('projectAnalyzed/spring-petclinic/target/site/jacoco/jacoco.xml')
if report_path.exists():
    tree = ET.parse(report_path)
    root = tree.getroot()
    classes = []
    for package in root.findall('package'):
        for cls in package.findall('class'):
            name = cls.attrib['name'].replace('/', '.')
            counters = {}
            for counter in cls.findall('counter'):
                covered = int(counter.attrib['covered'])
                missed = int(counter.attrib['missed'])
                total = covered + missed
                percent = round((covered / total) * 100, 2) if total > 0 else 0
                counters[counter.attrib['type']] = percent
            if 'INSTRUCTION' in counters:
                classes.append({'name': name, 'instruction_coverage': counters.get('INSTRUCTION', 0)})
    classes.sort(key=lambda x: x['instruction_coverage'])
    print('Lowest coverage classes:')
    for cls in classes[:5]:
        print(f'{cls["name"]}: {cls["instruction_coverage"]}%')
else:
    print('Report not found')