DROP TABLE IF EXISTS songwriter;
DROP TABLE IF EXISTS contribution;
DROP TABLE IF EXISTS project;
DROP TABLE IF EXISTS songwriter_contribution;

CREATE TABLE project (
	project_id int NOT NULL AUTO_INCREMENT,
	creation_date DATE NOT NULL,
	name varchar(128) NOT NULL,
	bpm INT NOT NULL,
	genre varchar(40),
	PRIMARY KEY (project_id)
	FOREIGN KEY (songwriter_id) REFERENCES songwriter (songwriter_id) ON DELETE CASCADE
);

CREATE TABLE songwriter (
	songwriter_id int NOT NULL AUTO_INCREMENT,
	songwriter_username varchar(60) NOT NULL,
	songwriter_email varchar(60) NOT NULL,
	PRIMARY KEY (songwriter_id),
	FOREIGN KEY (contribtuion_id) REFERENCES contribution (contribution_id) ON DELETE CASCADE 
);

CREATE TABLE contribution (
	contribution_id int NOT NULL AUTO_INCREMENT,
	time_stamp varchar(10) NOT NULL,
	PRIMARY KEY (contribution_id),
	FOREIGN KEY (project_id) REFERENCES project (project_id) ON DELETE CASCADE
);

CREATE TABLE songwriter_contribution (
	contribution_id int NOT NULL,
	songwriter_id int NOT NULL,
	FOREIGN KEY (contribtuion_id) REFERENCES contribution (contribution_id) ON DELETE CASCADE,
	FOREIGN KEY (songwriter_id) REFERENCES songwriter (songwriter_id) ON DELETE CASCADE
);