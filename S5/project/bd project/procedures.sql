-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++1/procedures++++++++++++++++++++++++++++++++++++++


use mydb;
drop procedure if EXISTS createclass;
DELIMITER $$
CREATE procedure createclass(classname varchar(45))
BEGIN
	insert into mydb.class values (classname);
END
$$
DELIMITER ;
-- call createclass('1s2');

-- ----------------------------------------------------------------------------------------------
drop procedure if EXISTS createstudent;
DELIMITER $$
CREATE procedure createstudent(idperson int,firstname varchar(45),lastname varchar(45),date_of_birth date ,mail varchar(45),class varchar(45))
BEGIN

	if (idperson not in(select id from mydb.person)) then
		insert into mydb.person values (idperson,firstname,lastname,date_of_birth,mail);
	end if;
	
    if(idperson not in(select s.idperson from mydb.student s)) then
		insert into mydb.student(classname,idperson) values (class,idperson);
	end if;

END
$$
DELIMITER ;
/* call createstudent(101,'s1','', '2022-10-10' ,'ssss','1s1');
call createstudent(102,'s2','', '2022-10-10' ,'ssss','1s1');
call createstudent(103,'s3','', '2022-10-10' ,'ssss','1s1');
call createstudent(104,'s4','', '2022-10-10' ,'ssss','1s2');
call createstudent(105,'s5','', '2022-10-10' ,'ssss','1s2');
*/
-- ----------------------------------------------------------------------------------------------
drop procedure if EXISTS createparent;
DELIMITER $$
CREATE procedure createparent(idperson int,firstname varchar(45),lastname varchar(45),date_of_birth date ,mail varchar(45),phone varchar(45))
BEGIN
	if (idperson not in(select id from mydb.person)) then
		insert into mydb.person values (idperson,firstname,lastname,date_of_birth,mail);
	end if;
    if (idperson not in(select p.idperson from mydb.parent p)) then
		insert into mydb.parent(idperson,phone) values (idperson,phone);
	end if;
END
$$
DELIMITER ;
-- call createparent(2,'yaya','yaya', '2022-10-10' ,'ssss','066666666');

-- ---------------------------------------------------------------------------------------------------
drop procedure if EXISTS createteacher;
DELIMITER $$
CREATE procedure createteacher(idperson int,firstname varchar(45),lastname varchar(45),date_of_birth date ,mail varchar(45),module varchar(45))
BEGIN
	if (idperson not in(select id from mydb.person)) then
		insert into mydb.person values (idperson,firstname,lastname,date_of_birth,mail);
	end if;
    if (idperson not in(select t.idperson from mydb.teacher t)) then
		insert into mydb.teacher(idperson,module) values (idperson, module);
	end if;
END
$$
DELIMITER ;
/* call createteacher(201,'t1','', '2022-10-10' ,'ssss','ph');
	call createteacher(202,'t2','', '2022-10-10' ,'ssss','math');
    call createteacher(203,'t3','', '2022-10-10' ,'ssss','ss');
    call createteacher(204,'t4','', '2022-10-10' ,'ssss','math');
    call createteacher(205,'t5','', '2022-10-10' ,'ssss','ph');
	
*/
-- ------------------------------------get_idstudent-------------------------------

drop procedure if EXISTS get_idstudent;
DELIMITER $$
CREATE procedure get_idstudent(firstname varchar(45),lastname varchar(45),date_of_birth date)
BEGIN
	declare d int default 0;
    select p.id from mydb.person p where p.firstname=firstname and p.lastname=lastname and p.date_of_birth=date_of_birth into d;
    select s.idstudent from mydb.student s where s.idperson=d; 
END
$$
DELIMITER ;
-- call get_idstudent('s3','','2022-10-10');

-- ------------------------------------------
-- ------------------------------------get_idteacher-------------------------------

drop procedure if EXISTS get_idteacher;
DELIMITER $$
CREATE procedure get_idteacher(firstname varchar(45),lastname varchar(45),date_of_birth date,module varchar(45))
BEGIN
	declare d int default 0;
    select p.id from mydb.person p where p.firstname=firstname and p.lastname=lastname and p.date_of_birth=date_of_birth into d;
    select t.idteacher from mydb.teacher t where t.idperson=d and t.module=module ; 
END
$$
DELIMITER ;
-- call get_idteacher('t1','t1','2010-10-10','math');

-- ------------------------------------------

-- ------------------------------------get_idseance-------------------------------

drop procedure if EXISTS get_idseance;
DELIMITER $$
CREATE procedure get_idseance(classname varchar(45),sdate date,stime time)
BEGIN
	select s.idseance from mydb.seance s where s.classname=classname and s.date=sdate and s.time=stime ; 
