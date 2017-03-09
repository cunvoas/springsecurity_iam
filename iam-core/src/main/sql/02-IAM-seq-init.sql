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

SET search_path = iam, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;


SELECT pg_catalog.setval('iam.seq_application', (SELECT MAX(app_id) FROM iam.iam_application), false);
SELECT pg_catalog.setval('iam.seq_ldap_member', (SELECT MAX(ldap_id) FROM iam.iam_ldap_member), false);
SELECT pg_catalog.setval('iam.seq_ressource', (SELECT MAX(res_id) FROM iam.iam_ressource), false);
SELECT pg_catalog.setval('iam.seq_role', (SELECT MAX(rol_id) FROM iam.iam_role), false);
SELECT pg_catalog.setval('iam.seq_utilisateur', (SELECT MAX(uti_id) FROM iam.iam_utilisateur), false);

