
use mydb;

DROP TRIGGER IF EXISTS `student_AFTER_INSERT_create_following`;

DELIMITER $$

CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`student_AFTER_INSERT_create_following` AFTER INSERT ON `student` 
FOR EACH ROW
BEGIN
	declare n int default 0;
    declare t int;
    declare i int default 0;
    declare s int;
    set s=new.idstudent;
    select count(*) from mydb.teacher into n; 
	while i<n do
		select idteacher from mydb.teacher limit i,1 into t;
        if (t in (select distinct idteacher from mydb.seance where classname=new.classname)) then 
			insert  mydb.following (idteacher,idstudent)values (t,s);
        end if;
        set i=i+1;
    end while;
	
END
$$
DELIMITER ;


-- -----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS `seance_AFTER_INSERT_create_following`;

DELIMITER $$

CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`seance_AFTER_INSERT_create_following` AFTER INSERT ON `seance` 
FOR EACH ROW
BEGIN
	declare m varchar(45);
    declare n int default 0;
    declare m1 varchar(45);
    declare i int default 0;
    declare somme int default 0;
    declare s int;
    declare t int;
    set t=new.idteacher;
    
    select module from mydb.teacher where idteacher=new.idteacher into m;
    select count(*) from following where idstudent in(select idstudent from mydb.student where classname=new.classname) into n;
    while (i<n) do
		select module from mydb.teacher where idteacher in (select idteacher from following where idstudent in(select idstudent from mydb.student where classname=new.classname) ) limit i,1 into m1;
        if (m1=m) then
			set somme=1;
        end if;
        set i=i+1;
    end while;


    if (somme=0) then
		set i=0;
		select count(*) from  mydb.student st where st.classname=new.classname into n;
		while (i<n) do
			select st.idstudent from  mydb.student st where st.classname=new.classname limit i,1 into s;
			insert into mydb.following (idteacher,idstudent)values (t,s);
            set i=i+1;
		end while;
    end if;

	
END
$$
DELIMITER ;

-- -----------------------------------------------------------------------------------
DROP TRIGGER IF EXISTS `seance_BEFORE_INSERT`;

DELIMITER $$

CREATE DEFINER = CURRENT_USER TRIGGER `mydb`.`seance_BEFORE_INSERT` BEFORE INSERT ON `seance` 
FOR EACH ROW
BEGIN
	declare n int default 0;
	select count(*) from mydb.seance s where s.classname=new.classname and s.date=new.date
    and (	
			(s.time between time(new.time) and time(new.time)+interval 59 minute)
			or
			(s.Endtime between time(new.time) and time(new.time)+interval 59 minute)
		) into n ;
    if (n>0) then
		signal sqlstate'45000'
        set message_text='this class isn\'t availble in this time';
    end if;
END
$$
DELIMITER ;
