CREATE USER 'work_exchange_app_system_login'@'localhost' IDENTIFIED BY 'work_exchange_app_system_pass';

GRANT ALL PRIVILEGES ON work_exchange_db.account_status TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.message_status TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.messages TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.order_chats TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.order_status TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.orders TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.user_role TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON work_exchange_db.users TO 'work_exchange_app_system_login'@'localhost';

GRANT ALL PRIVILEGES ON PROCEDURE addCreatedAccountPassByUserLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON PROCEDURE isFreeAccountLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON PROCEDURE saveAndGetActivationCodeByLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON PROCEDURE saveAndGetActivationCodeByUserIdProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT ALL PRIVILEGES ON PROCEDURE getUserIdByActivationCodeProcedure TO 'work_exchange_app_system_login'@'localhost';

FLUSH PRIVILEGES;


GRANT EXECUTE ON PROCEDURE addCreatedAccountPassByUserLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT EXECUTE ON PROCEDURE isFreeAccountLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT EXECUTE ON PROCEDURE saveAndGetActivationCodeByLoginProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT EXECUTE ON PROCEDURE saveAndGetActivationCodeByUserIdProcedure TO 'work_exchange_app_system_login'@'localhost';
GRANT EXECUTE ON PROCEDURE getUserIdByActivationCodeProcedure TO 'work_exchange_app_system_login'@'localhost';