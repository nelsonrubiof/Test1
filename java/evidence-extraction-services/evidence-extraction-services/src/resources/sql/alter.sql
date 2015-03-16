ALTER TABLE evidence_extraction_request ALTER dtype TYPE character varying(255);

-- 20110408 NR
ALTER TABLE evidence_provider ALTER COLUMN dtype TYPE VARCHAR(255);
ALTER TABLE evidence_provider ALTER COLUMN protocol TYPE VARCHAR;
ALTER TABLE evidence_provider ALTER COLUMN port TYPE VARCHAR;

-- 20140304 EMO
ALTER TABLE extraction_server ALTER COLUMN id SET DEFAULT nextval('extraction_server_seq'::regclass);
ALTER TABLE extraction_plan ALTER COLUMN id SET DEFAULT nextval('extraction_plan_seq'::regclass);
ALTER TABLE store_time ALTER COLUMN id SET DEFAULT nextval('store_time_seq'::regclass);
ALTER TABLE situation_request ALTER COLUMN id SET DEFAULT nextval('situation_request_seq'::regclass);
ALTER TABLE metric_request ALTER COLUMN id SET DEFAULT nextval('metric_request_seq'::regclass);
ALTER TABLE situation_sensor ALTER COLUMN id SET DEFAULT nextval('situation_sensor_seq'::regclass);
ALTER TABLE evidence_provider_request ALTER COLUMN id SET DEFAULT nextval('evidence_provider_request_seq'::regclass);
ALTER TABLE situation_request_range ALTER COLUMN id SET DEFAULT nextval('situation_request_range_seq'::regclass);
ALTER TABLE situation_extraction_request ALTER COLUMN id SET DEFAULT nextval('situation_extraction_request_seq'::regclass);

-- 20140423 EMO
-- ALTER TABLE evidence_file ADD COLUMN creation_date timestamp without time zone;
