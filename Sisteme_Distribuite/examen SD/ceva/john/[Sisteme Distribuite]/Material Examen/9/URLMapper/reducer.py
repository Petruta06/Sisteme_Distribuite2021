import sys

current_global_url = None
urls = []
url = None

for line in sys.stdin:
    line = line.strip()
    url, reference = line.split()
    if current_global_url != url:
        if current_global_url:
            print('%s -> %s' %(current_global_url, urls))
        urls = []
        current_global_url = url
    urls.append(reference)

if len(urls) > 0:
    print('%s -> %s' %(current_global_url, urls))