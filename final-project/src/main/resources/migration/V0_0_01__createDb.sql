create table users (
    id int unique auto_increment primary key,
    type varchar(10),
    role int,
    email varchar(25),
    password varchar(100),
    enabled boolean,
    name varchar(15),
    account_number varchar(15),
    balance double,
    currency varchar(3),
    date_of_birth datetime
);
create table email_token (
    email_token_id int unique auto_increment primary key,
    user_id int unique,
    token varchar(100),
    expiry_date BIGINT
);
create table wagers (
    wager_id int unique auto_increment primary key,
    player_id int,
    outcome_odd_id int,
    amount double,
    won_money double,
    currency varchar(3),
    processed boolean,
    win boolean,
    time_stamp datetime
);
create table outcome_odds (
    outcome_odd_id int unique auto_increment primary key,
    outcome_id int,
    odd double,
    from_date datetime,
    to_date datetime
);
create table outcomes (
    outcome_id int unique auto_increment primary key,
    sport_event_id int,
    bet_id int,
    value varchar(100)
);
create table bets (
    bet_id int unique auto_increment primary key,
    sport_event_id int,
    description varchar(150),
    type int
);
create table sport_events (
    sport_event_id int unique auto_increment primary key,
    sport_event_type int,
    title varchar(100),
    start_date datetime,
    end_date datetime
);
