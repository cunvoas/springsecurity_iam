--
-- PostgreSQL database dump
--

-- Dumped from database version 9.3.2
-- Dumped by pg_dump version 9.3.2
-- Started on 2014-01-20 17:45:46

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

--
-- TOC entry 7 (class 2615 OID 16395)
-- Name: iam; Type: SCHEMA; Schema: -; Owner: iam
--

CREATE SCHEMA iam;


ALTER SCHEMA iam OWNER TO iam;

SET search_path = iam, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 172 (class 1259 OID 16404)
-- Name: iam_application; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_application (
    app_id integer NOT NULL,
    app_url character varying(300),
    app_description character varying(200),
    app_code character varying(50)
);


ALTER TABLE iam.iam_application OWNER TO iam;

--
-- TOC entry 2005 (class 0 OID 0)
-- Dependencies: 172
-- Name: COLUMN iam_application.app_url; Type: COMMENT; Schema: iam; Owner: iam
--

COMMENT ON COLUMN iam_application.app_url IS 'URL vers l''application';


--
-- TOC entry 176 (class 1259 OID 16463)
-- Name: iam_ldap_member; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_ldap_member (
    ldap_id integer NOT NULL,
    ldap_query character varying(500),
    ldap_rol_id integer
);


ALTER TABLE iam.iam_ldap_member OWNER TO iam;

--
-- TOC entry 173 (class 1259 OID 16417)
-- Name: iam_ressource; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_ressource (
    res_id integer NOT NULL,
    res_code character varying(100),
    res_borne_inf integer,
    res_borne_sup integer,
    res_app_id integer
);


ALTER TABLE iam.iam_ressource OWNER TO iam;

--
-- TOC entry 174 (class 1259 OID 16427)
-- Name: iam_resval; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_resval (
    rol_id integer NOT NULL,
    res_id integer NOT NULL,
    val_id integer DEFAULT 0
);


ALTER TABLE iam.iam_resval OWNER TO iam;

--
-- TOC entry 2006 (class 0 OID 0)
-- Dependencies: 174
-- Name: TABLE iam_resval; Type: COMMENT; Schema: iam; Owner: iam
--

COMMENT ON TABLE iam_resval IS 'Valeur de ressource pour un role donné';


--
-- TOC entry 171 (class 1259 OID 16396)
-- Name: iam_role; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_role (
    rol_id integer NOT NULL,
    rol_code character varying(50),
    rol_description character varying(200),
    rol_commentaire character varying(1000),
    rol_app_id integer
);


ALTER TABLE iam.iam_role OWNER TO iam;

--
-- TOC entry 177 (class 1259 OID 16476)
-- Name: iam_utilisateur; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_utilisateur (
    uti_id integer NOT NULL,
    util_app_id integer,
    uti_rol_id integer,
    uti_code character varying(30)
);


ALTER TABLE iam.iam_utilisateur OWNER TO iam;

--
-- TOC entry 175 (class 1259 OID 16452)
-- Name: iam_valeur; Type: TABLE; Schema: iam; Owner: iam; Tablespace: 
--

CREATE TABLE iam_valeur (
    val_id integer NOT NULL,
    valeur character varying(12)
);


ALTER TABLE iam.iam_valeur OWNER TO iam;

--
-- TOC entry 180 (class 1259 OID 16495)
-- Name: seq_application; Type: SEQUENCE; Schema: iam; Owner: iam
--

CREATE SEQUENCE seq_application
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iam.seq_application OWNER TO iam;

--
-- TOC entry 2007 (class 0 OID 0)
-- Dependencies: 180
-- Name: seq_application; Type: SEQUENCE OWNED BY; Schema: iam; Owner: iam
--

ALTER SEQUENCE seq_application OWNED BY iam_application.app_id;


--
-- TOC entry 182 (class 1259 OID 16499)
-- Name: seq_ldap_member; Type: SEQUENCE; Schema: iam; Owner: iam
--

CREATE SEQUENCE seq_ldap_member
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iam.seq_ldap_member OWNER TO iam;

