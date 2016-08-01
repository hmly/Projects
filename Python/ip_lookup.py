"""
Country by IP Lookup

Automatically get the user IP address and display the user's
ip address, hostname, city, region, and country.
"""
import urllib.request as rq
import json

# Get IP address and other info
URL = 'http://ipinfo.io/json'
response = rq.urlopen(URL).read().decode('utf-8')
obj = json.loads(response)

# Display info
print("""
    ip address: %s
    hostname:   %s
    city:       %s
    region:     %s
    country:    %s"""
      % (obj['ip'], obj['hostname'], obj['city'], obj['region'], obj['country']))
