rs.initiate({
    _id : 'rs',
    members: [
        { 
            _id: 0, 
            host: "mongo1:27017", 
            priority: 30 
        },
        { 
            _id: 1, 
            host: "mongo2:27117", 
            priority: 20 
        },
        { 
            _id: 2, 
            host: "mongo3:27217", 
            priority: 10 
        },
        {
            _id: 3,
            host: "mongosup1:27317",
            arbiterOnly: true
        },
        {
            _id: 4,
            host: "mongosup2:27417",
            arbiterOnly: true
        }
   ]
})

rs.status()