--
-- TOC entry 181 (class 1259 OID 16497)
-- Name: seq_ressource; Type: SEQUENCE; Schema: iam; Owner: iam
--

CREATE SEQUENCE seq_ressource
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iam.seq_ressource OWNER TO iam;

--
-- TOC entry 179 (class 1259 OID 16493)
-- Name: seq_role; Type: SEQUENCE; Schema: iam; Owner: iam
--

CREATE SEQUENCE seq_role
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iam.seq_role OWNER TO iam;

--
-- TOC entry 178 (class 1259 OID 16491)
-- Name: seq_utilisateur; Type: SEQUENCE; Schema: iam; Owner: iam
--

CREATE SEQUENCE seq_utilisateur
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE iam.seq_utilisateur OWNER TO iam;

--
-- TOC entry 1990 (class 0 OID 16404)
-- Dependencies: 172
-- Data for Name: iam_application; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_application (app_id, app_url, app_description, app_code) FROM stdin;
1	http://iam.vaubanhumanis.gie.priv	Identity and Access Management : Gestion des accès utilisateur	IAM
\.


--
-- TOC entry 1994 (class 0 OID 16463)
-- Dependencies: 176
-- Data for Name: iam_ldap_member; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_ldap_member (ldap_id, ldap_query, ldap_rol_id) FROM stdin;
1	memberOf=CN=IAM Admin	1
\.


--
-- TOC entry 1991 (class 0 OID 16417)
-- Dependencies: 173
-- Data for Name: iam_ressource; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_ressource (res_id, res_code, res_borne_inf, res_borne_sup, res_app_id) FROM stdin;
2	APPLICATION	2	11	1
4	AJOUTER	4	5	1
5	MODIFIER	6	7	1
6	SUPPRIMER	8	9	1
3	LISTER	3	10	1
7	ROLE	12	21	1
8	LISTER	13	14	1
11	SUPPRIMER	19	20	1
9	AJOUTER	15	16	1
10	MODIFIER	17	18	1
13	LISTER	23	24	1
14	AJOUTER	25	26	1
16	SUPPRIMER	29	30	1
15	MODIFIER	27	28	1
12	RESSOURCE	22	33	1
1	IAM	1	34	1
17	AFFECTER_ROLE	31	32	1
\.


--
-- TOC entry 1992 (class 0 OID 16427)
-- Dependencies: 174
-- Data for Name: iam_resval; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_resval (rol_id, res_id, val_id) FROM stdin;
1	3	2
1	4	3
1	5	3
1	6	3
1	8	2
1	13	2
1	9	3
1	10	3
1	11	3
1	14	3
1	15	3
1	16	3
1	17	3
\.


--
-- TOC entry 1989 (class 0 OID 16396)
-- Dependencies: 171
-- Data for Name: iam_role; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_role (rol_id, rol_description, rol_commentaire, rol_app_id) FROM stdin;
1	Administrateur	Admin IAM	1
\.


--
-- TOC entry 1995 (class 0 OID 16476)
-- Dependencies: 177
-- Data for Name: iam_utilisateur; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_utilisateur (uti_id, util_app_id, uti_rol_id, uti_code) FROM stdin;
1	1	1	ADMIN_IAM
\.


--
-- TOC entry 1993 (class 0 OID 16452)
-- Dependencies: 175
-- Data for Name: iam_valeur; Type: TABLE DATA; Schema: iam; Owner: iam
--

COPY iam_valeur (val_id, valeur) FROM stdin;
1	INVISIBLE
2	VISIBLE
0	NON AFFECTE
3	MODIFICATION
\.


--
-- TOC entry 2008 (class 0 OID 0)
-- Dependencies: 180
-- Name: seq_application; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_application', 1, false);


--
-- TOC entry 2009 (class 0 OID 0)
-- Dependencies: 182
-- Name: seq_ldap_member; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_ldap_member', 1, false);


--
-- TOC entry 2010 (class 0 OID 0)
-- Dependencies: 181
-- Name: seq_ressource; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_ressource', 1, false);


