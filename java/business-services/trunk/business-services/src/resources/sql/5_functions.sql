--function para envio de epc
CREATE OR REPLACE FUNCTION "createRecordsForEPC"(p_epc_id integer)
  RETURNS integer AS
$BODY$
DECLARE
  v_area_id integer;
  v_area_description varchar;
  v_store_id integer;
  v_st_id integer;
  v_st_name varchar;

  v_epr_id integer;
  v_day_of_week integer;
  v_initial_time "time";
  v_duration integer;

  v_eprd_id integer;
  v_eprd_time_sample "time";
  curs1 refcursor;
  curs2 refcursor;
  curs3 refcursor;
  curs4 refcursor;

  v_epm_id integer;
  v_epm_evaluation_order integer;
  v_metric_variable_name varchar;
  v_metric_template_id integer;
  v_mt_description varchar;
  v_mt_evidence_type varchar;
  v_evidence_request_dtype varchar;

  v_s_id integer;
  v_m_id integer;

  v_ep_id integer;
  v_epc_priorization integer;

  result integer;
BEGIN
    result = 0;
    select into v_area_id,
            v_area_description,
            v_store_id,
            v_st_id,
            v_st_name,
            v_epc_priorization a.id as area_id,
            a.description,
            epc.store_id,
            st.id as st_id,
            st.name as st_name,
            epc.priorization
    from extraction_plan_customizing epc,
        place a,
        situation_template st
    where epc.id = p_epc_id and
        epc.store_id = a.store_id and
        a.area_type_id = epc.area_type_id and
        epc.situation_template_id = st.id;


    -- recorre los rangos del EPC
    OPEN curs1 FOR
        select epr.id,
            epr.day_of_week,
            epr.initial_time,
            epr.duration
        from extraction_plan_range epr
        where epr.extraction_plan_customizing_id = p_epc_id
        order by epr.day_of_week,
             epr.initial_time;
    LOOP
        FETCH curs1
            INTO v_epr_id,
                v_day_of_week,
                v_initial_time,
                v_duration;

        EXIT WHEN NOT FOUND;

        -- recorrer todos los detalles del rango
        Open curs2 for
        select eprd.id,
            eprd.time_sample
        from extraction_plan_range_detail eprd
        where eprd.extraction_plan_range_id = v_epr_id
        order by 2;

        loop
            FETCH curs2
                into v_eprd_id,
                v_eprd_time_sample;
            EXIT WHEN NOT FOUND;


            -- crear situacion
            select into v_s_id nextval('situation_seq');
            INSERT INTO public.situation(
                id,
                description,
                process_id,
                 situation_template_id)
            VALUES (
                v_s_id,
                v_st_name || ' ' || v_eprd_time_sample,
                null,
                v_st_id);

            -- recorrer EPM para crear metricas y request
            open curs3 FOR
                select epm.id,
                       epm.evaluation_order,
                       epm.metric_variable_name,
                       epm.metric_template_id,
                       mt.description,
                       mt.evidence_type_element
                from extraction_plan_metric epm,
                     metric_template mt
                where epm.extraction_plan_customizing_id = p_epc_id and
                      epm.metric_template_id = mt.id
                order by epm.evaluation_order;
            loop
                fetch curs3
                      into v_epm_id ,
                           v_epm_evaluation_order ,
                           v_metric_variable_name ,
                           v_metric_template_id ,
                           v_mt_description,
                           v_mt_evidence_type;
                EXIT WHEN NOT FOUND;

                -- crear metric

                select into v_m_id nextval('metric_seq');
                INSERT INTO public.metric(
                    id,
                    description,
                    metric_order,
                     metric_variable_name,
                    area_id,
                    extraction_plan_metric_id,
                      metric_template_id,
                    situation_id,
                    store_id)
                VALUES (
                    v_m_id,
                    v_mt_description || ' ' || v_eprd_time_sample || ' ' || v_area_description,
                    v_epm_evaluation_order,
                    v_metric_variable_name,
                    v_area_id,
                    v_epm_id,
                    v_metric_template_id,
                    v_s_id,
                    v_store_id);

                if (v_mt_evidence_type = 'IMAGE') then
                    v_evidence_request_dtype = 'RequestedImage';
                elsif (v_mt_evidence_type = 'VIDEO') then
                      v_evidence_request_dtype = 'RequestedVideo';
                elsif (v_mt_evidence_type = 'XML') then
                      v_evidence_request_dtype = 'RequestedXml';
                elsif (v_mt_evidence_type = 'PEOPLE_COUNTING') then
                      v_evidence_request_dtype = 'RequestedPeopleCounting';
                elsif (v_mt_evidence_type = 'COGNIMATICS_PEOPLE_COUNTER_141') then
                      v_evidence_request_dtype = 'RequestedCognimaticsPeopleCounter141';
                elsif (v_mt_evidence_type = 'KUMGO_IMAGE') then
                      v_evidence_request_dtype = 'RequestedKumGoImage';
                else
                      RAISE EXCEPTION 'Tipo de Evidencia no Soportado';
                end if;

                -- crear N request
                open curs4 for
                    select rel.evidence_provider_id
                    from rel_extraction_plan_metric_evidence_provider rel
                    where rel.extraction_plan_metric_id = v_epm_id;
                loop
                    fetch curs4 into v_ep_id;
                    EXIT WHEN NOT FOUND;

                    INSERT INTO public.evidence_request(
                        dtype,
                        id,
                        "day",
                        evidence_request_type,
                        evidence_time,
                        "type",
                        duration,
                        evidence_provider_id,
                        extraction_plan_range_detail_id,
                        metric_id
                        -- nuevo campo
                        ,priorization
                        )
                    VALUES (
                        v_evidence_request_dtype,
                        nextval('evidence_request_seq'),
                        v_day_of_week,
                        'SCHEDULED',
                        v_eprd_time_sample,
                        v_mt_evidence_type,
                        v_duration,
                        v_ep_id,
                        v_eprd_id,
                        v_m_id,
                        v_epc_priorization);

                        result = result + 1;
                        -- raise notice ': %', result;

                end loop;
                close curs4;

            end loop;
            close curs3;

        end loop; -- detalles de rango
        close curs2;

     end loop; -- rangos del epc
    close curs1;

     return result;

END;
$BODY$
LANGUAGE plpgsql VOLATILE;
ALTER FUNCTION "createRecordsForEPC"(integer)
  OWNER TO periscope;