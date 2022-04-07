CREATE TABLE administrator
(
    id           UUID                                              NOT NULL DEFAULT uuid_generate_v1() PRIMARY KEY,
    time_created TIMESTAMPTZ DEFAULT CLOCK_TIMESTAMP()             NOT NULL,
    time_updated TIMESTAMPTZ DEFAULT CLOCK_TIMESTAMP()             NOT NULL,
    username     VARCHAR(50) CHECK ( CHAR_LENGTH(username) > 3 )   NOT NULL,
    password     VARCHAR(100) CHECK ( CHAR_LENGTH(password) > 10 ) NOT NULL
);

CREATE TRIGGER update_audit_exception_note_trg
    BEFORE UPDATE
    ON administrator
    FOR EACH ROW
EXECUTE PROCEDURE update_user_table_fn();