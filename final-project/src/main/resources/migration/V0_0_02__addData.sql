insert into users (type, role, email, password, enabled, name, account_number, balance, currency, date_of_birth) values ("player", 1, "1@mail.ru", "$2a$10$3qdeP7QtbhFD/spUi4m1wuAOsXdtrJniISJSxW1Oam.Pq4BOztZ3W", true, "Player", "0000000-0000001",
 1000.0, "UAH", '2000-01-19');
insert into users (type, role, email, password, enabled, name, account_number, balance, currency, date_of_birth) values ("admin", 0, "2@mail.ru", "$2a$10$3qdeP7QtbhFD/spUi4m1wuAOsXdtrJniISJSxW1Oam.Pq4BOztZ3W", true, "Admin", "0000000-0000002",
 1000.0, "UAH", '2000-01-19');

insert into sport_events (sport_event_type, title, start_date, end_date) values (0, "Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round.", "2020-04-04", "2020-05-04");

insert into bets (type, description, sport_event_id) values (1, "Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round, bet on the winner.", 1);
insert into bets (type, description, sport_event_id) values (2, "Rafael Nadal vs. Alexander Zverev, Indian Wells 4th Round, bet on the score.", 1);

insert into outcomes (value, bet_id) values ("The winner is Rafael Nadal.", 1);
insert into outcomes (value, bet_id) values ("The winner is Alexander Zverev.", 1);
insert into outcomes (value, bet_id) values ("Rafael Nadal's score is 2.", 2);
insert into outcomes (value, bet_id) values ("Alexander Zverev's score is 2.", 2);

insert into outcome_odds(odd, from_date, to_date, outcome_id) values (1.5, '2020-02-9', '2020-03-02', 1);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (1.05, '2020-03-10', '2020-04-02', 1);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (0.75, '2020-01-10', '2020-02-02', 1);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (1.75, '2020-02-9', '2020-03-02', 2);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (1.25, '2020-03-10', '2020-04-02', 2);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (0.5, '2020-01-10', '2020-02-02', 2);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (2.75, '2020-02-9', '2020-03-02', 3);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (2.0, '2020-03-10', '2020-04-02', 3);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (0.25, '2020-01-10', '2020-02-02', 3);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (3.25, '2020-02-9', '2020-03-02', 4);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (2.25, '2020-03-10', '2020-04-02', 4);
insert into outcome_odds(odd, from_date, to_date, outcome_id) values (0.55, '2020-01-10', '2020-02-02', 4);

insert into wagers (amount, won_money, currency, processed, win, time_stamp, player_id, outcome_odd_id) values (200.0, 0.0, "UAH", true, false, '2020-01-22', 1, 3);
insert into wagers (amount, currency, processed, time_stamp, player_id, outcome_odd_id) values (100.0, "UAH", false, '2020-02-10', 1, 4);
