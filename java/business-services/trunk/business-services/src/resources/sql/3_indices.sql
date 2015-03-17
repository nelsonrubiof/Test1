CREATE INDEX "fki_evidence" ON "public"."proof"
  USING btree ("evidence_id");

CREATE INDEX "pending_evaluation_idx1" ON "public"."pending_evaluation"
  USING btree ("observed_situation_id");

CREATE INDEX "rel_evidence_request_evidence_idx" ON "public"."rel_evidence_request_evidence"
  USING btree ("evidence_request_id", "evidence_id");

CREATE INDEX "rel_observed_metric_evidence_idx1" ON "public"."rel_observed_metric_evidence"
  USING btree ("observed_metric_id", "evidence_id");

CREATE INDEX "evidence_idx_exidence_date" ON "public"."evidence"
  USING btree ("evidence_date");

CREATE INDEX "evidence_evaluation_idx_rejected" ON "public"."evidence_evaluation"
  USING btree ("rejected");

CREATE INDEX "evidence_evaluation_idx_pending_evaluation_id" ON "public"."evidence_evaluation"
  USING btree ("pending_evaluation_id");

CREATE INDEX "evidence_evaluation_idx_observed_metric_id" ON "public"."evidence_evaluation"
  USING btree ("observed_metric_id");

CREATE INDEX "metric_idx_store_id" ON "public"."metric"
  USING btree ("store_id");

CREATE INDEX "metric_idx_situation_id" ON "public"."metric"
  USING btree ("situation_id");

CREATE INDEX "metric_idx_metric_template_id" ON "public"."metric"
  USING btree ("metric_template_id");

CREATE INDEX "metric_idx_area_id" ON "public"."metric"
  USING btree ("area_id");

CREATE INDEX "metric_evaluation_idx_observed_metric_id" ON "public"."metric_evaluation"
  USING btree ("observed_metric_id");

CREATE INDEX "observed_metric_idx_observed_situation_id" ON "public"."observed_metric"
  USING btree ("observed_situation_id");

CREATE INDEX "observed_metric_idx_observed_metric_date" ON "public"."observed_metric"
  USING btree ("observed_metric_date");

CREATE INDEX "observed_metric_idx_evaluation_state" ON "public"."observed_metric"
  USING btree ("evaluation_state");

CREATE INDEX "observed_metric_idx_metric_id" ON "public"."observed_metric"
  USING btree ("metric_id");

CREATE INDEX "observed_situation_idx_observed_situation_date" ON "public"."observed_situation"
  USING btree ("observed_situation_date");

CREATE UNIQUE INDEX "observed_situation_evaluation_id_key" ON "public"."observed_situation_evaluation"
  USING btree ("id");

CREATE INDEX "pending_evaluation_in_priority" ON "public"."pending_evaluation"
  USING btree ("priority");

CREATE INDEX "pending_evaluation_in_change_priority" ON "public"."pending_evaluation"
  USING btree ("change_priority");

CREATE INDEX "pending_evaluation_evaluation_stateidx" ON "public"."pending_evaluation"
  USING btree ("evaluation_state");

CREATE INDEX "place_idx_store_id" ON "public"."place"
  USING btree ("store_id");

CREATE INDEX "place_idx_region_id" ON "public"."place"
  USING btree ("region_id");

CREATE INDEX "place_idx_place_type_id" ON "public"."place"
  USING btree ("place_type_id");

CREATE INDEX "place_idx_country_id" ON "public"."place"
  USING btree ("country_id");

CREATE INDEX "place_idx_corporate_id" ON "public"."place"
  USING btree ("corporate_id");

CREATE INDEX "place_idx_area_type_id" ON "public"."place"
  USING btree ("area_type_id");

CREATE INDEX "proof_idx_evidence_evaluation_id" ON "public"."proof"
  USING btree ("evidence_evaluation_id");

CREATE UNIQUE INDEX "rejected_history_id_key" ON "public"."rejected_history"
  USING btree ("id");

CREATE INDEX "situation_template_idx_product_id" ON "public"."situation_template"
  USING btree ("product_id");

CREATE INDEX "situation_situation_template_idx" ON "public"."situation"
  USING btree ("situation_template_id");

CREATE INDEX "idx_relation_evidence_provider_location_ep_to_id" ON "public"."relation_evidence_provider_location"
  USING btree ("evidence_provider_to_id");

CREATE INDEX "idx_relation_evidence_provider_location_ep_from_id" ON "public"."relation_evidence_provider_location"
  USING btree ("evidence_provider_from_id");

CREATE INDEX "rel_om_ev_observed_metric_idx" ON "public"."rel_observed_metric_evidence"
  USING btree ("observed_metric_id");

CREATE INDEX "rel_om_ev_evidence_id_idx" ON "public"."rel_observed_metric_evidence"
  USING btree ("evidence_id");

CREATE INDEX "pending_evaluation_queue_state_os_id_idx" ON "public"."pending_evaluation"
  USING btree ("evaluation_queue", "evaluation_state", "observed_situation_id");

CREATE INDEX "pending_evaluation_evaluation_queue_idx" ON "public"."pending_evaluation"
  USING btree ("evaluation_queue");

