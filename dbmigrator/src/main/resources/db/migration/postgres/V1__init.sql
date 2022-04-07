CREATE EXTENSION IF NOT EXISTS "uuid-ossp";
CREATE EXTENSION IF NOT EXISTS pgcrypto;

CREATE OR REPLACE FUNCTION max (uuid, uuid)
    RETURNS uuid AS
$$
BEGIN
   IF $1 IS NULL OR $1 < $2 THEN
       RETURN $2;
   END IF;

   RETURN $1;
END;
$$ LANGUAGE plpgsql;

CREATE AGGREGATE max (uuid)
(
    sfunc = max,
    stype = uuid
);

-- create wrapper function for hashing passwords
CREATE OR REPLACE FUNCTION hash_password(TEXT)
   RETURNS TEXT AS
$$
BEGIN
   IF $1 IS NOT NULL AND length($1) > 2 THEN
      RETURN crypt($1, gen_salt('bf', 10));
   ELSE
      RAISE EXCEPTION 'Pass code provided does not meet length requirement of 3';
   END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION update_user_table_fn()
    RETURNS TRIGGER AS
$$
BEGIN
    IF new.id <> old.id THEN -- help ensure that the id can't be updated once it is created
        RAISE EXCEPTION 'cannot update id once it has been created';
    END IF;

    new.time_updated := clock_timestamp();

    RETURN new;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION password_matches(TEXT, TEXT) -- $1 password, $2 bcrypted
    RETURNS BOOLEAN AS
$$
BEGIN
    RETURN crypt($1, $2) = $1;
END;
$$ LANGUAGE plpgsql;