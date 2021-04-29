CREATE TABLE make (
                        id serial primary key,
                        name varchar (50) unique not null
);

CREATE TABLE model (
                     id serial primary key,
                     name varchar (50) unique not null,
                     make int not null references make(id)
);

CREATE TABLE city (
                    id serial primary key,
                    name varchar (50) unique not null
);

CREATE TABLE typebody (
                      id serial primary key,
                      name varchar (50) unique not null
);

CREATE TABLE users (
                    id serial primary key,
                    name varchar (50) unique not null,
                    email varchar (50) unique not null,
                    password varchar (50) not null,
                    city int not null references city(id)
);

CREATE TABLE advert (
                      id serial primary key,
                      description varchar,
                      model int not null references model(id),
                      typebody int not null references typebody(id),
                      password varchar (50) not null,
                      mileage int,
                      price int,
                      photoId int,
                      yearOfIssue int,
                      sold boolean,
                      users int not null references users(id),
                      created date not null
);