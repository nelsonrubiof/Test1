insert
into operator_queue (id, activo, name)
values (-1,true,'REJECTED');


-- clasificacion
insert into clasificacion (id, description) values (1,'Leve');
insert into clasificacion (id, description) values (2,'Incide');
insert into clasificacion (id, description) values (3,'Muy Grave');

--motivi_rejected
insert into motivo_rejected (id, description) values (1,'Modificación.');
insert into motivo_rejected (id, description) values (2,'Rechazado por Supervisor.');
insert into motivo_rejected (id, description) values (3,'Cant''do mal ocupado.');
insert into motivo_rejected (id, description) values (4,'Cliente o Vendedor no esta interesado en la zona.');
insert into motivo_rejected (id, description) values (5,'Imagen en Negro.');
insert into motivo_rejected (id, description) values (6,'No se cuenta cliente.');
insert into motivo_rejected (id, description) values (7,'Se cuenta separado a quienes andan juntos.');
insert into motivo_rejected (id, description) values (8,'Mal identificado Posición del Producto.');
insert into motivo_rejected (id, description) values (9,'Cliente esta desde el principio del Video.');
insert into motivo_rejected (id, description) values (10,'Cliente esta mas o menos tiempo.');
insert into motivo_rejected (id, description) values (11,'Mal marcado.');
insert into motivo_rejected (id, description) values (12,'No se cuenta Vendedor o Cajero.');
insert into motivo_rejected (id, description) values (13,'Se cuenta personal de tienda, que no corresponde.');
insert into motivo_rejected (id, description) values (14,'Se cuenta vendedor o cliente dos más veces.');
insert into motivo_rejected (id, description) values (15,'Se cuenta vendedor por cliente o viceversa.');
insert into motivo_rejected (id, description) values (16,'Mal Identificado Faltante o Producto.');
insert into motivo_rejected (id, description) values (17,'Error Muy Grave.');
insert into motivo_rejected (id, description) values (18,'Mal evaluado.');
insert into motivo_rejected (id, description) values (19,'Mal puesto Si o No.');
insert into motivo_rejected (id, description) values (20,'No se evaluó situación.');

-- rel
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(3,1);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(3,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(4,1);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(4,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(5,1);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(5,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(6,1);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(6,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(7,1);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(7,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(8,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(8,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(9,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(9,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(10,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(10,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(11,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(11,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(12,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(12,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(13,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(13,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(14,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(14,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(15,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(15,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(16,2);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(16,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(17,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(18,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(19,3);
insert into rel_motivo_rejected_clasificacion (motivo_rejected_id, clasificacion_id) values(20,3);
