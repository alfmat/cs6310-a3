CREATE DATABASE cs6310_fa22_team068;
GO

USE cs6310_fa22_team068;
GO

CREATE MASTER KEY ENCRYPTION BY PASSWORD = 'someR!COOL';
GO

-- Tables 

CREATE TABLE Users (
  id VARCHAR(250) NOT NULL,
  position VARCHAR(250) NOT NULL,
  pwd VARBINARY(250) NOT NULL,
  PRIMARY KEY (id)
);

-- Creating Certs and Key for DB columns
CREATE CERTIFICATE Passwords  
   WITH SUBJECT = 'Customer Passwords';  
GO  

CREATE SYMMETRIC KEY User_Passwords  
    WITH ALGORITHM = AES_256  
    ENCRYPTION BY CERTIFICATE Passwords;  
GO  

-- Open the symmetric key with which to encrypt the data.  
OPEN SYMMETRIC KEY User_Passwords
   DECRYPTION BY CERTIFICATE Passwords;  
GO

INSERT INTO Users (id, position, pwd) VALUES ('admin_test', 'admin', EncryptByKey(Key_GUID('User_Passwords'), 'admin_pass'));
INSERT INTO Users (id, position, pwd) VALUES ('customer_test', 'customer', EncryptByKey(Key_GUID('User_Passwords'), 'customer_pass'));
INSERT INTO Users (id, position, pwd) VALUES ('employee_test', 'employee', EncryptByKey(Key_GUID('User_Passwords'), 'employee_pass'));