"""
Quote Tracker

A program that retrieve the current value and other important information
of stocks from a list of symbols entered by the user; the information are updated
every n seconds, where n is specified by the user.

Ex symbols: fb, goog, amzn, adbe, aapl, akam

Reference:
http://stackoverflow.com/questions/27543776/yahoo-finance-webservice-api
"""
import json
import time
import urllib.parse as parse
import urllib.request as rq

WIDTH = 151
url = 'http://query.yahooapis.com/v1/public/yql?{0}&format=json'
query = '''select {0} from yahoo.finance.quotes where symbol in ({1})'''
display_format = '%-30s%-8s%-15s%-10s%-10s%-10s%-19s%-19s%-12s%s'
info = ('Name', 'symbol', 'PreviousClose', 'Open', 'Bid', 'Ask',
        'YearRange', 'DaysRange', 'Volume', 'AverageDailyVolume')  # Non-mutable


def print_info(quo):
    print(display_format %
          (quo[info[0]], quo[info[1]], quo[info[2]], quo[info[3]],
           quo[info[4]], quo[info[5]], quo[info[6]], quo[info[7]],
           quo[info[8]], quo[info[9]]))

# Set up query for Yahoo Finance API
symbols = input('Enter a list of of stock symbols: ')
interval = int(input('Enter update interval (sec): '))
symbols = ','.join('"' + w.upper() + '"' for w in symbols.split(','))
query = query.format(','.join(info), symbols)
query = parse.urlencode({'q': query})
url = url.format(query) + '&env=http://datatables.org/alltables.env'

# Retrieve data from
quotes = rq.urlopen(url).read().decode('utf-8')
quotes = json.loads(quotes)['query']

# Display results; Ctrl-c to exit
while True:
    print('Time:', quotes['created'])
    print(display_format %
          (info[0], info[1], info[2], info[3], info[4], info[5],
           info[6], info[7], info[8], info[9]))
    print('-' * WIDTH)

    if len(symbols.split(',')) == 1:
        print_info(quotes['results']['quote'])
    else:
        for quo in quotes['results']['quote']:
            print_info(quo)
    print('=' * WIDTH)
    time.sleep(interval)
