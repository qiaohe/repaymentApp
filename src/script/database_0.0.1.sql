CREATE TABLE VALUE_MOBILE_AREA(
	ID int PRIMARY KEY NOT NULL,
	AREA_CODE varchar(10) NULL,
	CITY varchar(20) NULL,
	CREATE_TIME datetime2(7) NULL,
	N1_7 bigint NULL,
	PROVINCE varchar(20) NULL,
	TYPE varchar(10) NULL,
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
	scale float NULL,
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
	PRE_RATING int NULL,
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
	id bigint IDENTITY(1,1) PRIMARY KEY NOT NULL,
	CREATE_TIME date NULL,
	CRL_INDEX float NULL,
	STATUS int NULL,
	TYPE varchar(10) NULL,
	VALUE varchar(50) NULL,
	NAME varchar(50) NULL)

CREATE TABLE ID_SEQUENCE(
	NAME varchar(20) PRIMARY KEY NOT NULL,
	NEXT_VALUE bigint NULL,)

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
	TYPE smallint NULL)

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


CREATE TABLE TV_QUESTION_LIST(
ID BIGINT IDENTITY(1,1) PRIMARY KEY NOT NULL,
CITY VARCHAR(20),
FLAG INT,
ADDRESS VARCHAR(100)
)



/****** Object:  StoredProcedure [dbo].[PRECREDIT_CRL_BILL]    Script Date: 04/18/2014 20:42:14 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[PRECREDIT_CRL_BILL] @ID BIGINT
AS
BEGIN

INSERT INTO TEMP_CRL
SELECT
P.ID,
CASE WHEN SEX=0 THEN 1
     WHEN SEX=1 THEN 1.1
ELSE 1
END ,--性别
CASE WHEN DATEDIFF(YEAR,BIRTHDAY,GETDATE())BETWEEN 18 AND 20 THEN 0.6
	 WHEN DATEDIFF(YEAR,BIRTHDAY,GETDATE())BETWEEN 21 AND 25 THEN 0.7
	 WHEN DATEDIFF(YEAR,BIRTHDAY,GETDATE())BETWEEN 26 AND 28 THEN 0.8
	 WHEN DATEDIFF(YEAR,BIRTHDAY,GETDATE())BETWEEN 29 AND 35 THEN 1
	 WHEN DATEDIFF(YEAR,BIRTHDAY,GETDATE())BETWEEN 36 AND 55 THEN 1.2
ELSE 0.3
END,--年龄
'0.6',--学位
'0.7',--职业
'0.8',--EMAIL
'0.8',--CITY
'0.8',--BANK
'0.8',--卡类别
'1',--信用卡额度
'1',--信用卡应还款
'1',--账单日期
'10000',
'', --PRESCORE
''  --测试额度
FROM PRECREDIT P JOIN ID_CARD I ON P.ID_ID=I.ID
WHERE P.ID=@ID
---------------------性别、年龄参数-----------------------------------
UPDATE TEMP_CRL
SET EDUCATION_CRL=D.CRL_INDEX
FROM
PRECREDIT P JOIN MEMBER M ON P.MEMBER_ID=M.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN DICTIONARY D ON M.EDUCATION=D.VALUE
WHERE D.TYPE='EDUCATION'
AND P.ID=@ID
---------------------学位参数--------------------------------------
UPDATE TEMP_CRL
SET INDUSTRY_CRL=D.CRL_INDEX
FROM
PRECREDIT P JOIN MEMBER M ON P.MEMBER_ID=M.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN DICTIONARY D ON M.INDUSTRY=D.VALUE
WHERE D.TYPE='INDUSTRY'
AND P.ID=@ID
---------------------职业参数---------------------------------------
UPDATE TEMP_CRL
SET EMAIL_CRL=D.CRL_INDEX
FROM
PRECREDIT P JOIN MEMBER M ON P.MEMBER_ID=M.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN DICTIONARY D ON RTRIM(LTRIM(SUBSTRING(EMAIL, CHARINDEX('@',EMAIL)+1, 20)))=D.NAME
WHERE D.TYPE='EMAIL'
AND P.ID=@ID
---------------------EMAIL参数--------------------------------------
UPDATE TEMP_CRL
SET CITY_CRL=D.CRL_INDEX
FROM
PRECREDIT P JOIN ID_CARD I ON P.ID_ID=I.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN DICTIONARY D ON I.CITY=D.NAME
WHERE D.TYPE='CITY'
AND P.ID=@ID
---------------------城市参数---------------------------------------
UPDATE TEMP_CRL
SET BANK_CRL=D.CRL_INDEX
FROM
PRECREDIT P JOIN CREDITCARD C ON P.CC_ID=C.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN DICTIONARY D ON C.BANK=D.VALUE
WHERE D.TYPE='BANK'
AND P.ID=@ID
---------------------银行参数---------------------------------------
UPDATE TEMP_CRL
SET CARD_CRL=CASE WHEN CARD_LEVEL='普卡' THEN 0.8
                  WHEN CARD_LEVEL='金卡' THEN 1
                  WHEN CARD_LEVEL='白金卡' THEN 1.4
             ELSE 0.8
             END
FROM
PRECREDIT P JOIN CREDITCARD C ON P.CC_ID=C.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
            JOIN VALUE_BIN V ON (LEFT(C.CARD_NO,6)=V.BIN_NO OR LEFT(C.CARD_NO,5)=V.BIN_NO OR LEFT(C.CARD_NO,7)=V.BIN_NO OR LEFT(C.CARD_NO,8)=V.BIN_NO)
WHERE
P.ID=@ID
----------------------卡片类型参数------------------------------------
UPDATE TEMP_CRL
SET LIMIT_CRL=CASE WHEN B.CRL <=10000 THEN 1
                   WHEN B.CRL BETWEEN 10001 AND 20000 THEN 1.2
                   WHEN B.CRL BETWEEN 20001 AND 40000 THEN 1.4
                   WHEN B.CRL BETWEEN 40001 AND 50000 THEN 1.5
                   WHEN B.CRL >50000 THEN 1.8
              ELSE 1
              END
FROM
PRECREDIT P JOIN BILL B ON P.BILL_ID=B.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
WHERE
P.ID=@ID
---------------------信用卡额度区间参数-----------------------------------
UPDATE TEMP_CRL
SET USE_CRL=CASE WHEN  (AMT_RMB+AMT_USD*(SELECT VALUE FROM DICTIONARY WHERE DATEDIFF(MONTH,CREATE_TIME,GETDATE())=0 AND TYPE='EXCHANGE'))<=3000 THEN 1
				 WHEN  (AMT_RMB+AMT_USD*(SELECT VALUE FROM DICTIONARY WHERE DATEDIFF(MONTH,CREATE_TIME,GETDATE())=0 AND TYPE='EXCHANGE'))BETWEEN 3001 AND 10000 THEN 1.1
				 WHEN  (AMT_RMB+AMT_USD*(SELECT VALUE FROM DICTIONARY WHERE DATEDIFF(MONTH,CREATE_TIME,GETDATE())=0 AND TYPE='EXCHANGE'))BETWEEN 10001 AND 20000 THEN 1.2
			     WHEN  (AMT_RMB+AMT_USD*(SELECT VALUE FROM DICTIONARY WHERE DATEDIFF(MONTH,CREATE_TIME,GETDATE())=0 AND TYPE='EXCHANGE'))BETWEEN 20001 AND 50000 THEN 1.3
			     WHEN  (AMT_RMB+AMT_USD*(SELECT VALUE FROM DICTIONARY WHERE DATEDIFF(MONTH,CREATE_TIME,GETDATE())=0 AND TYPE='EXCHANGE'))>50000 THEN 1.4
	        ELSE 1
	        END
FROM
PRECREDIT P JOIN BILL B ON P.BILL_ID=B.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
WHERE
P.ID=@ID
--------------------使用额度区间参数-------------------------------------
UPDATE TEMP_CRL
SET BILL_CRL=CASE WHEN PAY_DUE>GETDATE() THEN 1.3
	              WHEN PAY_DUE<=GETDATE() AND DATEDIFF(DAY,PAY_DUE,GETDATE())<=30 THEN 1.1
	              WHEN PAY_DUE<=GETDATE() AND DATEDIFF(DAY,PAY_DUE,GETDATE()) >31 THEN 1
	         ELSE 1
	         END
FROM
PRECREDIT P JOIN BILL B ON P.BILL_ID=B.ID
			JOIN TEMP_CRL T ON P.ID=T.ID
WHERE
P.ID=@ID
-------------------账单期限参数-------------------------------------------
UPDATE TEMP_CRL
SET PRE_INDEX=SEX_CRL*AGE_CRL*EDUCATION_CRL*INDUSTRY_CRL*EMAIL_CRL*CITY_CRL*BANK_CRL*CARD_CRL*LIMIT_CRL*USE_CRL*BILL_CRL
FROM TEMP_CRL T
WHERE
T.ID=@ID
------------------计算预评分系数------------------------------------------
UPDATE TEMP_CRL
SET PRE_CRL=PRE_INDEX*BASE_VALUE
FROM TEMP_CRL T
WHERE
T.ID=@ID
-------------------计算测试额度--------------------------------------------
UPDATE PRECREDIT
SET CRL=(PRE_CRL/100)*100
FROM PRECREDIT P JOIN TEMP_CRL T ON P.ID=T.ID
WHERE P.ID=@ID

-------------------回写测试额度表------------------------------------------
UPDATE MEMBER
SET PRE_CRL=P.CRL,
    PRE_SCORE=PRE_INDEX,
    PRE_RATING=CASE WHEN PRE_INDEX>1.2 THEN 'A'
                    WHEN PRE_INDEX BETWEEN 1 AND 1.2 THEN 'B'
                    WHEN PRE_INDEX BETWEEN 0.5 AND 1 THEN 'C'
                    WHEN PRE_INDEX<0.5 THEN 'D'
               ELSE 'D' END
FROM PRECREDIT P JOIN MEMBER M ON P.MEMBER_ID=M.ID
                 JOIN TEMP_CRL T ON P.ID=T.ID
WHERE
P.ID=@ID
-------------------测试额度写入账户当测试额度------------------------------

SELECT CRL FROM PRECREDIT
WHERE ID=@ID

END







/****** Object:  StoredProcedure [dbo].[PROC_BORROW_OLD]    Script Date: 04/18/2014 15:17:41 ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[PROC_BORROW_OLD] @APPLNO VARCHAR(16)

AS
BEGIN

/*UPDATE APPL
SET  EXISTING_FLAG=CASE WHEN LAST_APPL_NO!='' AND LAST_DECISION='D' THEN 1
                       WHEN LAST_APPL_NO!='' AND LAST_DECISION='A' THEN 2
                  ELSE 0
                  END , --新旧户标识
     REPAY_TYPE='0'--等额还款
FROM  APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
			 JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
WHERE A.STATUS='0'
AND APPL_NO=@APPLNO*/
--------------------------------新旧户打标------------------------------------------------
INSERT INTO A_SCORE
(APPL_NO,
CREATE_TIME,
PBOC_BACK_TIME,
PBOC_TIME,
RATING,
RISK_REMIND,
SCORE)
SELECT
       APPL_NO,
       GETDATE(),
       '',
       '',
	   '',
	   '',
	   ''
