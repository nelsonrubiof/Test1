ALTER TABLE store ADD CONSTRAINT unique_corporate_store UNIQUE(corporate_id, corporate_store_id);

ALTER TABLE area_type ADD CONSTRAINT unique_corporate_areatype UNIQUE (corporate_id, corporate_area_type_id);  