
/*
CREATE TABLE role (
    id integer NOT NULL,
    libelle VARCHAR2(50),
    code VARCHAR2(10),
    status integer,
    population integer
);
ALTER TABLE role
    ADD CONSTRAINT role_pkey PRIMARY KEY (id);

CREATE TABLE personne (
    id integer NOT NULL,
    nom VARCHAR2(50),
    prenom VARCHAR2(50),
    email VARCHAR2(50),
    telephone VARCHAR2(50),
    userlogin VARCHAR2(50),
    motdepassecrypte VARCHAR2(100),
    actif boolean,
    dateexpiration date,
    role_id integer
);
ALTER TABLE personne
    ADD CONSTRAINT personne_pkey PRIMARY KEY (id);
ALTER TABLE personne
    ADD CONSTRAINT fk1a6a27ccbdaa2246 FOREIGN KEY (role_id) REFERENCES role(id);
    
CREATE SEQUENCE seq_personne
    START WITH 1
    INCREMENT BY 1;
    

INSERT INTO role (id, libelle, code, status, population) VALUES (0, 'Admin AST', 'AST', 2, 2);
INSERT INTO role (id, libelle, code, status, population) VALUES (1, 'Compta', 'COMPTA', 0, 2);
INSERT INTO role (id, libelle, code, status, population) VALUES (2, 'Gest. Client', 'GEST_CLI', 1, 2);
INSERT INTO role (id, libelle, code, status, population) VALUES (3, 'Médecin', 'MEDECIN', 0, 1);
INSERT INTO role (id, libelle, code, status, population) VALUES (4, 'Secrétaire administrative', 'SEC_ADMIN', 0, 1);
INSERT INTO role (id, libelle, code, status, population) VALUES (5, 'Secrétaire Médicale', 'SEC_MEDIC', 0, 1);

INSERT INTO personne (id, nom, prenom, email, telephone, userlogin, motdepassecrypte, actif, dateexpiration, role_id) VALUES (0, 'UNVOAS', 'Christophe', 'christophe.unvoas@gmail.com', NULL, 'CUS', 'eEocfXut0Wiz4TlfMWIHTtKCoTCdxlawQQaMWYToneSDcLHN0LBUG62oNFaVPfpk', true, '2020-12-12', 0);

*/
