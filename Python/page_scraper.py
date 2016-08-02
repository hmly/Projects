"""
Page scrapper

Connect to a site via a url and collect all links, including image
links, and save them to a text file. Within the text file, the links
will be organized by their extensions, thus image links will be grouped
together, while non-image links are within another group.

Sample url: https://yande.re/post
"""
from bs4 import BeautifulSoup
import urllib.request as request
import urllib.parse as parse

IMG_EXT = ('.tif', '.tiff', '.gif', '.jpeg', '.jpg', '.jif', '.jfif', '.jp2',
           '.jpx,', '.j2k', '.j2c', '.fpx', '.pcd', '.png', '.pdf')
PATH = 'data/links.txt'


def create_file(url):
    with open(PATH, 'w') as file:
        file.write('url: %s\n' % url)


def to_file(msg, links, display=True):
    with open('data/links.txt', 'a') as file:
        if display:
            print(msg)
        file.write(msg + '\n')
        for link in sorted(links):
            file.write(link + '\n')
            if display:
                print(link)


url = input('Enter a url: ')
html = request.urlopen(url).read().decode('utf-8')
soup = BeautifulSoup(html, 'lxml')
links = {'img': [], 'general': []}

# Organize the links
for tag in set(soup.find_all('a', href=True)):
    if tag['href'].startswith('/'):
        link = parse.urljoin(url, tag['href'])
    else:
        link = tag['href']

    if link.startswith('http'):
        if link.endswith(IMG_EXT):
            links['img'].append(link)  # is an image link
        else:
            links['general'].append(link)  # is a general link

# Save and display links
create_file(url)
to_file('--- Images ---', links['img'])
to_file('--- General ---', links['general'])
