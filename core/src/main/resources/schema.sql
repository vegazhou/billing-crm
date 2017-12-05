DROP TABLE meeting_records;
DROP TABLE pstn_package_usages;
DROP TABLE pstn_packages;
DROP TABLE pstn_charges;
DROP TABLE billing_progress;
DROP TABLE customer_to_ignore;

CREATE TABLE pstn_packages
(
  id                     VARCHAR(64) PRIMARY KEY,
  order_id               VARCHAR(64)  NOT NULL,
  site_name              VARCHAR(128) NOT NULL,
  customer_id            VARCHAR(64)  NOT NULL,

  start_date             DATE         NOT NULL,
  end_date               DATE         NOT NULL,

  total_minutes          INTEGER      NOT NULL,
  left_minutes           INTEGER      NOT NULL,
  july_2016_left_minutes INTEGER      NULL,

  first_billing_period   NUMBER(6, 0) NULL,
  last_billing_period    NUMBER(6, 0) NULL,

  created_at             DATE DEFAULT CURRENT_TIMESTAMP
);

CREATE INDEX orderid_idx_pstnpackages
  ON pstn_packages (order_id);

CREATE TABLE pstn_package_usages
(
  package_id     VARCHAR(64)  NOT NULL,
  billing_period NUMBER(6, 0) NOT NULL,
  used_minutes   INTEGER      NOT NULL,
  created_at     DATE DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT fk_package_id FOREIGN KEY (package_id) REFERENCES pstn_packages (id),
  CONSTRAINT pk_package_id_billing_period PRIMARY KEY (package_id, billing_period)
);

CREATE INDEX pid_idx_pstnpackageusages
  ON pstn_package_usages (package_id);

CREATE TABLE meeting_records
(
  id               VARCHAR(64) PRIMARY KEY,
  billing_period   NUMBER(6, 0),
  conf_id          NUMBER(19, 0),
  conf_name        VARCHAR2(512),
  customer_id      VARCHAR2(64),
  user_name        VARCHAR2(1024),
  site_name        VARCHAR2(128),
  host_name        VARCHAR2(1024),
  start_time       DATE,
  end_time         DATE,
  user_number      VARCHAR(32),
  access_number    VARCHAR(32),
  is_call_in       VARCHAR(1),
  is_international VARCHAR(1),

  access_type      VARCHAR(64),

  duration         INTEGER,
  coverd_minutes   INTEGER,
  uncoverd_minutes INTEGER,
  price            FLOAT,
  cost             FLOAT
);

CREATE INDEX idx_billing_period
  ON meeting_records (billing_period);


CREATE TABLE pstn_charges
(
  billing_period   NUMBER(6, 0)  NOT NULL,
  order_id         VARCHAR(64)   NOT NULL,
  uncoverd_minutes NUMBER(9, 0)  NOT NULL,
  cost             FLOAT         NOT NULL,
  customer_id      VARCHAR2(64)  NOT NULL,
  site_name        VARCHAR2(128) NOT NULL,
  created_at       DATE DEFAULT CURRENT_TIMESTAMP,
  CONSTRAINT pk_billing_period_order_id PRIMARY KEY (billing_period, order_id)
);

CREATE INDEX idx_site_name_pstn_charges
  ON pstn_charges (site_name);
CREATE INDEX idx_customer_id_pstn_charges
  ON pstn_charges (customer_id);
CREATE INDEX idx_billingperiod_pstn_charges
  ON pstn_charges (billing_period);


CREATE TABLE billing_progress
(
  billing_period  NUMBER(6, 0) NOT NULL,
  customer_id     VARCHAR2(64) NOT NULL,
  type            VARCHAR(32)  NOT NULL, -- PDF_GENERATION/PSTN_FEE_CALCULATION
  total_tasks     INT          NOT NULL,
  succeeded_tasks INT  DEFAULT 0,
  failed_tasks    INT  DEFAULT 0,
  status          VARCHAR(32)  NOT NULL, -- IN_PROGRESS/COMPLETED
  created_at      DATE DEFAULT CURRENT_TIMESTAMP,
  start_time      DATE,
  end_time        DATE,
  CONSTRAINT pk_billing_progress PRIMARY KEY (billing_period, customer_id, TYPE)
);

CREATE TABLE customer_to_ignore
(
  customer_id VARCHAR(64) PRIMARY KEY,
  type        VARCHAR(32) NOT NULL,
  created_at  DATE DEFAULT CURRENT_TIMESTAMP,
  FOREIGN KEY (customer_id) REFERENCES b_customer (pid)
);


-- alter table meeting_records add order_id varchar2(64) null;