FROM APPL
WHERE STATUS='0'
AND APPL_NO=@APPLNO
-------------------------------插入人行评级表-------------------------------------------
INSERT INTO APPROVAL
(APPL_NO,
AMT,
APR,
CLASS,
CREATE_TIME,
CREDITOR,
DECISION,
OPINION,
PROFILE,
REASON_1,
REASON_2,
REASON_3,
REPAY_TYPE,
TERM)
SELECT
        APPL_NO,
        '',
        '',
        'A',
        GETDATE(),
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        '0',
        TERM
FROM APPL
WHERE STATUS='0'
AND APPL_NO=@APPLNO
----------------------------------------------------------------------------------------
INSERT INTO APPL_TV
(APPL_NO,
CREATE_TIME,
DECISION,
TV_MEM_ANS_1,
TV_MEM_ANS_2,
TV_QUES_1,
TV_QUES_2,
TYPE)
SELECT
       APPL_NO,
       GETDATE(),
        '',
        '',
        '',
        '',
        '',
        '0'
FROM APPL
WHERE STATUS='0'
AND APPL_NO=@APPLNO
-----------------------------------------------------------------------------------------
INSERT INTO LAST_CREDIT
(CREATE_TIME,
LAST_DECISION,
LAST_PBOC_BACK_TIME,
LAST_RATING,
LAST_REASON_1,
LAST_REASON_2,
LAST_REASON_3,
LAST_SCORE,
LAST_APPL_NO,
MEMBER_ID)
SELECT
        GETDATE(),
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        '',
        MEMBER_ID
