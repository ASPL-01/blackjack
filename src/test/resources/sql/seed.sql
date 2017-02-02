use blackjacktest;

set FOREIGN_KEY_CHECKS = 0;
truncate table users;
truncate table games;
set FOREIGN_KEY_CHECKS = 1;

insert into `users` (`username`, `balance`)
values
  ('nzukoff', 10000.00);

insert into `games` (`outcome`, `user_id`, `dealer_cards`, `dealer_value`, `player_cards`, `player_value`, `bet`)
values
  ('WON', 1, '6', 6, '6:Q', 16, 100),
  ('LOST', 1, 'Q', 10,'9:8', 17, 200),
  ('WON', 1, '10', 10,'3:2:3:J', 18, 100),
  ('LOST', 1, '2', 2, '7:7', 14, 300);
