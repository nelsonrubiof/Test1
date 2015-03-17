CREATE FUNCTION merge_store(store_id INT, corporate INT, name_store TEXT, descr_store TEXT) RETURNS VOID AS
$$
BEGIN
    LOOP
        update store set name = name_store, description = descr_store
                where
                corporate_id = corporate and
                corporate_store_id = store_id;
        IF found THEN
            RETURN;
        END IF;
        BEGIN
            insert into store (id, name, description, corporate_id, corporate_store_id) values
                (nextval('seq_store_areatype'), name_store, descr_store, corporate, store_id);
            RETURN;
        EXCEPTION WHEN unique_violation THEN
            -- do nothing, and loop to try the UPDATE again
        END;
    END LOOP;
END;
$$
LANGUAGE plpgsql;


CREATE FUNCTION merge_areatype(area_type_id INT, corporate INT, name_areatype TEXT, descr_areatype TEXT) RETURNS VOID AS
$$
BEGIN
    LOOP
        update area_type
                set name = name_areatype, description = descr_areatype
                where
                corporate_id  = corporate and
                corporate_area_type_id = area_type_id;
        IF found THEN
            RETURN;
        END IF;
        BEGIN
	    insert into area_type (id, name, description, corporate_id, corporate_area_type_id) values
            (nextval('seq_store_areatype'), name_areatype, descr_areatype, corporate, area_type_id);
            RETURN;
        EXCEPTION WHEN unique_violation THEN
            -- do nothing, and loop to try the UPDATE again
        END;
    END LOOP;
END;
$$
LANGUAGE plpgsql;