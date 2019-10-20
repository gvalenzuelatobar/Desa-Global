CREATE TABLE persona(
  ID_PERSONA int not null,
  NOMBRE varchar(50) ,
  EMAIL varchar(50) ,
  PASSWORD varchar(50),
  FECHA_CREACION Date,
  FECHA_MODIFICACION Date,
  LAST_LOGIN Date,
  TOKEN int, 
  ACTIVO boolean,
  PRIMARY KEY (id_persona)
);

CREATE TABLE phones(
  number int not null,
  citycode int ,
  contrycode int ,
  id_persona int not null,
  PRIMARY KEY (number, id_persona ),
  FOREIGN KEY (id_persona) REFERENCES persona(id_persona)
);