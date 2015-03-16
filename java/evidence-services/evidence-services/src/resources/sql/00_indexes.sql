CREATE INDEX "store_time_extraction_plan_id_idx" ON "public"."store_time"
  USING btree ("extraction_plan_id");

CREATE INDEX "situation_sensor_situation_request_id_idx" ON "public"."situation_sensor"
  USING btree ("situation_request_id");

CREATE INDEX "situation_request_range_situation_request_id_idx" ON "public"."situation_request_range"
  USING btree ("situation_request_id");

CREATE INDEX "situation_request_extraction_plan_id_idx" ON "public"."situation_request"
  USING btree ("extraction_plan_id");

CREATE INDEX "situation_extraction_request_situation_request_range_id_idx" ON "public"."situation_extraction_request"
  USING btree ("situation_request_range_id");

CREATE UNIQUE INDEX "unique_process_id_ess_extraction_plan" ON "public"."processees"
  USING btree ("process_id_ees", "extraction_plan_id");

CREATE INDEX "metris_request_situation_request_id_idx" ON "public"."metric_request"
  USING btree ("situation_request_id");

CREATE INDEX "extraction_plan_evidence_extraction_services_server_id_idx" ON "public"."extraction_plan"
  USING btree ("evidence_extraction_services_server_id");

CREATE INDEX "idx_business_services_request_id" ON "public"."evidence_request"
  USING btree ("business_services_request_id");

CREATE INDEX "evidence_request_extraction_plan_detail_id_idx" ON "public"."evidence_request"
  USING btree ("extraction_plan_detail_id");

CREATE INDEX "evidence_provider_request_metric_request_id_idx" ON "public"."evidence_provider_request"
  USING btree ("metric_request_id");

CREATE INDEX "evidence_provider_evidence_provider_id_idx" ON "public"."evidence_provider_request"
  USING btree ("evidence_provider_id");

CREATE INDEX "evidence_provider_evidence_extraction_services_server_id_idx" ON "public"."evidence_provider"
  USING btree ("evidence_extraction_services_server_id");

CREATE INDEX "evidence_extraction_plan_detail_id_idx" ON "public"."evidence"
  USING btree ("extraction_plan_detail_id");