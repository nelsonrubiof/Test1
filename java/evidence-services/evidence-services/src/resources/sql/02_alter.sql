ALTER TABLE evidence_extraction_services_server ALTER COLUMN id SET DEFAULT nextval('evidence_extraction_services_server_seq'::regclass);
ALTER TABLE evidence_provider_request ALTER COLUMN id SET DEFAULT nextval('evidence_provider_request_seq'::regclass);
ALTER TABLE metric_request ALTER COLUMN id SET DEFAULT nextval('metric_request_seq'::regclass);
ALTER TABLE situation_extraction_request ALTER COLUMN id SET DEFAULT nextval('situation_extraction_request_seq'::regclass);
ALTER TABLE situation_request_range ALTER COLUMN id SET DEFAULT nextval('situation_request_range_seq'::regclass);
ALTER TABLE situation_sensor ALTER COLUMN id SET DEFAULT nextval('situation_sensor_seq'::regclass);
ALTER TABLE situation_request ALTER COLUMN id SET DEFAULT nextval('situation_request_seq'::regclass);

