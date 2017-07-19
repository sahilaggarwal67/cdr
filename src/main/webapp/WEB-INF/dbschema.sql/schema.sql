CREATE database cdr;
use cdr;

create table login(
id int NOT NULL AUTO_INCREMENT,
first_name varchar(255),
last_name varchar(255),
username varchar(255),
PRIMARY KEY(id)
);

insert into login(username) values ('user1');

create table company(
id int NOT NULL AUTO_INCREMENT,
name varchar(255),
PRIMARY KEY(id)
);

create table ship (
id int NOT NULL AUTO_INCREMENT,
name varchar(255),
company_id int,
monthly_fee decimal(10,2),
static_ip_fee decimal(10,2),
voice_to_terrestial decimal(10,2),
voice_to_cellular decimal(10,2),
account_type integer,
/*price1 decimal(10,2),
price2 decimal(10,2),*/
imsi1 varchar(50),
imsi2 varchar(50),
fee_static_charge decimal(10,2),
mapping_name varchar(255),
iridium_citadel_monthly_fee decimal(10,2),
PRIMARY KEY(id)
);

create table manual_fields (
id int NOT NULL AUTO_INCREMENT,
account_type integer,
entry_name varchar(255),
PRIMARY KEY(id)
);

insert into manual_fields(id,account_type,entry_name) values(1,1,'Sat-C');
insert into manual_fields(id,account_type,entry_name) values(2,1,'Email');
insert into manual_fields(id,account_type,entry_name) values(3,1,'SMS');
insert into manual_fields(id,account_type,entry_name) values(4,1,'PDN');
insert into manual_fields(id,account_type,entry_name) values(5,1,'Iridium Citadel');
insert into manual_fields(id,account_type,entry_name) values(6,1,'Iridium Openport');
insert into manual_fields(id,account_type,entry_name) values(7,1,'Seabrowser Cards');
insert into manual_fields(id,account_type,entry_name) values(8,1,'Crew Data Mb');
insert into manual_fields(id,account_type,entry_name) values(9,1,'Business Data Mb');
insert into manual_fields(id,account_type,entry_name) values(10,1,'Crew Voip Minutes');
insert into manual_fields(id,account_type,entry_name) values(11,2,'Crew Data Mb');
insert into manual_fields(id,account_type,entry_name) values(12,2,'VoIP Data Use Mb');
insert into manual_fields(id,account_type,entry_name) values(13,2,'Business Data Mb');
insert into manual_fields(id,account_type,entry_name) values(14,2,'Other Data Use Mb');
insert into manual_fields(id,account_type,entry_name) values(15,2,'Crew Voip Minutes');

/*create table ship_manual_entries (
id int NOT NULL AUTO_INCREMENT,
ship_id int,
applicable_date date,
entry_name varchar(255) not null,
entry_value decimal(10,2),
PRIMARY KEY(id)
);*/

create table type1report (
id int NOT NULL AUTO_INCREMENT,
ship_id int,
report_name varchar(255),
monthly_fee decimal(10,2),
static_ip_fee decimal(10,2),
created_by varchar(255),
created_time timestamp,
month varchar(50),
year varchar(50),
business_line_calls decimal(10,2),
rebate decimal(10,2),
rebate1 decimal(10,2),
rebate2 decimal(10,2),
overall_total decimal(10,2),
iridium_citadel_monthly_fee decimal(10,2),
type2_report_id numeric,
PRIMARY KEY(id)
);

create table type1reportmanualentries (
id int NOT NULL AUTO_INCREMENT,
report_id int,
manual_entry_id int,
manual_entry_unit decimal(10,2),
manual_entry_cost_per_unit decimal(10,2),
manual_entry_value decimal(10,2),
PRIMARY KEY(id)
);

/*create table type1reportmanualdata (
id int NOT NULL AUTO_INCREMENT,
report_id int,
manual_entry_id int,
manual_entry_value decimal(10,2),
PRIMARY KEY(id)
);*/

create table type1reportheaderdata (
id int NOT NULL AUTO_INCREMENT,
report_id int,
header_name varchar(4000),
PRIMARY KEY(id)
);