FROM APPL
WHERE STATUS='0'
AND MEMBER_ID NOT IN (SELECT ID FROM MEMBER)
AND APPL_NO=@APPLNO


UPDATE A_SCORE
SET
       PBOC_BACK_TIME=LAST_PBOC_BACK_TIME,
       SCORE=LAST_SCORE,
	   RATING=LAST_RATING,
	   RISK_REMIND='00000000000000000000000000000000000000000000000000',--旧户没有风险提示
	   PBOC_TIME=GETDATE()
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('A','B')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
--------------------------------------旧户正常还款自动过件---------------------------------
UPDATE A_SCORE
SET
       PBOC_BACK_TIME=LAST_PBOC_BACK_TIME,
       SCORE=LAST_SCORE,
	   RATING=LAST_RATING,
	   RISK_REMIND='00000000000000000000000000000000000000000000000000',--旧户没有风险提示
	   PBOC_TIME=GETDATE()
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='1'
	   AND M.BLOCK_CODE IN ('','A','B')--正常还款和一次逾期
	   AND (L.LAST_REASON_1 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_2 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_3 IN ('D500','D501','D600','D601','D602'))
	   AND DATEDIFF(DAY,L.LAST_PBOC_BACK_TIME,GETDATE())<=180
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
--------------------------前次信用卡还款失败和未确认或者拒绝客户信用评分----------------------
UPDATE  APPROVAL
   SET DECISION='A',
       AMT=A.AMT,
       APR=R.APR,
       TERM=A.TERM,
       REPAY_TYPE=A.REPAY_TYPE,
       PROFILE='旧户审核',
       REASON_1='A001',
       OPINION='旧户审核自动过件',
       CREDITOR='C00000',
       CLASS='S',
	   CREATE_TIME=GETDATE()
