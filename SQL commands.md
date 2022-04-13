create table user_role(
	id Serial primary key,
	role_name VARCHAR(30) unique not null
	)

	
insert into user_role (role_name)
values
('Employee'),
('Finance Manager');


create table users(
	id Serial primary key,
	username VARCHAR(30) unique not null,
	password VARCHAR(30) not null,
	first_name VARCHAR(100) not null,
	last_name VARCHAR(100) not null,
	email VARCHAR(100) not null,
	user_role_id int not null,
	constraint user_role_id foreign key (user_role_id) references user_role (id)
);


insert into users (username, password, first_name, last_name, email, user_role_id)
values 
('Unassigned', '00', 'Not', 'assigned', 'null@ca.com', 2),
('manhunter', 'martian','Jon','Jones','mm@jl.org', 2),
('kidflash', 'gozoom', 'Wally', 'West', 'kf@jl.org', 1),
('robin', 'nightwing', 'Dick', 'Greyson', 'birdboy@batman.com',1);

create table reimbursement_type(
	id Serial primary key,
	type VARCHAR(30) unique not null
)

create table reimbursement_status(
	id Serial primary key,
	status VARCHAR(30) unique not null
)

insert into reimbursement_type (type)
values 
('Lodging'),
('Travel'),
('Food'),
('Other');

insert into reimbursement_status  (status)
values 
('Pending'),
('Approved'),
('Denied');

create table tickets (
	id serial primary key,
	name VARCHAR(100) not null,
	status int not null,
	constraint status  foreign key (status) references reimbursement_status (id),
	description VARCHAR(500),
	type int not null,
	constraint type foreign key (type) references reimbursement_type (id),
	amount float not null,
	picture byteA,
	employee int not null,
	constraint employee foreign key (employee) references users (id),
	Create_time timestamp not null,
	finance_manager int not null,
	constraint finance_manager foreign key (finance_manager) references users (id),
	resTime timestamp 
	);

insert into tickets (name, status, description, type, amount, employee, create_time, finance_manager)
values 
('Motel 6', 1, 'Stay at Marysville Motel', 1, 67.99, 3, current_timestamp,  1),
('Delta flight 2070', 1, 'Busiess Trip to New York, Dolly account', 2, 370.99, 3,current_timestamp , 1)

