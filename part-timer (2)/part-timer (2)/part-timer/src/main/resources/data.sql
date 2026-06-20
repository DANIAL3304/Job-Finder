-- Insert Users
INSERT INTO app_user (name, email, password, phone_no, role) VALUES
                                                                 ('Alice', 'alice@example.com', 'password1', '1234567890', 'USER'),
                                                                 ('Bob', 'bob@example.com', 'password2', '0987654321', 'ADMIN'),
                                                                 ('Charlie', 'charlie@example.com', 'password3', '1122334455', 'USER'),
                                                                 ('Diana', 'diana@example.com', 'password4', '6677889900', 'USER');

ALTER TABLE app_user ALTER COLUMN user_id RESTART WITH 5;

-- Insert Companies
INSERT INTO companies (name, email, password, location) VALUES
                                                            ('TechCorp', 'contact@techcorp.com', 'compassword1', '123 Tech Street'),
                                                            ('BizGroup', 'info@bizgroup.com', 'compassword2', '456 Biz Avenue'),
                                                            ('EduSmart', 'hello@edusmart.com', 'compassword3', '789 Learning Blvd'),
                                                            ('GreenTech', 'contact@greentech.com', 'compassword4', '321 Green Lane');

ALTER TABLE companies ALTER COLUMN company_id RESTART WITH 5;

-- Insert Jobs (linked to companies)
INSERT INTO jobs (name, description, job_type, location, time, salary, date, company_id) VALUES
                                                                                             ('Software Engineer', 'Develop and maintain software.', 'Full-Time', 'Remote', '9-5', 6000, '2024-06-01', 1), -- TechCorp
                                                                                             ('Marketing Specialist', 'Plan and execute marketing campaigns.', 'Part-Time', 'City Center', '10-4', 4000, '2024-06-02', 2), -- BizGroup
                                                                                             ('Data Analyst', 'Analyze datasets for business insight.', 'Full-Time', 'HQ Office', '8-4', 5000, '2024-06-03', 3), -- EduSmart
                                                                                             ('UI Designer', 'Design user interfaces for apps.', 'Full-Time', 'Remote', 'Flexible', 4500, '2024-06-04', 1),
                                                                                                ('Sales Associate', 'Engage with customers in-store.', 'Part-Time', 'Mall Outlet', '12-6', 3500, '2024-06-05', 4); -- GreenTech

ALTER TABLE jobs ALTER COLUMN job_id RESTART WITH 6;

-- Insert Applications (linked to users and jobs)
INSERT INTO applications (application_date, status, user_id, job_id) VALUES
                                                                         ('2024-06-10', 'ACCEPTED', 1, 1),  -- Alice → Software Engineer
                                                                         ('2024-06-11', 'ACCEPTED', 2, 2), -- Bob → Marketing Specialist
                                                                         ('2024-06-12', 'PENDING', 3, 3),  -- Charlie → Data Analyst
                                                                         ('2024-06-13', 'REJECTED', 1, 3), -- Alice → Data Analyst
                                                                         ('2024-06-14', 'PENDING', 4, 4),  -- Diana → UI Designer
                                                                         ('2024-06-14', 'ACCEPTED', 3, 2), -- Charlie → Marketing Specialist
                                                                         ('2024-06-15', 'PENDING', 1, 4);  -- Alice → UI Designer

ALTER TABLE applications ALTER COLUMN application_id RESTART WITH 8;
