CREATE TABLE processedmessages (
      id bigserial NOT NULL,
      this_service text NOT NULL,
      next_service text
);

CREATE TABLE invokedservices (
      id bigserial NOT NULL,
      name text NOT NULL,
      invoke_time TIMESTAMPTZ
);