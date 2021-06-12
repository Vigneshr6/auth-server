insert into user (id,username,password,email,dob) values
 (1,'vigneshun','$2y$10$IL2jI8tPQbpyM20HPFWy1.reNW0H0d7ICO3LKMj/XVamvV.YNcYH6','vignesh@examplecom',parsedatetime('1994-06-22','yyyy-MM-dd'));

 insert into user_roles(user_id,role) values
 (1,1);