-- 1/import __;
-- ----------2/creer visiteur---------------
use tourismetp6;
create user 'visiteur'@'localhost' identified by '';
grant insert,delete on tourismetp6.reserver_hotel to 'visiteur'@'localhost';
flush privileges;
-- ----------3/creer gestionnaire---------------
create user 'gestionnaire'@'localhost' identified by '';
grant insert on tourismetp6.hotel to 'gestionnaire'@'localhost';
flush privileges;
grant insert on tourismetp6.sitetouristique to 'gestionnaire'@'localhost';
flush privileges;
grant insert,delete on tourismetp6.bus to 'gestionnaire'@'localhost';
flush privileges;
-- ----------4/Consulter_hotel---------------
drop PROCEDURE  if exists Consulter_hotel;
set DELIMITER $$	
CREATE PROCEDURE Consulter_hotel(in site varchar(50),in type_cuisine varchar(50),in date_visite date)
BEGIN
	select distinct h.nom from hotel h ,reserver_hotel rh,restaurant r,type_cuisine tc
	where h.nom=rh.hotel
	and h.nom=r.hotel
	and r.type_cuisine=tc.label
	and h.site =site
	and label=type_cuisine 
    and (select count(*) from tourismetp6.reserver_hotel where hotel=h.nom and date_res<date_visite)-
    (select count(*) from tourismetp6.reserver_hotel where hotel=h.nom and (date_add(date_res,interval NB_jours day))<date_visite)<h.NB_Chambres;
    
END $$
set DELIMITER ;

-- call Consulter_hotel('sitetest','type1','2022-1-5' );

-- ----------5/gerer_reserver_hotel---------------
drop PROCEDURE if exists gerer_reserver_hotel;
set DELIMITER $$
CREATE PROCEDURE gerer_reserver_hotel(in visiteur varchar(50),in hotel varchar(50),in date_debut date,in date_fin date)
BEGIN
declare vs varchar(50);
declare h varchar(50);
declare dr date;
declare b int default 0;
declare n int default 0;
declare i int default 0;
declare x int;
declare y int;
declare z int;

select count(*) from tourismetp6.reserver_hotel into n; 
while i<n do
	select rh.visiteur,rh.hotel,rh.date_res from tourismetp6.reserver_hotel rh limit i,1 into vs,h,dr;
    if(vs= visiteur and h=hotel)then
		set b=1;
    end if;
    set i=i+1;
end while;
if(b=0)then 
	set x=(select count(*) from tourismetp6.reserver_hotel rh where rh.hotel=hotel and rh.date_res<date_debut);
    set y=(select count(*) from tourismetp6.reserver_hotel rh where rh.hotel=hotel and (date_add(rh.date_res,interval rh.NB_jours day))<date_debut);
    set z=(select NB_Chambres from tourismetp6.hotel h where h.nom=hotel);
    if(x-y<z) then 
		insert into tourismetp6.reserver_hotel values(visiteur,hotel,date_debut,datediff(date_fin,date_debut));
        select 'nw reserve is saved' as msg;
    end if;
else 
	if(date_add((select now()),interval 48 hour)<(select date_res from tourismetp6.reserver_hotel rs where rs.hotel=hotel and rs.Visiteur=visiteur)) then
		update tourismetp6.reserver_hotel rh set date_res=date_debut,NB_jours=(datediff(date_fin,date_debut)) where rh.visiteur=visiteur and rh.hotel=hotel;
        select 'update of reserve' as msg;
    else
		select 'can\'t update cause of time' as msg;
	end if;
end if;
    
End $$

-- call gerer_reserver_hotel('v2','test1','2023-1-1','2023-1-5');



-- ----------6/maj_bus---------------

drop PROCEDURE if exists maj_bus;
set DELIMITER $$
CREATE PROCEDURE maj_bus(in ligne int)
begin
	declare h varchar(50);
	declare n int default 0;
    declare i int default 0;
    declare ct int default 0;
    declare b int default 0;
    select count(*) from tourismetp6.dessert_hotel into n; 
	while i<n do
		select nomhotel from tourismetp6.dessert_hotel where ligne_bus=ligne  limit i,1 into h;
        select count(*) from tourismetp6.dessert_hotel where ligne_bus!=ligne and nomhotel=h into ct;
        if(ct=0) then 
			set b=1;
		end if;
        set i=i+1;
    end while;
    if(b=0) then
		DELETE FROM tourismetp6.dessert_hotel where ligne_bus=ligne;
        DELETE FROM tourismetp6.bus b where b.ligne=ligne;
        select 'ligne deleted' as msg;
	elseif(b=1) then
		select 'can\'t delete ligne because there is hotels haven\'t other ligne' as msg;
	end if;
end $$
set DELIMITER ;
-- call maj_bus(3);

/*	
	---on peut utuliser ce trigger mais je pense que procedure est mieux--
	
    drop trigger if exists  bus_BEFORE_DELETE;
	set DELIMITER $$
	CREATE DEFINER = CURRENT_USER TRIGGER `tourismetp6`.`bus_BEFORE_DELETE` BEFORE DELETE ON `bus` FOR EACH ROW
	BEGIN
		declare h varchar(50);
		declare n int default 0;
		declare i int default 0;
		declare ct int default 0;
		declare b int default 0;
		select count(*) from tourismetp6.dessert_hotel into n; 
		while i<n do
			select nomhotel from tourismetp6.dessert_hotel where ligne_bus=old.ligne  limit i,1 into h;
			select count(*) from tourismetp6.dessert_hotel where ligne_bus!=old.ligne and nomhotel=h into ct;
			if(ct=0) then 
				set b=1;
			end if;
			set i=i+1;
		end while;
		if(b=0) then
			DELETE FROM tourismetp6.dessert_hotel where ligne_bus=old.ligne;
		else
			signal sqlstate '45000' set message_text='cant delete';
		end if;
	END $$
    
	CREATE PROCEDURE maj_bus(in ligne int)
	begin
		DELETE FROM tourismetp6.bus b where b.ligne=ligne;
    end $$
	set DELIMITER ;
*/

-- ----------7/statistiques--------------- 
drop PROCEDURE if exists statistiques;
set DELIMITER $$
CREATE PROCEDURE statistiques(in site varchar(50),in mois int)
begin
	select h.nom,count(*) nb_de_rsrv from tourismetp6.hotel h,tourismetp6.reserver_hotel rh,tourismetp6.sitetouristique s
    where h.nom=rh.hotel
    and h.site=s.nom
    and h.site=site
    and month(date_res)=mois
    group by h.nom;
    
end $$
set DELIMITER ;
-- call statistiques('sitetest',1);

-- ------------------------------------------
-- ------------------------------------------
-- pour q'un utilisateur(visiteur ou gestionnaire) puisse utiliser les procedure , il doi disposer les privileges suivants


grant execute on procedure tourismetp6.Consulter_hotel  to 'gestionnaire'@'localhost','visiteur'@'localhost';
flush privileges;
grant execute on procedure tourismetp6.gerer_reserver_hotel  to 'gestionnaire'@'localhost','visiteur'@'localhost';
flush privileges;
grant execute on procedure tourismetp6.maj_bus  to 'gestionnaire'@'localhost';
flush privileges;
grant execute on procedure tourismetp6.statistiques  to 'gestionnaire'@'localhost','visiteur'@'localhost';
flush privileges;

