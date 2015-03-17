    CREATE TABLE activity_log
    (
      "id" serial NOT NULL,
      "pending_evaluation_id" integer NOT NULL,
      "user_name" character varying(255) NOT NULL,
      "status" character varying(255) NOT NULL,
      "corporate_name" character varying(255) NOT NULL,
      "queue_name" character varying(255),
      "request_date" timestamp NOT NULL,
      "send_date" timestamp,
      CONSTRAINT "activity_log_pkey" PRIMARY KEY ("id")
    )
    WITH (
      OIDS=FALSE
    );
    ALTER TABLE activity_log OWNER TO periscope;