CREATE INDEX "pending_evaluarion_observed_situation_idx" ON "public"."pending_evaluation"
  USING btree ("observed_situation_id");

CREATE INDEX "observed_situation_situation_id" ON "public"."observed_situation"
  USING btree ("situation_id");

CREATE INDEX "evidence_request_idx_metric_id" ON "public"."evidence_request"
  USING btree ("metric_id");

CREATE INDEX "idx_evidence_evaluation_evidence_result" ON "public"."evidence_evaluation"
  USING btree ("evidence_result");
  
CREATE INDEX "operator_queue_name_idx" ON "public"."operator_queue"
  USING btree ("name");

CREATE INDEX "idx_pendig_eval_first" ON "public"."pending_evaluation"
  USING btree ("evaluation_queue", "evaluation_state", "operator_queue_id", "priority");

CREATE INDEX "pe_cp_eq_es" ON "public"."pending_evaluation"
  USING btree ("change_priority", "evaluation_queue", "evaluation_state");

CREATE INDEX "pe_eq_es_prior" ON "public"."pending_evaluation"
  USING btree ("priority", "evaluation_queue", "evaluation_state");

CREATE INDEX "rel_evidence_request_evidence_idx1" ON "public"."rel_evidence_request_evidence"
  USING btree ("evidence_id");  
  
CREATE INDEX "fki_indicator_formula" ON "public"."indicator"
  USING btree ("formula_id");

CREATE INDEX idx_reporting_data1
  ON reporting_data
  USING btree
  (store_id, area_id, evidence_date, sent_tomis);

CREATE INDEX idx_observed_stituation_id
  ON reporting_data
  USING btree
  (observed_situation_id);

-- Mejoran el rendimiento de solicitudes de Evaluaciones para Operator
CREATE INDEX evidence_idx1
  ON evidence
  USING btree
  (evidence_services_server_id);

CREATE INDEX evidence_request_idx1
  ON evidence_request
  USING btree
  (evidence_provider_id);

CREATE INDEX observed_situation_evaluation_idx1
  ON observed_situation_evaluation
  USING btree
  (observed_situation_id);

CREATE INDEX indicator_values_idx1
  ON indicator_values
  USING btree
  (observed_situation_id);

CREATE UNIQUE INDEX "metric_template_idx1"
  ON "public"."metric_template"
  USING btree
  ("description");

CREATE UNIQUE INDEX "metric_template_idx2"
  ON "public"."metric_template"
  USING btree
  ("name");

CREATE INDEX "idx_store_id" ON "public"."extraction_plan_customizing"
  USING btree ("store_id");

CREATE INDEX "extracion_plan_customizin_idx_area_type_id" ON "public"."extraction_plan_customizing"
  USING btree ("area_type_id");

CREATE INDEX "evidence_id_idx" ON "public"."rel_evidence_evaluation_evidence"
  USING btree ("evidence_id");

CREATE INDEX "evidence_evaluation_id_idx" ON "public"."rel_evidence_evaluation_evidence"
  USING btree ("evidence_evaluation_id");

CREATE INDEX "extraction_plan_customizing_idx" ON "public"."extraction_plan_metric"
  USING btree ("extraction_plan_customizing_id");

CREATE INDEX "metric_idx_extracion_plan_metric_id" ON "public"."metric"
  USING btree ("extraction_plan_metric_id");

ALTER TABLE "public"."rel_observed_metric_evidence"
  ADD CONSTRAINT "pkey_observed_metric_id_evidence_id"
  PRIMARY KEY ("observed_metric_id", "evidence_id");

CREATE INDEX "idx_sent_tomis" ON "public"."reporting_data"
  USING btree ("sent_tomis");

CREATE INDEX "idx_observed_metric_id" ON "public"."reporting_data"
  USING btree ("observed_metric_id");

CREATE INDEX "idx_evaluation_start_date" ON "public"."reporting_data"
  USING btree ("evaluation_start_date");

CREATE INDEX "evidence_date_idx" ON "public"."reporting_data"
  USING btree ("evidence_date");

CREATE INDEX "pending_evaluation_creation_type_idx" ON "public"."pending_evaluation"
  USING btree ("creation_type");

CREATE INDEX "pending_evaluation_creation_date" ON "public"."pending_evaluation"
  USING btree ("creation_date");

CREATE INDEX "observed_situation_id_idx" ON "public"."quality_evaluation"
  USING btree ("observed_situation_id");

CREATE INDEX "extraction_plan_metric_id_idx" ON "public"."rel_extraction_plan_metric_evidence_provider"
  USING btree ("extraction_plan_metric_id");

CREATE INDEX "evidence_provider_id" ON "public"."rel_extraction_plan_metric_evidence_provider"
  USING btree ("evidence_provider_id");

CREATE INDEX "evidence_provider_store_id_idx" ON "public"."evidence_provider"
  USING btree ("store_id");
  
CREATE INDEX evidence_region_transfer_evidence_id_transmisition_date_reg_idx
  ON evidence_region_transfer
  USING btree
  (evidence_id DESC NULLS LAST, transmisition_date DESC NULLS LAST, region_server_name COLLATE pg_catalog."default" DESC NULLS LAST, completed DESC NULLS LAST);
