﻿CREATE TABLE VALUE_MOBILE_AREA(
	ID int PRIMARY KEY NOT NULL,
	AREA_CODE varchar(10) NULL,
	CITY varchar(20) NULL,
	CREATE_TIME datetime2(7) NULL,
	N1_7 bigint NULL,
	PROVINCE varchar(20) NULL,
	TYPE varchar(15) NULL,
	UPDATOR varchar(10) NULL,
	ZIP_CODE varchar(6) NULL
)

CREATE TABLE VALUE_ID_AREA(
	ID int PRIMARY KEY NOT NULL,
	AREA varchar(60) NULL,
	CITY varchar(20) NULL,
	CREATE_TIME datetime2(7) NULL,
	CREATOR varchar(10) NULL,
	N1_2 varchar(2) NULL,
	N3_4 varchar(2) NULL,
	N5_6 varchar(2) NULL,
	PROVINCE varchar(20) NULL,
	XIAN varchar(20) NULL)

CREATE TABLE VALUE_BIN(
	ID int PRIMARY KEY NOT NULL,
	BANK varchar(10) NULL,
	BANK_NO int NULL,
	BIN_NO varchar(20) NULL,
	CARD_LEVEL varchar(10) NULL,
	CARD_NAME varchar(10) NULL,
	CARD_NO varchar(10) NULL,
	CREATE_TIME datetime2(7) NULL,
	FULL_NAME varchar(50) NULL,
	ICON varchar(10) NULL,
	IS_VALID bit NULL,
	LEN_BIN smallint NULL,
	LEN_CARD smallint NULL,
	TYPE varchar(10) NULL)

CREATE TABLE STAFF(
	STAFF_ID varchar(20)  PRIMARY KEY NOT NULL,
	CREATE_TIME datetime2(7) NULL,
	CREATOR varchar(10) NULL,
	ENROLL_TIME datetime2(7) NULL,
	QUIT_TIME datetime2(7) NULL,
	ROLE smallint NULL,
	STATUS varchar(10) NULL)

CREATE TABLE CREDIT_LIMIT_RANGE(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	FROM_VALUE bigint NULL,
	SCALE float NULL,
	TO_VALUE bigint NULL)

CREATE TABLE MEMBER(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	BLOCK_CODE varchar(10) NULL,
	CREATE_TIME datetime2(7) NULL,
	EDUCATION int NULL,
	email varchar(30) NULL,
	INDUSTRY int NULL,
	PRE_CRL int NULL,
	PRE_SCORE float NULL,
	PRE_RATING varchar(2) NULL,
	MOBILE varchar(20) NULL,
	NAME varchar(50) NULL,
	ORG_FLAG int NULL,
	PASSWORD varchar(30) NULL,
	POINTS int NULL,
	SEX int NULL,
	STATUS int NULL,
	TYPE int NULL,
	USER_NAME varchar(50) NULL,
	WC_CITY varchar(20) NULL,
	WC_NO varchar(50) NULL,
	WC_PROVINCE varchar(10) NULL,
	WC_SIGNATURE varchar(50) NULL,
	WC_USER_NAME varchar(50) NULL)

CREATE TABLE LAST_CREDIT(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	CREATE_TIME datetime2(7) NULL,
	LAST_APPL_NO varchar(20) NULL,
	LAST_DECISION varchar(10) NULL,
	LAST_PBOC_BACK_TIME datetime2(7) NULL,
	LAST_RATING varchar(10) NULL,
	LAST_REASON_1 varchar(10) NULL,
	LAST_REASON_2 varchar(10) NULL,
	LAST_REASON_3 varchar(10) NULL,
	LAST_SCORE float NULL,
	MEMBER_ID bigint NULL)

CREATE TABLE DICTIONARY(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	CREATE_TIME date NULL,
	CRL_INDEX float NULL,
	STATUS int NULL,
	TYPE varchar(10) NULL,
	VALUE varchar(50) NULL,
	NAME varchar(50) NULL)

CREATE TABLE PRICING(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	RATING varchar(2) NULL,
	TERM int NULL,
	APR float NULL,
	SAVED float NULL)

CREATE TABLE ID_SEQUENCE(
	NAME varchar(20) PRIMARY KEY NOT NULL,
	NEXT_VALUE bigint NULL)

CREATE TABLE APPL(
	APPL_NO varchar(20) PRIMARY KEY NOT NULL,
	AMT float NULL,
	BILL_ID bigint NULL,
	CC_ID bigint NULL,
	CREATE_TIME date NULL,
	ID_ID bigint NULL,
	REPAY_TYPE int NULL,
	STATUS int NULL,
	TERM int NULL,
	TITLE varchar(100) NULL,
	MEMBER_ID bigint NOT NULL,
	EXISTING_FLAG int NULL)


CREATE TABLE ACCOUNT(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	CRL int NULL,
	CRL_AVL int NULL,
	CRL_USED int NULL,
	DEBIT_AMT float NULL,
	MEMBER_ID bigint NOT NULL)

CREATE TABLE BILL(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	AMT_RMB float NULL,
	AMT_USD float NULL,
	BANK smallint NULL,
	CREATE_TIME datetime2(7) NULL,
	CRL bigint NULL,
	CYCLE_FROM datetime2(7) NULL,
	CYCLE_THRU datetime2(7) NULL,
	EMAIL varchar(255) NULL,
	IMAGE varchar(255) NULL,
	PAY_DUE date NULL,
	SOURCE int NULL,
	MEMBER_ID bigint NULL)

