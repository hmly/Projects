"""
Whois Search Tool

Get a domain name, preferably not an IP address and return the
whois result (whether the domain name is available for use.

Dotnul Whois API
http://dotnul.org/whois-lookup/
"""
import json
import urllib.request as rq

# Get whois query
domain = input('Enter domain name (google.com): ')
url = 'http://dotnul.com/api/whois/' + domain
response = rq.urlopen(url).read().decode('utf-8')
whois = str(json.loads(response)['whois'])

# Display the result
if '<br/>' in whois:
    whois = whois.replace('<br/>', '\n')
else:
    whois = whois.replace('<br />', '\n')
print(whois)


