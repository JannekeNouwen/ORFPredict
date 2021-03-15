-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-03-10 11:13:16.942

-- tables
-- Table: blast_result
CREATE TABLE blast_result (
    id int NOT NULL AUTO_INCREMENT,
    acc_code varchar(20) NOT NULL,
    e_value double(32,30) NOT NULL,
    seq text NOT NULL,
    aligned_seq text NOT NULL,
    identity_percent double(32,4) NOT NULL,
    title varchar(300) NOT NULL,
    blast_search_id int NOT NULL,
    CONSTRAINT blast_result_pk PRIMARY KEY (id)
);

-- Table: blast_search
CREATE TABLE blast_search (
    orf_id int NOT NULL,
    `database` varchar(30) NOT NULL,
    id int NOT NULL AUTO_INCREMENT,
    organism int NULL,
    exclude bool NOT NULL DEFAULT false,
    max_target_sequences int NOT NULL DEFAULT 100,
    expect_threshold int NOT NULL DEFAULT 0.05,
    word_size int NOT NULL DEFAULT 28,
    CONSTRAINT blast_search_pk PRIMARY KEY (id)
);

-- Table: orf
CREATE TABLE orf (
    id int NOT NULL AUTO_INCREMENT,
    seq text NOT NULL,
    orf_prediction_id int NOT NULL,
    start_pos int NOT NULL,
    CONSTRAINT orf_pk PRIMARY KEY (id)
);

-- Table: orf_prediction
CREATE TABLE orf_prediction (
    id int NOT NULL AUTO_INCREMENT,
    name varchar(30) NULL,
    seq text NOT NULL,
    user_id int NOT NULL,
    acc_code varchar(20) NULL,
    header varchar(200) NULL,
    CONSTRAINT orf_prediction_pk PRIMARY KEY (id)
);

-- Table: user
CREATE TABLE user (
    id int NOT NULL AUTO_INCREMENT,
    username varchar(30) NOT NULL,
    password varchar(100) NOT NULL,
    UNIQUE INDEX username (username),
    CONSTRAINT user_pk PRIMARY KEY (id)
);

-- foreign keys
-- Reference: blast_result_blast_search (table: blast_result)
ALTER TABLE blast_result ADD CONSTRAINT blast_result_blast_search FOREIGN KEY blast_result_blast_search (blast_search_id)
    REFERENCES blast_search (id);

-- Reference: blast_search_orf (table: blast_search)
ALTER TABLE blast_search ADD CONSTRAINT blast_search_orf FOREIGN KEY blast_search_orf (orf_id)
    REFERENCES orf (id);

-- Reference: orf_orf_prediction (table: orf)
ALTER TABLE orf ADD CONSTRAINT orf_orf_prediction FOREIGN KEY orf_orf_prediction (orf_prediction_id)
    REFERENCES orf_prediction (id);

-- Reference: orf_prediction_user (table: orf_prediction)
ALTER TABLE orf_prediction ADD CONSTRAINT orf_prediction_user FOREIGN KEY orf_prediction_user (user_id)
    REFERENCES user (id);

-- End of file.

