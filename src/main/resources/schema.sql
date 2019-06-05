CREATE TABLE IF NOT EXISTS
     Walker (
         walker_id NOT NULL PRIMARY KEY,
         username varchar(225) NOT NULL UNIQUE,
         password varchar(225),islogged varchar(10)
         )