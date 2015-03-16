CREATE TABLE reject_error (
  id integer NOT NULL,
  ose_id text,
  indicator_values text,
  CONSTRAINT pk_id PRIMARY KEY (id)
);
ALTER TABLE reject_error OWNER TO periscope;

CREATE TABLE reporting_data
(
  id integer NOT NULL DEFAULT nextval('reporting_data_seq'::regclass),
  area_id integer,
  cant_do boolean NOT NULL,
  cant_do_reason character varying,
  date_record timestamp without time zone,
  department text,
  evaluation_start_date timestamp without time zone,
  evaluation_end_date timestamp without time zone,
  evaluation_user text,
  evidence_date timestamp without time zone,
  metric_template_id integer,
  metric_val double precision,
  metric_count integer,
  observed_situation_id integer,
  product text,
  reject boolean NOT NULL,
  rejected_date timestamp without time zone,
  rejected_user text,
  rule_name text,
  sent_tomis boolean NOT NULL,
  sent_tomisdate timestamp without time zone,
  situation_template_id integer,
  state text,
  store_id integer,
  target double precision,
  upload_process_id integer,
  observed_metric_id integer,
  CONSTRAINT reporting_data_pkey PRIMARY KEY (id),
  CONSTRAINT fk9e838f1bda07e6b0 FOREIGN KEY (upload_process_id)
      REFERENCES upload_process (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION,
  CONSTRAINT fk9e838f1bf24b42c1 FOREIGN KEY (observed_situation_id)
      REFERENCES observed_situation (id) MATCH FULL
      ON UPDATE NO ACTION ON DELETE NO ACTION
);
ALTER TABLE reporting_data OWNER TO periscope;