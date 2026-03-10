import xml.etree.ElementTree as ET
from pathlib import Path

report_path = Path('projectAnalyzed/spring-petclinic/target/site/jacoco/jacoco.xml')
if report_path.exists():
    tree = ET.parse(report_path)
    root = tree.getroot()
    methods = []
    for package in root.findall('package'):
        for cls in package.findall('class'):
            class_name = cls.attrib['name'].replace('/', '.')
            for method in cls.findall('method'):
                method_name = method.attrib['name']
                counters = {}
                for counter in method.findall('counter'):
                    covered = int(counter.attrib['covered'])
                    missed = int(counter.attrib['missed'])
                    total = covered + missed
                    percent = round((covered / total) * 100, 2) if total > 0 else 0
                    counters[counter.attrib['type']] = percent
                if 'INSTRUCTION' in counters and counters['INSTRUCTION'] == 0:
                    methods.append({'class': class_name, 'method': method_name})
    print('Uncovered methods:')
    for m in methods[:10]:
        print(f'{m["class"]}#{m["method"]}')
else:
    print('Report not found')