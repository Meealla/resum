CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE SEQUENCE IF NOT EXISTS hobby_seq;
CREATE SEQUENCE IF NOT EXISTS experience_seq;
CREATE SEQUENCE IF NOT EXISTS personal_data_seq;
CREATE SEQUENCE IF NOT EXISTS education_seq;
CREATE SEQUENCE IF NOT EXISTS social_link_seq;
CREATE SEQUENCE IF NOT EXISTS resume_seq;

CREATE TABLE IF NOT EXISTS personal_data (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                full_name VARCHAR(255) NOT NULL, address VARCHAR(255),
                                bio VARCHAR(255), position VARCHAR(255),
                                phone BIGINT, website VARCHAR(255), email VARCHAR(255));


CREATE TABLE IF NOT EXISTS resume (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                personal_data_id UUID NOT NULL,
                                FOREIGN KEY (personal_data_id) REFERENCES personal_data(id));


CREATE TABLE IF NOT EXISTS hobby (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                    hobby VARCHAR(255), resume_id UUID,
                                    FOREIGN KEY (resume_id) REFERENCES resume(id));

CREATE TABLE IF NOT EXISTS experience (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                    description VARCHAR(255), position VARCHAR(255),
                                    from_year VARCHAR(255), to_year VARCHAR(255), name VARCHAR(255), resume_id UUID,
                                    FOREIGN KEY (resume_id) REFERENCES resume(id));

CREATE TABLE IF NOT EXISTS education (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                description VARCHAR(255), position VARCHAR(255),
                                from_year VARCHAR(255), to_year VARCHAR(255), name VARCHAR(255),
                                resume_id UUID,
                                FOREIGN KEY (resume_id) REFERENCES resume(id));

CREATE TABLE IF NOT EXISTS social_link (id UUID PRIMARY KEY UNIQUE DEFAULT uuid_generate_v4(),
                                link VARCHAR(255), name VARCHAR(255), resume_id UUID,
                                FOREIGN KEY (resume_id) REFERENCES resume(id));





