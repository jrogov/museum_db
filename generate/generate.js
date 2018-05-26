// https://github.com/request/request/blob/master/README.md

const request = require('request')

/*
 * CONFIG
 */

const serverHost = 'localhost'
const serverPort = 8080
const serverUrl = 'http://' + serverHost + ':' + serverPort + '/api'

const GET = 'GET'
const POST = 'POST'
const PUT = 'PUT'
const DELETE = 'DELETE'
const PATCH = 'PATCH'

function api(method, url, body, callback){
    //console.log(body)
    r = {
        method: method,
        url: serverUrl + url
    }

    if( method != GET && body != null )
        r.json = body
    // console.log(body)

    request(r, callback)
}

/*
 * RANDOM VALUE GENERATORS CONFIG
 */

const N = 100;
authors_num = exhibit_num = visitor_num = hall_num = tour_num = tour_max_len = N

// const authors_num = 100
// const exhibit_num = 1000
// const visitor_num = 19000
// const hall_num = 200
// const tour_num = 5
// const tour_max_len = 1

// const authors_num = 10000
// const exhibit_num = 100000
// const visitor_num = 1900000
// const hall_num = 2000
// const tour_num = 50
// const tour_max_len = 15


function getRandom(min, max) {
    return (Math.random() * (max - min + 1)) + min;
}

function getRandInt(min, max) {
    return Math.floor(getRandom(min,max))
}

function getElement(list){
    return list[getRandInt(0, list.length-1)]
}

function getFewElements(list, num){
    s = new Set();
    while(s.size < num)
        s.add(getRandInt(0, list.length-1))
    return Array.from(s).map( (i) => list[i] );
}

