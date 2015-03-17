CREATE SEQUENCE evidence_requests_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
ALTER TABLE evidence_requests_seq OWNER TO periscope;

CREATE SEQUENCE extraction_plan_detail_seq
 INCREMENT 1
 MINVALUE 1
 MAXVALUE 9223372036854775807
 START 1
 CACHE 1;
ALTER TABLE extraction_plan_detail_seq OWNER TO periscope;

CREATE SEQUENCE process_id_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE process_id_seq OWNER TO periscope;

CREATE SEQUENCE evidence_extraction_services_server_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE evidence_extraction_services_server_seq OWNER TO periscope;

CREATE SEQUENCE evidence_provider_request_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE evidence_provider_request_seq OWNER TO periscope;

CREATE SEQUENCE metric_request_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE metric_request_seq OWNER TO periscope;

CREATE SEQUENCE situation_extraction_request_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE situation_extraction_request_seq OWNER TO periscope;

CREATE SEQUENCE situation_request_range_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE situation_request_range_seq OWNER TO periscope;

CREATE SEQUENCE situation_sensor_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE situation_sensor_seq OWNER TO periscope;

CREATE SEQUENCE situation_request_seq
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE situation_request_seq OWNER TO periscope;
