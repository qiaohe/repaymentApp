
/****** Object:  StoredProcedure [PRECREDIT_CRL_BILL]    Script Date: 04/18/2014 20:42:14 ******/
-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE PRECREDIT_CRL_BILL @ID BIGINT
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
UPDATE PRECREDIT
SET CRL=CASE WHEN CRL>50000 THEN '50000' ELSE CRL END
FROM PRECREDIT P JOIN TEMP_CRL T ON P.ID=T.ID
WHERE P.ID=@ID
-------------------最大额度5万元-------------------------------------------
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

GO



CREATE PROCEDURE PROC_BORROW_OLD @APPLNO VARCHAR(16)

AS
BEGIN

/*
UPDATE APPL
SET  EXISTING_FLAG=CASE WHEN LAST_APPL_NO!='' AND LAST_DECISION='D' THEN 1
                        WHEN LAST_APPL_NO!='' AND LAST_DECISION='A' THEN 2
                  ELSE 0
                  END , --新旧户标识
     REPAY_TYPE='0'--等额还款              
FROM  APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID 
			 JOIN LAST_CREDIT L ON A.MEMBER_ID=L.MEMBER_ID
WHERE A.STATUS='0'
AND APPL_NO=@APPLNO
*/
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
SUG_CRL,
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
        AMT,
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
AND MEMBER_ID NOT IN (SELECT MEMBER_ID FROM LAST_CREDIT)
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
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期	
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
--------------------------------------旧户正常还款自动过件---------------------------------        
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
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
---------------------------------正常还款旧户直接过件-----------------------------------
UPDATE APPL_TV
   SET TYPE='3',
       DECISION='2',
       CREATE_TIME=GETDATE()
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID
            JOIN APPL_TV T ON A.APPL_NO=T.APPL_NO 
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE  	   
		   A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
-------------------------旧户正常用卡不需要照会---------------------------------------------
UPDATE ACCOUNT 
SET  CRL_USED=CRL_USED+P.AMT,
	 CRL_AVL=CRL-(CRL_USED+P.AMT)
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID 
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
            JOIN APPROVAL P ON A.APPL_NO=P.APPL_NO
WHERE  A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
------------------------------------正常还款旧户借款账户更新--------------------------
UPDATE LAST_CREDIT 
SET LAST_APPL_NO=A.APPL_NO,
    LAST_DECISION=P.DECISION,
    LAST_PBOC_BACK_TIME=S.PBOC_BACK_TIME,
    LAST_RATING=S.RATING,
    LAST_REASON_1=P.REASON_1,
    LAST_REASON_2=P.REASON_1,
    LAST_REASON_3=P.REASON_1,
    LAST_SCORE=S.SCORE
FROM LAST_CREDIT L JOIN APPL A ON L.MEMBER_ID=A.MEMBER_ID
                   JOIN APPROVAL P ON A.APPL_NO=P.APPL_NO
                   JOIN A_SCORE S ON A.APPL_NO=S.APPL_NO
                   JOIN MEMBER M ON A.MEMBER_ID=M.ID 
                   JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE  A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
------------------------------------前次处理结果更新-----------------------------------
UPDATE APPL
SET  STATUS='7' 
FROM APPL A JOIN MEMBER M ON A.MEMBER_ID=M.ID 
            JOIN ACCOUNT C ON A.MEMBER_ID=C.MEMBER_ID
WHERE  A.EXISTING_FLAG='2'
	   AND M.BLOCK_CODE IN ('','B','C')--正常还款和一次逾期
	   AND A.AMT<=CRL_AVL 
       AND A.STATUS='0'
       AND A.APPL_NO=@APPLNO
------------------------------------正常还款旧户借款处理完成状态更新------------------------


END

GO

-- =============================================
-- Author:		<Author,,Name>
-- Create date: <Create Date,,>
-- Description:	<Description,,>
-- =============================================
CREATE PROCEDURE ADDRESS @ID BIGINT

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
