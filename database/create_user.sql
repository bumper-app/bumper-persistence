DO
$body$
BEGIN
   IF NOT EXISTS (
      SELECT *
      FROM   pg_catalog.pg_user
      WHERE  usename = 'bumper') THEN
      CREATE ROLE bumper LOGIN PASSWORD 'bumper';
   END IF;
END
$body$