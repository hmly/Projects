"""
Fetch Current Weather

A program that get the current weather given the user
geo-location (latitude and longitude); other possible
inputs include city name, zipcode, and city id .

Replace <API key> with the one you obtained.

Open Weather Map API:
http://openweathermap.org/current
"""
import urllib.request as rq
import urllib.parse as parse
import json


def cel_to_far(cel):
    return cel * 1.8 + 32


def deg_to_compass(x):
    val = int((x / 22.5) + 0.5)
    compass = ('N', 'NNE', 'NE', 'ENE', 'E', 'ESE', 'SE', 'SSE', 'S', 'SSW', 'SW', 'WSW', 'W', 'WNW', 'NW', 'NNW')
    return compass[(val % 16)]

# Get city name
resp = rq.urlopen('http://ipinfo.io/json').read().decode('utf-8')
json_data = json.loads(resp)
lat, lon = json_data['loc'].split(',')

# Set up query
url = 'http://api.openweathermap.org/data/2.5/weather?{0}'
query = parse.urlencode({'lat': lat, 'lon': lon, 'q': json_data['city'],
                         'units': 'metric', 'APPID': '<API key>'})
url = url.format(query)

# Fetch weather info
resp = rq.urlopen(url).read().decode('utf-8')
data = json.loads(resp)

# Display weather info
print('''
    Location: %s
    Weather: %s
    Temperature: %d°F / %d°C
    High: %d°F / %d°C
    Low: %d°F / %d°C
    Wind: %d Kmph %s
    Humidity: %d
    ''' % (data['name'], data['weather'][0]['description'].capitalize(),
           cel_to_far(data['main']['temp']), data['main']['temp'],
           cel_to_far(data['main']['temp_max']), data['main']['temp_max'],
           cel_to_far(data['main']['temp_min']), data['main']['temp_min'],
           data['wind']['speed'], deg_to_compass(data['wind']['deg']),
           data['main']['humidity']))
