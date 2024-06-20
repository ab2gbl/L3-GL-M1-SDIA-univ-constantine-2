-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++1/users++++++++++++++++++++++++++++++++++++++

use mydb;
DROP USER IF EXISTS 'admin_p'@'localhost','parent'@'localhost','teacher'@'localhost';
CREATE USER 'admin_p'@'localhost' IDENTIFIED BY '';
CREATE USER 'parent'@'localhost' IDENTIFIED BY '';
CREATE USER 'teacher'@'localhost' IDENTIFIED BY '';
-- ---------------------------------------------------------------------------------
-- admin_p
GRANT all PRIVILEGES on mydb.* TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.createclass TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.createstudent TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.createparent TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.createteacher TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.get_idstudent TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.addchildren TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.addnote TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.consultnotes TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.addseance TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.addabsence TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.consultabsence_of_seance TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.consultabsence_of_student TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE `mydb`.get_idteacher TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.get_idseance TO 'admin_p'@'localhost';
FLUSH PRIVILEGES;





-- ----------------------------------------------------------------------
-- teacher 

GRANT EXECUTE ON PROCEDURE mydb.login_teacher TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.get_idstudent TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.addnote TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.consultabsence_of_seance TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.addabsence TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.consultabsence_of_student TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.consultnotes TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.get_idseance TO 'teacher'@'localhost';
FLUSH PRIVILEGES;
-- -----------------------------------------------------------------------
-- parent 
GRANT EXECUTE ON PROCEDURE mydb.login_parent TO 'parent'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.get_idstudent TO 'parent'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.addchildren TO 'parent'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.get_mychildren TO 'parent'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.consultabsence_of_student TO 'parent'@'localhost';
FLUSH PRIVILEGES;
GRANT EXECUTE ON PROCEDURE mydb.consultnotes TO 'parent'@'localhost';
FLUSH PRIVILEGES;
-- ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
