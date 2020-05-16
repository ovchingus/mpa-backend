START TRANSACTION;

update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (8, 9)) as subquery WHERE id = 1
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (9,10)) as subquery WHERE id = 2
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (10,12)) as subquery WHERE id = 3
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (10,11)) as subquery WHERE id = 4
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (11,12)) as subquery WHERE id = 5
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (12,13)) as subquery WHERE id = 6
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (12,17)) as subquery WHERE id = 7
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (12,17)) as subquery WHERE id = 8
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (13,14)) as subquery WHERE id = 10
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (13,17)) as subquery WHERE id = 11
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (13,17)) as subquery WHERE id = 12
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (15,3)) as subquery WHERE id = 13
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (15,16)) as subquery WHERE id = 14
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (15,17)) as subquery WHERE id = 15
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (16,13)) as subquery WHERE id = 16
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (16,4)) as subquery WHERE id = 17
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (17,4)) as subquery WHERE id = 18
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (17,18)) as subquery WHERE id = 19
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (18,5)) as subquery WHERE id = 22
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (18,19)) as subquery WHERE id = 23
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (19,5)) as subquery WHERE id = 24
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (19,6)) as subquery WHERE id = 25
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (8,1)) as subquery WHERE id = 26
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (9,2)) as subquery WHERE id = 27
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (10,2)) as subquery WHERE id = 28
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (11,2)) as subquery WHERE id = 29
update transition set description = subquery.conc_description from (select string_agg(description, ' => ') as conc_description from state where state.id in (14,15)) as subquery WHERE id = 30

--

COMMIT;