// https://github.com/request/request/blob/master/README.md

const request = require('request')

// CONFIG

const serverHost = 'localhost'
const serverPort = 8080
const serverUrl = 'http://' + serverHost + ':' + serverPort + '/api'

const GET = 'GET'
const POST = 'POST'
const PUT = 'PUT'
const DELETE = 'DELETE'
const PATCH = 'PATCH'

const URL='url'
const JSON='json'

function api(method, url, body){
    console.log(body)
    r = {
        method: method,
        url: serverUrl + url
    }

    bodytype='json'
    if( method == GET)
        bodytype='url'
    r[bodytype] = body

    request(r)
}

//------------------------------------------------------------------------------

api(POST, '/author',{
        name: "Johnny",
        country: "UK",
        birthdate: 'somedate'
    }
)