FROM APPL A JOIN APPROVAL P ON A.APPL_NO=P.APPL_NO
            JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN PRICING R ON RATING=R.RATING AND A.TERM=R.TERM
            JOIN A_SCORE S ON A.APPL_NO=S.APPL_NO
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('A','B')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
---------------------------------正常还款旧户直接过件-----------------------------------
UPDATE  APPROVAL
   SET DECISION='A',
       AMT=A.AMT,
       APR=R.APR,
       TERM=A.TERM,
       REPAY_TYPE=A.REPAY_TYPE,
       PROFILE='旧户审核',
       REASON_1='A001',
       OPINION='前次信用卡还款失败、未确认、拒绝接受客户自动过件',
       CREDITOR='C00000',
       CLASS='S',
	   CREATE_TIME=GETDATE()
FROM APPL A JOIN APPROVAL P ON A.APPL_NO=P.APPL_NO
            JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
            JOIN PRICING R ON RATING=R.RATING AND A.TERM=R.TERM
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='1'
	   AND M.BLOCK_CODE IN ('','A','B')--正常还款和一次逾期
	   AND (L.LAST_REASON_1 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_2 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_3 IN ('D500','D501','D600','D601','D602'))
	   AND DATEDIFF(DAY,L.LAST_PBOC_BACK_TIME,GETDATE())<=180
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
-------------------------前次信用卡还款失败和未确认或者拒绝客户直接过件---------------------
UPDATE APPL_TV
   SET TYPE='3',
       DECISION='2',
       CREATE_TIME=GETDATE()
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN APPL_TV T ON A.APPL_NO=T.APPL_NO
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
		   A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('A','B')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