END
$$
DELIMITER ;
-- call get_idseance('1s1','2022-10-10','10:00');

-- ------------------------------------------
-- ------------------------------------addchildren-------------------------------

drop procedure if EXISTS addchildren;
DELIMITER $$
CREATE procedure addchildren(idparent int,idstudent int)
BEGIN
	if ((Select s.idparent from mydb.student s WHERE s.idstudent = idstudent) is null) then
		UPDATE mydb.student s SET s.idparent =  idparent WHERE (s.idstudent = idstudent);
	end if;
END
$$
DELIMITER ;
-- call addchildren(1,1);

-- ------------------------------------------
drop procedure if EXISTS addnote;
DELIMITER $$
CREATE procedure addnote(idteacher int,idstudent int,test_point int,evaluation int,examen_point int, notes varchar(90))
BEGIN
	update mydb.following f set f.test_point=test_point,f.evaluation=evaluation,f.examen_point=examen_point,f.notes=notes where f.idteacher=idteacher and f.idstudent=idstudent ;
END
$$
DELIMITER ;
-- call addnote(1,0,20,20,20,'good');

-- -------------------------------------------
drop procedure if EXISTS consultnotes;
DELIMITER $$
CREATE procedure consultnotes(idstudent int)
BEGIN
	select f.idstudent,t.module,p.firstname as teacher_firstname,p.lastname as teacher_lastname,f.evaluation,f.test_point,f.examen_point,f.notes
    from following f,teacher t,person p
    where t.idperson=p.id and f.idstudent=idstudent;
END
$$
DELIMITER ;
-- call consultnotes(1);

-- ---------------------------------------------------------------------------------

drop procedure if EXISTS addseance;
DELIMITER $$
CREATE procedure addseance(idteacher int,class varchar(45),sdate date,stime time)
BEGIN
	insert into mydb.seance (idteacher,classname,date,time,endtime) values (idteacher,class,sdate,stime,time(stime)+interval 59 minute);
END
$$
DELIMITER ;
-- call addseance(2,'1s1','2021-10-14','11:00');

-- --------------------------------------------------------------------------------------
drop procedure if EXISTS addabsence;
DELIMITER $$
CREATE procedure addabsence(idseance int,idstudent int)
BEGIN
	insert into mydb.absence values (idseance,idstudent);
END
$$
DELIMITER ;
-- call addabsence(11,12);
-- -------------------------------------------------------------------


drop procedure if EXISTS consultabsence_of_seance;
DELIMITER $$
CREATE procedure consultabsence_of_seance(idseance int)
BEGIN
	Select a.idstudent,p.firstname,p.lastname,p.date_of_birth
    from mydb.absence a,mydb.student s,mydb.person p
    where a.idstudent=s.idstudent and s.idperson=p.id
    and a.idseance=idseance;
END
$$
DELIMITER ;
-- call consultabsence_of_seance(1);

-- --------------------------------------------------------------------------------------
drop procedure if EXISTS consultabsence_of_student;
DELIMITER $$
CREATE procedure consultabsence_of_student(idstudent int)
BEGIN
	Select s.idseance,t.module,p.firstname,p.lastname,s.date,s.time,s.Endtime
    from mydb.absence a,mydb.seance s,mydb.person p,mydb.teacher t
    where a.idseance=s.idseance and s.idteacher=t.idteacher and t.idperson=p.id
    and a.idstudent=idstudent;
END
$$
DELIMITER ;
-- call consultabsence_of_student(1);
-- -------------------------------------------------------------------------------------------------
drop procedure if EXISTS get_mychildren;
DELIMITER $$
CREATE procedure get_mychildren(idparent int)
BEGIN
	Select s.idstudent
    from mydb.student s
    where s.idparent=idparent;
    
END
$$
DELIMITER ;
-- call get_mychildren(2);
-- -------------------------------------------------------------------------------------------------
drop procedure if EXISTS login_teacher;
DELIMITER $$
CREATE procedure login_teacher(idteacher int,firstname varchar(45),lastname varchar(45))
BEGIN
	Select *
    from mydb.person p,mydb.teacher t
    where t.idperson=p.id
    and t.idteacher=idteacher and p.firstname=firstname and p.lastname=lastname;
END
$$
DELIMITER ;
-- call login_teacher(2,'t1','t1');
-- -------------------------------------------------------------------------------------------------
drop procedure if EXISTS login_parent;
DELIMITER $$
CREATE procedure login_parent(idparent int,firstname varchar(45),lastname varchar(45))
BEGIN
	Select *
    from mydb.person p,mydb.parent pt
    where pt.idperson=p.id
    and pt.idparent=idparent and p.firstname=firstname and p.lastname=lastname;
END
$$
DELIMITER ;
-- call login_parent(2,'p1','p1');
-- +++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
