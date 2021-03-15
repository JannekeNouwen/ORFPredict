-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-03-10 11:13:16.942

-- foreign keys
ALTER TABLE blast_result
    DROP FOREIGN KEY blast_result_blast_search;

ALTER TABLE blast_search
    DROP FOREIGN KEY blast_search_orf;

ALTER TABLE orf
    DROP FOREIGN KEY orf_orf_prediction;

ALTER TABLE orf_prediction
    DROP FOREIGN KEY orf_prediction_user;

-- tables
DROP TABLE blast_result;

DROP TABLE blast_search;

DROP TABLE orf;

DROP TABLE orf_prediction;

DROP TABLE user;

-- End of file.