var name = [
    "John", "Illidan", "Jack", "Jojo", "Martin", "Patrick", "Sean", "Elizabeth", "Sonya", "Prokhor", "Mira", "Semion", "Sharlotta", "Joseph", "Jotaro", "Mary", "Gabriel", "David", "Eve", "Pimen", "Amadeus", "Christopher", "Matthew",
    "Jumanji", "Pete", "Angela", "Louis", "Harry", "Sarah", "Daisy", "Pasha", "Sam", "Han", "Sakura", "Sasuke", "Naruto", "Boruto", "Misku", "Masashi", "Burger", "Ivan", "Vladimir", "Alexander", "Yaroslav", "Neo",
    "Stanley", "Roman", "Akakii", "Sudo", "Afanasii", "Dmitrii", "Sergei", "Som", "Xi", "Mango", "Milly", "Lelush", "Dio", "Caesar", "Carl", "Gustav", "Paer", "Arno",
    "Aguillar", "Shawn", "Ezio", "Hodor", "Thor", "Loki", "Anthony", "Clint", "Natasha", "Lilo", "Aloha", "Stich", "Bro", "Alexei", "Ramzes", "Vasilii", "Tituan", "Leeroy",
    "Funz", "Hans", "Sebastian", "Elon", "Evan", "Jan", "Peon", "Usama", "Sora", "Asuna", "Asuma", "Chouji", "Shino", "Shinon", "Sore", "Kirigaya", "Daniil", "Sherlock", "Abdul", "Muhammad",
    "Bill", "Sammy", "Mark", "Hideo", "Reimu", "Marisa", "Satori", "Koishi", "Flandre", "Remilia", "Yukari", "Chen", "Ran", "Yuyuko", "Youmu", "Chiruno", "Sakuya"
];
var middlename = [
    "Jack", "Michael", "Christine", "Mikkel", "Miggel", "Sherlock", "Melory", "Nine", "Francisco", "Diego", "Hose", "de Paula", "Zifa", "Erick", "Saber", "Naruto", "Sanic", "Archer", "Saruman", "Peter", "Stewart",
    "Montorn", "Luke", "Vader", "Zyzz", "Bee", "Wan", "Chan", "Cheng", "So", "Boruto", "Pedigree", "Shauma", "Monte", "Shaverma", "Biceps", "Rm", "Burning",
    "Catan", "Bird", "Su", "So", "mon", "au", "ferlie", "la", "Mio", "Gustav", "Carl", "de", "Mourne", "aur", "of", "pro", "contra", "not", "Vasilievich", "Petro", "dios", "Paul", "Fast",
    "the", "bin", "Aurelius", "Lucius", "Ali"
];
var surname = [
    "Doe", "Putin", "Stormrage", "Ivanov", "Memar", "Proshya", "Parfe", "Minin", "Joestar", "Jackson", "Perminov", "Mastodont", "Mama", "Stalin", "Mustafaev", "Fomin", "Schiefelbein", "Rogov", "Orlov", "Sorokin", "Chevchevadze",
    "Griffin", "Pemolux", "Pepe", "Potter", "Ridley", "Ford", "Fischer", "Gerrera", "Skywalker", "Erso", "Uchiha", "Uzumaki", "Kishimoto", "Kojima", "Gufovskiy", "Lenin", "Merkel", "Derjavin", "Romanov", "Rurikovich",
    "Stedley", "Rf", "Zhmylove", "Afanasiev", "Klimenkov", "Peng", "Zhiley", "Wii", "Pong", "Ping", "Mong", "Brando", "Suomi", "Mannerheim", "Medvedev", "Balalaika", "Esenin", "Sonin", "Pomnin", "Sosnin", "Auditore", "Odinson", "Stark", "Romanoff", "Eastwood", "Kerrigan", "Dallas", "York", "Orlean", "Shinkaruk",
    "Piostro", "Caliostro", "Montreal", "Paris", "Merloz", "Mio", "Debs", "Zimmer", "Musk", "Polnareff", "Ripper", "Laden", "Sora", "Bulq", "Yuuki", "Goldberg", "Iceberg", "Shekelberg",
    "Akimichi", "Nara", "Aburame", "Kupriyanov", "Gorshenev", "Kazuto", "Ishutin", "Tannenbaum", "Timchenko", "Holmes", "Gates", "Apostolos", "Pupa", "Lupa", "Gonzales", "Dornan", "Hakurei", "Kirisame", "Komeiji", "Konpaku", "Saigyoji", "Yakumo", "Scarlet",
    "Herrington", "Wolf", "Izayoi", "Tropchenko", "Hitler", "Newell", "Nukem", "Bitterman", "Antonin", "Augustus"
];
function getName(){
    return getElement(name)
    + " " + getElement(middlename)
    + " " + getElement(surname);
}
var countries = [ "Afghanistan", "Albania", "Algeria", "Andorra", "Angola", "Antigua and Barbuda", "Argentina", "Armenia", "Australia", "Austria", "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium", "Belize", "Benin", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana", "Brazil", "Brunei", "Bulgaria", "Burkina Faso", "Burundi", "Cabo Verde", "Cambodia", "Cameroon", "Canada", "Central African Republic (CAR)", "Chad", "Chile", "China", "Colombia", "Comoros", "Democratic Republic of the Congo", "Republic of the Congo", "Costa Rica", "Cote d'Ivoire", "Croatia", "Cuba", "Cyprus", "Czech Republic", "Denmark", "Djibouti", "Dominica", "Dominican Republic", "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia", "Eswatini (formerly Swaziland)", "Ethiopia", "Fiji", "Finland", "France", "Gabon", "Gambia", "Georgia", "Germany", "Ghana", "Greece", "Grenada", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana", "Haiti", "Honduras", "Hungary", "Iceland", "India", "Indonesia", "Iran", "Iraq", "Ireland", "Israel", "Italy", "Jamaica", "Japan", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait", "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein", "Lithuania", "Luxembourg", "Macedonia (FYROM)", "Madagascar", "Malawi", "Malaysia", "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mexico", "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Morocco", "Mozambique", "Myanmar (formerly Burma)", "Namibia", "Nauru", "Nepal", "Netherlands", "New Zealand", "Nicaragua", "Niger", "Nigeria", "North Korea", "Norway", "Oman", "Pakistan", "Palau", "Palestine", "Panama", "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Poland", "Portugal", "Qatar", "Romania", "Russia", "Rwanda", "Saint Kitts and Nevis", "Saint Lucia", "Saint Vincent and the Grenadines", "Samoa", "San Marino", "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone", "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea", "South Sudan", "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland (renamed to Eswatini)", "Sweden", "Switzerland", "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates (UAE)", "United Kingdom (UK)", "United States of America (USA)", "Uruguay", "Uzbekistan", "Vanuatu", "Vatican City (Holy See)", "Venezuela", "Vietnam", "Yemen", "Zambia", "Zimbabwe" ];
function getCountry(){
    return getElement(countries);
}

var types = [ "student", "senior", "casual", "priveleged" ];
function getType(){
    return getElement(types);
}

//first, second and third words in name of exhibit
var exhibit_1 = ["Sword", "Book", "Head", "Skull", "Pyke", "Ruler", "Triangle", "Cross", "Soup", "Title", "Dakimakura", "Rasengan", "Ramen", "Shorts", "Secret"];
var exhibit_2 = ["of", "from", "es", "desu", "by"];
var exhibit_3 = ["Fame", "Glory", "Frost", "Pain", "Money", "People", "Gul'dan", "Illidan", "Saint", "Illuminati", "Mother", "Father", "Ded", "Bart", "THE WORLD", ];
function getExhibitName(){
    return getElement(exhibit_1) + ' ' + getElement(exhibit_2) + ' ' + getElement(exhibit_3);
}

var room_prefix = ["Fame", "Mygosh", "Anime", "FOX NEWS", "Arts", "War", "Sadness", "Anger", "Love", ];
function getRoomName() {
    if(getRandInt(0,1) == 0)
        return getElement(room_prefix)+" hall";
    else
        return "Hall of "+getElement(room_prefix);
}

var category_names = [ "Animation", "Architecture", "Body", "Brief", "Cinema", "Comic", "Dance", "Digital", "Drawing", "Engraving", "Fractal", "Gastronomy", "Gold", "Graffiti", "Music", "Opera", "Painting", "Photography", "Poetry", "Pottery", "Sculpture", "Singing", "Theatre", "Woodwork", "Writing", ];
function getCategoryName(){ return getElement(category_names); }

var materials = ["glue", "wood", "sticks", "hair", "corpses", "skulls", "fire", "electricity", "ice", "magic", "water", "sand", "plastic", "metal", "blood", "tears", "titanium", "aluminium", "java", "javascript", "brain", "books", "suffering", "happiness", "smiles", "eyeballs", ];
function getMaterial(){ return getElement(materials); }
var schedule = ["24/7", "9:00-20:00", "10:00-21:00", "13:00-20:00", ];
function getSchedule(){ return getElement(schedule); }
var tickettypes = ["excursion", "child", "adult"];
function getTicketType(){ return getElement(tickettypes); }
var languages = ["Mandarin (entire branch)", "Spanish", "English", "Hindi[a]", "Arabic", "Portuguese", "Bengali (Bangla)", "Russian", "Japanese", "Punjabi", "German", "Javanese", "Wu (e.g. Shanghainese)", "Malay (inc. Malaysian and Indonesian)", "Telugu", "Vietnamese", "Korean", "French", "Marathi", "Tamil", "Urdu", "Turkish", "Italian", "Yue (incl. Cantonese)", "Thai", "Gujarati", "Jin", "Southern Min (incl. Hokkien and Teochew)", "Persian", "Polish", "Pashto", "Kannada", "Xiang (Hunanese)", "Malayalam", "Sundanese", "Hausa", "Odia (Oriya)", "Burmese", "Hakka", "Ukrainian", "Bhojpuri", "Tagalog (Filipino)", "Yoruba", "Maithili", "Uzbek", "Sindhi", "Amharic", "Fula", "Romanian", "Oromo", "Igbo", "Azerbaijani", "Awadhi", "Gan Chinese", "Cebuano (Visayan)", "Dutch", "Kurdish", "Serbo-Croatian", "Malagasy", "Saraiki", "Nepali", "Sinhalese", "Chittagonian", "Zhuang", "Khmer", "Turkmen", "Assamese", "Madurese", "Somali", "Marwari", "Magahi", "Haryanvi", "Hungarian", "Chhattisgarhi", "Greek", "Chewa", "Deccan", "Akan", "Kazakh", "Northern Min[disputed – discuss]", "Sylheti", "Zulu", "Czech", "Kinyarwanda", "Dhundhari", "Haitian Creole", "Eastern Min (inc. Fuzhounese)", "Ilocano", "Quechua", "Kirundi", "Swedish", "Hmong", "Shona", "Uyghur", "Hiligaynon/Ilonggo (Visayan)", "Mossi", "Xhosa", "Belarusian", "Balochi", "Konkani"];
function getLanguage(){ return getElement(languages)}

function getDate(dateMin, dateMax) {
    return new Date(getRandom(dateMin, dateMax)).toLocaleDateString();
}

//magic numbers, "na glazok"
function getDateBirth() {
    return getDate(-300000000000, 1800000000000);
}
function getDateExhibit() {
    return getDate(-20000000000, 900000000000);
}
function getDateVisit() {
    return getDate(0, 900000000000);
}

/*
 * DATA GENERATION
 */

function emit(num, f){
    return new Array(num).fill().map(f);
}

function errcb(e,r,b)
{
    if(e) return console.error(e);
    if(b.errorMsg) return console.error(b.errorMsg);
}

// Categories
api(
    POST,
    '/category/many',
    category_names.map( (n) => { return { name : n };}),
    (e,r,b) => {
        if(e) return console.error(e);

var categories = b;
// console.log(categories)

// Authors
api(
    POST,
    '/author/many',
    emit(authors_num, () => {
        return {
            name: getName(),
            country: getCountry(),
            birthdate: getDateBirth(),
        };
    }),
    (e,r,b) => {
        if(e) return console.error(e)

var authors = b;
// console.log(authors)

// Exhibits
api(
    POST,
    '/exhibit/many',
    emit(exhibit_num, () => {
        return {
                name: getExhibitName(),
                materials: materials,
                creationdate: getDateExhibit()
            };
        }
    ),
    (e,r,b) => {
        if(e) return console.error(e);

var exhibits = b;
// console.log(exhibits)

// Connect Authors, Categories and Exhibits
// Works = Exhibits
// ASYNCHRONOUSLY
exhibits.forEach((exhibit) => {
    api(POST, '/author/'+getElement(authors).id+'/works', [exhibit.id], errcb)
})


authors.forEach((author) => {
    // Categories
    api(POST, '/author/'+author.id+'/categories',
        getFewElements(categories, getRandInt(1,4)).map((v) => v.name ),
        errcb
    );
    // Deadlock =(
    // // Contemporaries
    // api(POST, '/author/'+author.id+'/contemporaries',
    //     getFewElements(authors, getRandInt(1,5)).filter((a) => a.id != author.id).map((a) => a.id),
    //     errcb
    // );
})

// Rooms
api(POST,
    '/room/many',
    emit(hall_num, () => {
        return {
            name : getRoomName(),
            schedule : getSchedule()
        }
    }),
    (e,r,b) =>{
        if(e) console.error(e)

var rooms = b
// console.log(rooms)

// Place exhibits in rooms
// ASYNCHRONOUSLY
// Deadlock possible :(
exhibits.forEach((exhibit) =>
{
    api(POST, '/exhibit/'+exhibit.id+'/place',
        { id: getElement(rooms).id },
        errcb
    )
})


api(POST,
    '/visitor/many',
    emit(visitor_num, () => {
        return {
            name : getName(),
            type : getType()
        }
    }),
    (e,r,b) => {
        if(e) console.error(e)

var visitors = b
// console.log(visitors)

// Visit once
// ASYNCHRONOUSLY
visitors.forEach((visitor) => {
    api(POST,
        '/visitor/'+visitor.id+'/visit',
        {
            visitorid: visitor.id,
            tickettype: getTicketType(),
            price: getRandom(500, 1000)
        },
        errcb
    )
})


})
})
})
})
})

/*
 * TEST LAUNCHES
 */

// api(
//     POST,
//     '/author/many',
//     emit(10, () => { return {name: getName(), country: getCountry(), birthdate: getDateBirth()}; }),
//     (e,r,b) => { if(e) return console.error(e); console.log(b); }
//     )

// api(
//     POST,
//     '/exhibit/many',
//     emit(10, () => { return {
//         name: getExhibitName(),
//         materials: getFewElements(materials, 3),
//         creationdate: getDateExhibit()
//     }}),
//     (e,r,b) => { if(e) return console.error(e); console.log(b)})

//------------------------------------------------------------------------------

//authors_id = []

//api(GET, '/author/void', '', (err, r, b) => {console.log(err, r, b)});

//api(POST, '/author', { humbaga: 'nono' }, (e,r,b) => console.log(e, r, b) )

//api(GET, '/author/5b0808d47df7834057d7c1bc', null, (e,r,b) => console.log(b));
// api(POST, '/author/many',
//     [
//         { name: "Johnny", country: "UK", birthdate: "Crippled day" },
//         { name: "Gyro", country: "Italy", birthdate: "BEST DAY" }
//     ],
//     (error, response, body) => {
//         if (error) {
//             return console.error(error);
//         }
//         console.log(body)
//         api(POST, '/author/'+body[0].id+'/contemporaries', [body[1].id],
//                 (err, r, b) => {
//                     if(err) return console.error(err);
//                     console.log(b);
//                 }
//            )
//     }
// )

// api(POST, '/author', {
//         name: "Gyro",
//         country: "UK",
//         birthdate: "BEST DAY"
//     },
//     (error, response, body) => {
//         if (error) {
//             return console.error(error);
//         }
//         console.log(body)
//         console.log('Added author '+body.id)
//         //authors_id.push(body.id)

//        //  api(GET, '/author/' + body.id, '',
//        //     (error, response, body) => {
//        //          if (error) {
//        //            return console.error(error);
//        //          }
//        //          console.log(body)
//        //      }
//        // );
// })

//  api(GET, '/category'+ '/Humbaga', null, (e,r,b) => console.log('Humbaga found: ' + b ));
//  api(POST, '/category', {name: 'Humbaga'}, (e,r,b) => { console.log('Humbaga added: '); console.log(b);} );
//  api(GET, '/category', null, (e,r,b) => console.log('Categories:' + b));
//  api(GET, '/category'+ '/Humbaga', null, (e,r,b) => console.log('Humbaga found: ' + b ));