-------------------------旧户正常用卡不需要照会---------------------------------------------
UPDATE APPL_TV
   SET TYPE='3',
       DECISION='2',
       CREATE_TIME=GETDATE()
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN APPL_TV T ON A.APPL_NO=T.APPL_NO
            JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='1'
	   AND M.BLOCK_CODE IN ('','A','B')--正常还款和一次逾期
	   AND (L.LAST_REASON_1 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_2 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_3 IN ('D500','D501','D600','D601','D602'))
	   AND DATEDIFF(DAY,L.LAST_PBOC_BACK_TIME,GETDATE())<=180
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
-------------------------前次信用卡还款失败和未确认或者拒绝客户不需要照会--------------------
UPDATE APPL
SET  STATUS='5'
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE  A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('A','B')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
------------------------------------正常还款旧户借款处理完成状态更新--------------------------
UPDATE APPL
SET  STATUS='5'
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
			JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE
	   A.EXISTING_FLAG='1'
	   AND M.BLOCK_CODE IN ('','A','B')--正常还款和一次逾期
	   AND (L.LAST_REASON_1 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_2 IN ('D500','D501','D600','D601','D602') OR L.LAST_REASON_3 IN ('D500','D501','D600','D601','D602'))
	   AND DATEDIFF(DAY,L.LAST_PBOC_BACK_TIME,GETDATE())<=180
	   AND A.AMT<=CRL_AVL
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
-----------------------------前次信用卡还款失败和未确认或者拒绝客户状态更新--------------------

END



-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE [dbo].[ADDRESS] @ID BIGINT

AS
BEGIN

UPDATE ID_CARD
SET PROVINCE=CASE WHEN SUBSTRING(ADDRESS,1,3) IN ('北京市','上海市','天津市','重庆市') THEN  SUBSTRING(ADDRESS,1,2)
                  WHEN SUBSTRING(ADDRESS,1,2) IN ('西藏','新疆','宁夏','广西') THEN SUBSTRING(ADDRESS,1,2)
                  WHEN SUBSTRING(ADDRESS,1,3) ='内蒙古' THEN SUBSTRING(ADDRESS,1,3)
                  WHEN CHARINDEX('省',ADDRESS)!=0  THEN LEFT(ADDRESS,PATINDEX('%省%',ADDRESS)-1)
			 ELSE ''
			 END,
	    CITY=CASE WHEN SUBSTRING(ADDRESS,1,3) IN ('北京市','上海市','天津市','重庆市') THEN  SUBSTRING(ADDRESS,1,2)
                  WHEN CHARINDEX('省',ADDRESS)!=0 THEN SUBSTRING(ADDRESS,PATINDEX('%省%',ADDRESS)+1 ,PATINDEX('%市%',ADDRESS)-1-CHARINDEX('省',ADDRESS))
			      WHEN CHARINDEX('自治区',ADDRESS)!=0 THEN SUBSTRING(ADDRESS,PATINDEX('%自治区%',ADDRESS)+3 ,PATINDEX('%市%',ADDRESS)-3-CHARINDEX('自治区',ADDRESS))
		     ELSE ''
		     END
FROM  ID_CARD
WHERE CHARINDEX('市',ADDRESS)!=0
AND ID=@ID
END


