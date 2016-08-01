"""
Get the atomic time from an online Atomic Clock

NTP from http://www.pool.ntp.org/zone/north-america
"""
import ntplib
from time import ctime

c = ntplib.NTPClient()
response = c.request('us.pool.ntp.org', version=3)
print(ctime(response.tx_time))
