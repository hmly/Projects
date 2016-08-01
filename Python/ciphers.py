"""
Vigenere, Vernam, Caesar Ciphers

A demonstration of using the three ciphers to encode and decode
a message using a randomly generated key / salt value.
"""
import random

ALPHA = 'ABCDEFGHIJKLMNOPQRSTUVWXYZ'
WIDTH = len(ALPHA)
A_ASCII = 65


def caesar_encode(salt, msg):
    cipher = ''
    for w in msg.upper():
        if w.isalpha():
            cipher += ALPHA[(ord(w)-A_ASCII+salt) % WIDTH]
        else:
            cipher += w
    return cipher


def caesar_decode(salt, cipher):
    msg = ''
    for w in cipher:
        if w.isalpha():
            msg += ALPHA[(ord(w)-A_ASCII-salt) % WIDTH]
        else:
            msg += w
    return msg


def vigenere_encode(salt, msg):
    cipher = ''
    msg = msg.upper()
    for i in range(len(msg)):
        if msg[i].isalpha():
            cipher += ALPHA[(ord(msg[i]) + ord(salt[i])) % WIDTH]
        else:
            cipher += msg[i]
    return cipher


def vigenere_decode(salt, cipher):
    msg = ''
    for i in range(len(cipher)):
        if cipher[i].isalpha():
            msg += ALPHA[(ord(cipher[i]) - ord(salt[i])) % WIDTH]
        else:
            msg += cipher[i]
    return msg


def vernam_encode(salt, msg):
    cipher = ''
    for i in range(len(msg)):
        cipher += chr(ord(msg[i]) ^ ord(salt[i]))  # XOR
    return cipher


def vernam_decode(salt, cipher):
    msg = ''
    for i in range(len(cipher)):
        msg += chr(ord(cipher[i]) ^ ord(salt[i]))  # XOR
    return msg

text = 'the brown fox jumped over the lazy dog'
print('Original text:', text.upper())

# Test Caesar Cipher
key = random.randrange(1, len(ALPHA)+1)
new_text = caesar_encode(key, text)
print('\n::: Caesar Cipher :::')
print('key:', key)
print('cipher:', new_text)
print('original:', caesar_decode(key, new_text))

# Text Vigenere Cipher
key = ''.join(chr(A_ASCII+random.randint(0, WIDTH-1)) for _ in range(len(text)))
new_text = vigenere_encode(key, text)
print('\n::: Vigenere Cipher :::')
print('key:', key)
print('cipher:', new_text)
print('original:', vigenere_decode(key, new_text))

# Test Vernam Cipher
key = ''.join(chr(random.randint(ord(' '), ord('~')+1)) for _ in range(len(text)))
new_text = vernam_encode(key, text)
print('\n::: Vernam Cipher :::')
print('key:', key)
print('cipher:', new_text)
print('original:', vernam_decode(key, new_text))