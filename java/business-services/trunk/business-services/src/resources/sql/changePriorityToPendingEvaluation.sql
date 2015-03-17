CREATE OR REPLACE FUNCTION changeprioritytopendingevaluation(posi integer, queue_name_id integer)
  RETURNS void AS
$BODY$
DECLARE
prio_primero_marcado integer;
prio_primero_no_marcado integer;
dif_prio integer;
pos integer;
pendingEvaluationId integer;
curs1 refcursor;
curs2 refcursor;
curs3 refcursor;

BEGIN

    -- actualizar registros anteriores a la posicion posi
    pos := 1;
    OPEN curs1 FOR
        SELECT id
        FROM pending_evaluation
        WHERE change_priority = false AND
              evaluation_queue = 'OPERATOR' AND
              evaluation_state = 'ENQUEUED' AND
              operator_queue_id = queue_name_id
        ORDER BY priority
        limit posi-1;
    LOOP
        FETCH curs1 INTO pendingEvaluationId;
        EXIT WHEN NOT FOUND;

        UPDATE pending_evaluation
        SET priority = pos
        WHERE id = pendingEvaluationId;

        pos := pos + 1;
    END LOOP;


    -- actualizar registros marcados a posiciones siguientes
     OPEN curs2 FOR
        SELECT id
        FROM pending_evaluation
        WHERE change_priority = true AND
              evaluation_queue = 'OPERATOR' AND
              evaluation_state = 'ENQUEUED' AND
              operator_queue_id = queue_name_id
        ORDER BY priority;
    LOOP
        FETCH curs2 INTO pendingEvaluationId;
        EXIT WHEN NOT FOUND;

        UPDATE pending_evaluation
        SET priority = pos
        WHERE id = pendingEvaluationId;
        pos := pos + 1;
    END LOOP;


    -- actualizar el resto de los registros no marcados
    OPEN curs3 FOR
        SELECT id
        FROM pending_evaluation
        WHERE change_priority = false AND
              evaluation_queue = 'OPERATOR' AND
              evaluation_state = 'ENQUEUED' AND
              operator_queue_id = queue_name_id and
              priority >= posi
        ORDER BY priority;
    LOOP
        FETCH curs3 INTO pendingEvaluationId;
        EXIT WHEN NOT FOUND;

        UPDATE pending_evaluation
        SET priority = pos
        WHERE id = pendingEvaluationId;
        pos := pos + 1;
    END LOOP;


    -- borrar marca
    update pending_evaluation
    set change_priority = FALSE
    WHERE change_priority = true AND
          evaluation_queue = 'OPERATOR' AND
          evaluation_state = 'ENQUEUED' AND
          operator_queue_id = queue_name_id;

   RETURN;
END;
$BODY$
  LANGUAGE 'plpgsql' VOLATILE;
ALTER FUNCTION changeprioritytopendingevaluation(integer, integer) OWNER TO periscope;
