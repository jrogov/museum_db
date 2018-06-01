#!/usr/bin/env python3 

from sys import exit
from gendata import *
import requests
from random import randint, random

N = 100;
authors_num = exhibit_num = visitor_num = room_num = excursion_num = excursion_max_len = N

# authors_num = 100
# exhibit_num = 1000
# visitor_num = 19000
# room_num = 200
# excursion_num = 5
# excursion_max_len = 1

# authors_num = 10000
# exhibit_num = 100000
# visitor_num = 1900000
# room_num = 2000
# excursion_num = 50
# excursion_max_len = 15

server_host = 'localhost'
server_port = '8080'
server_url = 'http://' + server_host + ':' + server_port + '/api'

GET = 'GET'
POST = 'POST'
PUT = 'PUT'
DELETE = 'DELETE'
PATCH = 'PATCH'

def api(method, url, body, call_limit=100):
    url = server_url + url;
    if( method == GET ):
        return requests.request(method, url)
    else:
        return requests.request(method, url, json=body)


# Add Categories
r = api(
        POST, 
        '/category/many',
        [ {'name': i} for i in category_names])

if(r.status_code != 200):
    print(r.text); exit(1)
categories = r.json()
print('Generated {} categories'.format(len(categories)))

# Add Authors
r = api(
        POST,
        '/author/many',
        [ 
            {
                'name': rand_name(), 
                'country': rand_country(), 
                'birthdate': rand_date_birth()
                }
            for i in range(0,authors_num)
        ]
)

if(r.status_code != 200):
    print(r.text); exit(1)
authors = r.json()
print('Generated {} authors'.format(len(authors)))

# Add Exhibits
r = api(
        POST,
        '/exhibit/many',
        [
            {
                'name': rand_exhibit_name(),
                'materials': rand_elems(materials, randint(0,min(5,len(materials)))),
                'creationdate': rand_date_exhibit()
            }
            for i in range(0,exhibit_num)
        ]
)

if(r.status_code != 200):
    print(r.text); exit(1)
exhibits = r.json()
print('Generated {} exhibits'.format(len(exhibits)))

# Add Exhibits as Works of some Author
for exhibit in exhibits:
    r = api(
            POST, 
            '/author/'+str(rand_elem(authors)['id'])+'/works', 
            [exhibit['id']]
    )
print('Connected Exhibits and Authors')

for i in range(0, len(authors)-1):
    author = authors[i]
    id = str(author['id'])
    
    # Add some Categories to Author (used for interests of visitors)
    r = api(
            POST,
            '/author/'+id+'/categories',
            [ c['name'] for c in rand_elems(categories, randint(1,4))]
    )
    if(r.status_code != 200):
        print(r.text); exit(1)

    # Add Contemporaries of Author
    # r = api(
    #         POST,
    #         '/author/'+id+'/contemporaries',
    #         [ c['id'] 
    #             for c in rand_elems(
    #                 authors[i+1:], 
    #                 randint(1, min(5, len(authors)-i-1))
    #             )
    #         ]
    # )
    # if(r.status_code != 200):
    #     print(r.text); exit(1)
    print('Added categories and contemporaries to {}/{} author'.format(i+1, len(authors)))

# Add rooms
r = api(
        POST,
        '/room/many',
        [{'name': rand_room_name(), 'schedule' : rand_schedule()} 
            for i in range(0,room_num)]
)
        
if(r.status_code != 200):
    print(r.text); exit(1)
rooms = r.json()
print('Generated {} rooms'.format(len(rooms)))

# Place Exhibits in Rooms
for exhibit in exhibits:
    id = exhibit['id']
    r = api(
            POST,
            '/exhibit/'+id+'/place',
            { 'id': rand_elem(rooms)['id'] }
    )
    if(r.status_code != 200):
        print(r.text); exit(1)

print('Placed Exhibits in Rooms')

# Add Visitors
r = api(
        POST,
        '/visitor/many',
        [{'name': rand_name(), 'type': rand_visitor_type()} for i in range(0,visitor_num)]
)
if(r.status_code != 200):
    print(r.text); exit(1)
visitors = r.json()
print('Generated {} visitors'.format(len(visitors)))

# Add Excursions
excursions = []
for i in range(0,excursion_num):
    path = [ {'id' : r['id']} for r in rand_elems(rooms, randint(0,excursion_max_len))]
    r = api(
            POST,
            '/excursion',
            {
                'date'      : rand_date_visit(),
                'language'  : rand_language(),
                'guidename' : rand_name(),
                'maxpeople' : randint(10,25),
                'pathlen'   : len(path),
                'category'  : rand_elem(categories),
                'path'      : path
            }
    )
    if(r.status_code != 200):
        print(r.text); exit(1)
    excursion = r.json()
    excursions.append(excursion)
print('Generated {} excursions'.format(len(excursions)))

# Visitors Visit some Excursions
for visitor in visitors:
    id = str(visitor['id'])
    to_visit = rand_elems(excursions, randint(1,3))
    for excursion in to_visit:
        r = api(
                POST,
                '/visitor/'+id+'/visit',
                {
                    'visitorid'  : id,
                    'tickettype' : rand_tickettype(),
                    'price'      : randint(500,1000),
                    'excursionid': rand_elem(excursions)['id']
                }
        )
print('Visitors visited some Excursions')

visitor = rand_elem(visitors)
print('Visitor: ' + str(visitor))
r=api(GET, '/visitor/'+str(visitor['id']), None);
if(r.status_code != 200):
    print(r.text); exit(1)
print('Same visitor: '+str(r.json()))