CREATE TABLE ID_CARD(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	ADDRESS varchar(200) NULL,
	BIRTHDAY datetime2(7) NULL,
	CITY varchar(255) NULL,
	CREATE_TIME datetime2(7) NULL,
	ID_NO varchar(20) NULL,
	IMAGE_BACK varchar(20) NULL,
	IMAGE_FRONT varchar(20) NULL,
	ISSUER varchar(30) NULL,
	NAME varchar(50) NULL,
	NAME_ENG varchar(20) NULL,
	NATIONALITY varchar(20) NULL,
	PROVINCE varchar(20) NULL,
	SEX int NULL,
	VALID_FROM datetime2(7) NULL,
	VALID_THRU datetime2(7) NULL,
	MEMBER_ID bigint NULL)

CREATE TABLE CREDITCARD(
	ID int IDENTITY(1,1) PRIMARY KEY NOT NULL,
	BANK smallint NULL,
	CARD_NO varchar(20) NULL,
	CREATE_TIME datetime2(7) NULL,
	EMAIL varchar(50) NULL,
	IMAGE varchar(30) NULL,
	NAME varchar(30) NULL,
	NAME_ENG varchar(30) NULL,
	PASSWORD varchar(50) NULL,
	STATUS smallint NULL,
	TYPE smallint NULL,
	VALID_FROM datetime2(7) NULL,
	VALID_THRU datetime2(7) NULL,
	MEMBER_ID bigint NULL,
	ISVALID bit NULL)

CREATE TABLE APPROVAL(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	APPL_NO varchar(20) NOT NULL,
	AMT float NULL,
	SUG_CRL float NULL,
	APR float NULL,
	CLASS varchar(10) NULL,
	CREATE_TIME datetime2(7) NULL,
	CREDITOR varchar(10) NULL,
	DECISION varchar(10) NULL,
	OPINION varchar(100) NULL,
	PROFILE varchar(100) NULL,
	REASON_1 varchar(10) NULL,
	REASON_2 varchar(10) NULL,
	REASON_3 varchar(10) NULL,
	REPAY_TYPE smallint NULL,
	TERM smallint NULL
)
CREATE TABLE APPL_TV(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	APPL_NO varchar(20) NOT NULL,
	CREATE_TIME datetime2(7) NULL,
	DECISION smallint NULL,
	TV_MEM_ANS_1 varchar(100) NULL,
	TV_MEM_ANS_2 varchar(100) NULL,
	TV_QUES_1 varchar(100) NULL,
	TV_QUES_2 varchar(100) NULL,
	[TYPE] smallint NULL)

CREATE TABLE A_SCORE(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	APPL_NO varchar(20) NOT NULL,
	CREATE_TIME datetime2(7) NULL,
	PBOC_BACK_TIME datetime2(7) NULL,
	PBOC_TIME datetime2(7) NULL,
	RATING varchar(10) NULL,
	RISK_REMIND varchar(50) NULL,
	SCORE float NULL
)

CREATE TABLE REPAY_PLAN(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	DUE_AMT float NULL,
	DUE_DATE datetime2(7) NULL,
	DUE_INTEREST float NULL,
	DUE_PRINCIPAL float NULL,
	OVERDUE_AMT float NULL,
	OVERDUE_DAY int NULL,
	OVERDUE_INTEREST float NULL,
	PAID_INTEREST float NULL,
	PAID_PRINCIPAL float NULL,
	REST_PRINCIPAL float NULL,
	TERM int NULL,
	TERM_NO int NULL,
	BID bigint NULL,
	MEMBER_ID bigint NULL)


CREATE TABLE PRECREDIT(
	ID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	BILL_ID int NULL,
	CC_ID int NULL,
	CREATE_TIME datetime2(7) NULL,
	CRL int NULL,
	ID_ID int NULL,
	MEMBER_ID bigint NULL)

CREATE TABLE BORROW(
	BID bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	AMT float NULL,
	APR float NULL,
	CUR_DELQ int NULL,
	INTEREST float NULL,
	MAX_DELQ int NULL,
	PAID_INTEREST float NULL,
	PAID_PRINAIPAL float NULL,
	PRINAIPAL float NULL,
	STARTDATE datetime2(7) NULL,
	STATUS int NULL,
	TERM int NULL,
	APP_NO varchar(20) NOT NULL,
	MEMBER_ID bigint NOT NULL)

------------------新增数据表------------------
CREATE TABLE TEMP_CRL(
ID BIGINT,
SEX_CRL FLOAT,
AGE_CRL FLOAT,
EDUCATION_CRL FLOAT,
INDUSTRY_CRL FLOAT,
EMAIL_CRL FLOAT,
CITY_CRL FLOAT,
BANK_CRL FLOAT,
CARD_CRL FLOAT,
LIMIT_CRL FLOAT,
USE_CRL FLOAT,
BILL_CRL FLOAT,
BASE_VALUE BIGINT,
PRE_INDEX FLOAT,
PRE_CRL BIGINT )


CREATE TABLE WECHAT_MENU(
ID BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
MENU_KEY VARCHAR(20),
NAME VARCHAR(20),
URL VARCHAR(50)
)

CREATE TABLE WECHAT_MESSAGE_TEMPLATE(
ID BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
STATUSES VARCHAR(20),
MENU_ID BIGINT,
TEMPLATE VARCHAR(100)
)
