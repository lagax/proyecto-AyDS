DROP TABLE IF EXISTS `inmoapp_development`.`cities`;
CREATE  TABLE `inmoapp_development`.`cities` (
    id integer NOT NULL auto_increment,
    name varchar(50) NOT NULL,
    postcode integer,
    constraint pkcity PRIMARY KEY (id,name)
);

DROP TABLE IF EXISTS `inmoapp_development`.`addresses`;
CREATE  TABLE `inmoapp_development`.`addresses` (
    id integer NOT NULL auto_increment,
    address varchar(50),
    neighborhood varchar(50),
	city_id integer,
    constraint pkaddress PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `inmoapp_development`.`realEstates`;
CREATE  TABLE `inmoapp_development`.`realEstates` (
    id integer NOT NULL auto_increment,
    name varchar(50),
    email varchar(50),
    web_site varchar(50),
    phone_number integer,
    address_id integer not null,
    constraint pkrealEstate PRIMARY KEY (id,name)
);

DROP TABLE IF EXISTS `inmoapp_development`.`owners`;
CREATE  TABLE `inmoapp_development`.`owners` (
    id integer NOT NULL auto_increment,
    dni int not null,
    first_name varchar(50) NOT NULL,
    last_name varchar(50) NOT NULL,
    email varchar(50),
    address_id integer not null,
    constraint pkowner PRIMARY KEY (id,dni)
);

DROP TABLE IF EXISTS `inmoapp_development`.`buildings`;
CREATE  TABLE `inmoapp_development`.`buildings` (
    id integer NOT NULL auto_increment,
    type int,#("land","farm","house","garage","office","flat"),
    description varchar(50),
    price integer,
    status int,# for sale, for rent 
    address_id integer NOT NULL,
	owner_id integer,
    constraint pkbuilding PRIMARY KEY (id),
    constraint chtypeRange check(type>=1 and type<=6),
    constraint chstatusRange check(status>=1 and status<=2)
);

DROP TABLE IF EXISTS `inmoapp_development`.`realEstates_owners`;
CREATE  TABLE `inmoapp_development`.`realEstates_owners` (
    realEstate_id integer NOT NULL,
    owner_id integer NOT NULL,
    constraint pkrealEstates_owners PRIMARY KEY (realEstate_id,owner_id)
);
