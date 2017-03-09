--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.2
-- Started on 2014-03-17 10:34:37

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

SET search_path = iam, pg_catalog;

--
-- TOC entry 1990 (class 0 OID 16404)
-- Dependencies: 172
-- Data for Name: iam_application; Type: TABLE DATA; Schema: iam; Owner: iam
--

INSERT INTO iam_application (app_id, app_url, app_description, app_code) VALUES (1, 'http://iam.vaubanhumanis.gie.priv', 'Identity and Access Management : Gestion des accès utilisateur', 'IAM');


--
-- TOC entry 1989 (class 0 OID 16396)
-- Dependencies: 171
-- Data for Name: iam_role; Type: TABLE DATA; Schema: iam; Owner: iam
--

INSERT INTO iam_role (rol_id, rol_description, rol_commentaire, rol_app_id, rol_code) VALUES (1, 'Administrateur', 'Admin IAM', 1, 'ADMIN');

INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (2, 'APPLICATION', 2, 11, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (7, 'ROLE', 12, 21, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (8, 'LISTER', 13, 14, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (11, 'SUPPRIMER', 19, 20, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (10, 'MODIFIER', 17, 18, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (13, 'LISTER', 23, 24, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (16, 'SUPPRIMER', 29, 30, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (15, 'MODIFIER', 27, 28, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (12, 'RESSOURCE', 22, 33, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (17, 'AFFECTER_ROLE', 31, 32, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (3, 'LISTER', 3, 4, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (6, 'SUPPRIMER', 9, 10, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (1, 'IAM', 1, 36, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (18, 'ACCUEIL', 34, 35, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (4, 'CREER', 5, 6, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (9, 'CREER', 15, 16, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (14, 'CREER', 25, 26, 1);
INSERT INTO iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) VALUES (5, 'UPLOAD', 7, 8, 1);


INSERT INTO iam_valeur (val_id, valeur) VALUES (1, 'INVISIBLE');
INSERT INTO iam_valeur (val_id, valeur) VALUES (2, 'VISIBLE');
INSERT INTO iam_valeur (val_id, valeur) VALUES (0, 'NON AFFECTE');
INSERT INTO iam_valeur (val_id, valeur) VALUES (3, 'ACTION');


INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 4, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 5, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 6, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 8, 2);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 13, 2);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 9, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 10, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 11, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 14, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 15, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 16, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 17, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 3, 3);
INSERT INTO iam_resval (rol_id, res_id, val_id) VALUES (1, 18, 2);


--
-- TOC entry 1995 (class 0 OID 16476)
-- Dependencies: 177
-- Data for Name: iam_utilisateur; Type: TABLE DATA; Schema: iam; Owner: iam
--

INSERT INTO iam_utilisateur (uti_id, util_app_id, uti_rol_id, uti_code) VALUES (1, 1, 1, 'ADMIN_IAM');


--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 180
-- Name: seq_application; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_application', 28, true);


--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 182
-- Name: seq_ldap_member; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_ldap_member', 1, false);


--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 181
-- Name: seq_ressource; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_ressource', 21, true);


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 179
-- Name: seq_role; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_role', 6, true);


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 178
-- Name: seq_utilisateur; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_utilisateur', 1, false);


-- Completed on 2014-03-17 10:34:37

--
-- PostgreSQL database dump complete
--

