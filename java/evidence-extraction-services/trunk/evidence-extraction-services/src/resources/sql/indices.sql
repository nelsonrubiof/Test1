
--20110408

CREATE INDEX evidence_file_idx_filename ON evidence_file (filename);
CREATE INDEX idx_evidence_file_evidence_extraction_request_id ON evidence_file (evidence_extraction_request_id);