--
-- TOC entry 2011 (class 0 OID 0)
-- Dependencies: 179
-- Name: seq_role; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_role', 1, false);


--
-- TOC entry 2012 (class 0 OID 0)
-- Dependencies: 178
-- Name: seq_utilisateur; Type: SEQUENCE SET; Schema: iam; Owner: iam
--

SELECT pg_catalog.setval('seq_utilisateur', 1, false);


--
-- TOC entry 1863 (class 2606 OID 16411)
-- Name: pk_app; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_application
    ADD CONSTRAINT pk_app PRIMARY KEY (app_id);


--
-- TOC entry 1871 (class 2606 OID 16470)
-- Name: pk_ldap; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_ldap_member
    ADD CONSTRAINT pk_ldap PRIMARY KEY (ldap_id);


--
-- TOC entry 1867 (class 2606 OID 16431)
-- Name: pk_res_rol; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_resval
    ADD CONSTRAINT pk_res_rol PRIMARY KEY (rol_id, res_id);


--
-- TOC entry 1865 (class 2606 OID 16421)
-- Name: pk_ress; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_ressource
    ADD CONSTRAINT pk_ress PRIMARY KEY (res_id);


--
-- TOC entry 1861 (class 2606 OID 16403)
-- Name: pk_rol; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_role
    ADD CONSTRAINT pk_rol PRIMARY KEY (rol_id);


--
-- TOC entry 1873 (class 2606 OID 16480)
-- Name: pk_uti; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_utilisateur
    ADD CONSTRAINT pk_uti PRIMARY KEY (uti_id);


--
-- TOC entry 1869 (class 2606 OID 16456)
-- Name: pk_val; Type: CONSTRAINT; Schema: iam; Owner: iam; Tablespace: 
--

ALTER TABLE ONLY iam_valeur
    ADD CONSTRAINT pk_val PRIMARY KEY (val_id);


--
-- TOC entry 1875 (class 2606 OID 16422)
-- Name: fk_res_app; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_ressource
    ADD CONSTRAINT fk_res_app FOREIGN KEY (res_app_id) REFERENCES iam_application(app_id);


--
-- TOC entry 1876 (class 2606 OID 16442)
-- Name: fk_resval_res; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_resval
    ADD CONSTRAINT fk_resval_res FOREIGN KEY (res_id) REFERENCES iam_ressource(res_id);


--
-- TOC entry 1877 (class 2606 OID 16447)
-- Name: fk_resval_rol; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_resval
    ADD CONSTRAINT fk_resval_rol FOREIGN KEY (rol_id) REFERENCES iam_role(rol_id);


--
-- TOC entry 1878 (class 2606 OID 16458)
-- Name: fk_resval_val; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_resval
    ADD CONSTRAINT fk_resval_val FOREIGN KEY (val_id) REFERENCES iam_valeur(val_id);


--
-- TOC entry 1879 (class 2606 OID 16471)
-- Name: fk_rol_ldap; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_ldap_member
    ADD CONSTRAINT fk_rol_ldap FOREIGN KEY (ldap_rol_id) REFERENCES iam_role(rol_id);


--
-- TOC entry 1874 (class 2606 OID 16412)
-- Name: fk_role_app; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_role
    ADD CONSTRAINT fk_role_app FOREIGN KEY (rol_app_id) REFERENCES iam_application(app_id);


--
-- TOC entry 1881 (class 2606 OID 16486)
-- Name: fk_uti_app; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_utilisateur
    ADD CONSTRAINT fk_uti_app FOREIGN KEY (util_app_id) REFERENCES iam_application(app_id);


--
-- TOC entry 1880 (class 2606 OID 16481)
-- Name: fk_uti_rol; Type: FK CONSTRAINT; Schema: iam; Owner: iam
--

ALTER TABLE ONLY iam_utilisateur
    ADD CONSTRAINT fk_uti_rol FOREIGN KEY (uti_rol_id) REFERENCES iam_role(rol_id);


-- Completed on 2014-01-20 17:45:46

--
-- PostgreSQL database dump complete
--

