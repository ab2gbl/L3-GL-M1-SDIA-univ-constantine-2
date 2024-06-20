alter table following
add check (examen_point>=0 and examen_point<=20 and
			test_point>=0 and test_point<=20 and
            evaluation>=0 and evaluation<=20 );