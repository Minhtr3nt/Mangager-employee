
CREATE DATABASE identity_service;

USE identity_service;

CREATE TABLE `user` (
  `id` varchar(255) NOT NULL,
  `dob` date DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `username` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- identity_service.`role` definition
CREATE TABLE `role` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- identity_service.user_roles definition

CREATE TABLE `user_roles` (
  `user_id` varchar(255) NOT NULL,
  `roles_name` varchar(255) NOT NULL,
  PRIMARY KEY (`user_id`,`roles_name`),
  KEY `FK6pmbiap985ue1c0qjic44pxlc` (`roles_name`),
  CONSTRAINT `FK55itppkw3i07do3h7qoclqd4k` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `FK6pmbiap985ue1c0qjic44pxlc` FOREIGN KEY (`roles_name`) REFERENCES `role` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- identity_service.permission definition

CREATE TABLE `permission` (
  `name` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- identity_service.role_permissions definition

CREATE TABLE `role_permissions` (
  `role_name` varchar(255) NOT NULL,
  `permissions_name` varchar(255) NOT NULL,
  PRIMARY KEY (`role_name`,`permissions_name`),
  KEY `FKf5aljih4mxtdgalvr7xvngfn1` (`permissions_name`),
  CONSTRAINT `FKcppvu8fk24eqqn6q4hws7ajux` FOREIGN KEY (`role_name`) REFERENCES `role` (`name`),
  CONSTRAINT `FKf5aljih4mxtdgalvr7xvngfn1` FOREIGN KEY (`permissions_name`) REFERENCES `permission` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- identity_service.invalidated_token definition

CREATE TABLE `invalidated_token` (
  `id` varchar(255) NOT NULL,
  `expiry_time` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO identity_service.`user` (id,dob,first_name,last_name,password,username) VALUES
	 ('0f1d5103-a231-4998-bf5d-585c82c24dc8','2003-09-02','Doctor','Strange','$2a$10$GqLnc7NI6p8ru02Jl12/oePqQXwachPqDZnjYxMUCEf/bv2uMdzPK','ChiThui1'),
	 ('2a54c571-d45a-4fdb-9976-ada6e188568b',NULL,NULL,NULL,'$2a$10$vPIx6SX9SjyNmh8XKd7a8efcEenIOSaeTABkU9nXpTqgEvj4tiMWq','admin'),
	 ('7ea50efe-5335-444e-957e-d658c5ff8216','2003-09-02','Doctor','Strange','$2a$10$hT3LUw5jbbLtth93Hp1tIOYcSDVes6zQa.nWTt9K.bQbvkW1Igcza','ChiThui3'),
	 ('88b8cb49-5bcf-4c33-b971-d9570ec286e9','2003-09-02','Doctor','Strange','$2a$10$uC1fYvMYY3PqKpOydAfJQefvwazlsuB.eUBwwaFPfA.YjMiIyv2vq','ChiThui9'),
	 ('8c607a0d-56b4-4d39-ae2e-0b3a4954b05c','2003-09-02','Doctor','Strange','$2a$10$PyH/uXNbDuEYToemNCE1qu9xOaOwYIitOLyW.ORMcPWJNx0nbtLsu','ChiThui8'),
	 ('9e62bdc2-081a-450c-8012-e17eb01c7101','2013-09-02','Doctor','Strange','$2a$10$7P0u6IcXH9OEwpvaXEtBpuMRT9ZNt5WDBN4kKFfhG2rW4ebx.esje','ChiThui5'),
	 ('af0072ab-a1ff-47eb-8353-aae339cd8f8d','2003-09-02','Doctor','Strange','$2a$10$x4izqgRLS4f9lo7sv98CfOyEMS3yCMhcaXSsrYPPJNk62LBucKtPa','ChiThui4'),
	 ('ee4ab813-15d6-4b42-9a29-f98bfeec36d8','2003-09-02','Doctor','Strange','$2a$10$g7bCvh848x4EwNJgy5XMfuWcjumInpE/d0ZlWcwK0l80Qei.cxfy.','ChiThui2');


INSERT INTO identity_service.`role` (name,description) VALUES
	 ('ADMIN','Admin role'),
	 ('USER','User role');

INSERT INTO identity_service.permission (name,description) VALUES
	 ('APPROVE_POST','Approve a post'),
	 ('CREATE_POST','Create a post'),
	 ('REJECT_POST','Reject a post'),
	 ('UPDATE_POST','Update a post');

INSERT INTO identity_service.user_roles (user_id,roles_name) VALUES
	 ('2a54c571-d45a-4fdb-9976-ada6e188568b','ADMIN'),
	 ('88b8cb49-5bcf-4c33-b971-d9570ec286e9','USER'),
	 ('8c607a0d-56b4-4d39-ae2e-0b3a4954b05c','USER'),
	 ('9e62bdc2-081a-450c-8012-e17eb01c7101','USER');

INSERT INTO identity_service.role_permissions (role_name,permissions_name) VALUES
	 ('ADMIN','APPROVE_POST'),
	 ('USER','APPROVE_POST'),
	 ('ADMIN','CREATE_POST'),
	 ('ADMIN','REJECT_POST'),
	 ('ADMIN','UPDATE_POST');
     

INSERT INTO identity_service.invalidated_token (id,expiry_time) VALUES
	 ('005cc89a-642a-4716-a646-3a6689431e94','2024-11-08 02:08:47'),
	 ('0b24530a-011c-45f0-959f-5cd43ca6a116','2024-11-08 12:52:58'),
	 ('2cbc7773-2d3a-4d3b-b707-3d4e520f315f','2024-11-08 02:07:41'),
	 ('31132329-c824-44b4-b911-0a3543b030a7','2024-11-08 12:52:08'),
	 ('9c946212-81fd-4d10-8344-76778aca1910','2024-11-07 17:48:34'),
	 ('c2afd5d5-c756-4014-b7e8-c11423b62b50','2024-11-08 12:54:37');



