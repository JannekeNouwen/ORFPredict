-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2021-02-20 13:09:29.455

-- foreign keys
ALTER TABLE blast_result
    DROP FOREIGN KEY blast_result_orf;

ALTER TABLE orf
    DROP FOREIGN KEY orf_orf_prediction;

ALTER TABLE orf_prediction
    DROP FOREIGN KEY orf_prediction_user;

-- tables
DROP TABLE blast_result;

DROP TABLE orf;

DROP TABLE orf_prediction;

DROP TABLE user;

-- End of file.

