import re
import os

directory = 'src/main/resources/templates/marketing'
for root, _, files in os.walk(directory):
    for file in files:
        if file.endswith('.html'):
            filepath = os.path.join(root, file)
            with open(filepath, 'r') as f:
                content = f.read()
            
            # Find the "Quản lý Slider" anchor tag and replace its href
            # Example: <a href="/admin/sliders" ...> <i ...></i> Quản lý Slider </a>
            # or <a href="#" ...
            content = re.sub(
                r'<a href="[^"]*"(.*?Quản lý Slider.*?</a>)',
                r'<a href="/marketing/sliders"\1',
                content,
                flags=re.DOTALL
            )
            
            with open(filepath, 'w') as f:
                f.write(content)

print("Fixed links.")
