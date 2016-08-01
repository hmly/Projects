"""
Zip / Postal Code Lookup

Hippopotam API
http://www.zippopotam.us/
"""
import json
import urllib.request as rq

country = input('Enter country code: ')
zipcode = input('Enter zipcode: ')
url = 'http://api.zippopotam.us/' + country + '/' + zipcode

response = rq.urlopen(url).read().decode('utf-8')
obj = json.loads(response)['places'][0]

print("""Location
    City:  %s
    State: %s"""
      % (obj['place name'], obj['state']))