create table type1reportdata (
id int NOT NULL AUTO_INCREMENT,
report_id int,
time varchar(200),
network varchar(200),
from_msisdn varchar(200),
to_msisdn varchar(200),
duration varchar(50),
price decimal(10,2),
rate decimal(10,2),
header_id int,
PRIMARY KEY(id)
);

create table type2reportdata (
id int NOT NULL AUTO_INCREMENT,
report_id int,
cdr_id varchar(50),
msisdn varchar(50),
imsi varchar(50),
call_start varchar(50),
description varchar(100),
b_number varchar(50),
bundle_indicator varchar(50),
unit_price decimal(10,2),
quantity_charged decimal(10,2),
call_type varchar(50),
isp_price decimal(10,2),
mobile_number varchar(50),
other_party_msisdn varchar(50),
duration varchar(50),
call_date varchar(50),
call_time varchar(50),
total_price decimal(10,2),
price1 decimal(10,2),
price2 decimal(10,2),
PRIMARY KEY(id)
);

create table type2rate (
id int NOT NULL AUTO_INCREMENT,
rate_entry_name varchar(200),
rate_type int,
multiplier int,
PRIMARY KEY(id)
);

create table type2parent (
id int NOT NULL AUTO_INCREMENT,
PRIMARY KEY(id)
);

create table type3parent (
id int NOT NULL AUTO_INCREMENT,
PRIMARY KEY(id)
);

create table type3report (
id int NOT NULL AUTO_INCREMENT,
ship_id int,
report_name varchar(255),
fee_charges decimal(10,2),
mapping_name varchar(255),
type3_report_id int,
invoice_no varchar(255),
invoice_date varchar(255),
invoice_subtotal decimal(10,2),
vat decimal(10,2),
other decimal(10,2),
total decimal(10,2),
PRIMARY KEY(id)
);

create table type3reportdata (
id int NOT NULL AUTO_INCREMENT,
report_id int,
item varchar(255),
desciption varchar(255),
units decimal(10,2),
cost_per_unit decimal(10,2),
amount decimal(10,2),
PRIMARY KEY(id)
);

create table type3item (
id int NOT NULL AUTO_INCREMENT,
item varchar(255),
PRIMARY KEY(id)
);

insert into type3item(item) values('Traffic - SMS');
insert into type3item(item) values('Traffic - Voice');
insert into type3item(item) values('Traffic - Broadband');

create table type3description (
id int NOT NULL AUTO_INCREMENT,
descriptipn varchar(255),
PRIMARY KEY(id)
);

/*insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(1,'Basic voice -FBB/PSTN',1,1);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(2,'Basic voice -FBB/Cellular',2,1);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(3,'Fax group 3',2,13);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(4,'Iridium voice',2,21);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(5,'Inmarsat Mini-M',2,6);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(6,'Basic voice - FBB/FBB',2,1);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(7,'Mobile Terminating',1,0);
insert into type2rate(id,rate_entry_name,rate_type,multiplier) values(8,'Prepaid Server',1,0); */

alter table type1report add column voice_rebate decimal(10,4) default 0;
alter table type1report add column data_rebate decimal(10,4) default 0;
alter table type1reportheaderdata add column actual_header_name varchar(4000);
alter table type2reportdata add column billing_period varchar(50);
alter table type2reportdata add column event_id varchar(50);
alter table type2reportdata add column show_in_report int;
alter table type1reportdata add column actual_price decimal(10,4) default 0;
alter table type2reportdata add column quantity decimal(10,2) default 0;
alter table type2reportdata add column record_no int default 0;
alter table ship add column voice_rebate decimal(10,4) default 0;
alter table ship add column data_rebate decimal(10,4) default 0;
alter table type3parent add column overall_total decimal(10,4) default 0;
alter table type3parent add column company_id int;
alter table type3parent add column parent_id int;

create table type3companyparent (
id int NOT NULL AUTO_INCREMENT,
overall_total decimal(10,4) default 0,
PRIMARY KEY(id)
);

alter table type3parent add column format int default 0;
