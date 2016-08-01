"""
Generate a unique cd key / activation code / product key
"""
import binascii
import os

n = int(input('Enter the number of cd-keys you want: '))
for i in range(n):
    print(binascii.hexlify(os.urandom(16)).decode('ascii'))