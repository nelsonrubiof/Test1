ALTER TABLE operator_queue_priority ADD CONSTRAINT unique_operator_queue_priority UNIQUE(corporate_id,operator_queue_name);

CREATE INDEX activity_log_index
  ON activity_log
  USING btree
  (user_name DESC NULLS LAST, request_date DESC NULLS LAST);

-- ALTER TABLE subscription ADD CONSTRAINT unique_user_subscription UNIQUE(user_name, operator_queue_priority_id);
-- ALTER TABLE subscription ADD CONSTRAINT unique_group_subscription UNIQUE(operators_group_id, operator_queue_priority_id);