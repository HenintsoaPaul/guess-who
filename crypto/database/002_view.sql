create or replace view v_current_commission_rate as
select tab1.*
from commission_rate tab1
         join (select id_commission_type, max(add_date) as dd
               from commission_rate cr
               group by cr.id_commission_type) tab2
              on tab1.id_commission_type = tab2.id_commission_type and tab1.add_date = tab2.dd